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
	private int theoryQuestionCnt;
	private int timeLimit;
	private int slideSpeed;
	private double dangerScore;
	private int passScore;
	private int passTheoryScore;
	private int passDangerScore;
	private int passPracticeScore;
	
	private int practiceHumanTotalScore;
	private int practiceCarTotalScore;
	private int practiceHumanScore;
	private int practiceCarScore;
	
	private int userAverage;
	private int rightCnt;
	private int wrongCnt;
	private int banCnt;
	private int trySeq;
	
	private List<LearningProblem> learningProblemList; 
}
