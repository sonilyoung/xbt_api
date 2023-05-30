package egovframework.com.stu.learning.vo;

import java.util.List;

import lombok.Data;

@Data
public class Learning{
	private Long procCd;
	private String userId;
	private String userName;
	private String procYear;
	private String procSeq;
	private String eduStartDate;
	private String eduEndDate;
	private Long dayNo;
	private String procNm;
	private String menuCd;
	private String menuNm;
	private Long moduleId;
	private String moduleType;
	private String eduType;
	private String eduDate;
	private int studyLvl;
	private String bagScanId;
	private String actionDiv;
	private String userActionDiv;
	private String actionDivName;
	private String moduleNm;
	private String answer;
	private String answerDiv;
	private String passYn;
	private String endYn;
	private String languageCode;
	private double gainScore;
	private int questionCnt;
	private int timeLimit;
	private int slideSpeed;
	private int passScore;
	private int userAverage;
	private int rightCnt;
	private int wrongCnt;
	private int trySeq;
	
	private List<LearningProblem> learningProblemList; 
}
