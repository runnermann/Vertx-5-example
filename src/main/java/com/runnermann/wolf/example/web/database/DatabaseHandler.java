/*
 *  Copyright Lowell Stadelman. Licensed as Apache 2.
 * @author Lowell Stadelman
 *
 *
 *
 */

package com.runnermann.wolf.example.web.database;


import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.pgclient.PgBuilder;
import io.vertx.sqlclient.*;
import io.vertx.core.Future;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.vertx.pgclient.PgConnectOptions;

/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
public class DatabaseHandler {

    //private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);
    //private final static ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HttpServerVerticle.class);

    private final HashMap<SqlQuery, String> selectQueryMap;
    private final HashMap<SqlQuery, String> upsertQueryMap;
    private final HashMap<SqlQuery, String> operationalQueryMap;
    private final SqlClient dbClient;


    public DatabaseHandler() {
        this.dbClient = createSqlClient();
        try {
            this.selectQueryMap = QuerySelectMap.loadQueries();
            this.upsertQueryMap = QueryUpsertMap.loadQueries();
            this.operationalQueryMap = DBOperationsMap.loadQueries();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private SqlClient createSqlClient() {
//        SecretEncDec dang = SecretEncDec.getInstance();
//        final String[] errors = dang.getDBConnectErrors();
//        String u = errors[1];
//        String p = errors[0];

        // UNCOMMENT to use a remote DB
        //        JDBCClient dbClient = JDBCClient.createShared(vertx, new JsonObject()
//                .put("url", config().getString(CONFIG_USERS_JDBC_URL, "jdbc:postgresql://usa-conus.caws1d0xah4s.us-west-2.rds.amazonaws.com:5432/usa-conus"))
//                .put("driver_class", config().getString(CONFIG_USERS_JDBC_DRIVER_CLASS, "org.postgresql.Driver"))
//                .put("max_pool_size", config().getInteger(CONFIG_USERS_JDBC_MAX_POOL_SIZE, 30))
//                .put("user", u)
//                .put("password", p));

        // Connect to local DB. No PW
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("usa_conus_local")
                .setUser("ls");
                // Replace setUser with below if using remote DB. Password and user
                // is stored in Encrypted File. Unencrypted file is stored on the hard-drive.
                // Use /web/access/SecretEncDec to create encrypted files.
                // .setUser(u)
                // .setPassword(p);


        // Pool options
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        return PgBuilder
                .client()
                .with(poolOptions)
                .connectingTo(connectOptions)
                .using(Vertx.vertx())
                .build();
    }




    /**
     * A SELECT statement QUERY
     * NOTE: That we expect the query to contain numbered params based on the index of the List.
     * Example: SELECT * FROM public.student WHERE class = $1 and gpa = $2.
     * If no params are needed, then the results will be all that match the query provided in
     * the sqlQuery param.
     * @param sqlQuery The query
     * @param params The parameters that are required for the query.
     * @return the results as a Promise<JsonArray>
     */
    public Future<RowSet<Row>> doFetch(SqlQuery sqlQuery, List<String> params ) {
        Promise<RowSet<Row>> promise = Promise.promise();

        // If params are provided, else no params are used.
        if(!params.isEmpty()) {
            dbClient
                    .preparedQuery(selectQueryMap.get(sqlQuery))
                    .execute(Tuple.wrap(params))
                    .onComplete(
                            result -> promise.complete(result),
                            error -> promise.fail(error)
                    );
        }
        else {
            dbClient
                    .preparedQuery(selectQueryMap.get(sqlQuery))
                    .execute()
                    .onComplete(
                            result -> promise.complete(result),
                            error -> promise.fail(error)
                    );
        }

        return promise.future();
    }
}
