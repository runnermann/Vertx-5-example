package com.knocscore.access;

import com.knocscore.fmannotations.FMAnnotations;

import javax.crypto.SecretKey;
import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>Singleton class</p>
 * <p>It is expected that the Main method of this class is used to encrypt the S3 IAM keys that will
 * be used to retrieve resources and signedURLs from S3. Encryption should be done when a new set
 * of keys for the IAM are created. The key may be placed in the Error class. After encrypted
 * two files are saved to the root. The .cpr file which contains the Cipher Encryption key ysed to
 * decrypt the s3 keys. The encrypted s3 keys are stored in the .enc file. 1) uncomment the main
 * class and the Error class. 2) replace the key with the new S3 IAM keys. 3) run the main method.
 * 4) comment out the main method and the Error class.</p>
 *
 * NOTE! Comment out the plain text keys from the Error class before shipping!!!!!
 *
 * NOTE: if there is an error during runtime. Check that the keysfiles created are from the same
 * version. This is a serializable class. Files that are not built by the same version will cause a
 * runtime crash when Vertx is initialized.
 */
public class ModelError {
    //public static final long serialVersionUID = 20201112l;
    private static ModelError CLASS_INSTANCE;

//    private static final Logger LOGGER = LoggerFactory.getLogger(ModelError.class);
//    private final static ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ModelError.class);
    private static final int IV_LENGTH_BYTE = 12;
    private static final int AES_KEY_BIT = 256;

    private static final int ARY_LENGTH = 14; // 25 April 2025 LS

    // stores the SecretKey and nonce used to decrypt the
    // s3 keys
    private File cypherKeyFile = new File("zero.cpr");
    // stores the encrypted S3 keys
    private File keysFile = new File("one.enc");


    private Dec dec;

    private ModelError() {
        // uncomment when creating files is completed.
        // comment out when creating files.
        init();
    }

    public static synchronized ModelError getInstance() {
        if(CLASS_INSTANCE == null) {
            CLASS_INSTANCE = new ModelError();
        }
        return CLASS_INSTANCE;
    }


    // *** public methods used when vertx server is deployed and operational ***//


    public boolean arrIsEmpty() {
        return dec.arr.length == 0;
    }

    private void init() {

        try {
            this.setModelError();
        }
        catch (Exception e) {
            //LOGGER.warn("CRITICAL ERROR!!!: ModelError threw exception in AppAccessVerticle {} \n{}", e.getMessage(), e.getStackTrace());
            e.printStackTrace();
            // System.exit(1);
        }
    }

    /**
     * Returns the keys for S3
     * @return
     */
    public String[] getS3Errors() {
        String[] sArr = new String[2];
        sArr[0] = dec.arr[0];
        sArr[1] = dec.arr[1];
        return sArr;
    }

    /**
     * Returns the keys for the DB connection
     * @return
     */
    public String[] getDBConnectErrors() {
        String[] sArr = new String[2];
        sArr[0] = dec.arr[8];
        sArr[1] = dec.arr[9];
        return sArr;
    }

    /**
     * Note that the first two of the dec array are
     * subtracted from this array. The index number
     * starts from dec.arr at idx 2. Thus dec.arr[ 4 ] == getEpirtsErrors()[ 2 ]
     * @return
     */
    public String getEpirtsErrors(int idx) {
        return dec.arr[idx];
    }


    private void setModelError() throws Exception {
        Syekic icDec;
        InnerOps<Syekic> innoDec1 = new InnerOps<Syekic>();
        // just gets the file
        icDec = innoDec1.getSyekFile(cypherKeyFile.getName());

        // 2. retrieve keys and store in memory
        InnerOps<Syek> innoDec2 = new InnerOps<Syek>();
        // double byte array
        Syek syekDec = innoDec2.getSyekFile(keysFile.getName());

        // 2.a. The decrypted keys stored in memory.
        dec = new Dec();
        // !!!!! if fails first time, the length is different in the file.
        // Run a second time.
        for(int i = 0; i < dec.arr.length; i++) {
            dec.arr[i] = Util.decryptWithPrefixIV(syekDec.arr[i], icDec.one);
            //LOGGER.debug("decrypted key: " + dec.arr[i]);
        }
    }
     // Used for saving the cipher key
     // to file. One is the AES/GCM SecretKey
     // two is the nonce;
    private static class Syekic implements Serializable {
        public static final long serialVersionUID = 20201112l;
        public Syekic() {/* No args constructor */}
        private SecretKey one;
        //private byte[] iv;
    }
     // one is accessKey and two is SecretKey for s3.
     // Used to store encrypted keys in file.
    private static class Syek implements Serializable {
        public Syek() {/* no args constructor */}
        public static final long serialVersionUID = 20201112l;
        private byte[][] arr = new byte[ARY_LENGTH][];
    }
     // Stores the unencrypted keys in memory. One is S3AccessKey
     // and two is s3 SecretKey
    private static class Dec {
        String[] arr = new String[ARY_LENGTH];
    }
    private static class InnerOps<T> {
        //public static final long serialVersionUID = 20201112l;
        void createSyekFile(T syek, File m) {
            //for(int i = 0; i < m.keyFiles.length; i++) {
                try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/main/resources/" + m.getName()), 512))) {
                    //m.keyFiles[i].createNewFile();
                    out.writeObject(syek);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //}
        }

        /**
         * gets the encrypted keys from the enryptedFile.dat
         * @param fileName
         * @return
         */
        T getSyekFile(String fileName) {
            //if (new File("/" + fileName).exists()) {
                T t = null;
                try (ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(getClass().getResourceAsStream("/" + fileName)))) {
                    while (true) {
                        t = (T) input.readObject();
                    }
                } catch (EOFException e) {
                    // expected. do nothing
                } catch (FileNotFoundException e) {

                    //e.printStackTrace();
                } catch (IOException e) {
                    //e.printStackTrace();
                } catch (ClassNotFoundException e) {
                   // e.printStackTrace();
                } finally {
                    return t;
                }
            //} else {
            //    System.err.println("I cannot find the file at /app/" + fileName);
            //}
            //return null;
        }
    }

    /*  ******* DO BEFORE SHIPPING ******** */
    /* Uncomment to encrypt and save to file the s3 IAM access and secret keys */
    /*
    Note that the main method calls the class instance. Class instance calls init() that is
    used when the vertx server is deployed on its host. init() is not needed for the main
    method. If the files do not exist, it will cause an error. Comment out init() while creating
    the files. Uncomment init() when done creating the files.

    For testing, use test keys. When running main method, the result should print test keys not
    live key. And vice versa.
     */

 /*   @FMAnnotations.DoNotDeployMethod
    public static void main(String[] args) throws Exception {
       // *********** Encryption is conducted prior to a build or after creating a new IAM key for S3 *********** //
        String OUTPUT_FORMAT = "%-30s:%s";
        ModelError m = ModelError.getInstance();

        // 1. create SecretKey
        // encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key
        Syekic icEnc = new Syekic();
        icEnc.one = Util.getAESKey(AES_KEY_BIT);
        // 2. create IV
        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
        byte[] iv = Util.getRandomNonce(IV_LENGTH_BYTE);

        // 3. save secretKey and IV to file
        InnerOps<Syekic> innoEnc1 = new InnerOps<>();
        innoEnc1.createSyekFile(icEnc, m.cypherKeyFile);
        // 4. encrypt S3 & Stripe keys with Secret key & iv
        Syek syekEnc = new Syek();
        // s3 and Stripe accessKey, Cipher SecretKey and Cipher IV
        Error error = new Error();
        for(int i = 0; i < syekEnc.arr.length; i++) {
            syekEnc.arr[i] = Util.encryptWithPrefixIV(error.arr[i].getBytes(UTF_8), icEnc.one, iv);
        }
        // 5. save encrypted keys to file
        InnerOps<Syek> inno2 = new InnerOps<>();
        /// save array of keys to file
        inno2.createSyekFile(syekEnc, m.keysFile);

        // verify output is correct

        System.out.println("\n------ AES GCM Encryption ------");
        System.out.println(String.format(OUTPUT_FORMAT, "Input (acc text)", Error.encryptionKey));
        System.out.println(String.format(OUTPUT_FORMAT, "Input (sec text)", Error.s3secret));
        System.out.println(String.format(OUTPUT_FORMAT, "encrypted Key (hex)", Util.hex(icEnc.one.getEncoded())));

        System.out.println("\n------ AES GCM Decryption ------");
        System.out.println(String.format(OUTPUT_FORMAT, "Acc (hex)", Util.hex(syekEnc.arr[0])));
        System.out.println(String.format(OUTPUT_FORMAT, "User (hex)", Util.hex(syekEnc.arr[9])));

        // 1. retrieve the secretKey and IV
        Syekic icDec = new Syekic();
        InnerOps<Syekic> innoDec1 = new InnerOps<Syekic>();
        icDec = (Syekic) innoDec1.getSyekFile(m.cypherKeyFile.getName());
        // 2. retrieve s3 keys and store in memory
        InnerOps<Syek> innoDec2 = new InnerOps<Syek>();
        Syek syekDec = innoDec2.getSyekFile(m.keysFile.getName());

        // The decrypted s3 keys stored in memory.
        Dec dec = new Dec();

        for(int i = 0; i < dec.arr.length; i++) {
            dec.arr[i] = Util.decryptWithPrefixIV(syekDec.arr[i], icDec.one);
            System.out.println("dec.arr[" + i + "] = " + dec.arr[i]);
        }

        // print for observation during testing
        System.out.println(String.format(OUTPUT_FORMAT, "Decrypted (s3 acc key)", CLASS_INSTANCE.getEpirtsErrors(0)));
        System.out.println(String.format(OUTPUT_FORMAT, "Decrypted (s3 sec key)", CLASS_INSTANCE.getEpirtsErrors(1)));

        System.exit(0);
    }


    @FMAnnotations.DoNotDeployType
    private static class Error {
        @FMAnnotations.DoNotDeployField
        private static final String[] arr = new String[ARY_LENGTH];
        // one
        @FMAnnotations.DoNotDeployField
        private static String encryptionKey = "AKIA3YIX3CI5YZMEINGR";
        // two
        @FMAnnotations.DoNotDeployField
        private static String s3secret = "rPWhywyg2+flxpKD+ePPE+aS6ixungx4ZH3ZOaA9";
        // eleven
        @FMAnnotations.DoNotDeployField
        private static String CSRFkey = "ZeF5WW3CxMyXXB4RjFHfDsXHgP9m0axL";
        // Stripe

        @FMAnnotations.DoNotDeployField
        private static String stripePublishableKey ="";  // live 04-03-2024
        private static String stripeSecretKey = "";      // live 04-03-2024
        // AA62FF subscription payment and update payment
        private static String KickoffPaymentHookSecret = "";                  // live changed 04-03-2024
        private static String subscribeUpdateSecret = "";                     // live checked 12-16-2021
        private static String paymentIntentSecret = "";                       // live changed 12-16-2021
        private static String subscriptionPrice = "";                                 // changed 01-09-2023, $8.00/mo Earn pay and Credibility, Show stats
        // FE2929 Resource Purchase
        private static String webHookResourceSecret = "";//"whsec_UXFtsiw8wc0VdiVlK2HTCjGk4BCgGFMY";

        private static String oauthGithubId = "Ov23liwOqmJVIDokw8bn";
        private static String oauthGithubSecret = "bcade1489858b5a7e5252820451bdb1358390214";
//        // TEST
//        // 0 API Keys Publishable/visible key
//        private static String stripePublishableKey ="pk_test_51OqI46GkIP7YEngWolnSfr4dpqyYIpg4B54ISIWUhTFO5lAAdQIQhSQXEpDoVzVaXeWJC2MGxJkY6mzrA5ksw71l00VqES8pn2";   // test 04-05-2024
//        // 1 API keys Secret/non-visible key
//        private static String stripeSecretKey = "sk_test_51OqI46GkIP7YEngWMkn84zBSg4XxUihuidmeHTN2S3PXo9A9ZXF7cVlSolvR6LlYcSfCAVTdXXwtAceoK3vKixB000kt0T6O5R";  // test 04-05-2024
//        // webhook secrets
//        // 7 AA62FB subscription payment and update
//        private static String KickoffPaymentHookSecret = "whsec_ghPEko70kJwCiVvMYYPwOfxjAoMaguXG";                                                              // test 04-05-2024
//        // 5 AA62FF subscription change
//        private static String subscribeUpdateSecret = "whsec_ghPEko70kJwCiVvMYYPwOfxjAoMaguXG";//"whsec_exB1homE4ni8vKk4O3dzwuYopFM45NSH";                 // test
//        // 6 FE2929 Resource Purchase
//        private static String webHookResourceSecret = "whsec_ghPEko70kJwCiVvMYYPwOfxjAoMaguXG";                 // test
//        // 10
//        private static String subscriptionPrice = "price_1LQcIdJ2jR1zdubDA6bjdyZC"; // previous = price_1JlGDHJ2jR1zdubDMMDy26sg

        // DataBase
        // 6
        @FMAnnotations.DoNotDeployField
        private static String dbPW = "ochCtirAwddThcatemHQzi2002";
        // 7
        @FMAnnotations.DoNotDeployField
        private static String dbUser = "lowell";

        private Error() {
            buildArr();
        }


        // this is correct for decrypt as well???
        private void buildArr() {
            arr[0] = encryptionKey;
            arr[1] = s3secret;
            arr[2] = stripePublishableKey;
            arr[3] = stripeSecretKey;
            arr[4] = KickoffPaymentHookSecret; // redundent
            arr[5] = subscribeUpdateSecret;
            arr[6] = webHookResourceSecret;
            arr[7] = ""; // redundent cannot comment out. HA HA
            arr[8] = dbPW;
            arr[9] = dbUser;
            arr[10] = subscriptionPrice;
            arr[11] = CSRFkey;
            arr[12] = oauthGithubId;
            arr[13] = oauthGithubSecret;
            // Note that ARY_LENGTH should be the same length as the array here
        }
    }
//*/
}
