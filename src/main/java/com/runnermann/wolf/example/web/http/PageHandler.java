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
import java.util.stream.Collector;


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


    /**
     * Get all blogs and output a list. This does NOT limit the number, e.g. paginate,
     * nor is the output in a logical order.
     * @param context
     * @param templateEngine
     */
    void blogsAllPageHandler(RoutingContext context, FreeMarkerTemplateEngine templateEngine) {
        List<String> params = new ArrayList<>();

        dbHandler.doFetch(SqlQuery.FETCH_ALL_BLOGS, params)
                .onComplete(res -> {
                    if (res.failed()) {
                        context.fail(res.cause());
                        return;
                    }

                    // Step 1: Process the result in parallel
                    RowSet<Row> rows = res.result();
                    JsonArray returnArry = rows.stream()
                            .parallel() // Enable parallel processing
                            .map(row -> {
                                long id = row.getLong("blog_id");

                                // Construct the JsonObject for each row
                                return new JsonObject()
                                        .put("blog_id", id)
                                        .put("image_card_link", row.getString("image_card_link"))
                                        .put("image_card_alt", row.getString("image_card_alt"))
                                        .put("title", row.getString("title"))
                                        .put("card_intro", row.getString("card_intro"))
                                        .put("create_date", row.getLocalDate("create_date"))
                                        .put("author", row.getString("author"))
                                        .put("read_time", row.getInteger("read_time"))
                                        // The "page_endpoint" field requires special handling
                                        .put("page_endpoint", "/articles/" + id + "/" + row.getString("page_endpoint"));
                            })
                            .collect(Collector.of(
                                    JsonArray::new, // Supplier: Create a new JsonArray
                                    JsonArray::add, // Accumulator: Add each JsonObject to the JsonArray
                                    JsonArray::addAll // Combiner: Combine JsonArrays in parallel
                            ));

                    // Step 2: Add processed data to the context
                    context.put("blogData", returnArry);

                    // Step 3: Render the page
                    Page page = new Page();
                    page.commonHandlerWPolicy(context, "webroot/templates/blog_listing.ftl", templateEngine);
                });
    }



    /**
     * Handles blogs either as articles format or as a blog format depending on the 'selected article' Boolean in the
     * public.blog table.
     * @param context
     * @param templateEngine
     */
    void blogPageHandler(RoutingContext context, FreeMarkerTemplateEngine templateEngine) {
        String id = context.request().getParam("id");
        List<String> params = new ArrayList<>();
        params.add(id);

        dbHandler.doFetch(SqlQuery.FETCH_BLOG_BY_ID, params)
                .onComplete(res -> {
                    if (res.failed()) {
                        context.fail(res.cause());
                        return;
                    }

                    // Step 1: Process the result of FETCH_BLOG_BY_ID in parallel
                    RowSet<Row> rows = res.result();
                    JsonArray blogDataAry = rows.stream()
                            .parallel()                          // Enable parallel processing
                            .map(Row::toJson)                    // Convert each row to JSON
                            .collect(Collector.of(               // Collect result into a JsonArray
                                    JsonArray::new,                  // Create new JsonArray
                                    JsonArray::add,                  // Add element to JsonArray
                                    JsonArray::addAll                // Combine results in parallel
                            ));

                    context.put("blogData", blogDataAry);

                    // Step 2: Fetch the rows for FETCH_BLOG_ROWS and process them
                    dbHandler.doFetch(SqlQuery.FETCH_BLOG_ROWS, params)
                            .onComplete(resi -> {
                                if (resi.failed()) {
                                    context.fail(resi.cause());
                                    return;
                                }

                                RowSet<Row> rowSet = resi.result();
                                JsonArray rowData = rowSet.stream()
                                        .parallel()                      // Enable parallel processing
                                        .map(Row::toJson)                // Convert each row to JSON
                                        .collect(Collector.of(
                                                JsonArray::new,              // Create new JsonArray
                                                JsonArray::add,              // Add element to JsonArray
                                                JsonArray::addAll            // Combine results in parallel
                                        ));

                                context.put("rowData", rowData);

                                // Step 3: Decide the template based on the "article" value
                                Page page = new Page();
                                if (blogDataAry.getJsonObject(0).getBoolean("article")) {
                                    page.commonHandlerWPolicy(
                                            context,
                                            "webroot/templates/blog/article_template.ftl",
                                            templateEngine
                                    );
                                } else {
                                    page.commonHandlerWPolicy(
                                            context,
                                            "webroot/templates/blog/blog_template.ftl",
                                            templateEngine
                                    );
                                }
                            });
                });
    }

}
