/*
 *  Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *  Copyright (c) 2017 INSA Lyon, CITI Laboratory.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.knocscore.database;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

import io.vertx. rxjava3.pgclient.PgBuilder;
import io.vertx.pgclient.PgConnectOptions;

import io.vertx.sqlclient.PoolOptions;

import com.knocscore.access.ModelError;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import io.vertx.rxjava3.sqlclient.SqlClient;





/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
public class UserDatabaseVerticle extends AbstractVerticle {
    ModelError dang = ModelError.getInstance();
    final String[] errors = dang.getDBConnectErrors();

    // Modified from original for PostgreSQL
    public static final String CONFIG_USERS_JDBC_URL = "user.jdbc.url";
    public static final String CONFIG_USERS_JDBC_DRIVER_CLASS = "user.jdbc.driver_class";
    public static final String CONFIG_USERS_JDBC_MAX_POOL_SIZE = "user.jdbc.max_pool_size";
    public static final String CONFIG_USERS_SQL_QUERIES_RESOURCE_FILE = "user.sqlqueries.resource.file";
    public static final String CONFIG_USERS_QUEUE = "user.queue";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDatabaseVerticle.class);

    @Override
    public void start(Promise<Void> promise) throws Exception {
        LOGGER.info("UserDatabaseVerticle start called");
        HashMap<SqlQuery, String> sqlQueries = loadSqlQueries();
    // Remote DB connection
//        String u = errors[1];
//        String p = errors[0];
//        JDBCClient dbClient = JDBCClient.createShared(vertx, new JsonObject()
//                .put("url", config().getString(CONFIG_USERS_JDBC_URL, "jdbc:postgresql://usa-conus.caws1d0xah4s.us-west-2.rds.amazonaws.com:5432/usa-conus"))
//                .put("driver_class", config().getString(CONFIG_USERS_JDBC_DRIVER_CLASS, "org.postgresql.Driver"))
//                .put("max_pool_size", config().getInteger(CONFIG_USERS_JDBC_MAX_POOL_SIZE, 30))
//                .put("user", u)
//                .put("password", p));

//         xxxxxxx = createShared(vertx, new JsonObject()
//                .put("url", config().getString(CONFIG_USERS_JDBC_URL, "jdbc:postgresql://localhost:5432/usa_conus_local"))
//                .put("driver_class", config().getString(CONFIG_USERS_JDBC_DRIVER_CLASS, "org.postgresql.Driver"))
//                .put("max_pool_size", config().getInteger(CONFIG_USERS_JDBC_MAX_POOL_SIZE, 30)));

        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("jdbc:postgresql://localhost:5432")
                .setDatabase("usa_conus_local");
//                .setUser("user")
//                .setPassword("secret");

        // Pool options
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        SqlClient dbClient = PgBuilder
                .client()
                .with(poolOptions)
                .connectingTo(connectOptions)
                .build();


        UserDatabaseService.create(dbClient, sqlQueries, ready -> {
            if (ready.succeeded()) {
                ServiceBinder binder = new ServiceBinder(vertx);
                binder.setAddress(CONFIG_USERS_QUEUE).register(UserDatabaseService.class, ready.result());
                LOGGER.info("Database is conected");
                promise.complete();
            } else {
                LOGGER.warn("WARNING: Database connection failed. {}");
                promise.fail(ready.cause());
            }
        });
    }

    /*
     * Note: this uses blocking APIs, but data is small...
     */
    private HashMap<SqlQuery, String> loadSqlQueries() throws IOException {

        String queriesFile = config().getString(CONFIG_USERS_SQL_QUERIES_RESOURCE_FILE);
        InputStream queriesInputStream;
        if (queriesFile != null) {
            queriesInputStream = new FileInputStream(queriesFile);
        } else {
            queriesInputStream = getClass().getResourceAsStream("/db-queries.properties");
        }

        Properties queriesProps = new Properties();
        queriesProps.load(queriesInputStream);
        queriesInputStream.close();

        HashMap<SqlQuery, String> sqlQueries = new HashMap<>();

        sqlQueries.put(SqlQuery.DB_ROLLBACK, queriesProps.getProperty("db-rollback"));

        sqlQueries.put(SqlQuery.INSERT_KSCORE_DONATE_TRX, queriesProps.getProperty("insert-knocscore-donatetrx"));
        sqlQueries.put(SqlQuery.FETCH_KSCORE_PREKICK_STATS, queriesProps.getProperty("select-prekick-metrics"));
        sqlQueries.put(SqlQuery.INSERT_EMAIL_PREKICK, queriesProps.getProperty("insert-email-prekick"));
        sqlQueries.put(SqlQuery.SELECT_PREKICK_BACKERS, queriesProps.getProperty("select-prekick-backers"));

        sqlQueries.put(SqlQuery.CREATE_USERS_TABLE, queriesProps.getProperty("create-users-table"));
//        sqlQueries.put(SqlQuery.ALL_USERS, queriesProps.getProperty("all-users"));
        sqlQueries.put(SqlQuery.GET_USER_BY_EMAIL, queriesProps.getProperty("get-user-by-email"));
        sqlQueries.put(SqlQuery.USER_EXISTS, queriesProps.getProperty("get-user-by-email"));
        sqlQueries.put(SqlQuery.CREATE_USER, queriesProps.getProperty("create-user"));
        sqlQueries.put(SqlQuery.CREATE_USER_FMAPPREQ, queriesProps.getProperty("create-userfmappreq"));
        sqlQueries.put(SqlQuery.CREATE_KNOC_USER, queriesProps.getProperty("create-knoc-user"));
        sqlQueries.put(SqlQuery.FINALIZE_NEW_USER, queriesProps.getProperty("update-finalize-user"));
        sqlQueries.put(SqlQuery.SAVE_USER, queriesProps.getProperty("save-user"));
        sqlQueries.put(SqlQuery.ALL_USERS_DATA, queriesProps.getProperty("all-users-data"));
        sqlQueries.put(SqlQuery.GET_USER_BY_UUID, queriesProps.getProperty("get-user-by-uuid"));
        sqlQueries.put(SqlQuery.ALL_USERS_DECKS, queriesProps.getProperty("all-users-decks"));
        sqlQueries.put(SqlQuery.SET_CODE, queriesProps.getProperty("set-code"));
        sqlQueries.put(SqlQuery.UPSERT_USER_CODE, queriesProps.getProperty("upsert-user-code"));
        sqlQueries.put(SqlQuery.UPSERT_MIL_IMAGE, queriesProps.getProperty("upsert-mil-image"));
        sqlQueries.put(SqlQuery.UPSERT_ATHLETE_IMAGE, queriesProps.getProperty("upsert-athlete-image"));


        sqlQueries.put(SqlQuery.QR_FETCH_DECK_NAME_PRICE, queriesProps.getProperty("qr-fetch-deck-name-price"));
        sqlQueries.put(SqlQuery.QR_FETCH_CAMPAIGN, queriesProps.getProperty("qr-fetch-campaign"));
        sqlQueries.put(SqlQuery.UPSERT_EMAIL_BOUNCED, queriesProps.getProperty("upsert-email-bounced"));
        sqlQueries.put(SqlQuery.UPSERT_EMAIL_OPENED, queriesProps.getProperty("upsert-email-opened"));
        sqlQueries.put(SqlQuery.UPSERT_EMAIL_COMPLAINT, queriesProps.getProperty("upsert-email-complaint"));
        sqlQueries.put(SqlQuery.UPSERT_EMAIL_OPTOUT, queriesProps.getProperty("upsert-email-optout"));

        sqlQueries.put(SqlQuery.UPDATE_BIO, queriesProps.getProperty("update-bio"));
        sqlQueries.put(SqlQuery.UPSERT_ATHLETE, queriesProps.getProperty("upsert-athlete"));

        sqlQueries.put(SqlQuery.UPSERT_MIL_SERVICE, queriesProps.getProperty("upsert-mil-service"));
        sqlQueries.put(SqlQuery.UPSERT_ED_GENERAL, queriesProps.getProperty("upsert-ed-general"));
        sqlQueries.put(SqlQuery.INSERT_POR_EMPLOYER, queriesProps.getProperty("insert-por-employer"));
        sqlQueries.put(SqlQuery.INSERT_INDUSTRY_DATA, queriesProps.getProperty("insert-industry-data"));
        sqlQueries.put(SqlQuery.INSERT_APP_REQ, queriesProps.getProperty("insert_fm_app_req"));
        sqlQueries.put(SqlQuery.INSERT_CAMPAIGN_KEYCLICK, queriesProps.getProperty("insert-campaign-keyclick"));
        sqlQueries.put(SqlQuery.INSERT_MEMBERSHIP_FEE, queriesProps.getProperty("insert-fee-pay"));
        sqlQueries.put(SqlQuery.INSERT_NOTE_PERSON, queriesProps.getProperty("insert_note_person"));
        sqlQueries.put(SqlQuery.INSERT_NOTE_SYSTEM, queriesProps.getProperty("insert_note_system"));
        sqlQueries.put(SqlQuery.INSERT_PAYMENT_RECIEVED, queriesProps.getProperty("insert_payment_recieved"));
        sqlQueries.put(SqlQuery.INSERT_PREDECESSORCHAIN, queriesProps.getProperty("insert_predicessorchain"));
        sqlQueries.put(SqlQuery.INSERT_PURCHASED_CHILDDECK, queriesProps.getProperty("insert_purchased_childdeck"));
        sqlQueries.put(SqlQuery.INSERT_TRANSACTION, queriesProps.getProperty("insert_transaction"));
        sqlQueries.put(SqlQuery.INSERT_VIDEOCHAT_HOST, queriesProps.getProperty("insert_videochat_host"));
        sqlQueries.put(SqlQuery.INSERT_VIDEOCHAT_ATTENDEE, queriesProps.getProperty("insert_videochat_attendee"));
        sqlQueries.put(SqlQuery.INSERT_VISITOR, queriesProps.getProperty("insert_visitor"));

        sqlQueries.put(SqlQuery.FETCH_ACCT_NO, queriesProps.getProperty("fetch-acct-no"));
        sqlQueries.put(SqlQuery.FETCH_CHILDDECK_EXISTS, queriesProps.getProperty("fetch_childdeck_exists"));
        sqlQueries.put(SqlQuery.FETCH_BULK_EMAIL_REQS, queriesProps.getProperty("fetch-bulk-email-reqs"));
        sqlQueries.put(SqlQuery.FETCH_EMAILS_OTHER, queriesProps.getProperty("fetch-emails-other"));
        sqlQueries.put(SqlQuery.FETCH_EMAILS_DIFF, queriesProps.getProperty("fetch-emails-diff"));
        sqlQueries.put(SqlQuery.FETCH_ALL_JOBS, queriesProps.getProperty("fetch-all-jobs"));
        sqlQueries.put(SqlQuery.FETCH_JOB_BY_ID, queriesProps.getProperty("fetch-job-by-id"));
        sqlQueries.put(SqlQuery.FETCH_TEAM, queriesProps.getProperty("fetch-team"));
        sqlQueries.put(SqlQuery.FETCH_USER_HASH, queriesProps.getProperty("fetch-user-hash"));
        sqlQueries.put(SqlQuery.FETCH_USER_STATS, queriesProps.getProperty("fetch-user-stats"));
        sqlQueries.put(SqlQuery.FETCH_STATS_ENTITY, queriesProps.getProperty("fetch-stats-entity"));
        sqlQueries.put(SqlQuery.FETCH_STATS_POS, queriesProps.getProperty("fetch-stats-positions"));
        sqlQueries.put(SqlQuery.FETCH_STATS_PROJECTS, queriesProps.getProperty("fetch-stats-projects"));
        sqlQueries.put(SqlQuery.FETCH_STATS_ACHIEVEMENT, queriesProps.getProperty("fetch-stats-achievement"));
        sqlQueries.put(SqlQuery.FETCH_STATS_ATHLETICS, queriesProps.getProperty("fetch-stats-athletics"));
        sqlQueries.put(SqlQuery.FETCH_STATS_PEOPLE, queriesProps.getProperty("fetch-stats-people"));
        sqlQueries.put(SqlQuery.FETCH_STATS_KNOWLEDGE, queriesProps.getProperty("fetch-stats-knowledge"));
        sqlQueries.put(SqlQuery.FETCH_MEMBERS_LIST, queriesProps.getProperty("fetch-members-list"));

        sqlQueries.put(SqlQuery.FETCH_PREDECESSOR_USER, queriesProps.getProperty("fetch-predecessor-user"));
        sqlQueries.put(SqlQuery.FETCH_STUDENT, queriesProps.getProperty("fetch-student"));
        sqlQueries.put(SqlQuery.FETCH_MEMBER_STATUS, queriesProps.getProperty("fetch-member-status"));
        sqlQueries.put(SqlQuery.FETCH_SUBSCRIPT_ID, queriesProps.getProperty("fetch-subscript-id"));
        sqlQueries.put(SqlQuery.FETCH_STRIPE_ID, queriesProps.getProperty("fetch-strip-id"));
        sqlQueries.put(SqlQuery.FETCH_CODE, queriesProps.getProperty("fetch-code"));
        sqlQueries.put(SqlQuery.FETCH_DECK_PUR_DETAILS, queriesProps.getProperty("fetch-deck-purchase-details"));
        sqlQueries.put(SqlQuery.FETCH_HASPURCHASED, queriesProps.getProperty("fetch-hasPurchased"));
        sqlQueries.put(SqlQuery.FETCH_VIDEOCHAT_MEETING, queriesProps.getProperty("fetch-videochat-meeting"));
        sqlQueries.put(SqlQuery.FETCH_VIDEOCHAT_PCODE, queriesProps.getProperty("fetch_videochat_pcode"));
        sqlQueries.put(SqlQuery.FETCH_ALL_BLOGS, queriesProps.getProperty("fetch-all-blogs"));
        sqlQueries.put(SqlQuery.FETCH_BLOG_BY_ID, queriesProps.getProperty("fetch-blog-by-id"));
        sqlQueries.put(SqlQuery.FETCH_BLOG_ROWS, queriesProps.getProperty("fetch-blog-rows"));
        sqlQueries.put(SqlQuery.FETCH_BLOG_REFS, queriesProps.getProperty("fetch-blog-refs"));

        sqlQueries.put(SqlQuery.UPDATE_STRIPE_ID, queriesProps.getProperty("update-account-stripe-id"));
        sqlQueries.put(SqlQuery.UPDATE_PW, queriesProps.getProperty("update-pw"));
        sqlQueries.put(SqlQuery.UPDATE_STUDENT, queriesProps.getProperty("update-student"));
        sqlQueries.put(SqlQuery.UPDATE_SUBSCRIPTION, queriesProps.getProperty("update-subscription"));
        sqlQueries.put(SqlQuery.UPDATE_SUBSCRIPTION_ONID, queriesProps.getProperty("update-subscript-on-id"));
        sqlQueries.put(SqlQuery.UPDATE_SUBSCRIPTION_TRIALONID, queriesProps.getProperty("update-subscript-trial-on-id"));
        sqlQueries.put(SqlQuery.UPSERT_USER_DECKSTATSAMOUNT, queriesProps.getProperty("upsert-userdeckstats-amount"));
        sqlQueries.put(SqlQuery.SUBSCRIPT_START, queriesProps.getProperty("subscript_start"));
        sqlQueries.put(SqlQuery.SUBSCRIPT_CANCEL, queriesProps.getProperty("subscript_cancel"));
        sqlQueries.put(SqlQuery.SUBSCRIPT_CANCEL_ONID, queriesProps.getProperty("subscript-cancel-on-id"));

        sqlQueries.put(SqlQuery.DELETE_USER, queriesProps.getProperty("delete-user"));
        sqlQueries.put(SqlQuery.DELETE_TEST_MONKEY, queriesProps.getProperty("delete-test-monkey"));
        sqlQueries.put(SqlQuery.DELETE_PREDECESSOR_MONKEY, queriesProps.getProperty("delete-predicessor-monkey"));

        return sqlQueries;
    }
}
