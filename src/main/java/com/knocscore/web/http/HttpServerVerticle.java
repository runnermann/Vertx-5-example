package com.knocscore.web.http;

import com.knocscore.access.ModelError;
import com.knocscore.protect.Page;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.providers.GithubAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

// LOGGER
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the Main Call for the server. Everything starts here.
 */
public class HttpServerVerticle extends VerticleBase {
    private static final int PORT = 80;
    private static final String HOST = "http://localhost:";
    private static final String LOCAL_HOST = HOST + Integer.toString(PORT);
    private static final String BUILDER_CALLBACK = "/BLDR1609"; // The endpoint for profile builder
    // knocscore classes
    private final HttpUtility httpUtilty = new HttpUtility();
    // libraries
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtility.class);
    private static FreeMarkerTemplateEngine templateEngine;




    private void init() {
        httpUtilty.init();

    }


    @Override
    public Future<?> start() {
        this.init();

        templateEngine = FreeMarkerTemplateEngine.create(vertx);
        HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create(vertx);

        final Router router = Router.router(vertx);
        // caching stores resources in a cache
        router.get("/app/*").handler(StaticHandler.create().setCachingEnabled(false));
        // INDEX
        //router.get("/").handler(this::landingPage);


        // -------------------------------------------- --------------------------------------------//
        //                                       OAuth2 SECURE                                      //
        // -------------------------------------------- --------------------------------------------//
        final ModelError mo = ModelError.getInstance();
        // GITHUB
        final String CLIENT_ID = mo.getEpirtsErrors(12);
        final String CLIENT_SECRET = mo.getEpirtsErrors(13);


        // Persist users logins so they don't have to login constantly.
        router.route()
                .handler(SessionHandler
                        .create(LocalSessionStore.create(vertx)));  // (1)


        // Create or log-in account
        router.get("/")                               // (4)
                .handler(this::landingPage);
                            //.onFailure(ctx::fail);

        // The Auth
        OAuth2Auth githubAuthProvider = GithubAuth.create(vertx, CLIENT_ID, CLIENT_SECRET);
        router.get("/protected")
                .handler(
                        OAuth2AuthHandler.create(vertx, githubAuthProvider,  "http://localhost:80/callback")
                                .setupCallback(router.route("/callback")) //BLDR1609
                                .withScope("user:email"))
                .handler(ctx -> {
                    WebClient.create(ctx.vertx())
                            .getAbs("https://api.github.com/user/emails")
                            .authentication(new TokenCredentials(ctx.user().<String>get("access_token")))
                            .as(BodyCodec.jsonArray())
                            .send()
                            .onFailure(err -> {
                                System.out.println("Error attempting to get users email from Github API: " + err.getMessage());
                                err.printStackTrace();
                                ctx.session().destroy();
                                ctx.fail(err);
                            })
                            .onSuccess(res -> {
                                JsonObject json = new JsonObject();
                                json.put("private_emails", res.body());
                                // we pass the client info to the template
                                ctx.put("userInfo", json);
                                // and now delegate to the engine to render it.
                                engine.render(ctx.data(), "views/protected.hbs")
                                        .onSuccess(buffer -> {
                                            ctx.response()
                                                    .putHeader("Content-Type", "text/html")
                                                    .end(buffer);
                                        })
                                        .onFailure(ctx::fail);
                            });
                });

        // -------------------------------------------- --------------------------------------------//
        //                                      END OAuth2 SECURE                                   //
        // -------------------------------------------- --------------------------------------------//


        // ------------------------------------- --------------------------------- //
        //                                 STATS PAGE                              //
        // ------------------------------------- --------------------------------- //

        // QR-Code views stats pane for
        // User Profile for Recruiters,
        // User Stats, 1st page
        router.get("/Q52/UPG022/:user_hash").blockingHandler(this::qrUserStats);
        // User supporting stats
        router.post("/Q55/ATTATE001").handler(this::statsDetailsAthletics);
        router.post("/Q55/ASTATE011").handler(this::statsDetailsAchievements);
        router.post("/Q55/USTATE022").handler(this::statsDetailsEntity);
        router.post("/Q55/KSTATE033").handler(this::statsDetailsKnowledge);
        router.post("/Q55/PSTATE044").handler(this::statsDetailsPerson);
        router.post("/Q55/PRTATE055").handler(this::statsDetailsProjects);
        router.post("/Q55/POTATE066").handler(this::statsDetailsPositions);


        return vertx.createHttpServer()
                .requestHandler(router)
                .listen(Integer.getInteger("port", PORT))
                .onSuccess(server -> System.out.println("HTTP server started on port: " + server.actualPort()));

    }


    // ****************************************************************************** //
    // ****************************************************************************** //
    //
    //                ************ END OF START METHOD **************
    //
    // ****************************************************************************** //
    // ****************************************************************************** //




    private void landingPage(RoutingContext context) {
        if (httpUtilty.logCall(context)) {
            httpUtilty.deceptiveRequestHandler(context);
        } else {
            Page page = new Page();
            page.commonHandlerWPolicy(context, "/webroot/templates/index.ftl", templateEngine);
        }
    }



    private void qrUserStats(RoutingContext context) {
        // Get the user's hash
        String hash = context.request().getParam("user_hash");
        hash = hash.substring(1);

        userStats(context, hash);
    }

    private int[] freeRecall =  {279, 0,    0, 34, 48, 98, 642,827,724,600, 0,23, 87, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] nonFree =     {470, 122,  0, 28,  0, 72, 654,522,810,522, 0,48, 41, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] fakeCitations = {0, 0,    0,  0,  0,  0,   0,  0,  0,  7, 0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0};
    private int[] fakePatents   = {0, 0,    0,  0,  0,  0,   0,  0,  0,  4, 0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0};
    private int[] fakePercentile = {10,21, 16,  5, 29, 28,  14,  1,  6,  8,17, 8, 25, 3,25,16,22,21,17,21,25,34 };
    private String fakeEmployer = "KNOC Inc.";
    private String fakePosition = "CEO and Founder";
    private String fakeReqByIndustry = "higher";
    private int fakePercent = 90;
    private String fakeIndustry = "Software Engineering";
    private String fakeEquivWorkAtEntity ="Apple Google and Facebook";
    private String fakeOtherEd = "Various institutions including Stanford, Rice University, and UofI at Urbana Champaign.";
    private String fakeOtherMajor = "Software Engineering";

    /**
     * From QR-Code or link, Display the users stats in their personal webpage.
     * @param context
     */
    private void userStats(RoutingContext context, String hash) {

        if(hash != null && hash.length() == 32) {

            LOGGER.debug("user_hash {}", hash);

            // For image and meta tags
            // context.put("photo_link", hash + "a.png");
            // 'a' avatar

            // NOTE: As of the time this query was developed, we could not find a great way to fetch this data using Vert-X.It repeats
            // The student data in every tuple, even though it is only needed once.
            // Transactions or mutliple queries
            // will not work. Multiple queries would be best, however with async one must rely on the other as in a andThen,
            // however, sadly, multiple fetches also would not work bc of Async and order. 2022-12-27 L.S. Also note,
            // the summation/grouping for the stats is slower when grouped on less data.
            String finalHash = hash;
            AtomicLong atomicIdx = new AtomicLong(-1);
            AtomicLong lastHrs = new AtomicLong();
            AtomicReference<String> primeSubj = new AtomicReference<>("");
            String finalHash1 = hash;
            dbService.rxFetchUserStats(hash)
                    .flatMapPublisher(Flowable::fromIterable)
                    .map(obj -> {
                        // User data
                        if(obj.getString("first_name") != null) {
                            final long create = obj.getLong("create_time");
                            final long review = obj.getLong("review_test_time");
                            final long discussMinutes = null == obj.getLong("req_duration") ? 0 : obj.getLong("req_duration");
                            final long discuss = TimeUnit.MINUTES.toMillis(discussMinutes);
                            // Not fully implemented yet. Table, query and webpage view exists. Placeholder so we build out the plumbing.
                            final long practicalMinutes = null == obj.getLong("pract_duration") ? 0 : obj.getLong("pract_duration");
                            final long practical = TimeUnit.MINUTES.toMillis(practicalMinutes);
                            // Everyone is in milliseconds
                            final long total = create + review + discuss + practical;
                            final long totalHrs = TimeUnit.MILLISECONDS.toHours(total);
                            if(totalHrs > 2) {
                                if(lastHrs.get() < totalHrs) {
                                    primeSubj.set(obj.getString("deck_subj"));
                                }
                                lastHrs.set(totalHrs);

                                final String name = UserAlphabet.decrypt(obj.getString("first_name")) + " " + UserAlphabet.decrypt(obj.getString("last_name"));
                                final String linkName = name.strip().replace(" ", "-") +"-" + finalHash;
                                final String photoLink = hash + "-profile-img.webp";
                                final String endpoint = "/knoc-for/" + linkName;
                                // Use the common method to set data in the context.
                                setUserStatsInContext(context, obj, hash);
                                context.put("name", name)
                                        .put("photo_link", photoLink)
                                        .put("endpoint", endpoint)
                                        .put("prime_subj", primeSubj.get());

                                // KnoC Metrics data
                                final double percentCreate = (double) create / total;
                                final double percentReview = (double) review / total;
                                final double percentDiscuss = (double) discuss / total;
                                final double percentPractical = (double) practical / total;

                                final int study = (int) (percentCreate * 100);
                                final int create1 = (int) (percentReview * 100);
                                final int discuss1 = (int) (percentDiscuss * 100);
                                final int practical1 = (int) (percentPractical * 100);
                                final NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.US);
                                final double d = Integer.parseInt(obj.getString("amount")) * .01;
                                final String amt = d != 0 ? numFormat.format(d) : "0";
                                String duration = "";
                                if(0 != d) {
                                    // Calc Time of Amount
                                    String start = null == obj.getString("create_date") ? "" : obj.getString("create_date");
                                    //Instantiating the SimpleDateFormat class
                                    SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-dd-MM");
                                    //Parsing the given String to Date object
                                    Date date = dateformatter.parse(start);
                                    long startMilli = date.getTime();
                                    long endMilli = null != obj.getString("last_date") ? 0 : Long.parseLong(obj.getString("last_date"));
                                    long amtTimeMilli = endMilli - startMilli;
                                    duration = "";
                                    // End calc time
                                    if (amtTimeMilli < 2629800000l) { // 1 month
                                        duration = "1 mon period";
                                    } else if (amtTimeMilli < 31556926000l) { // < 1 year
                                        long dur = amtTimeMilli / 2629800000l;
                                        duration = dur + " mos period";
                                    } else {
                                        double rem = amtTimeMilli % 31556926000l;
                                        double month = rem / 2629800000l;
                                        long year = amtTimeMilli / 31556926000l;
                                        if (month > 5) {
                                            duration = year + 1l + " yr period";
                                        } else {
                                            duration = year + " yr period";
                                        }
                                    }
                                } else {
                                    duration = "0";
                                }

                                String subj = null == obj.getString("deck_subj") ? "none" : obj.getString("deck_subj");
                                subj = subj.replace("_", " ");
                                final String subj_ctx = subj.strip().replace(" ", "_");
                                //if(totalHrs > 2) {
                                // loop data
                                // atomicIdx starts at -1. Cannot increment in an if statement when value is 0.
                                int idxVal = atomicIdx.incrementAndGet();
                                return new JsonObject()
                                        .put("subj_ctx", subj_ctx)
                                        .put("subj", subj)
                                        .put("amount", amt)
                                        .put("amountTime", duration) // this data is incorrect. Need to redo this query.
                                        .put("create", create1)
                                        .put("study", study)
                                        .put("discuss", discuss1)
                                        .put("practical", practical1)
                                        .put("totalHrs", totalHrs)
                                        .put("freeRecall", freeRecall[idxVal])
                                        .put("nonRPts", nonFree[idxVal])
                                        .put("totalPts", nonFree[idxVal] + freeRecall[idxVal])
                                        .put("citations", fakeCitations[idxVal] > 0 ? fakeCitations[idxVal] : null) // cat II
                                        .put("patents",   fakePatents[idxVal] >   0 ? fakePatents[idxVal] : null)
                                        .put("initialpercentile", fakePercentile[idxVal])
                                        .put("initentity", fakeEquivWorkAtEntity)
                                        .put("hash", finalHash1);

//                                                .put("votes", votes) // cat III
                                //}

                            }
                        }
                        return new JsonObject().put("totalHrs", 0);
                    })
                    .collect(JsonArray::new, JsonArray::add)
                    .flatMap(result -> {
                        context.put("qrStatsData", result);
                        if(result.isEmpty()) {
                            return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                        }
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats.ftl");
                    })
                    .subscribe(ar -> {
                        context.response().putHeader("Content-Type", "text/html");
                        context.response().end(ar);
                    }, t -> apiFailure(context, 404, "Ooops! Sorry, I did not find the individual, or they do not have stats to show yet."));
        }
        else {
            LOGGER.warn("hash is not 32 in length");
        }
    }

    /**
     * Returns the specific stats for the individual after the user has viewed their knocscore.
     * NOTE: Subject must not have spaces, replace space with _
     * @param context
     */
    private void statsDetailsEntity(RoutingContext context) {
        JsonObject jo = context.getBodyAsJson();
        String hash = jo.getString("hash");
        String subj = jo.getString("subj");
        context.put("subj", subj);
        context.put("details_type", "The Employer/Entity's <span id='user_name'></span> has worked for or with.");
        context.put("sort_by", "rating");

        dbService.rxFetchStatsEntity(hash, subj)
                .flatMapPublisher(Flowable::fromIterable)
                .map(obj -> {
                            // Table Data... :)
                            return getTableData(obj);
                        }
                )
                .collect(JsonArray::new, JsonArray::add)
                .flatMap(result -> {
                    context.put("table_data", result);
                    context.put("success", true);
                    if(result.isEmpty()) {
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                    }
                    return templateEngine.rxRender(context.data(), "webroot/templates/section/dynamo_table.ftl");
                })
                .subscribe(ar -> {
                    context.response().putHeader("Content-Type", "text/html");
                    context.response().end(ar);
                }, t -> apiFailure(context, 404, "I did not find the individual, or they do not have stats to show yet."));
    }

    /**
     * Related to graph. Get an individuals stats only page.
     * @param context
     */
    private void statsDetailsPerson(RoutingContext context) {
        JsonObject jo = context.getBodyAsJson();
        String hash = jo.getString("hash");
        String subj = jo.getString("subj");
        context.put("subj", subj);
        context.put("details_type", "People <span id='user_name'></span> has worked with.");
//        context.put("sort_by", "id");

        dbService.rxFetchStatsPeople(hash, subj)
                .flatMapPublisher(Flowable::fromIterable)
                .map(obj -> {
                            String name = UserAlphabet.decrypt(obj.getString("first_name")) + " " + UserAlphabet.decrypt(obj.getString("last_name"));
                            return new JsonObject()
                                    .put("name", name)
                                    .put("relation", obj.getString("relation"))
                                    .put("domains", obj.getString("domain_list"));
                        }
                )
                .collect(JsonArray::new, JsonArray::add)
                .flatMap(result -> {
                    context.put("table_data", result);
                    if(result.isEmpty()) {
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                    }
                    return templateEngine.rxRender(context.data(), "webroot/templates/section/dynamo_table.ftl");
                })
                .subscribe(ar -> {
                    context.response().putHeader("Content-Type", "text/html");
                    context.response().end(ar);
                }, t -> apiFailure(context, 404, "I did not find the individual, or they do not have stats to show yet."));
    }

    /**
     * Note we use the list_table instead of the dynomo_table. Here we expect only one
     * tuple and only a single column coming from the database. We use list_table because
     * the column.
     * @param context
     */
    private void statsDetailsAthletics(RoutingContext context) {
        JsonObject jo = context.getBodyAsJson();
        String hash = jo.getString("hash");
        String subj = jo.getString("subj");
        context.put("subj", subj);
        context.put("details_type", "The athletic activities that <span id='user_name'></span> has participated in.");
//        context.put("sort_by", "hours");

        dbService.rxFetchStatsAthletics(hash)
                .flatMapPublisher(Flowable::fromIterable)
                .map(obj -> {
                    String[] data = splitRowData(obj);
                    context.put("table_data", data);

                    return data;
                })
                .collect(JsonArray::new, JsonArray::add)
                .flatMap(result -> {
                    context.put("other_data", result);
                    if(result.isEmpty()) {
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                    }
                    return templateEngine.rxRender(context.data(), "webroot/templates/section/list_table.ftl");
                })
                .subscribe(ar -> {
                    context.response().putHeader("Content-Type", "text/html");
                    context.response().end(ar);
                }, t -> apiFailure(context, 404, "I did not find the individual, or they do not have stats to show yet."));
    }

    /**
     * Returns the Achievements stats for the individual after the user has viewed their knocscore.
     * NOTE: Subject must not have spaces, replace space with _.
     * @param context
     */
    private void statsDetailsAchievements(RoutingContext context) {
        JsonObject jo = context.getBodyAsJson();
        String hash = jo.getString("hash");
        String subj = jo.getString("subj");
        context.put("subj", subj);
        context.put("details_type", "The Achievements that <span id='user_name'></span> has earned.");
        context.put("sort_by", "value");

        dbService.rxFetchStatsAchievement(hash, subj)
                .flatMapPublisher(Flowable::fromIterable)
                .map(obj -> {
                            return getTableData(obj);
                        }
                )
                .collect(JsonArray::new, JsonArray::add)
                .flatMap(result -> {
                    context.put("table_data", result);
                    if(result.isEmpty()) {
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                    }
                    return templateEngine.rxRender(context.data(), "webroot/templates/section/dynamo_table.ftl");
                })
                .subscribe(ar -> {
                    context.response().putHeader("Content-Type", "text/html");
                    context.response().end(ar);
                }, t -> apiFailure(context, 404, "I did not find the individual, or they do not have stats to show yet."));
    }


    private void statsDetailsKnowledge(RoutingContext context) {
        JsonObject jo = context.getBodyAsJson();
        String hash = jo.getString("hash");
        String subj = jo.getString("subj");
        context.put("subj", subj);
        context.put("details_type", "The knowledge that <span id='user_name'></span> has interacted with.");

        dbService.rxFetchStatsKnowledge(hash, subj)
                .flatMapPublisher(Flowable::fromIterable)
                .map(obj -> {
                            // Table Data... :)
                            return getTableData(obj);
                        }
                )
                .collect(JsonArray::new, JsonArray::add)
                .flatMap(result -> {
                    context.put("table_data", result);
                    if(result.isEmpty()) {
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                    }
                    return templateEngine.rxRender(context.data(), "webroot/templates/section/dynamo_table.ftl");
                })
                .subscribe(ar -> {
                    context.response().putHeader("Content-Type", "text/html");
                    context.response().end(ar);
                }, t -> apiFailure(context, 404, "I did not find the individual, or they do not have stats to show yet."));
    }


    private void statsDetailsPositions(RoutingContext context) {
        JsonObject jo = context.getBodyAsJson();
        String hash = jo.getString("hash");
        String subj = jo.getString("subj");
        context.put("subj", subj);
        context.put("details_type", "The Positions of Responsibility that <span id='user_name'></span> has held.");
        context.put("sort_by", "value");


        dbService.rxFetchStatsPOS(hash, subj)
                .flatMapPublisher(Flowable::fromIterable)
                .map(obj -> {
                            // Table Data... :)
                            return getTableData(obj);
                        }
                )
                .collect(JsonArray::new, JsonArray::add)
                .flatMap(result -> {
                    context.put("table_data", result);
                    if(result.isEmpty()) {
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                    }
                    return templateEngine.rxRender(context.data(), "webroot/templates/section/dynamo_table.ftl");
                })
                .subscribe(ar -> {
                    context.response().putHeader("Content-Type", "text/html");
                    context.response().end(ar);
                }, t -> apiFailure(context, 404, "I did not find the individual, or they do not have stats to show yet."));
    }

    private void statsDetailsProjects(RoutingContext context) {
        JsonObject jo = context.getBodyAsJson();
        String hash = jo.getString("hash");
        String subj = jo.getString("subj");
        context.put("subj", subj);
        context.put("details_type", "The Projects that <span id='user_name'></span> has worked on.");
        context.put("sort_by", "value");

        dbService.rxFetchStatsProjects(hash, subj)
                .flatMapPublisher(Flowable::fromIterable)
                .map(obj -> {
                            // Table Data... :)
                            return getTableData(obj);
                        }
                )
                .collect(JsonArray::new, JsonArray::add)
                .flatMap(result -> {
                    context.put("table_data", result);
                    if(result.isEmpty()) {
                        return templateEngine.rxRender(context.data(), "webroot/templates/qr/qrstats-tutorial.ftl");
                    }
                    return templateEngine.rxRender(context.data(), "webroot/templates/section/dynamo_table.ftl");
                })
                .subscribe(ar -> {
                    context.response().putHeader("Content-Type", "text/html");
                    context.response().end(ar);
                }, t -> apiFailure(context, 404, "I did not find the individual, or they do not have stats to show yet."));
    }


    /**
     * Helper to get Table Data
     * @param obj // Original Database Obj.
     * @return table data.
     */
    private JsonObject getTableData(JsonObject obj) {
        JsonObject jObj = new JsonObject();

        Object[] names = obj.fieldNames().toArray();
        for(int i = 0; i < names.length; i++) {
            String name = names[i].toString();
            String str = obj.getString(name);
            jObj.put(name, str);
        }
        return jObj;
    }

    /**
     * When we know a tuple contains an array,
     * split the array. Expects there to be only
     * one tuple. As in getAthleticAchievements
     * where there is only a single tupple for the
     * user.
     * @param obj
     * @return
     */
    private String[] splitRowData(JsonObject obj) {
        Object[] names = obj.fieldNames().toArray();
        try {
            String name = names[0].toString();
            JsonArray arr = obj.getJsonArray(name);
            return httpUtilty.getArrayFromJsonArray(arr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}//-- END CLASS --
