package com.runnermann.wolf.example.web.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class QueryUpsertMap {

    /*
     * Note: this uses blocking APIs, but data is small...
     */
     static HashMap<SqlQuery, String> loadQueries() throws IOException {

        HashMap<SqlQuery, String> sqlQueriesMap = new HashMap<>();

        InputStream queriesInputStream = DatabaseHandler.class.getResourceAsStream("/db-queries.properties");

        Properties queriesProps = new Properties();
        queriesProps.load(queriesInputStream);
        queriesInputStream.close();

        sqlQueriesMap.put(SqlQuery.INSERT_EMAIL_PREKICK, queriesProps.getProperty("insert-email-prekick"));

        sqlQueriesMap.put(SqlQuery.UPSERT_USER_CODE, queriesProps.getProperty("upsert-user-code"));
        sqlQueriesMap.put(SqlQuery.UPSERT_ATHLETE_IMAGE, queriesProps.getProperty("upsert-athlete-image"));

        sqlQueriesMap.put(SqlQuery.UPSERT_EMAIL_BOUNCED, queriesProps.getProperty("upsert-email-bounced"));
        sqlQueriesMap.put(SqlQuery.UPSERT_EMAIL_OPENED, queriesProps.getProperty("upsert-email-opened"));
        sqlQueriesMap.put(SqlQuery.UPSERT_EMAIL_COMPLAINT, queriesProps.getProperty("upsert-email-complaint"));
        sqlQueriesMap.put(SqlQuery.UPSERT_EMAIL_OPTOUT, queriesProps.getProperty("upsert-email-optout"));

        sqlQueriesMap.put(SqlQuery.INSERT_INDUSTRY_DATA, queriesProps.getProperty("insert-industry-data"));


        return sqlQueriesMap;
    }
}
