package egovframework.com.api.edc.vo;

import lombok.Data;

@Data
public class AiForceUserScore{
	private Long learnNo;
	private String procCd;
	private String procYear;
	private String procSeq;
	private String userId;
	private String testDate;
	private String studyLvl;
	private String moduleId;
	private String moduleNm;
	private int gainScore;
	private int rightCnt;
	private int wrongCnt;
	private String endYn;
	private String passYn;
	private int trySeq;
	private String insertDate;

}
