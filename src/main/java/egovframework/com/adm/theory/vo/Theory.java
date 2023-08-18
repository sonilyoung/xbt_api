package egovframework.com.adm.theory.vo;

import java.util.List;

import lombok.Data;

@Data
public class Theory {

	private Long questionNo;
	private String questionId;
	private String studyLvl;
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
	private String actionDiv;
	private String insertId;
	private String insertDate;
	private String command;
	private String searchval;
	
	private List<String> questionIdList;

}
