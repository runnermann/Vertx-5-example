package com.knocscore.web.http;


import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class HttpUtility {

    private static HashMap<String, Boolean> TEMP_BLACKLIST = new HashMap();
    private static final HashMap<String, Boolean> BLACKLIST_MAP = new HashMap();
    private static final HashMap<String, Boolean> REFERRER_BLACKLIST = new HashMap<>();
    private static HashMap<String, IPNode> ipAttack = new HashMap<>(20);
    // Adding white list
    private static final ArrayList<String> WHITE_LIST = new ArrayList<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtility.class);

    String[] getArrayFromJsonArray(JsonArray jsonArray) {
        return jsonArray.stream().map(String::valueOf).toArray(String[]::new);
    }

    private int[] getIntArrayFromJsonArray(JsonArray jsonArray) {
        return jsonArray.stream()
                .mapToInt(n -> Integer.parseInt(n.toString())) // Use `mapToInt` to get an IntStream
                .toArray();
    }



    void init() {
        BLACKLIST_MAP.put("5.8.16",     true);
        BLACKLIST_MAP.put("106.75.33",  true); // china hacker. Confirmed 2022-09-09, following call spoofed IP address.
        BLACKLIST_MAP.put("66.249.79",  true); // --?
        BLACKLIST_MAP.put("114.88.163", true); // Injection in referrer odd code Shang Hai 2022-03-06, broke sql
        BLACKLIST_MAP.put("58.53.128",  true);  // Shanghai China // attempted HTTP Huangpu china 22-03-10
        BLACKLIST_MAP.put("193.118.53", true); //
        BLACKLIST_MAP.put("194.209.25", true); // DDOS attack on 2022-03-10
        BLACKLIST_MAP.put("59.175.144", true); // attempted HTTP wuhan china 22-03-10
        BLACKLIST_MAP.put("82.148.6",   true); // Injection odd code in referrer, Moscow 22-03-11
        BLACKLIST_MAP.put("45.148.18",  true); // DDOS attack on 2024-01-09, OSLO NORWAY
        BLACKLIST_MAP.put("91.90.121",  true); // DDOS attack on 2024-01-09, MANCHESTER United Kingdom
        BLACKLIST_MAP.put("92.119.179", true); // SLOW ROLL testing
        BLACKLIST_MAP.put("146.70.111", true); // SLOW ROLL testing
        WHITE_LIST.add("http://localhost:80");
    }

    void deceptiveRequestHandler(RoutingContext context) {
        HttpServerRequest request = context.request();
        String referrer = request.getHeader("referer");
        referrer = referrer.replaceAll("[^a-zA-Z0-9\\\\/:.]*", "");
        String ipClient = request.remoteAddress().host();
        LOGGER.warn("referrer: " + referrer + " || caller ipAddress:  " + ipClient);
        apiFailure(context, 400, "Error. please try again later. ");
    }


    /**
     * Called when the page is first called.
     * @param context
     */
    boolean logCall(RoutingContext context) {
        //apiResponse(context, 200, null, null);
        return logCall(context, "", "");
    }

    private static long time = System.currentTimeMillis();
    /**
     * Logs a caller if the caller is not equal to the elb ip address that was
     * captured at the vertx initialization.
     *
     * NOTE FOR TESTING: USE curl: The example tests for a blacklisted referrer
     * curl -H "User-Agent: from dev testing on 8080 haha" -H "Referer: http://blueriotlabs.com/" http://localhost:8080
     *
     * @param context
     * @return returns true if the ip address is blacklisted or not an IP Address.
     */
    private boolean logCall(RoutingContext context, String event, String userAgent) {
        try {
            //LOGGER.debug("log call time: {}", (System.currentTimeMillis() - time));
            long time1 = System.currentTimeMillis();
            // create a new map every 20 minutes. Works with AWS's security. AWS
            // creates a blacklist for a longer duration but not short duration.
            if((time1 - time) > 20000) {
                ipAttack = new HashMap<>();
                TEMP_BLACKLIST = new HashMap<>();
                this.time = System.currentTimeMillis();
            }

            HttpServerRequest request = context.request();
            String ipClient = request.remoteAddress().host();

            if(ipClientIsBlacklisted(ipClient)) {
                LOGGER.warn("BlackListed IP Address recorded: {}",ipClient );
                return true;
            }
            if(! ipHammerThreshold(ipClient) ) {
                String path = request.path();
                // Add KnoCScore to path so we know it is from
                // this Vertx Server
                path = "knocscore.com" + path;
                // block if referrer is blacklisted.
                String referrer = request.getHeader("referer");
                if (referrer != null && ! referrer.contains("localhost") && referrerIsBlacklisted(referrer)) {
                    LOGGER.warn("blacklisted referrer recorded: " + referrer);
                    return true;
                }
                // if person is obviously attempting to hack
                // put on permanent blacklist
                LOGGER.debug("path: {}", path);
                if (path.length() > 96 || path.contains("error")) { // cannot block ../ it is rewritten as /
                    LOGGER.debug("ip address: {} added to blacklist. path: {}", ipClient, path);
                    int n = ipClient.lastIndexOf(".");
                    n = n == -1 ? ipClient.length() + 1 : n;
                    String shortIp = ipClient;
                    if (n < 6) {
                        shortIp = ipClient.substring(0, n);
                    }
                    BLACKLIST_MAP.put(shortIp, true);
                    // log the hack attempt.
                    referrer = referrer == null ? "none" : referrer;
// @TODO fix dbService here
//                    dbService.rxInsertVisitor(path, ipClient, referrer, event + " --NOTE: IP added to static instance blacklist", userAgent)
//                            .doOnError(e -> LOGGER.warn(e.getMessage()))
//                            .subscribe();
                    return true;
                }

                referrer = referrer == null ? "not listed" : referrer;
// // @TODO fix dbService here
//                dbService.rxInsertVisitor(path, ipClient, referrer, event, userAgent)
//                        .doOnError(e -> LOGGER.warn(e.getMessage()))
//                        .subscribe();
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Check if client is hammering our server.
     * @param ipClient
     * @return returns true if added to temp_blacklist
     */
    private boolean ipHammerThreshold(String ipClient) {
        if(ipAttack.containsKey(ipClient)) {
            long timeNow = System.currentTimeMillis();

            IPNode node = ipAttack.get(ipClient);
            int num = node.getNum();
            if(10 < num && (timeNow - node.getTime()) < num * 2000) {
                TEMP_BLACKLIST.put(ipClient, true);
                LOGGER.warn("IP address added to TEMP_BLACKLIST {}", ipClient);
                return true;
            } else {
                node.setNum( num += 1);
                ipAttack.put(ipClient, node);
                if(timeNow - node.getTime() > 8000) {
                    node.setTime(timeNow);
                    node.setNum(1);
                    LOGGER.debug("Resetting: num: " + node.getNum() + " time: " + node.getTime());
                }
            }
        } else {
            ipAttack.put(ipClient, new IPNode());
        }
        return false;
    }

    /**
     * If client ip address is on the permenant blacklist
     * @param ipClient
     * @return
     */
    private boolean ipClientIsBlacklisted(String ipClient) {
        if(ipClient.length() > 64 || ipClient.length() < 6) {return true;}
        if(ipClient.matches("\\D")) { return true; }
        int n = ipClient.lastIndexOf(".");

        if (n > 0) {
            String s = ipClient.substring(0, n);
            return BLACKLIST_MAP.containsKey(s) || TEMP_BLACKLIST.containsKey(ipClient);
        } else {
            LOGGER.debug("false");
            return false;
        }
    }

    /**
     * NOTE FOR TESTING: USE curl: THe example tests for a blacklisted referrer
     * curl -H "User-Agent: I am your master using 8080 haha" -H "Referer: http://blueriotlabs.com/" http://localhost:8080
     * @param referrer
     * @return
     */
    private boolean referrerIsBlacklisted(String referrer) {
        //LOGGER.debug("referrer: {}", referrer);
        if(referrer.length() > 96 || referrer.length() < 6) {return true;}

        int start = referrer.indexOf("/") + 1; // first slash
        int end = referrer.indexOf(".") - start;
        end = end < 14 ? end : 13;
        //LOGGER.debug("start idx: {}, end idx: {}", start, end);
        //boolean bool = REFERRER_BLACKLIST.containsKey(referrer.substring(start, end + start));
        //LOGGER.debug("referrer substring: {}, is blacklisted {}", referrer.substring(start, end + start), bool);
        return REFERRER_BLACKLIST.containsKey(referrer.substring(start, end + start));
    }



    private void apiFailure(RoutingContext context, String errorMessage) {
        apiFailure(context, 500, errorMessage);
    }

    private void apiFailure(RoutingContext context, Throwable t) {
        apiFailure(context, 500, t.getMessage());
    }

    private void apiFailure(RoutingContext context, int statusCode, String error) {
        LOGGER.warn("WARNING: apiFailure called. MESSAGE to USER: <{}>", error);

        context.response().setStatusCode(statusCode);
        context.response().putHeader("Content-Type", "application/json");
        context.response().end(new JsonObject()
                .put("success", false)
                .put("error", error).encode());
    }
}
