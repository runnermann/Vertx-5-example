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

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.*;
import io.vertx.rxjava3.sqlclient.SqlClient;


import java.util.HashMap;
import java.util.List;


/**
 * @author <a href="https://julien.ponge.org/">Julien Ponge</a>
 */
@ProxyGen
@VertxGen
public interface UserDatabaseService {

    @GenIgnore
    static UserDatabaseService create(SqlClient client, HashMap<SqlQuery, String> sqlQueries, Handler<AsyncResult<UserDatabaseService>> readyHandler) {
        return new UserDatabaseServiceImpl(client, sqlQueries, readyHandler);
    }

    @GenIgnore
    static com.knocscore.database.rxjava3.UserDatabaseService createProxy(Vertx vertx, String busAddress) {
        return new com.knocscore.database.rxjava3.UserDatabaseService(new UserDatabaseServiceVertxEBProxy(vertx, busAddress));
    }

//    @Fluent
//    UserDatabaseService dbCallForRollback(Handler<AsyncResult<Void>> resultHandler);

//    @Fluent
//    UserDatabaseService upsertEdGeneral(JsonArray params, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertMilService(JsonArray params, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertMilSvcImage(JsonArray params, Handler<AsyncResult<Void>> resultHandler);

//    @Fluent
//    UserDatabaseService insertPorEmployer(JsonArray params, Handler<AsyncResult<Void>> resultHandler);

//    @Fluent
//    UserDatabaseService userExists(String email, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService userExistsNoPw(String email, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService newUserCode(String code, long time, String email, String userName, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService finalizeNewUser(String userName, String pw, String salt, String myhash, String email, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchChilddeckExists(long parentDeckId, String userEmail, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchUserByEmail(String email, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchUserByUuid(int uuid, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService createUser(String email, String username, String password, String password_salt, String hash, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService createKnocUser(String email, String firstName, String lastName, String password, String password_salt, String myhash, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService saveUser(int id, String email, String username, String password, String password_salt, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService deleteUser(int id, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService deleteTestMonkey(String hash, String user_name, String orig_email, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService deletePredecessorMonkey(String userHash, Handler<AsyncResult<Void>> resultHandler );
//
//    @Fluent
//    UserDatabaseService fetchAllUsersData(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchAllUsersDecks(String email, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchkscoreprekickstats(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchKScoreBackers(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchDecksBySubject(String subject, String region, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchBulkEmailReqs( Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchBulkEmailOther( Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchBulkEmailDiff( Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService setCode(String email, String code, long set_time, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService validateCode(String email, String code, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService updatePW(String email, String hashedPw, String salt, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService updateStudent(String username, String password, String salt, String hash, String email, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchDeckPurchaseDetails(long deckID, String buyerEmail, String byrClrEmail, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchDeckNamePriceByQR(long deckID, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchCampaignByQR(long campaignID, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertCampaignKeyClick(long campaignID, String ipLoc, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertEmailPrekick(String email, String name, String employer, String type, boolean makePublic, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService updateStripeId(String origEmail, String stripeId, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStripeId(String orig_email, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchSubscriptId(String orig_email, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService setSubscriptionPayment(String catagory, String strip_cusid, String status, String orig_email, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchUserStats(String user_hash, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStatsEntity(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStatsPOS(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStatsProjects(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStatsAchievement(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStatsAthletics(String user_hash, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertAthlete(JsonArray params, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertAthleteImage(JsonArray params, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService updateBio(String hash, String descript, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStatsPeople(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStatsKnowledge(String user_hash, String domain_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchStudent(String user_hash, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService updateSubscriptOnID(String catagory, String status, String stripe_cusid, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService updateSubscriptTrialOnID(String catagory, String status, String date, String stripe_cusid, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertUserDeckStatsAmount(String userHash, long deck_id, int amount,  Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertEmailBounced(String email, boolean bounced, String note, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertEmailOpened(String email, boolean opened, String note, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertEmailComplaint(String email, boolean complaint, String note, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService upsertEmailOptout(String email, boolean optOut, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertKScoreDonateTrx(String stripe_trx_id, String object, String email, String name, int amount, String status, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertFeePay(long tax, long paid, long balance, String orig_email, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertSubscriptionStart(String subID, String custID, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertSubscriptionCancelledFmApp(String email, String reason, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertSubscriptionCancelledOnID(String email, String reason, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertVisitor(String endpoint, String loc, String referrer, String event, String userAgent, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertTransaction(String stripe_charge_id, String author_hash, String distributor_hash, String payer_hash,
//                                          String item_id, String item_descript, String type_payment, long amount, long fee,
//                                          String status, String notes, long creatorPay, long distroPay, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertPaymentReceived(long transId, long payerId, double amount, double tax, double fee, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertPurchasedChildDeck(String userEmail, String deckFullName, String userHash, long deck_id, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchHasPurchased(long deckId, String byrHash, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertNotePerson(String orig_email, String note, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertNoteSystem(long system_issue_id, String note, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertFMAppReq(String email, String ipLoc, String osType, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertPredecessorChain(String user_hash, String prev_hash, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertVideoChatHost(String host_Hash, String meeting_time, String meeting_type, String meeting_subject,
//                                            String deck_subj, String section, Double platform_fee, long req_duration, String auth_code,
//                                            Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService insertVideoChatAttendee(long meeting_id, String attendee_email, double host_fee, Handler<AsyncResult<Void>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchAcctNo(String hash, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//    @Fluent
//    UserDatabaseService fetchMemberStatus(String orig_email, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchAllBlogs(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    public UserDatabaseService fetchMemberList(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchBlogByID(long job_id, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchBlogRows(long blog_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchBlogRefs(long blog_id, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchAllJobs(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchJobByID(long job_id, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchTeam(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchUserHash(String email, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchPredecessorUser(String hash, Handler<AsyncResult<JsonObject>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchVideochatMeeting(long meetingID, Handler<AsyncResult<List<JsonObject>>> resultHandler);
//
//    @Fluent
//    UserDatabaseService fetchVideoChatPCode(Handler<AsyncResult<List<JsonObject>>> resultHandler);
//

}