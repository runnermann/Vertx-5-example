package com.runnermann.wolf.example.web.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


/**
 * The operations of the DB that are not insert, update, or select.
 */
public class DBOperationsMap {
    /**
    * Note: this uses blocking APIs, but data is small...
    */
    static HashMap<SqlQuery, String> loadQueries() throws IOException {
        // Create the hashmap
        HashMap<SqlQuery, String> sqlQueriesMap = new HashMap<>();
        // Get the file
        InputStream queriesInputStream = DatabaseHandler.class.getResourceAsStream("/db-queries.properties");

        Properties queriesProps = new Properties();
        queriesProps.load(queriesInputStream);
        queriesInputStream.close();

        // Put the queries from the properties file in the map.
        sqlQueriesMap.put(SqlQuery.DB_ROLLBACK, queriesProps.getProperty("db-rollback"));
        sqlQueriesMap.put(SqlQuery.DELETE_TEST_MONKEY, queriesProps.getProperty("delete-test-monkey"));

        return sqlQueriesMap;
    }
}
