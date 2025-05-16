package com.runnermann.wolf.example.database;


import com.runnermann.wolf.example.web.database.DatabaseHandler;
import com.runnermann.wolf.example.web.database.SqlQuery;
import io.vertx.core.Future;

import io.vertx.core.json.JsonArray;
import io.vertx.junit5.VertxExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.vertx.junit5.VertxTestContext;


import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(VertxExtension.class)
public class UserDatabaseServiceTest {

    final static Properties queriesProps = new Properties();

    // Basic first test, check Implements




    @Test
    public void fetchBlogById_HandlerTest(VertxTestContext testContext) throws IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        List<String> params = new ArrayList<>(1);
        params.add("1");

        Future<RowSet<Row>> ret = dbHandler.doFetch(SqlQuery.FETCH_BLOG_BY_ID, params);
        ret.onComplete(testContext.succeeding(res -> {
            RowSet<Row> rows = res.value();//result();
            rows.forEach(row -> {
                testContext.verify(() -> {
                    assertThat(row).isNotNull();
                    assertThat(row.getString("title")).startsWith("The");
                    testContext.completeNow();
                });
            });
        }));
    }


    @Test
    public void fetchBlogRef_handlerTest(VertxTestContext testContext) throws IOException {

        DatabaseHandler dbHandler = new DatabaseHandler();

        List<String> params = new ArrayList<>(1);
        params.add("3");

        Future<RowSet<Row>> ret = dbHandler.doFetch(SqlQuery.FETCH_BLOG_REFS , params);
        ret.onComplete(testContext.succeeding(res -> {
            RowSet<Row> rows = res.value();//result();
            rows.forEach(row -> {
                testContext.verify(() -> {
                    assertThat(row).isNotNull();
                    assertThat(row.getString("reference")).startsWith("Fletcher");
                    testContext.completeNow();

                });
            });
        }));
    }

    @Test
    public void fetchBlogRows_handlerTest(VertxTestContext testContext) throws IOException {

        DatabaseHandler dbHandler = new DatabaseHandler();
        List<String> params = new ArrayList<>(1);
        params.add("1");

        Future<RowSet<Row>> ret = dbHandler.doFetch(SqlQuery.FETCH_BLOG_ROWS, params);
            ret.onComplete(testContext.succeeding(res -> {
            RowSet<Row> rows = res.value();//result();
            rows.forEach(row -> {
                testContext.verify(() -> {
                    assertThat(row).isNotNull();
                    assertThat(row.getString("para")).startsWith("How");
                    testContext.completeNow();

                });
            });
        }));
    }

    @Test
    public void fetchAllBlogs_handlerTest(VertxTestContext testContext) throws IOException {

        DatabaseHandler dbHandler = new DatabaseHandler();
        List<String> params = new ArrayList<>();

        Future<RowSet<Row>> ret = dbHandler.doFetch(SqlQuery.FETCH_ALL_BLOGS, params);
        ret.onComplete(testContext.succeeding(res -> {
            RowSet<Row> rows = res.value();//result();
            rows.forEach(row -> {
                testContext.verify(() -> {
                    assertThat(row).isNotNull();
                    assertThat(row.getInteger("blog_id")).isEqualTo(1);
                    testContext.completeNow();
                });
            });
        }));
    }

    // INSERT, UPDATE and UPSERT tests

    public void upsertNewUserFmEmail_Test(VertxTestContext testContext) throws IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        List<String> params = new ArrayList<>();

    }


}
