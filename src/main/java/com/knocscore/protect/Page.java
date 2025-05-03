package com.knocscore.protect;

import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;



public final class Page {

    /**
     * Complex and long string that requires constant updating.
     */
    private static String str = "default-src 'none'; " +
            "connect-src 'self' " +
            "https://github.com/login https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ https://www.google-analytics.com https://github.com/ " +
            "'sha256-47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=' 'sha256-1PxuDsPyGK6n+LZsMv0gG4lMX3i3XigG6h0CzPIjwrE=' 'sha256-alQkhzRik30p4D42M4x52HUwzK1/HLrcDh9ydLkkoOI='; " +
            "script-src 'strict-dynamic' 'sha256-QUtZQ6HW0aqUTy3GM5XLPjvV6yhaJke0Kgg7aYT9xyQ=' 'sha256-24PRI8Sm7v2Nuw7qyPPnYiyJRAUKnw1Fm5f6+MopORo=' 'sha256-uH4jZwaEaClhXtK6dcejiHuUAiZa6BbE4v5YCwZju+A=' " +
            "'sha256-uH4jZwaEaClhXtK6dcejiHuUAiZa6BbE4v5YCwZju+A=' 'self' https://js.stripe.com/v3/ ; " +
            "script-src-elem https://unpkg.com/typewriter-effect@latest/dist/core.js https://www.googletagmanager.com https://connect.facebook.net/en_US/fbevents.js https://js.stripe.com/v3/buy-button.js 'sha256-jLOKe9q0JpFAMrIuG+RpcSAgDRlkGCsWn4n1raCrsi0=' 'sha256-24PRI8Sm7v2Nuw7qyPPnYiyJRAUKnw1Fm5f6+MopORo=' 'sha256-QUtZQ6HW0aqUTy3GM5XLPjvV6yhaJke0Kgg7aYT9xyQ=' 'sha256-tkf9PAS6+4xbRoVdMYB0G/VhH0KqriqIYbS8kj4Q7Rg=' 'sha256-hbplbbfEd+Pp7ELCAkiBV+vs1E1niUQx+Rs1os3qOh8=' 'sha256-qPUzfiXqmNULT3koLK8N0u2Y2Lp9xBfnyoUW4623kho=' 'sha256-BbaqkTjoEOpYXMZEAXRQ90/aBM6kGU8V0pGm4PCUQAg=' 'sha256-fdakAW8HxZSewIcWkpWP4npHdEQy6+AiZ2yLoJawk/0=' 'self'; " +
            "frame-src https://js.stripe.com/v3/ ; " +
            "img-src 'self' data: blob: https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ https://flashmonkey-deck-photo.s3.us-west-2.amazonaws.com/ https://www.googletagmanager.com ; " +
            "media-src https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ ; " +
            "style-src 'sha256-alQkhzRik30p4D42M4x52HUwzK1/HLrcDh9ydLkkoOI=' 'sha256-1PxuDsPyGK6n+LZsMv0gG4lMX3i3XigG6h0CzPIjwrE=' 'sha256-fH7fGHSUDtXnw6o27/xOwSg9KqQz+BiL3wj8RHjZ6/E=' 'sha256-JryMZ1bDjCN7hr3j+4n8NXlww3lqEhTrd5Xh62mDSwI=' 'sha256-cPDgbLhKQ/RKKkHGkAKViR37U8aXOM/5ObXLIHMGSTY=' 'sha256-biLFinpqYMtWHmXfkA1BPeCY0/fNt46SAZ+BBk5YUog=' 'sha256-UJykzqj9ptkjrvC3f/Tj8qDfpQEf+3Xg8cmKhZPI7dI=' 'self'; " +
            "frame-ancestors 'none'; object-src 'none'; base-uri 'self'; form-action 'self'; " +
            "font-src 'self'; "; // +



    // Content security policy
    private static final String CONTENT_SECURITY_POLICY = str;

    //private FreeMarkerTemplateEngine templateEngine;
    private static final String VIDEO_CHAT_SEC_POLICY = "Access-Control-Allow-Origin '*'; Access-Control-Allow-Methods 'GET,POST,OPTIONS'; Access-Control-Allow-Headers 'Origin,Accept,X-Requested-With, Content-Type';";
    //private static final String KICK_PRELAUNCH_POLICY = "default-src 'self' sha256-1PxuDsPyGK6n+LZsMv0gG4lMX3i3XigG6h0CzPIjwrE=; style-source 'sha256-1PxuDsPyGK6n+LZsMv0gG4lMX3i3XigG6hoCzPIjwrE=' 'sha256-alQkhzRik30p4D42M4x52HUwzK1/HLrcDh9ydLkkoOI=' 'sha256-47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=';";

    // Default constructor
    public Page() {
        /* nothing */
    }


    /**
     * Provides a common sourcefile that configures proper browser policies to protect against
     * Malicious actors on the User/Client side
     * Does not check for deceptive requests. use pageHandler to check. Then use this unless
     * this is a non-public endpoint such as a QR.
     * @param context
     * @param pageName
     */
    public void commonHandlerWPolicy(RoutingContext context, String pageName, FreeMarkerTemplateEngine templateEngine) {
        templateEngine.render(context.data(), pageName)
                .onSuccess(buffer -> {
                        context.response().putHeader("Content-Type", "text/html");
                        context.response().putHeader("Cache-Control", " max-age=604800, must-revalidate");
                        context.response().putHeader("X-Frame-Options", "deny");
                        context.response().putHeader("X-Content-Type-Options", "nosniff");
                        context.response().putHeader("Strict-Transport-Security", " max-age=31536000; includeSubDomains; preload");
                        context.response().putHeader("Content-Security-Policy", CONTENT_SECURITY_POLICY);
                        context.response().end(buffer);
                })
                .onFailure(err -> {
                    err.printStackTrace();
                    context.fail(err.getCause());
                });
    }

//    public void vidChatHandlerWPolicy(RoutingContext context, String pageName, FreeMarkerTemplateEngine templateEngine) {
//        templateEngine.render(context.data(), pageName, ar -> {
//            if (ar.succeeded()) {
//                context.response().putHeader("Content-Type", "text/html");
//                context.response().putHeader("Cache-Control", " max-age=3000, must-revalidate");
//                context.response().putHeader("X-Frame-Options", "deny");
//                context.response().putHeader("X-Content-Type-Options", "nosniff");
//                context.response().putHeader("Strict-Transport-Security", " max-age=31536000; includeSubDomains; preload");
//                context.response().putHeader("Content-Security-Policy", VIDEO_CHAT_SEC_POLICY);
//                context.response().end(ar.result());
//            } else {
//                //               LOGGER.warn(ar.cause().toString());
//                context.fail(ar.cause());
//            }
//        });
//    }
}
