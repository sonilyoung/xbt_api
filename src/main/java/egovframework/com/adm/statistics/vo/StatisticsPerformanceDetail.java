package egovframework.com.adm.statistics.vo;

import lombok.Data;

@Data
public class StatisticsPerformanceDetail {
	private Long procCd;
	private String userId;
	private String procName;
	private String userName;
	private int rightCnt;
	private int wrongCnt;
	private int totalCnt;
	private int gainScore;
	private int trySeq;
	private int tryCnt;
}
