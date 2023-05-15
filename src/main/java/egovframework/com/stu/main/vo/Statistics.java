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

	List<Statistics> titleList;
	List<Statistics> dataList;

}
