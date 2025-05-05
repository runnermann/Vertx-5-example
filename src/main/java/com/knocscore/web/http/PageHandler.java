package com.knocscore.web.http;

import com.knocscore.database.rxjava3.UserDatabaseService;
import com.knocscore.protect.Page;
import com.knocscore.utility.BusAddressMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.ext.web.RoutingContext;

//import static com.knocscore.web.http.HttpServerVerticle.templateEngine;

public class PageHandler {



//    private void blogsAllPageHandler(RoutingContext context, UserDatabaseService dbService, ) {
//        HttpUtility ut = new HttpUtility();
//        if(ut.logCall(context)) {
//            ut.deceptiveRequestHandler(context);
//        } else {
//            dbService.rxFetchAllBlogs()
//                    .map(res -> {
//                        JsonArray blogAry = new JsonArray();
//                        res.stream().forEach(dbObj -> {
//                            long id = dbObj.getLong("blog_id");
//                            JsonObject jobObj = new JsonObject()
//                                    .put("blog_id", id)
//                                    .put("image_card_link", dbObj.getString("image_card_link"))
//                                    .put("image_card_alt", dbObj.getString("image_card_alt"))
//                                    .put("title", dbObj.getString("title"))
//                                    .put("card_intro", dbObj.getString("card_intro"))
//                                    .put("create_date", dbObj.getString("create_date"))
//                                    .put("author", dbObj.getString("author"))
//                                    .put("read_time", dbObj.getString("read_time"))
//                                    .put("page_endpoint", "/articles/" + id + "/" + dbObj.getString("page_endpoint"));
//
//                            blogAry.add(jobObj);
//                        });
//                        return blogAry;
//                    })
//                    .subscribe(result -> {
//                        context.put("blogData", result);
//                        //pageHandlerPrivate(context, "webroot/templates/jobs.ftl");
//                        Page page = new Page();
//                        page.commonHandlerWPolicy(context, "webroot/templates/blog_listing.ftl", templateEngine);
//                    }, e -> apiFailure(context, "Failed to produce the blogs listing: MESSAGE: " + e.getMessage()));
//        }
//    }
//
//    private void blogPageHandler(RoutingContext context) {
////        if (logCall(context)) {
////            deceptiveRequestHandler(context);
////        } else {
//        long id = Long.valueOf(context.request().getParam("id"));
//
//        dbService.rxFetchBlogByID(id)
//                .map(res -> {
//                    JsonArray blogArry = new JsonArray();
//                    JsonObject obj = new JsonObject()
//                            .put("id", id)
//                            .put("article", res.getString("article"))
//                            .put("title", res.getString("title"))
//                            .put("read_time", res.getString("read_time"))
//                            .put("meta_descript", res.getString("meta_descript"))
//                            .put("card_info", res.getString("card_info"))
//                            .put("author", res.getString("author"))
//                            .put("create_date", res.getString("create_date"))
//                            .put("change_date", res.getString("change_date"))
//                            .put("change_descript", res.getString("change_descript"))
//                            .put("page_endpoint", res.getString("page_endpoint"))
//                            .put("image_link", res.getString("image_link"))
//                            .put("image_twitter_link", res.getString("image_twitter_link"));
//                    blogArry.add(obj);
//
//
//
//                    dbService.rxFetchBlogRows(id)
//                            .map(ret -> {
//                                JsonArray jAry = new JsonArray();
//                                ret.stream().forEach(dbObj -> {
//                                    JsonObject jObj = new JsonObject()
//                                            .put("intro", dbObj.getBoolean("intro"))
//                                            .put("new", dbObj.getBoolean("new_row"))
//                                            .put("left", dbObj.getBoolean("section_left"))
//                                            .put("left_end", dbObj.getBoolean("section_left_end"))
//                                            .put("subtitle", dbObj.getBoolean("subtitle"))
//                                            .put("num_list_start", dbObj.getBoolean("num_list_start"))
//                                            .put("num_start_no", dbObj.getInteger("num_start_no"))
//                                            .put("num_list_end", dbObj.getBoolean("num_list_end"))
//                                            .put("num_list_item", dbObj.getBoolean("num_list_item"))
//                                            .put("para_num", dbObj.getInteger("para_num"))
//                                            .put("h2_h3", dbObj.getString("h2_h3"))
//                                            .put("h2_subtitle", dbObj.getString("h2_subtitle"))
//                                            .put("para", dbObj.getString("para"))
//                                            .put("img", dbObj.getBoolean("img"))
//                                            .put("img_name", dbObj.getString("img_name"))
//                                            .put("img_alt", dbObj.getString("img_alt"))
//                                            .put("img_sz", dbObj.getString("img_sz"))
//                                            .put("unorder_list_start", dbObj.getBoolean("unorder_list_start"))
//                                            .put("unorder_list_end", dbObj.getBoolean("unorder_list_end"))
//                                            .put("unorder_list_item", dbObj.getBoolean("unorder_list_item"));
//                                    jAry.add(jObj);
//                                });
//
//                                dbService.rxFetchBlogRefs(id)
//                                        .map(refii -> {
//                                            JsonArray refArry = new JsonArray();
//                                            refii.stream().forEach(dbObji -> {
//                                                JsonObject jObji = new JsonObject()
//                                                        .put("found", true)
//                                                        .put("reference", dbObji.getString("reference"));
//                                                refArry.add(jObji);
//                                            });
//                                            return refArry;
//                                        }).subscribe( e -> {
//                                            context.put("rowData", jAry);
//                                            context.put("blogData", blogArry);
//                                            context.put("sourceData", e);
//                                            Page page = new Page();
//                                            if(res.getString("article").equals("true")) {
//                                                page.commonHandlerWPolicy(context, "webroot/templates/blog/article_template.ftl", templateEngine);
//                                            } else {
//                                                page.commonHandlerWPolicy(context, "webroot/templates/blog/blog_template.ftl", templateEngine);
//                                            }
//                                        }, e -> {
//                                            apiFailure(context, "Failed to produce the blog: MESSAGE: " + e.getMessage());
//                                            e.printStackTrace();
//                                        });
//
//                                return jAry;
//                            })
//                            .subscribe();
//                    return blogArry;
//                })
//                .subscribe();
//        //}
//    }
//
//    private void jobsAllPageHandler(RoutingContext context) {
//        if(logCall(context)) {
//            deceptiveRequestHandler(context);
//        } else {
//            dbService.rxFetchAllJobs()
//                    .map(res -> {
//                        JsonArray jobsAry = new JsonArray();
//                        res.stream().forEach(dbObj -> {
//                            JsonObject jobObj = new JsonObject()
//                                    .put("job_id", dbObj.getInteger("job_id"))
//                                    .put("job_position", dbObj.getString("job_position"))
//                                    .put("employment_type", dbObj.getString("employment_type"))
//                                    .put("duration", dbObj.getString("duration"))
//                                    .put("job_location", dbObj.getString("job_location"))
//                                    .put("remote", dbObj.getString("remote"));
//
//                            jobsAry.add(jobObj);
//                        });
//                        return jobsAry;
//                    })
//                    .subscribe(result -> {
//                        context.put("jobsData", result);
//                        //pageHandlerPrivate(context, "webroot/templates/jobs.ftl");
//                        Page page = new Page();
//                        page.commonHandlerWPolicy(context, "webroot/templates/jobs.ftl", templateEngine);
//                    }, e -> apiFailure(context, "Failed to produce the decks: MESSAGE: " + e.getMessage()));
//        }
//    }
//
//    private void jobPageHandler(RoutingContext context) {
//        if(logCall(context)) {
//            deceptiveRequestHandler(context);
//        } else {
//            long id = Long.valueOf(context.request().getParam("id"));
//            dbService.rxFetchJobByID(id)
//                    .map(res -> {
//                        JsonArray jobAry = new JsonArray();
//
//                        if (res.getBoolean("found")) {
//                            LOGGER.debug("There is data");
//                            JsonObject obj = new JsonObject()
//                                    .put("job_id", res.getInteger("job_id"))
//                                    .put("job_position", res.getString("job_position"))
//                                    .put("employment_type", res.getString("employment_type"))
//                                    .put("duration", res.getString("duration"))
//                                    .put("description", res.getString("description"))
//                                    .put("pay", res.getString("pay"))
//                                    .put("incentives", res.getString("incentives"))
//                                    .put("job_location", res.getString("job_location"))
//                                    .put("remote", res.getString("remote"))
//                                    .put("work_auth", res.getString("work_auth"))
//                                    .put("international", res.getString("international"))
//                                    .put("required_docs", res.getString("required_docs"))
//                                    .put("required_knowledge", res.getString("required_knowledge"))
//                                    .put("major", res.getString("major"))
//                                    .put("email_contact", res.getString("email_contact"));
//
//                            jobAry.add(obj);
//                        } else {
//                            LOGGER.debug("The array is empty. No data exists.");
//                            jobAry.add(new JsonObject().put("found", false));
//                        }
//
//                        return jobAry;
//                    })
//                    .subscribe(result -> {
//                        context.put("title", "Jobs info");
//                        context.put("jobsData", result);
//                        LOGGER.info("calling template.job_detailsPage");
//                        Page page = new Page();
//                        page.commonHandlerWPolicy(context, "webroot/templates/job_details.ftl", templateEngine);
//
//                    }, e -> apiFailure(context, "Failed to produce the job: MESSAGE: " + e.getMessage()));
//        }
//    }
//
//    private void generalPageHandler(RoutingContext context) {
//        if(logCall(context)) {
//            deceptiveRequestHandler(context);
//        } else {
//            dbService.rxFetchTeam()
//                    .map(res -> {
//                        JsonArray teamAry = new JsonArray();
//                        res.stream().forEach(dbObj -> {
//                            JsonObject peopleData = new JsonObject()
//                                    .put("photo_link", dbObj.getString("photo_link"))
//                                    .put("name", UserAlphabet.decrypt(dbObj.getString("first_name"))
//                                            + " " + UserAlphabet.decrypt(dbObj.getString("last_name")))
//                                    .put("job_position", dbObj.getString("job_position"))
//                                    .put("descript", dbObj.getString("descript"));
//
//                            teamAry.add(peopleData);
//                        });
//                        return teamAry;
//                    })
//                    .subscribe(result -> {
//                        context.put("title", "Team info");
//                        context.put("peopleData", result);
//
//                        Page page = new Page();
//                        page.commonHandlerWPolicy(context, "webroot/templates/about.ftl", templateEngine);
//
//                    }, e -> apiFailure(context, "Failed to produce the team: MESSAGE: " + e.getMessage()));
//        }
//    };
}
