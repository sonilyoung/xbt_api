package egovframework.com.stu.main.vo;

import java.util.List;

import lombok.Data;

@Data
public class Statistics {
	
	private Long learnNo;
	private String procCd;
	private String procYear;
	private String procSeq;
	private String userId;
	private String testDate;
	private int studyLvl;
	private int moduleId;
	private int gainScore;
	private int rightCnt;
	private int wrongCnt;
	private String endYn;
	private String passYn;
	private String trySeq;
	private String insertDate;
	private String type;
	private String languageCode;

	private String[] categories;	
	private List<Integer> level1;
	private List<Integer> level2;
	private List<Integer> level3;
	private List<Integer> level4;
	private List<Integer> level5;

}
