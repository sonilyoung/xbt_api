package egovframework.com.api.crypto.LEA;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import egovframework.com.api.crypto.LEA.padding.PKCS5Padding;
import egovframework.com.api.crypto.LEA.symm.LEA;

/* Revision History
 * Author              Date             Description
 * ------------------  --------------   ------------------
 * jaehong hwang	   2022. 06. 20.     
 */
@Service
@PropertySource("classpath:globals.properties")
public class LEAEncryptor{
	
	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LEAEncryptor.class);
	
	
	
	@Value("${LEADecryptor.secret}")
	private String KEY;
	
    private BlockCipherMode cipher;
    
    public void onCreate() {
    	
        cipher = new LEA.ECB();
        cipher.init(BlockCipher.Mode.ENCRYPT, KEY.getBytes());
        cipher.setPadding(new PKCS5Padding(16));
        
        String data = "test123456"; // 암호화 할 데이터
        String encryptData = encrypt(data); // 암호화 한 데이터
        
        LOGGER.debug("====encryptData>>"+encryptData);
        
        cipher.init(BlockCipher.Mode.DECRYPT, KEY.getBytes());
        cipher.setPadding(new PKCS5Padding(16));
        
        String decryptData = decrypt(encryptData);
        
        LOGGER.debug("====decryptData>>"+decryptData);
    }

    public String encrypt(String data) {
    	
    	cipher = new LEA.ECB();
    	LOGGER.info(KEY, KEY.getBytes().length);
        cipher.init(BlockCipher.Mode.ENCRYPT, KEY.getBytes());
        cipher.setPadding(new PKCS5Padding(16));
    	
        byte[] ct1 = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Base64로 인코딩한 String값을 return
        // Base64로 인코딩 하지 않으면 암호화한 데이터가 깨지는 문제가 발생함.
        String result = new String(Base64.encodeBase64(ct1));
        
        cipher.reset();

        return result;
    }

    public String decrypt(String encryptData) {
    	
    	cipher = new LEA.ECB();
        cipher.init(BlockCipher.Mode.DECRYPT, KEY.getBytes());
        cipher.setPadding(new PKCS5Padding(16));
        
        // Base64로 인코딩된 암호문을 decode하여 byte형식으로 저장
    	byte[] ct1 = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes(StandardCharsets.UTF_8)));
    	
        String result = new String(ct1 , StandardCharsets.UTF_8);
        
        cipher.reset();
        
        return result;
    }
    
    public boolean match(String encryped, String message) {
    	return (decrypt(encryped).equals(message));
    }
    
}
