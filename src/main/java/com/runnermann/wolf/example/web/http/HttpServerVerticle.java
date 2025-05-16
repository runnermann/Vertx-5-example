package com.runnermann.wolf.example.web.http;

import com.runnermann.wolf.example.web.access.SecretEncDec;
import com.runnermann.wolf.example.web.protect.Page;
import com.runnermann.wolf.example.utility.BusAddressMap;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.file.FileSystemOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.providers.LinkedInAuth;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

// LOGGER
import org.slf4j.LoggerFactory;


/**
 * This is the Main Call for the server. Everything starts here.
 */
public class HttpServerVerticle extends VerticleBase {

    // BUS ADDRESSES
    public static final String BUS_USERDB_QUEUE = "user.db";
    private static final int PORT = 80;
    //private EventBus eBus;// = vertx.eventBus();
    private final static ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HttpServerVerticle.class);
    private static FreeMarkerTemplateEngine templateEngine;



    /**
     * Main Verticle initialization. Other verticles will use configs from this if needed
     */
    private void init() {
        BusAddressMap.putAddress("database", BUS_USERDB_QUEUE);
    }



    @Override
    public Future<?> start() {
        this.init();

        //eBus = vertx.eventBus();
        templateEngine = FreeMarkerTemplateEngine.create(vertx);
        final Router router = Router.router(vertx);

        FileSystemOptions fso = new FileSystemOptions();
        System.setProperty("vertx.disableFileCaching", "true");
        fso.setFileCachingEnabled(false);
        // caching stores resources in a cache
        router.get("/app/*").handler(StaticHandler.create().setCachingEnabled(false));
        // INDEX
        router.get("/").handler(this::landingPage);
        // Persist users logins so they don't have to login constantly.
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));


        // -------------------------------------------- --------------------------------------------//
        //                                       OAuth2 SECURE                                      //
        // -------------------------------------------- --------------------------------------------//
        final SecretEncDec mo = SecretEncDec.getInstance();
        // GITHUB
        final String CLIENT_ID = mo.getEpirtsErrors(7);
        final String CLIENT_SECRET = mo.getEpirtsErrors(8);
        // The Auth
        final OAuth2Auth linkedinAuth = LinkedInAuth.create(vertx, CLIENT_ID, CLIENT_SECRET);
        // We first build the request.
        /*
         * Send the user to the Linkedin Auth Page. LinkedIn displays a sign in to the user.
         * When the user signs in, they then accept or deny the permissions (withScope(xxxx) for access to their
         * information. Expect, they may grant access to their email, but not their profile.
         */
        router.get("/protected")
                .handler(
                        OAuth2AuthHandler.create(vertx, linkedinAuth,  "http://localhost:80/callback")
                                .setupCallback(router.route("/callback")) //BLDR1609
                                .withScope("openid profile email")
                                .pkceVerifierLength(64))
                .handler(ctx -> {
                    // Linkedin Fails here. Github succeeds to here.
                    System.out.println("We are here, line 122 HttpServerVerticle");
                    System.out.println("Now request token");
                    WebClient.create(ctx.vertx())
                            .getAbs("https://api.linkedin.com/v2/userinfo")
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
                                System.out.println("Succeeded");
                                JsonObject json = new JsonObject();
                                json.put("private_emails", res.body());
                                // we pass the client info to the template
                                ctx.put("userInfo", json);
                                // and now delegate to the engine to render it.
                                templateEngine.render(ctx.data(), "views/protected.ftl")
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



        // -------------------------------------------- --------------------------------------------//
        //                                      GENERAL PAGES                                       //
        // -------------------------------------------- --------------------------------------------//
        PageHandler handler = new PageHandler();
        // --- Blog related ---
        // Gets a list of all blogs
        router.route("/blog-listing").handler(ctx -> handler.blogsAllPageHandler(ctx, templateEngine));
        // Gets a specific blog
        router.route("/articles/:id/:endpoint").handler(ctx -> handler.blogPageHandler(ctx, templateEngine));

        // Methods are stubs only
        router.get("/about").handler(ctx -> handler.aboutPageHandler(ctx, templateEngine));
        router.route("/our-backers").handler(ctx -> handler.ourBackersHandler(ctx, templateEngine));
        // Just a webpage
        router.route("/privacy").handler(context -> pageHandler(context, "webroot/templates/privacy.ftl"));
        router.route("/eula-agreement").handler(context -> pageHandler(context, "webroot/templates/eula.ftl"));



        return vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT)
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
        Page page = new Page();
        page.commonHandlerWPolicy(context, "/webroot/templates/index.ftl", templateEngine);
    }

    private void loginHandler(RoutingContext handle) {
        /** FUTURE USE **/
    }



    /**
     * For simple webpages requiring no database access. We pass the context so we know who to
     * return the page to, and the page name. We ensure that the safety policies are set, attempt to
     * prevent deceptive requests, and pass it to the templateEngine.
     * @param context
     * @param pageName
     */
    private void pageHandler(RoutingContext context, String pageName) {
        Page page = new Page();
        page.commonHandlerWPolicy(context, pageName, templateEngine);
    }



    /**
     * Biolerplate code to send messages across the eventBus. Upon return, we send the response
     * to the user.
     * @param address
     * @param jArray
     * @param context
     */
    private void eventBusDo(String address, JsonArray jArray, RoutingContext context) {
        // sending a message to the async listener WorkerVerticle
        vertx.eventBus().request(address, jArray)
                .onComplete(reply -> {
                    if(reply.succeeded()) {
                        JsonArray objAry;
                        if(reply.result().body() != null) {
                            objAry = (JsonArray) reply.result().body();
                        } else {
                            JsonArray jsonReturn = new JsonArray();
                            objAry = jsonReturn.add(new JsonObject().put("message", "Success"));
                        }
                        context.response().end(objAry.encode());
                    } else {
                        LOGGER.warn("ERROR: FROM eventBusDo. Reply failed.\n");
                        context.put("failed", "true");
                        context.response().end();
                    }
        });
    }

}//-- END CLASS --
