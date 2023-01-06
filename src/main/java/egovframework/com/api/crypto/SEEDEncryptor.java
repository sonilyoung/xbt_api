package egovframework.com.api.crypto;

import org.springframework.stereotype.Service;

/* Revision History
 * Author              Date             Description
 * ------------------  --------------   ------------------
 * jaehong hwang	   2022. 06. 20.     
 */
@Service
public class SEEDEncryptor implements Encryptor {

    private static final String DES_KEY = "20151223@!kinac#";
    private static SeedCipher seed = new SeedCipher(); 
    
    public byte[] getKey() throws Exception {
       return DES_KEY.getBytes();
    }


    /**
     * <pre>
     * 
     * </pre>
     * @param message
     * @return
     * @throws Exception
     * @see encrypt(java.lang.String)
     */
    public String encrypt(String message) throws Exception {
        return Base64Encoder.encodeBase64URLSafeString(seed.encrypt(message, getKey(), "UTF-8"));
    }

    /**
     * @param encryptedMessage
     * @return
     * @throws Exception
     * @see decrypt(java.lang.String)
     */
    public String decrypt(String message) throws Exception {
        byte[] encryptbytes = Base64Encoder.decodeBase64(message);
        return seed.decryptAsString(encryptbytes, getKey(), "UTF-8");
    }


    /**
     * <pre>
     * 
     * </pre>
     * @param encryped
     * @param message
     * @return
     * @throws Exception
     * @see match(java.lang.String, java.lang.String)
     */
    public boolean match(String encryped, String message) throws Exception {
        return decrypt(encryped).equals(message);
    }
    
}
// end of SEEDEncryptor.java