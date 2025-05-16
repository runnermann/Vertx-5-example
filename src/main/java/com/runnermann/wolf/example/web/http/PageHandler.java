package com.runnermann.wolf.example.web.http;


import com.runnermann.wolf.example.web.database.DatabaseHandler;
import com.runnermann.wolf.example.web.database.SqlQuery;
import com.runnermann.wolf.example.web.protect.Page;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import org.apache.http.MethodNotSupportedException;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;



public class PageHandler {

    private DatabaseHandler dbHandler = new DatabaseHandler();
    private final static ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HttpServerVerticle.class);


    /**
     * Send the about page
     * @param context
     * @param templateEngine
     */
    void aboutPageHandler(RoutingContext context, FreeMarkerTemplateEngine templateEngine) {
        try {
            throw new MethodNotSupportedException("Method is not implemented yet");
        } catch (MethodNotSupportedException e) {
            throw new RuntimeException(e);
        }
    };

    /**
     * Send the backers page
     * @param context
     * @param templateEngine
     */
    void ourBackersHandler(RoutingContext context, FreeMarkerTemplateEngine templateEngine) {
        try {
            throw new MethodNotSupportedException("Method is not implemented yet");
        } catch (MethodNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


    void blogsAllPageHandler(RoutingContext context, FreeMarkerTemplateEngine templateEngine) {
        List<String> params = new ArrayList<>();
        dbHandler.doFetch(SqlQuery.FETCH_ALL_BLOGS, params)
        .onComplete(res -> {
            JsonArray returnArry = new JsonArray();
            if(res.succeeded()) {
                RowSet<Row> rows = res.result();

                rows.forEach(row -> {
                                    long id = row.getLong("blog_id");
                    JsonObject jobObj = new JsonObject()
                            .put("blog_id", id)
                            .put("image_card_link", row.getString("image_card_link"))
                            .put("image_card_alt", row.getString("image_card_alt"))
                            .put("title", row.getString("title"))
                            .put("card_intro", row.getString("card_intro"))
                            .put("create_date", row.getLocalDate("create_date"))
                            .put("author", row.getString("author"))
                            .put("read_time", row.getInteger("read_time"))
                            .put("page_endpoint", "/articles/" + id + "/"
                                    + row.getString("page_endpoint"));
                    returnArry.add(jobObj);
                });
            }

            context.put("blogData", returnArry);

            Page page = new Page();
            page.commonHandlerWPolicy(context, "webroot/templates/blog_listing.ftl", templateEngine);
        });
    }


    /**
     * Handles blogs either as articles format or as a blog format depending on the selected article Boolean in the
     * public.blog table.
     * @param context
     * @param templateEngine
     */
    void blogPageHandler(RoutingContext context, FreeMarkerTemplateEngine templateEngine) {
            String id = context.request().getParam("id");
            List<String> params = new ArrayList<>();
            params.add(id);

            JsonArray blogDataAry = new JsonArray();
            //JsonObject blogDataRow = new JsonObject();

            dbHandler.doFetch(SqlQuery.FETCH_BLOG_BY_ID, params)
                .onComplete(res -> {
                    RowSet<Row> rows = res.result();
                    rows.forEach(row -> {
                        //String[] data = splitRowData(row.toJson());
                        blogDataAry.add(row);
                });
                context.put("blogData", blogDataAry);

                JsonArray rowDataAry = new JsonArray();
                dbHandler.doFetch(SqlQuery.FETCH_BLOG_ROWS, params)
                    .onComplete(resi -> {
                        if (resi.succeeded()) {
                            RowSet<Row> rowsi = resi.result();
                            rowsi.forEach(row -> {
                                JsonObject rowDataRow = new JsonObject()
                                    .put("intro", row.getBoolean("intro"))
                                    .put("new", row.getBoolean("new_row"))
                                    .put("left", row.getBoolean("section_left"))
                                    .put("left_end", row.getBoolean("section_left_end"))
                                    .put("subtitle", row.getBoolean("subtitle"))
                                    .put("num_list_start", row.getBoolean("num_list_start"))
                                    .put("num_start_no", row.getInteger("num_start_no"))
                                    .put("num_list_end", row.getBoolean("num_list_end"))
                                    .put("num_list_item", row.getBoolean("num_list_item"))
                                    .put("para_num", row.getInteger("para_num"))
                                    .put("h2_h3", row.getString("h2_h3"))
                                    .put("h2_subtitle", row.getString("h2_subtitle"))
                                    .put("para", row.getString("para"))
                                    .put("img", row.getBoolean("img"))
                                    .put("img_name", row.getString("img_name"))
                                    .put("img_alt", row.getString("img_alt"))
                                    .put("img_sz", row.getString("img_sz"))
                                    .put("unorder_list_start", row.getBoolean("unorder_list_start"))
                                    .put("unorder_list_end", row.getBoolean("unorder_list_end"))
                                    .put("unorder_list_item", row.getBoolean("unorder_list_item"));

                            rowDataAry.add(rowDataRow);
                        });
                    }

                    context.put("rowData", rowDataAry);
                    Page page = new Page();
                    if(blogDataAry.getJsonObject(0).getBoolean("article")) {
                        page.commonHandlerWPolicy(context, "webroot/templates/blog/article_template.ftl", templateEngine);
                    } else {
                        page.commonHandlerWPolicy(context, "webroot/templates/blog/blog_template.ftl", templateEngine);
                    }
                });
            });
    }
}
