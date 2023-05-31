package egovframework.com.common.vo;

/**
 * 응답 코드 정의 클래스
 * 
 * @author jkj0411
 *
 */
public enum SeqGroupCode {
	XBT_BAG_ID(
			"X", "가방XRAY코드"
	), XBT_UNIT_SCAN_ID(
			"US", "단품XRAY코드"			
	), XBT_UNIT_ID(
			"U", "단품코드"
	), XBT_THEORY_ID(
			"S", "이론코드"		
	), XBT_UNIT_GROUP_ID(
			"G", "단품그룹코드");
	

	private String code;
	private String codeName;

	private SeqGroupCode(String code, String codeName) {
		this.code = code;
		this.codeName = codeName;
	}

	public String getCode() {
		return code;
	}

	public String getCodeName() {
		return codeName;
	}
}
