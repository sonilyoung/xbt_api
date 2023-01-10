package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduBaselineDetailProc {
	
	private Long procCd;/* 과정코드 */
	private String procYear;/* 과정년도 */
	private String procNm; /* 과정명 */
	private String procSeq;/* 과정차수 */
	private String sugangReqStartDate;/* 수강신청시작일시 */
	private String sugangReqDeadlineDt;/* 수강신청마감일시 */
	private String eduStartDate;     /* 교육시작일시 */
	private String eduEndDate;     /* 교육종료일시 */              
	private String totStudyTime; /* 총학습시간 */
	private String totStudyDate;  /* 총학습일자 */
	private String procGainGrade; /* 과정취득학점 */
	private String limitPersonCnt; /* 한계인원수 */
	private String endingStdScore;    /* 수료기준점수 */
	private String endingStdAttendPercent;/* 수료기준출석율 */
	private String eduType;                 /* 교육타입 */
	private String xrayLastTestType;      /* x-ray최종평가방식 */
	private String decipLastTestEffect;   /* 판독최종평가횟수 */
	private String theoryLastTestEffect;  /* 이론최종평가횟수 */
	private String banPassFailProcess;    /* 금지통과불합격처리 */
	private String repeatedStudyingYn;     /* 반복학습여부 */
	private String endingProcessEndYn;    /* 수료처리완료여부 */  
	
}
