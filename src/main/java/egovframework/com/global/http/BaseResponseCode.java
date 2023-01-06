package egovframework.com.global.http;

/**
 * 응답 코드 정의 클래스
 * 
 * @author jkj0411
 *
 */
public enum BaseResponseCode {
	SUCCESS(
			"0000", "성공"
	), SAVE_SUCCESS(
			"0201", "저장성공"
	), AUTH_FAIL(			
			"0401", "인증실패"
	), AUTH_ERROR(
			"0403", "인증된 사용자가 아닙니다."
	), DATA_IS_DUPLICATE(
			"0600", "중복된 데이터가 있습니다."
	), DATA_IS_NULL(
			"0605", "데이터가 없습니다."
	), DELETE_ERROR(
			"0700", "삭제 중 내부 오류가 발생"
	), SAVE_ERROR(
			"0800", "저장시 내부 오류가 발생"
	), INPUT_CHECK_ERROR(
			"0900", "입력값 무결성 오류"
	), UNKONWN_ERROR(
	        "9998", "내부 오류가 발생"
	), PARAMS_ERROR(
	        "0422", "파라미터 오류"	        
	), EXTENSION_ERROR(
	        "0433", "파일확장자 오류"
	), DELETE_SUCCESS(
			"0434", "삭제되었습니다."
	), DUPLICATE_CEO(
	        "8886", "이미대표이사가 등록되어있습니다."				
	), STOP_ID(
	        "8887", "계정이 중지되었습니다."			
	), SAME_ERROR(
	        "8888", "동일한 데이터가 존재합니다."
	), EXCEL_TYPE(
	        "8889", "엑셀형식이 잘못되었습니다."
	), BASELINE_NULL(
	        "9996", "차수정보가 존재하지 않습니다."	        
	), BASELINE_CHECK1(
	        "9997", "중복된차수가 존재합니다."
	), BASELINE_CHECK2(
	        "9998", "현재일자가 등록하려는 차수정보에 속하지 않습니다."	        
	), INFORMATION_ERROR(
			"9999", "입력 정보 없음");
	

	private String code;
	private String message;

	private BaseResponseCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
