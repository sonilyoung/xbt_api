package egovframework.com.api.crypto;

/* Revision History
 * Author              Date             Description
 * ------------------  --------------   ------------------
 * jaehong hwang	   2022. 06. 20.     
 */
public interface CryptoPadding {

	
	public byte[] addPadding(byte[] source, int blockSize);

	
	public byte[] removePadding(byte[] source, int blockSize);

}
