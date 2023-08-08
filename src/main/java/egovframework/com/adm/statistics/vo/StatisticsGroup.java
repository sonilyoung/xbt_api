package egovframework.com.adm.statistics.vo;

import lombok.Data;

@Data
public class StatisticsGroup {
	
	private Long procCd;
	private String procYear;
	private String procName ;
	private String groupName;
	private int procSeq;
	private int rightCnt;
	private int wrongCnt;
	private int totalCnt;

}
