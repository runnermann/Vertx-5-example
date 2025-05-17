package com.runnermann.wolf.example.web.access;


import com.runnermann.wolf.example.fmannotations.FMAnnotations;
import com.runnermann.wolf.example.utility.FileUtility;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>Singleton class</p>
 * <p>It is expected that the Main method of this class is used to encrypt the keys that will
 * be used in this program. Encryption should be done when a new
 * keys for are added. Keys are stored outside of the project. After encrypted,
 * two files are saved to the root. The zero.cpr file which contains the Cipher Encryption key ysed to
 * encrypt and decrypt the keys. The encrypted keys used by the DB and other capabilities are stored
 * in the one.enc file.
 * When needed, re-encrypt the keys
 * 1) uncomment the main class and the Error class.
 * 3) run the main method and watch the output. The output should be error free. If it has errors, run it a second time.
 * It should not take more than twice. The first iteration creates the files, but sometimes the output shows errors.
 * 4) comment out the main method and the Error class.</p>
 *
 *
 * NOTE: if there is an error during runtime. Check that the keysfiles zero.cpr and one.enc are from the same
 * version. This is a serializable class. Files that are not built by the same version will cause a
 * runtime crash when Vertx is initialized.
 *
 * It's also highly recommended to remove logging except when it is needed during development.
 */
public class SecretEncDec {

    private static final int ARR_LENGTH = 9; // 14 May 2025 Wolf
    public static final long VERSION = 20250515;
    private static SecretEncDec CLASS_INSTANCE;

    private final static ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(SecretEncDec.class);
    private static final int IV_LENGTH_BYTE = 12;
    private static final int AES_KEY_BIT = 256;

    // stores the SecretKey and nonce used to decrypt the
    // s3 keys
    private File cypherKeyFile = new File("zero.cpr");
    // stores the encrypted S3 keys
    private File keysFile = new File("one.enc");
    private Dec dec;


    /**
     * Class Constructor
     */
    private SecretEncDec() {
        init();
    }

    public static synchronized SecretEncDec getInstance() {
        if(CLASS_INSTANCE == null) {
            CLASS_INSTANCE = new SecretEncDec();
        }
        return CLASS_INSTANCE;
    }

    // *** public methods used when vertx server is deployed and operational ***//

    private void init() {
        try {
            this.setModelError();
        }
        catch (Exception e) {
            LOGGER.warn("CRITICAL ERROR!!!: SecretEncDec threw exception in AppAccessVerticle {} \n{}", e.getMessage(), e.getStackTrace());
            e.printStackTrace();
        }
    }

    /**
     * Returns the keys for the DB connection
     * @return
     */
    public String[] getDBConnectErrors() {
        String[] sArr = new String[2];
        sArr[0] = dec.arr[2];
        sArr[1] = dec.arr[3];
        return sArr;
    }


    public String getEpirtsErrors(int idx) {
        return dec.arr[idx];
    }


    private void setModelError() throws Exception {
        Syekic icDec;
        InnerOps<Syekic> innoDec1 = new InnerOps<Syekic>();
        // Get the file
        icDec = innoDec1.getSyekFile(cypherKeyFile.getName());

        // 2. retrieve keys and store in memory
        InnerOps<Syek> innoDec2 = new InnerOps<Syek>();
        // double byte array
        Syek syekDec = innoDec2.getSyekFile(keysFile.getName());

        // 2.a. The decrypted keys stored in memory.
        dec = new Dec();
        // !!!!! if fails when building the file the first time, the length may
        // be different in the file.
        // Run a second time.
        for(int i = 0; i < dec.arr.length; i++) {
            dec.arr[i] = Util.decryptWithPrefixIV(syekDec.arr[i], icDec.one);
            LOGGER.debug("decrypted key: " + dec.arr[i]);
        }
    }
     // Used for saving the cipher key
     // to file. One is the AES/GCM SecretKey
     // two is the nonce;
    private static class Syekic implements Serializable {
        public static final long serialVersionUID = VERSION;
        public Syekic() {/* No args constructor */}
        private SecretKey one;
    }

    private static class Syek implements Serializable {
        public Syek() {/* no args constructor */}
        public static final long serialVersionUID = VERSION;
        private byte[][] arr = new byte[ARR_LENGTH][];
    }
     // Stores the unencrypted keys in memory. One is S3AccessKey
     // and two is s3 SecretKey
    private static class Dec {
        String[] arr = new String[ARR_LENGTH];
    }

    /**
     * INNER CLASS
     * @param <T>
     */
    private static class InnerOps<T> {
        void createSyekFile(T syek, File mfile) {
                try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/main/resources/" + mfile.getName()), 512))) {
                    out.writeObject(syek);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        /**
         * gets the encrypted keys from the enryptedFile.dat
         * @param fileName
         * @return
         */
        T getSyekFile(String fileName) {
                T t = null;
                try (ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(getClass().getResourceAsStream("/" + fileName)))) {
                    while (true) {
                        t = (T) input.readObject();
                    }
                } catch (EOFException e) {
                    // expected. do nothing
                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    return t;
                }
        }
    }

    /*  ******* DO BEFORE SHIPPING ******** */
    /* Uncomment to encrypt and save to file the secret keys */
    /*
    Note that the main method calls the class instance. Class instance calls init() that is
    used when the vertx server is deployed on its host.
    If there is an error, run the main method a second time. This will happen in some situations
    such as when updating the version, and when no files exist already.

    For testing, use test keys. When running main method, the result should print test keys not
    live key. And vice versa.
     */

    @FMAnnotations.DoNotDeployMethod
    public static void main(String[] args) throws Exception {
       // *********** Encryption is conducted prior to a build or after creating a new IAM key for S3 *********** //
        String OUTPUT_FORMAT = "%-30s:%s";
        SecretEncDec mError = SecretEncDec.getInstance();

        // 1. create SecretKey
        // encrypt and decrypt need the same key.
        // get AES 256 bits (32 bytes) key
        Syekic icEnc = new Syekic();
        icEnc.one = Util.getAESKey(AES_KEY_BIT);
        // 2. create IV
        // encrypt and decrypt need the same IV.
        // AES-GCM needs IV 96-bit (12 bytes)
        byte[] iv = Util.getRandomNonce(IV_LENGTH_BYTE);

        // 3. Save the cypher key -secretKey and IV to file, zero.cpr
        InnerOps<Syekic> innoEnc1 = new InnerOps<>();
        innoEnc1.createSyekFile(icEnc, mError.cypherKeyFile);
        // 4. encrypt keys from hard drive with Secret/cypher key & iv
        Syek syekEnc = new Syek();
        // Cipher SecretKey and Cipher IV
        Error error = new Error();
        for(int i = 0; i < syekEnc.arr.length; i++) {
            syekEnc.arr[i] = Util.encryptWithPrefixIV(error.arr[i].getBytes(UTF_8), icEnc.one, iv);
        }
        // 5. save encrypted keys to file
        InnerOps<Syek> inno2 = new InnerOps<>();

        // save array of keys to file
        inno2.createSyekFile(syekEnc, mError.keysFile);

        // verify output is correct

        System.out.println("\n------ AES GCM Encryption ------");

        System.out.println(String.format(OUTPUT_FORMAT, "encrypted Key (hex)", Util.hex(icEnc.one.getEncoded())));

        System.out.println("\n------ AES GCM Decryption ------");
        System.out.println(String.format(OUTPUT_FORMAT, "Acc (hex)", Util.hex(syekEnc.arr[0])));
        System.out.println(String.format(OUTPUT_FORMAT, "User (hex)", Util.hex(syekEnc.arr[3])));

        // 1. retrieve the secretKey and IV
        Syekic icDec = new Syekic();
        InnerOps<Syekic> innoDec1 = new InnerOps<Syekic>();
        icDec = (Syekic) innoDec1.getSyekFile(mError.cypherKeyFile.getName());
        // 2. retrieve keys and store in memory
        InnerOps<Syek> innoDec2 = new InnerOps<Syek>();
        Syek syekDec = innoDec2.getSyekFile(mError.keysFile.getName());

        // The decrypted keys stored in memory.
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

        private static final String[] arr = new String[ARR_LENGTH];


        private Error() {
            try {
                buildArr();
            } catch (IOException e) {
                System.out.println("ERROR: buildMap() failed");
                throw new RuntimeException(e);
            }
        }

        // this is correct for decrypt as well???
        private void buildArr() throws IOException {

            String desktop = FileUtility.DESKTOP.getAbsolutePath();
            String filePathStr = desktop + "/VertxServerSecrets/ServerSecrets.properties";

            File file = new File(filePathStr);
            if(!file.exists()) {
                throw new RuntimeException("ERROR IN buildARR: File not found. PATH:" + filePathStr);
            }

             //* Uncomment the top line if you want to keep the unencrypted keys stored with your projects resources.
             //* Otherwise your keys are expected to be stored on your desktop at /ServerSecrets/ServerSecrets.properties".
             //* Its important to note that we look for they keys by name. Spelling and capitolization are VERY important.
             //* You can have as many keys as you need, but update the length of the array in the ARR_LENGTH constant at
             //* the top.

            // To get a password and user name after it has been encrypted. Usage:
            //      SecretEncDec sed = new SecretEncDec.getInstance();
            //      String pw = sed[0];
            //      String user = sed[1]

            //InputStream queriesInputStream = DatabaseHandler.class.getResourceAsStream("/ServerSecrets.properties");
            InputStream queriesInputStream = new FileInputStream(filePathStr);

            Properties secretProps = new Properties();
            secretProps.load(queriesInputStream);

            arr[0] = secretProps.getProperty("encryptionKey"); // This is always needed. Create this key and store in the .properies file.
            arr[1] = secretProps.getProperty("CSRFkey"); // This is always needed. Create this key and store it in the .properties file.
            arr[2] = secretProps.getProperty("dbPW");
            arr[3] = secretProps.getProperty("dbUser");
            arr[4] = secretProps.getProperty("s3Secret");
            arr[5] = secretProps.getProperty("oauthGithubId");
            arr[6] = secretProps.getProperty("oauthGithubSecret");
            arr[7] = secretProps.getProperty("linkedinID"); // Get this from Linkedin
            arr[8] = secretProps.getProperty("linkedinSecret"); // Get this from Linkedin

            queriesInputStream.close();

        }
    }
//*/
}
