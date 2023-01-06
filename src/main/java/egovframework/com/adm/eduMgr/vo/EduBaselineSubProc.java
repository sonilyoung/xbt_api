package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduBaselineSubProc {
	
	private Long procCd;    /* 과정코드 */
	private String procYear; /* 과정년도 */
	private String procSeq;  /* 과정차수 */
	private String pattern;  /* 패턴 */
	private String procNm;  /* 과정명 */
	private String realTestUseYn;    /* 실기점수반영여부 */
	private String evaluationSubjectCd; /*평가과목코드*/
	private String evaluationSubjectNm; /*평가과목*/
	private String realPresco;   /*실기만점*/ 
	private String patternPlusScore;/* 패턴가중치 */
	private String patternFallScore;/* 패턴과락점수 */
	private String patternWbtProcDiv;/* 패턴wbt과정구분 */

	
}
