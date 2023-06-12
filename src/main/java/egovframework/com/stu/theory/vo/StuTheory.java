package egovframework.com.stu.theory.vo;

import java.util.List;

import lombok.Data;

@Data
public class StuTheory {
	private Long questionNo;
	private Long procCd;
	private String procYear;
	private String procSeq;
	private String questionId;
	private String questionType;
	private String useYn;
	private String lageGroupCd;
	private String middleGroupCd;
	private String smallGroupCd;
	private String question;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private byte[] choiceImg1;
	private byte[] choiceImg2;
	private byte[] choiceImg3;
	private byte[] choiceImg4;
	private String multiPlusImgName;
	private byte[] multiPlusImg;
	private double gainScore;
	private String actionDiv;
	private String userActionDiv;
	private String insertId;
	private String userId;
	private String userName;
	private String insertDate;
	private String answer;
	private String answerDiv;
	private String endYn;
	private String menuCd;
	private int trySeq;
	private int rightCnt;
	private int wrongCnt;
	private int questionCnt;	
	private int studyLvl;

	private List<StuTheory> questionList;
	private List<StuTheory> theoryList;

}
