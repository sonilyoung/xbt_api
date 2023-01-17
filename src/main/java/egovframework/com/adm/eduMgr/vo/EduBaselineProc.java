package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduBaselineProc {
	private Long procCd; /* 과정코드 */
	private String procYear; /* 과정년도 */			
	private String procNm; /* 과정명 */
	private String procSeq; /* 차수 */
	private String eduDate; /* 학습기간 */
	private String timeGrade; /*시간/학점*/
	private String totStudyTime; /*교육기간*/
	private String limitPersonCnt; /* 한계인원수 */
	private String endingProcessEndStatus;/* 수료처리완료여부 */
	
}
