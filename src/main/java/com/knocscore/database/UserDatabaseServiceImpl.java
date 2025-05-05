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


import com.knocscore.utility.BusAddressMap;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.SingleHelper;

import io.vertx.rxjava3.sqlclient.Row;
import io.vertx.rxjava3.sqlclient.SqlClient;
import io.reactivex.rxjava3.core.Completable;


import io.vertx.rxjava3.CompletableHelper;



import io.vertx.core.Future;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


import io.vertx.rxjava3.sqlclient.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
public class UserDatabaseServiceImpl implements UserDatabaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDatabaseServiceImpl.class);
    //private final static ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HttpServerVerticle.class);

    private final HashMap<SqlQuery, String> sqlQueries;
    private final SqlClient dbClient;

    UserDatabaseServiceImpl(SqlClient dbClient, HashMap<SqlQuery, String> sqlQueries, Handler<AsyncResult<UserDatabaseService>> readyHandler) {
        this.dbClient = dbClient;
        this.sqlQueries = sqlQueries;

        // Reactive initialization of the database
        this.dbClient.preparedQuery(sqlQueries.get(SqlQuery.CREATE_USERS_TABLE))
                .rxExecute()
                .flatMapCompletable(result -> Completable.complete()) // Ignore result, complete only
                .subscribe(
                        // Success
                        () -> readyHandler.handle(Future.succeededFuture(this)),
                        // Failure
                        throwable -> {
                            LOGGER.error("Failed to create users table", throwable);
                            readyHandler.handle(Future.failedFuture(throwable));
                        });

    }




    //------------------ START CUSTOM CODE -------------------- //

//    @Override
//    public UserDatabaseService dbCallForRollback(Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.preparedQuery(sqlQueries.get(SqlQuery.DB_ROLLBACK))
//                .rxExecute()
//                .ignoreElement()
//                .subscribe( () -> {
//                    CompletableHelper.toObserver(resultHandler);
//                });
//        return this;
//    }



//    @Override
//    public UserDatabaseService upsertIndustryUserInput(String entityName, String description, String website, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.QR_FETCH_CAMPAIGN), new JsonArray().add(campaignID))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }


//    @Override
//    public UserDatabaseService insertPorEmployer(JsonArray params, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.preparedQuery(sqlQueries.get(SqlQuery.INSERT_POR_EMPLOYER))
//                .rxExecute(Tuple.of(params))
//                .ignoreElement()
//                .subscribe( () -> {
//                    CompletableHelper.toObserver(resultHandler);
//                });
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertEdGeneral(JsonArray params, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.UPSERT_ED_GENERAL), params)
//                .ignoreElement()
//                .subscribe( () -> {
//                    CompletableHelper.toObserver(resultHandler);
//                });
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertMilService(JsonArray params, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.UPSERT_MIL_SERVICE), params)
//                .ignoreElement()
//                .subscribe( () -> {
//                    CompletableHelper.toObserver(resultHandler);
//                });
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertMilSvcImage(JsonArray params, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.UPSERT_MIL_IMAGE), params)
//                .ignoreElement()
//                .subscribe( () -> {
//                    CompletableHelper.toObserver(resultHandler);
//                });
//        return this;
//    }

//    @Override
//    public UserDatabaseService upsertAthlete(JsonArray params, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPSERT_ATHLETE), params)
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertAthleteImage(JsonArray params, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPSERT_ATHLETE_IMAGE), params)
//                .ignoreElement()
//                .subscribe( () -> {
//                    CompletableHelper.toObserver(resultHandler);
//                });
//        return this;
//    }
//
//
//    @Override
//    public UserDatabaseService updateBio(String hash, String descript, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPDATE_BIO), new JsonArray().add(descript).add(hash))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//
//    // example fetchAllPagesData in wiki-step-9
//    @Override
//    public UserDatabaseService fetchAllJobs(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery(sqlQueries.get(SqlQuery.FETCH_ALL_JOBS))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchAllBlogs(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery(sqlQueries.get(SqlQuery.FETCH_ALL_BLOGS))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//         return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchMemberList(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery(sqlQueries.get(SqlQuery.FETCH_MEMBERS_LIST))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchBlogRows(long blog_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_BLOG_ROWS), new JsonArray().add(blog_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchBlogRefs(long blog_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_BLOG_REFS), new JsonArray().add(blog_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchBlogByID(long blog_id, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.preparedQuery(
//                sqlQueries.get(SqlQuery.FETCH_BLOG_BY_ID))
//                .rxExecute(Tuple.of(blog_id))
//                .map(rows -> {
//                    if (rows.rowCount() > 0) {
//                        Row row = rows.iterator().next(); // Fetch the single expected row
//                        return new JsonObject()
//                                .put("found", true)
//                                .put("article", row.getString("article"))
//                                .put("title", row.getString("title"))
//                                .put("read_time", row.getString("read_time"))
//                                .put("meta_descript", row.getString("meta_descript"))
//                                .put("card_info", row.getString("card_info"))
//                                .put("author", row.getString("author"))
//                                .put("create_date", row.getString("create_date"))
//                                .put("change_date", row.getString("change_date"))
//                                .put("change_descript", row.getString("change_descript"))
//                                .put("page_endpoint", row.getString("page_endpoint"))
//                                .put("image_link", row.getString("image_link"))
//                                .put("image_twitter_link", row.getString("image_twitter_link"));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchJobByID(long job_id, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_JOB_BY_ID), new JsonArray().add(job_id))
//                .map(result -> {
//                    if (result.getNumRows() > 0) {
//                        JsonArray row = result.getResults().get(0);
//                        return new JsonObject()
//                                .put("found", true)
//                                .put("job_id", row.getInteger(0))
//                                .put("job_position", row.getString(1))
//                                .put("employment_type", row.getString(2))
//                                .put("duration", row.getString(3))
//                                .put("description", row.getString(4))
//                                .put("pay", row.getString(5))
//                                .put("incentives", row.getString(6))
//                                .put("job_location", row.getString(7))
//                                .put("remote", row.getString(8))
//                                .put("work_auth", row.getString(9))
//                                .put("international", row.getString(10))
//                                .put("required_docs", row.getString(11))
//                                .put("required_knowledge", row.getString(12))
//                                .put("major", row.getString(13));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchTeam(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery(
//                sqlQueries.get(SqlQuery.FETCH_TEAM))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchUserByEmail(String email, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.GET_USER_BY_EMAIL), new JsonArray().add(email))
//                .map(result -> {
//                    if (result.getNumRows() > 0) {
//                        JsonArray row = result.getResults().get(0);
//                        return new JsonObject()
//                                .put("found", true)
//                                .put("uuid", row.getInteger(0))
//                                .put("rawContent", row.getString(1));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchVideoChatPCode(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery(
//                sqlQueries.get(SqlQuery.FETCH_VIDEOCHAT_PCODE))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService userExists(String email, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.USER_EXISTS), new JsonArray().add(email))
//                .map(result -> {
//                    if (result.getNumRows() > 0) {
//                        return new JsonObject()
//                                .put("found", true);
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//
//    /**
//     * If the user exists and the pw salt is less than 12 chars, or if the user does not exist,
//     * will return that the user does not exist. This enables a user to retry setting thier account
//     * from the application while also preventing data overwrite.
//     */
//    @Override
//    public UserDatabaseService userExistsNoPw(String email, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.GET_USER_BY_EMAIL), new JsonArray().add(email))
//                .map(result -> {
//                    LOGGER.info("userExistsNoPW called");
//                    if (result.getNumRows() == 1) {
//                        JsonObject row = result.getRows().get(0);
//                        String salt = row.getString("password_salt");
//                        if(salt != null && salt.length() < 12) {
//                            // for step 2 of user creation from the
//                            // application.
//                            return new JsonObject().put("found", false);
//                        }
//                        return new JsonObject().put("found", true);
//                    }
//                    return new JsonObject().put("found", false);
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService newUserCode(String code, long time, String email, String userName, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.UPSERT_USER_CODE), new JsonArray().add(code).add(time).add(email).add(userName).add(code).add(time))
//                .map(result -> {
//                    LOGGER.debug("newUserCode called");
//                    return new JsonObject().put("found", true);
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchUserByUuid(int uuid, Handler<AsyncResult<JsonObject>> resultHandler) {
//        Single<ResultSet> resultSet = dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.GET_USER_BY_UUID), new JsonArray().add(uuid));
//                resultSet
//                    .map(result -> {
//                        if (result.getNumRows() == 1) {
//                            JsonObject row = result.getRows().get(0);
//                            return new JsonObject()
//                                    .put("found", true)
//                                    .put("uuid", row.getInteger("uuid"))
//                                    .put("email", row.getString("email"))
//                                    .put("userName", row.getString("username")) // Formerly "CONTENT"
//                                    .put("password", row.getString("password"))
//                                    .put("password_salt", row.getString("password_salt"));
//                        } else {
//                            return new JsonObject().put("found", false);
//                        }
//                    })
//                    .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService setSubscriptionPayment(String catagory, String cus_id, String status, String encOrig_email,  Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPDATE_SUBSCRIPTION), new JsonArray().add(catagory).add(cus_id).add(status).add(encOrig_email))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService updateStudent(String username, String password, String salt, String hash, String email, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPDATE_STUDENT), new JsonArray()
//                .add(username).add(password).add(salt).add(hash).add(email)) // students
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService finalizeNewUser(String userName, String pw, String salt, String myhash, String email, Handler<AsyncResult<Void>> resultHandler) {
//        int[] encEmail = UserAlphabet.encryptToIntAry(email);
//        // If the user has not already been set in the predicessor chain, this sets it to the default.
//        String default_prev_hash = "8797d6ebd034c235a2d5aa08a39501f5";
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.FINALIZE_NEW_USER), new JsonArray()
//                .add(myhash).add(default_prev_hash).add(default_prev_hash).add(default_prev_hash).add(default_prev_hash) // predecessorchain
//                .add(myhash).add(encEmail) // emailHash
//                .add(UserAlphabet.encryptToIntAry(userName)).add(encEmail).add(encEmail) // person
//                .add(encEmail) // account
//                .add(encEmail)) // student
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService createUser(String email, String userName, String password, String password_salt, String myhash, Handler<AsyncResult<Void>> resultHandler) {
//        int[] encEmail = UserAlphabet.encryptToIntAry(email);
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.CREATE_USER), new JsonArray().add(email).add(userName).add(password).add(password_salt).add(myhash)
//                        .add(myhash).add(encEmail) // emailHash
//                        .add(UserAlphabet.encryptToIntAry(userName)).add(encEmail).add(encEmail) // person
//                        .add(encEmail) // account
//                        .add(encEmail)) // student
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//    // The data base does not provide the storage names, they are x1 x2 and x3.
//    // x1 = hash, x2 = encrypted password, x3 = salt.
//    @Override
//    public UserDatabaseService createKnocUser(String email, String firstName, String lastName, String encPassword, String password_salt, String hash, Handler<AsyncResult<Void>> resultHandler) {
//        int[] encEmail = UserAlphabet.encryptToIntAry(email);
//        int[] encFirstName = UserAlphabet.encryptToIntAry(firstName);
//        int[] encLastName = UserAlphabet.encryptToIntAry(lastName);
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.CREATE_KNOC_USER),
//                        new JsonArray()
//                .add(hash).add(encPassword).add(password_salt) // emailHash
//                .add(encFirstName).add(encLastName).add(encEmail).add(encEmail).add(hash) // person
//                .add(hash).add(encEmail))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService saveUser(int uuid, String email, String userName, String password, String password_salt, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.SAVE_USER), new JsonArray().add(email).add(password).add(userName).add(password_salt).add(uuid))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService deleteUser(int uuid, Handler<AsyncResult<Void>> resultHandler) {
//        JsonArray data = new JsonArray().add(uuid);
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.DELETE_USER), data)
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService deleteTestMonkey(String hash, String user_name, String orig_email, Handler<AsyncResult<Void>> resultHandler ) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.DELETE_TEST_MONKEY), new JsonArray().add(hash).add(user_name).add(orig_email).add(orig_email).add(orig_email))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService deletePredecessorMonkey(String userHash, Handler<AsyncResult<Void>> resultHandler ) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.DELETE_PREDECESSOR_MONKEY), new JsonArray().add(userHash))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchAllUsersData(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery(sqlQueries.get(SqlQuery.ALL_USERS_DATA))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchUserHash(String email, Handler<AsyncResult<JsonObject>> resultHandler) {
//        Single<ResultSet> resultSet = dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_USER_HASH), new JsonArray().add(email));
//                resultSet
//                        .map(result -> {
//                            if(result.getNumRows() == 1) {
//                                JsonObject row = result.getRows().get(0);
//                                return new JsonObject()
//                                        .put("found", true)
//                                        .put("hash", row.getString("hash"));
//                            } else {
//                                return new JsonObject().put("found", false);
//                            }
//                        })
//                        .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    /**
//     * Returns a single users list of decks.
//     * @param email
//     * @param resultHandler
//     * @return
//     */
//    @Override
//    public UserDatabaseService fetchAllUsersDecks(String email, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.ALL_USERS_DECKS), new JsonArray().add(email))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchDecksBySubject(String subject, String region, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery(sqlQueries.get(SqlQuery.DECKS_BY_SUBJECT))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService setCode(String email, String code, long set_time, Handler<AsyncResult<Void>> resultHandler) {
//        // must convert the string set_time to an integer.
//        // Better for storage.
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.SET_CODE), new JsonArray().add(code).add(set_time).add(email))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    /**
//     * Fetch the code that is set to validate the correct user with their email or phone(when implemented).
//     * @param email
//     * @param code
//     * @param resultHandler
//     * @return
//     */
//    @Override
//    public UserDatabaseService validateCode(String email, String code, Handler<AsyncResult<JsonObject>> resultHandler) {
//        Single<ResultSet> resultSet = dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_CODE), new JsonArray().add(email));
//                // fetch the code and valid time from the db using fetchCode
//                resultSet
//                    .map(result -> {
//                        if (result.getNumRows() > 0) {
//                            JsonArray jAry = result.getResults().get(0);
//                            // subtract 15 minutes from now as valid time
//                            long validTime = System.currentTimeMillis() - 900000;
//                            // 0st column is code as a String, 2rd column set_time as an Integer
//                            if(code.equals(jAry.getString(0))  && (validTime < jAry.getLong(1))) {
//                                return new JsonObject()
//                                        .put("valid", true);
//                            }
//                        }
//                        return new JsonObject()
//                                .put("valid", false);
//                    })
//                    .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService updatePW(String email, String hashedPw, String salt, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.UPDATE_PW), new JsonArray().add(hashedPw).add(salt).add(email))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//
//        //LOGGER.info("updatePW completed.");
//        return this;
//    }
//
//    // ---- ACCOUNT RELATED -----
//
//    @Override
//    public UserDatabaseService fetchDeckPurchaseDetails(long deckID, String buyerEmail, String buyerClrEmail, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_DECK_PUR_DETAILS), new JsonArray().add(deckID).add(buyerEmail).add(buyerClrEmail))
//                .map(result -> {
//                    LOGGER.debug("called UserDatabaseServiceImpl.fetchDeckPurcahseDetails. there is data. Num rows: <{}>", result.getNumRows() );
//                    if (result.getNumRows() == 1) {
//                        LOGGER.debug("called UserDatabaseServiceImpl.fetchDeckPurcahseDetails. there is one row of data");
//                        JsonArray row = result.getResults().get(0);
//                        return new JsonObject()
//                                .put("found", true)
//                                .put("price", row.getLong(0))
//                                .put("creator_email", row.getString(1))
//                                .put("distro_email", row.getString(2))
//                                .put("share_distro", row.getString(3))
//                                .put("full_name", row.getString(4))
//                                // creator
//                                .put("auth_stripe_acct_id", row.getString(5))
//                                .put("auth_acct_status", row.getString(6))//acct_status,
//                                .put("auth_paid_date", row.getString(7))//paid_date
//                                .put("auth_currency", row.getString(8))//currency
//                                // distributor
//                                .put("dist_stripe_acct_id", row.getString(9))
//                                .put("dist_acct_status", row.getString(10))//acct_status,
//                                .put("dist_paid_date", row.getString(11))//paid_date
//                                .put("dist_currency", row.getString(12))//currency
//                                // buyer
//                                .put("byr_acct_status", row.getString(13))//acct_status,
//                                .put("byr_paid_date", row.getString(14))//paid_date
//                                .put("byr_currency", row.getString(15))//currency
//                                //The platform Fee (FlashMonkey) This is paid by the remainder left from the transfers
//                                .put("fee", row.getLong(16))
//                                // the buyers hash
//                                .put("hash", row.getString(17));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchHasPurchased(long deckId, String byrHash, Handler<AsyncResult<JsonObject>> resultHandler) {
//        Single<ResultSet> resultSet = dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_HASPURCHASED), new JsonArray().add(deckId).add(byrHash));
//        resultSet
//                .map(result -> {
//                    if(result.getNumRows() == 1) {
//                        JsonObject row = result.getRows().get(0);
//                        return new JsonObject()
//                                .put("found", true)
//                                .put("deck_id", row.getString("deck_id"));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchDeckNamePriceByQR(long deckID, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//            dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.QR_FETCH_DECK_NAME_PRICE), new JsonArray().add(deckID))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//                return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchCampaignByQR(long campaignID, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.QR_FETCH_CAMPAIGN), new JsonArray().add(campaignID))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchChilddeckExists(long parentDeckId, String userEmail, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_CHILDDECK_EXISTS), new JsonArray().add(parentDeckId).add(userEmail))
//                .map(result -> {
//                    JsonArray row = result.getResults().get(0);
//                    return new JsonObject()
//                            .put("found", true);
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchPredecessorUser(String hash, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_PREDECESSOR_USER), new JsonArray().add(hash))
//                .map(result -> {
//                    JsonArray row = result.getResults().get(0);
//                    return new JsonObject()
//                            .put("found", true)
//                            .put("origin_hash", row.getString(0))
//                            .put("gp_hash", row.getString(1))
//                            .put("gp_rep_hash", row.getString(2))
//                            .put("prev_hash", row.getString(3));
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//
//    @Override
//    public UserDatabaseService fetchStudent(String user_hash, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_STUDENT), new JsonArray().add(user_hash))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchUserStats(String user_hash, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams( sqlQueries.get(SqlQuery.FETCH_USER_STATS), new JsonArray().add(user_hash).add(user_hash).add(user_hash))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchStatsEntity(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams( sqlQueries.get(SqlQuery.FETCH_STATS_ENTITY), new JsonArray().add(user_hash).add(domain_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchStatsPOS(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_STATS_POS), new JsonArray().add(user_hash).add(domain_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchStatsProjects(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_STATS_PROJECTS), new JsonArray().add(user_hash).add(domain_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchStatsAchievement(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_STATS_ACHIEVEMENT), new JsonArray().add(user_hash).add(domain_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchStatsAthletics(String user_hash, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_STATS_ATHLETICS), new JsonArray().add(user_hash))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//
//
//    @Override
//    public UserDatabaseService fetchStatsPeople(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_STATS_PEOPLE), new JsonArray().add(user_hash).add(domain_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchStatsKnowledge(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_STATS_KNOWLEDGE), new JsonArray().add(user_hash).add(domain_id))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchkscoreprekickstats(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery( sqlQueries.get(SqlQuery.FETCH_KSCORE_PREKICK_STATS))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    public UserDatabaseService fetchKScoreBackers(Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQuery( sqlQueries.get(SqlQuery.SELECT_PREKICK_BACKERS))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//
//    @Override
//    public UserDatabaseService updateStripeId(String origEmail, String stripeId, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPDATE_STRIPE_ID), new JsonArray().add(stripeId).add(origEmail))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchStripeId(String orig_email, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_STRIPE_ID), new JsonArray().add(orig_email))
//                .map(result -> {
//                    if (result.getNumRows() == 1) {
//                        JsonArray row = result.getResults().get(0);
//                        return new JsonObject()
//                                .put("found", true)
//                                .put("stripe_acct_id", row.getString(0));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchSubscriptId(String orig_email, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_SUBSCRIPT_ID), new JsonArray().add(orig_email))
//                .map(result -> {
//                    if (result.getNumRows() == 1) {
//                        JsonArray row = result.getResults().get(0);
//                        return new JsonObject()
//                                .put("found", true)
//                                .put("stripe_subid", row.getString(0));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchAcctNo(String hash, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_ACCT_NO), new JsonArray().add(hash))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchMemberStatus(String orig_email, Handler<AsyncResult<JsonObject>> resultHandler) {
//        dbClient.rxQueryWithParams(
//                sqlQueries.get(SqlQuery.FETCH_MEMBER_STATUS), new JsonArray().add(orig_email))
//                .map(result -> {
//                    if (result.getNumRows() == 1) {
//                        JsonArray row = result.getResults().get(0);
//                        return new JsonObject()
//                                .put("found", true)
//                                // check if account is suspended, normal, or special?
//                                .put("account_status", row.getString(0))
//                                // check type of account
//                                .put("catagory", row.getString(1))
//                                // what was paid
//                                .put("stripe_acct_id", row.getString(2))
//                                // we get the stripe_cusid in case
//                                // the acct_id is not set
//                                .put("stripe_cusid", row.getString(3))
//                                // when it was paid
//                                .put("date", row.getString(4));
//                    } else {
//                        return new JsonObject().put("found", false);
//                    }
//                })
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchBulkEmailReqs( Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_BULK_EMAIL_REQS), new JsonArray())
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchBulkEmailOther( Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_EMAILS_OTHER), new JsonArray())
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchBulkEmailDiff( Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_EMAILS_DIFF), new JsonArray())
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService fetchVideochatMeeting(long meetingID, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.FETCH_VIDEOCHAT_MEETING), new JsonArray().add(meetingID))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertEmailBounced(String email, boolean bounced, String note, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPSERT_EMAIL_BOUNCED), new JsonArray().add(email).add(bounced).add(note).add(bounced).add(note))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertEmailOpened(String email, boolean opened, String note, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPSERT_EMAIL_OPENED), new JsonArray().add(email).add(opened).add(note).add(opened).add(note))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertEmailComplaint(String email, boolean complaint, String note, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPSERT_EMAIL_COMPLAINT), new JsonArray().add(email).add(complaint).add(note).add(complaint).add(note))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertEmailOptout(String email, boolean optOut, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPSERT_EMAIL_OPTOUT), new JsonArray().add(email).add(optOut).add(optOut))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertKScoreDonateTrx(String stripe_trx_id, String object, String email, String name, int amount, String status, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.INSERT_KSCORE_DONATE_TRX), new JsonArray().add(stripe_trx_id).add(object).add(email).add(name).add(amount).add(status))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertCampaignKeyClick(long campaignID, String locIP, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.INSERT_CAMPAIGN_KEYCLICK), new JsonArray().add(campaignID).add(locIP))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertEmailPrekick(String email, String name, String employer, String type, boolean makePublic, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.INSERT_EMAIL_PREKICK), new JsonArray().add(email).add(name).add(employer).add(type).add(makePublic))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertPurchasedChildDeck(String userEmail, String newDeckFullName, String userHash, long deckID, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.INSERT_PURCHASED_CHILDDECK), new JsonArray().add(userEmail).add(newDeckFullName).add(userHash).add(deckID).add(deckID))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertVisitor(String endpoint, String loc, String referrer, String event, String userAgent, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_VISITOR), new JsonArray().add(endpoint).add(loc).add(referrer).add(event).add(userAgent))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertFeePay(long tax, long paid, long balance, String orig_email, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_MEMBERSHIP_FEE), new JsonArray().add(tax).add(paid).add(balance).add(orig_email))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertSubscriptionStart(String subID, String custID, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.SUBSCRIPT_START), new JsonArray().add(subID).add(custID))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService updateSubscriptOnID(String catagory, String status, String cusid, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPDATE_SUBSCRIPTION_ONID), new JsonArray().add(catagory).add(status).add(cusid))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService updateSubscriptTrialOnID(String catagory, String status, String date, String cusid, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.UPDATE_SUBSCRIPTION_ONID), new JsonArray().add(catagory).add(status).add(date).add(cusid))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService upsertUserDeckStatsAmount(String userHash, long deck_id, int amount,  Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.UPSERT_USER_DECKSTATSAMOUNT), new JsonArray().add(userHash).add(deck_id).add(amount).add(amount))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertSubscriptionCancelledFmApp(String email, String reason, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.SUBSCRIPT_CANCEL), new JsonArray().add(reason).add(email))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertSubscriptionCancelledOnID(String cusID, String reason, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.SUBSCRIPT_CANCEL_ONID), new JsonArray().add(reason).add(cusID))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertTransaction(String stripe_charge_id, String author_hash, String distributor_hash,
//                                                 String payer_hash, String deck_id, String deck_descript,
//                                                 String type_payment, long amount, long fee, String status,
//                                                 String notes, long creatorPay, long distroPay, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_TRANSACTION), new JsonArray().add(stripe_charge_id)
//                        .add(author_hash).add(distributor_hash).add(payer_hash).add(deck_id).add(deck_descript)
//                        .add(type_payment).add(amount).add(fee).add(status).add(notes).add(creatorPay).add(distroPay))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertPaymentReceived(long transId, long payerId, double amount, double tax, double fee, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_PAYMENT_RECIEVED), new JsonArray().add(transId).add(payerId).add(amount).add(tax).add(fee))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertNotePerson(String orig_email, String note, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_NOTE_PERSON), new JsonArray().add(orig_email).add(note))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertNoteSystem(long system_issue_id, String note, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_NOTE_SYSTEM), new JsonArray().add(system_issue_id).add(note))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertFMAppReq(String email, String ipLoc, String osType, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_APP_REQ), new JsonArray().add(email).add(ipLoc).add(osType))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//
//    }
//
//    @Override
//    public UserDatabaseService insertPredecessorChain(String user_hash, String prev_hash, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_PREDECESSORCHAIN), new JsonArray().add(user_hash).add(prev_hash).add(prev_hash).add(prev_hash).add(prev_hash))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }
//    // NOTE: We are inserting, and returning the ID. Note the handler!
//    @Override
//    public UserDatabaseService insertVideoChatHost(String host_Hash, String meeting_type, String meeting_time, String meeting_subject,
//                                            String deck_subj, String section, Double platform_fee, long req_duration,
//                                            String meeting_code, Handler<AsyncResult<List<JsonObject>>> resultHandler) {
//        dbClient.rxQueryWithParams(sqlQueries.get(SqlQuery.INSERT_VIDEOCHAT_HOST),new JsonArray().add(host_Hash).add(meeting_type).add(meeting_time)
//                .add(meeting_subject).add(deck_subj).add(section).add(platform_fee).add(req_duration).add(meeting_code))
//                .map(ResultSet::getRows)
//                .subscribe(SingleHelper.toObserver(resultHandler));
//        return this;
//    }
//
//    @Override
//    public UserDatabaseService insertVideoChatAttendee(long meeting_id, String attendee_email, double host_fee, Handler<AsyncResult<Void>> resultHandler) {
//        dbClient.rxUpdateWithParams(sqlQueries.get(SqlQuery.INSERT_VIDEOCHAT_ATTENDEE), new JsonArray().add(meeting_id).add(attendee_email).add(host_fee))
//                .ignoreElement()
//                .subscribe(CompletableHelper.toObserver(resultHandler));
//        return this;
//    }



}
