package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduProc {
	private Long procCd;/*과정코드*/
	private String procNm;/*과정명*/
	private String procGroupDtlGroup;
	private String procGroupDtlGroupNm;/*과정분류상세분류명*/
	private String largeGroupCd;/*대분류코드*/
	private String largeGroupCdNm;/*대분류코드명*/
	private String middleGroupCd;/*중분류코드*/
	private String middleGroupCdNm;/*중분류코드명*/
	private String smallGroupCd;/*소분류코드*/
	private String smallGroupCdNm;/*소분류코드명*/
	private String totStudyDate;/*총학습일자*/
	private String totStudyTime;/*총학습시간*/
	private String limitPersonCnt;/*한계인원수*/
	private int score100;/*점수100*/
	private int endingStdScore;/*수료기준점수*/
	private int endingStdAttendPercent;/*수료기준출석율*/
	private String procIntroTarget;/*과정소개목적*/
	private String procDcContent;/*과정설명내용*/
	private int grade;/*학점*/
	private String useYn;/*xbt사용여부*/
	
}
