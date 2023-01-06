package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduProcDetail {

	private Long procCd;   /* 과정코드 */
	private String totStudyTime;/*총학습시간*/
	private int grade;/*학점*/
	private String totStudyDate;/*총학습일자*/
	private String limitPersonCnt;/*한계인원수*/
	private String realTestUseYn; /* 실기평가사용여부 */
	private String realWbtProcDiv;/* 실기wbt과정구분 */
	private int realPlusScore;  /* 실기가중치 */
	private int realScore100;   /* 실기점수100 */
	private String xrayStrStage;   /* x-ray시작단계 */
	private String xrayEndStage;   /* x-ray종료단계 */
	private String xrayLastTestType;/* x-ray최종평가방식 */
	private String banPassFailProcess;/* 금지통과불합격처리 */
	private String bodyStrStage;       /* body시작단계 */
	private String bodyEndStage;       /* body종료단계 */
	private String eduType;             /* 교육타입 */
	private String decipLastTestEffectCnt; /* 판독최종평가실시횟수 */
	private String theoryLastTestEffectCnt; /* 이론최종평가실시횟수 */
	private String repeatedStudyingYn; /* 반복학습여부 */		
	
}
