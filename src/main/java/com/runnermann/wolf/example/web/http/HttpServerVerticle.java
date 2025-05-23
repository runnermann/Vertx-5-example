package com.runnermann.wolf.example.web.http;

import com.runnermann.wolf.example.web.access.SecretEncDec;
import com.runnermann.wolf.example.web.protect.Page;
import com.runnermann.wolf.example.utility.BusAddressMap;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.file.FileSystemOptions;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2Options;
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

import java.util.ArrayList;
import java.util.List;


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
        // The OpenID Auth

        // Linkedin expects these differently than GitHub. Set these here
        final JsonObject extraParams = new JsonObject()
                .put("authentication_method", "client_secret_post")
                .put("client_id", CLIENT_ID)
                .put("client_secret", CLIENT_SECRET)
                .put("redirect_uri", "http://localhost:80/callback")
                .put("response_type", "code")
                .put("grant_type", "authorization_code")
                .put("scope","openid profile email");

        // Vert.x Auth "scopes" will fail, unless there is an array of scopes
        final List<String> scopes = new ArrayList<>();
        scopes.add("openid");
        scopes.add("email");
        scopes.add("profile");

        // Create HttpClientOptions
        // Not sure if this is needed, just followed Vert.x
        // guide.
        final HttpClientOptions options = new HttpClientOptions()
                .setDefaultPort(80)
                .setKeepAlive(true)
                .setConnectTimeout(5000)
                .setIdleTimeout(10000)
                .setSsl(false);

        final OAuth2Auth linkedinAuth = OAuth2Auth.create(vertx, new OAuth2Options()
                .setExtraParameters(extraParams) // we set the params here. Nightmare to figure this out!!!
                .setHttpClientOptions(options)
                .setClientId(CLIENT_ID)
                .setSite("https://www.linkedin.com")
                .setTokenPath("/oauth/v2/accessToken")
                .setAuthorizationPath("/oauth/v2/authorization")
                .setUserInfoPath("/people/~"));


        // We first build the request.
        /*
         * Send the user to the Linkedin Page. LinkedIn displays a sign in to the user.
         * When the user signs in, they then accept or deny the requests (withScope(xxxx) for access to their
         * information.They may grant access to their email, but not their profile.
         * Be sure to share this redirect_url with Linkedin: http://localhost:80/callback
         * Original: https://www.linkedin.com/developers/tools/oauth/redirect
         */
        router.get("/protected")
                .handler(OAuth2AuthHandler.create(vertx, linkedinAuth, "http://localhost:80/callback")
                        .setupCallback(router.route("/callback"))
                        .withScopes(scopes))
                // Confusing but the handler abstracts all of the steps needed to return the needed access_token.
                // We can then use it next.
                .handler(ctx -> {
                    // If you don't use these, remove them
                    // very helpful with debugging
                    final User user = ctx.user();
                    final JsonObject tknMap = user.principal();

                    WebClient.create(ctx.vertx())
                            .getAbs("https://api.linkedin.com/v2/userinfo")
                            //.addQueryParam("access_token", tknMap.getString("access_token"))
                            .authentication(new TokenCredentials(ctx.user().<String>get("access_token")))
                            .as(BodyCodec.jsonObject())
                            .send()
                            .onFailure(err -> {
                                System.err.println("Error attempting to get profile from Linkedin API: " + err.getMessage());
                                err.printStackTrace();
                                ctx.session().destroy();
                                ctx.fail(err);
                            })
                            .onSuccess(res -> {
                                JsonObject jObj = res.bodyAsJsonObject();
                                // This should succeed at retrieving the users profile information.
                                LOGGER.error(Json.encodePrettily(jObj));
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
