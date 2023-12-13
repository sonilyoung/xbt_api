package egovframework.com.stu.learning.vo;

import lombok.Data;

@Data
public class LearningProblem{
	private Long progressNo;
	private Long moduleId;
	private Long procCd;
	private String procYear;
	private String procSeq;
	private String userId;
	private String bagScanId;
	private String unitId;
	private int actionDiv;
	private String userActionDiv;
	private String answerYn;
	private int gainScore;
	private String endYn;
	private String insertDate;
	private String eduType;
	private String studyLvl;
	private String actionDivName;
	private String userActionDivName;
	private String languageCode;
	private String unitName;
	private String openYn;
	private String passYn;
	private String unitGroupCd;
	private int trySeq;
	private int questionCnt;
	private byte[] imgReal;//실사
	private byte[] imgFront;//정면
	private byte[] imgSide;//측면	
	//private List<Learning> problemList;//문제목록
	//private List<LearningImg> imageList; //이미지목록
}
