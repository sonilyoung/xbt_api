package egovframework.com.api.edc.vo;

import lombok.Data;

@Data
public class AiForceLearningResult{
	private Long progressNo;
	private Long moduleId;
	private Long procCd;
	private String procYear;
	private String procSeq;
	private int trySeq;
	private int studyLvl;
	private String userId;
	private String bagScanId;
	private String unitId;
	private String unitName;
	private String unitGroupCd;
	private String unitGroupName;
	private String actionDivName;
	private String actionDiv;
	private String userActionDiv;
	private int gainScore;
	private String answeryn;
	private String insertDate;

}
