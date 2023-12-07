package egovframework.com.adm.system.vo;

import lombok.Data;

@Data
public class XbtScore {
	
	private String userId;
	private int practiceScore;//실기점수
	private int theoryScore;//이론평가점수
	private int	evaluationScore;//xbt평가점수
	
	private Long procCd;
	private String procYear;
	private String procSeq;
	private String eduCode;
	private String eduName;
	private String userNm;
	private String procNm;
	private String compNm;
	private String deptNm;
	
	private int gainScore;
	private int dangerScore;
	private int passScore;
	private int passTheoryScore;
	private int passDangerScore;
	private int passPracticeScore;
	private int practiceHumanScore;
	private int practiceCarScore;
	
	private String practiceYn;
	private String theoryYn;
	private String dangerYn;
	private String evaluationYn;
	private String passYn;
	private String endingProcessDate;
	private String eduStartDate;
	private String eduEndDate;
	private String endingYn;
	private String command;
	
	private String theoryPassYn;
	private String practicePassYn;
	private String evaluationPassYn;
	private String dangerPassYn;
	
}
