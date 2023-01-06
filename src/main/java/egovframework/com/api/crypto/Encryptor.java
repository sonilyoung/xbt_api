package egovframework.com.api.crypto;

/* Revision History
 * Author              Date             Description
 * ------------------  --------------   ------------------
 * jaehong hwang	   2022. 06. 20.     
 */
public interface Encryptor {

	/**
	 * 암호화된 문자열을 평문으로 복호화한다.
	 *
	 * @param encryptedMessage 암호화된 문자열
	 * @return 복호화된 평문
	 * @throws Exception 복호화 할때 발생하는 에러.
	 */
	String decrypt(String encryptedMessage) throws Exception;

	/**
	 * 지정한 문자열을 암호화 한다.
	 *
	 * @param message 암호화할 문자열
	 * @return 암호화된 문자열
	 * @throws Exception 암호화 할때 발생하는 에러.
	 */
	String encrypt(String message) throws Exception;

	/**
	 * 암호화된 문자열과 암호화할 문자열이 매치하는지 체크한다.
	 *
	 * @param encryped 암호화된 문자열
	 * @param message  암호화되지 않은 문자열
	 * @return 매치가되면 <tt>true</tt> 그렇지 않으면 <tt>false</tt>
	 * @throws Exception 비교시 발생하는 에러
	 */
	boolean match(String encryped, String message) throws Exception;
}
