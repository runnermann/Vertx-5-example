package com.knocscore.web.access;

import com.knocscore.access.BucketName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.knocscore.access.ModelError;
import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * For user S3 access related methods
 * <pre>
 *      1. The user is authenticated or "AuthN".
 *      2. Generate Unique URL to resource
 *      3. return unique URL to user
 *      *
 *      1. We listen to the bus for the call from the HttpServer for either get, put, or list
 *      a deck or resource.
 *      2. We call the handler. The handler authenticates the user with either the username and pw
 *      or token.
 *      3. If the user is authenticated "AuthN" and (future) authorized "AuthZ" a presigned URL is
 *      requested from S3. The users app access S3 based on the IAM and the presignedURL.
 *      * </pre>
 */
public class AppAccessVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppAccessVerticle.class);

    private final int getDuration = 1000 * 60 * 7;
    private final int SIX_MINUTES = 6;


    /**
     * <p>Listen for call on bus and perform GET, PUT or LIST etc... if the user
     * is valid. </p>
     * <pre>
     *     1. Ensure user is logged in, and user is authorized.
     *     2. Generate Unique URL to resource
     *     3. return unique URL to user
     *
     *     requested from S3. The users app access S3 based on the IAM and the presignedURL
     * </pre>
     * @param promise
     * @throws Exception
     */
    @Override
    public void start(Promise<Void> promise) {

        LOGGER.debug("AppAccess verticle started ....");
        // Create a signed JWT or JWTS token
        // S3 access key related.
        // Create a signed JWT or JWTS token
        // S3 access key related.
        final ModelError dang = ModelError.getInstance();

        final String regionName = "us-west-2";
        final String[] errors = dang.getS3Errors();
        final AwsCredentials creds = AwsBasicCredentials.create(errors[0], errors[1]);
        S3Client s3Client = S3Client
                .builder()
                .region(Region.of(regionName))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();

        final EventBus eBus = vertx.eventBus();


        /*
         * returns a JsonArray containing the signedURLs for deck/card media put operation
         */
        eBus.consumer("web.media.putS3", message -> {
            LOGGER.debug("AppAccessVerticle, start, eBus listener for \"user.media.putS3\" called");
            JsonArray jArray = (JsonArray) message.body();
            JsonObject json = jArray.getJsonObject(0);
            Map<String, Object> metadata = json.getMap();
            String key = metadata.get("key").toString();

            JsonArray putURL = getS3PresignedPutURL(
                    key,
                    BucketName.AVATAR_BUCKET.getName());
            // send the accessURLs to the user
            message.reply(putURL);


            LOGGER.debug("AppAccessVerticle web.media.putS3... " +
                    "Implement JWT's in web page for better security");
        });
    }




    /**
     * Parses refreshCode
     * @param code
     * @return
     */
    private List<Integer> parseCode(String code) {
        List<Integer> l = new ArrayList<>(32);

        code = code.substring(0, code.length() - 1);
        String[] parts = code.split(",");
        for(String s : parts) {
            l.add(Integer.parseInt(s));
        }
        return l;
    }


    /**
     * <p>Get a JsonArray of presigned URLs to upload to S3.</p>
     * <p><b>NOTE: </b> This is NOT a parallel process. It currenlty is
     * a blocking method to create several presignedUrl's</p>
     * @param objKey "_hash_objectsName.webp"
     * @return returns an JsonArray of presignedURLs.
     */
    private JsonArray getS3PresignedPutURL(String objKey, String bucket) {
        final ModelError dang = ModelError.getInstance();
        final String[] errors = dang.getS3Errors();
        final AwsCredentials creds = AwsBasicCredentials.create(errors[0], errors[1]);

        LOGGER.debug("createS3MediaPutURL called");

        try {

            S3Presigner presigner = S3Presigner.builder()
                    .region(Region.US_WEST_2) // Specify the region
                    .credentialsProvider(StaticCredentialsProvider.create(creds))
                    .build();

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(objKey)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(SIX_MINUTES))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            String presignedURL = presignedRequest.url().toString();
            LOGGER.info("Presigned URL to upload a file to: [{}]", presignedURL);

            JsonArray returnArray = new JsonArray();
            returnArray.add(presignedURL);
            return returnArray;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * <p>Get a JsonArray of presigned URLs to upload to S3.</p>
     * <p><b>NOTE: </b> This is NOT a parallel process. It currenlty is
     * a blocking method to create several presignedUrl's</p>
     * @param jsonArray array of  _hash_objectsName.webp
     * @return returns an JsonArray of presignedURLs.
     */
    private JsonArray getS3PresignedPutURLs(JsonArray jsonArray, String bucket) {
        final ModelError dang = ModelError.getInstance();
        final String[] errors = dang.getS3Errors();
        final AwsCredentials creds = AwsBasicCredentials.create(errors[0], errors[1]);

        LOGGER.debug("createS3MediaPutURL called");
        JsonObject job = jsonArray.getJsonObject(1);
        String[] keys = job.getString("key").split(",");
        JsonArray returnArray = new JsonArray();

        try {
            for(String key : keys) {
                S3Presigner presigner = S3Presigner.builder()
                        .region(Region.US_WEST_2) // Specify the region
                        .credentialsProvider(StaticCredentialsProvider.create(creds))
                        .build();

                PutObjectRequest objectRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build();

                PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(SIX_MINUTES))  // The URL expires in 10 minutes.
                        .putObjectRequest(objectRequest)
                        .build();

                PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
                String presignedURL = presignedRequest.url().toString();
                returnArray.add(presignedURL);
                return returnArray;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnArray;
    }

}
