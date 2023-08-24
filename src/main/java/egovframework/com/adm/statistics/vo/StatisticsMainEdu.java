package egovframework.com.adm.statistics.vo;

import lombok.Data;

@Data
public class StatisticsMainEdu {
	private String authCd;
	private String userId;
	private String userNm;
	private String procName;
	private String procYear;
	private String procSeq;
	private int limitPersonCnt;
	private String eduStartDate;
	private String eduEndDate;
	private int eduPercent;
	
}
