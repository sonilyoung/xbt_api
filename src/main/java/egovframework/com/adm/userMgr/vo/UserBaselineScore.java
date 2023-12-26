package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class UserBaselineScore {

	private Long procCd;  /*과장코드*/
	private String procYear;/* 과정년도 */
	private String procSeq;/* 과정차수 */
	private String procNm; /* 과정명 */
	private String eduCode;
	private String eduName;
	private String userId;
	private String userNm;
	
	private String passYn;
	private String endYn;
	private int rightCnt;
	private int wrongCnt;
	private int totalCnt;
	private int gainScore;
	private String testDate;

	
	//조회조건
	private String command;
	private String searchval;
	
	
	


	
}
