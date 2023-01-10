package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class UserBaselineSubInfo {
	private Long procCd;                           /* 과정코드 */
	private String procYear;  						/*교육년도*/
	private String procSeq;							/*교육차수*/	
	private String userId;							/*사용자ID*/
	private String evaluationSubjectCd; /*평가과목코드*/
	private String evaluationSubjectName;/*평가과목*/
	private String realScore;/*실기점수*/
	private String totScore;/*취득점수*/
	private String patternPassYn;/*합격여부*/
	private String endTest;/*평가완료여부*/
	
}
