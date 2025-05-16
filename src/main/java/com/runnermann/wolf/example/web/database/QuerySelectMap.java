package com.runnermann.wolf.example.web.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class QuerySelectMap {


    /*
     * Note: this uses blocking APIs, but data is small...
     */
     static HashMap<SqlQuery, String> loadQueries() throws IOException {

        HashMap<SqlQuery, String> sqlQueriesMap = new HashMap<>();

        InputStream queriesInputStream = DatabaseHandler.class.getResourceAsStream("/db-queries.properties");

        Properties queriesProps = new Properties();
        queriesProps.load(queriesInputStream);
        queriesInputStream.close();


        sqlQueriesMap.put(SqlQuery.SELECT_PREKICK_BACKERS, queriesProps.getProperty("select-prekick-backers"));
        sqlQueriesMap.put(SqlQuery.FETCH_MEMBERS_LIST, queriesProps.getProperty("fetch-members-list"));
        sqlQueriesMap.put(SqlQuery.FETCH_ALL_BLOGS, queriesProps.getProperty("fetch-all-blogs"));
        sqlQueriesMap.put(SqlQuery.FETCH_BLOG_BY_ID, queriesProps.getProperty("fetch-blog-by-id"));
        sqlQueriesMap.put(SqlQuery.FETCH_BLOG_ROWS, queriesProps.getProperty("fetch-blog-rows"));
        sqlQueriesMap.put(SqlQuery.FETCH_BLOG_REFS, queriesProps.getProperty("fetch-blog-refs"));

        return sqlQueriesMap;
    }

}
