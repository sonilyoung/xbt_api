package egovframework.com.adm.statistics.vo;

import java.util.List;

import lombok.Data;

@Data
public class StatisticsMainYear {
	
	private String procYear;
	private Long totCnt;
	private Long passCnt;
	private Long passPercent;

	private List<String> categories;
	private List<Long> totCntList;//교육인원
	private List<Long> passCntList;//합격자
	private List<Long> passPercentList;//합격률
}
