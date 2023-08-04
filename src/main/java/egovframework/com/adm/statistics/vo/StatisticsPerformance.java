package egovframework.com.adm.statistics.vo;

import lombok.Data;

@Data
public class StatisticsPerformance {
	
	private String procCd;
	private String procYear;
	private String procName;
	private int procSeq;
	private int averageScore;
	private int studentCnt;
}
