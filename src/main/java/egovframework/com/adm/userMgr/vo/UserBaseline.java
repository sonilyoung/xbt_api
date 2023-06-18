package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class UserBaseline {

	private Long procCd;  /*과장코드*/
	private String procYear;/* 과정년도 */
	private String procSeq;/* 과정차수 */
	private String procNm; /* 과정명 */
	private String eduDate;   /* 교육기간 */
	private String trainees;/* 교육인원 */
	private String endingProcessEndYn;/* 수료처리완료여부 */
	
	private String userId;
	private String userNm;
	private String compNm;
	private String deptNm;
	private String gainScore;
	private int practiceScore;
	private int practiceBeforeScore;
	private String practiceYn;
	private String theoryScore;
	private int evaluationScore;
	private String passYn;
	private String endingProcessDate;
	private String eduStartDate;
	private String eduEndDate;
	private String endingYn;
	private int questionCnt;
	private int rightCnt;
	private int wrongCnt;
	private int evaluationTotalScore;
	private int theoryTotalScore;
	private int practiceTotalScore;
	
	
	
	


	
}
