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
	private int gainScore;
	private String endYn;
	private String insertDate;
	private String eduType;
	private String studyLvl;
	private String actionDivName;
	
	//private List<Learning> problemList;//문제목록
	//private List<LearningImg> imageList; //이미지목록
}
