package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class Baseline {
	private Long procCd;
	private String procYear;
	private String procSeq;
	private Long moduleId;
	private String eduStartDate;
	private String eduEndDate;
	private String totStudyDate;
	private int limitPersonCnt;
	private int endingStdScore;
	private String endingProcessEndYn;
	private String insertDate;
	private String insertId;
	private String updateDate;
	private String updateId;

}
