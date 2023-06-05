package egovframework.com.adm.learningMgr.vo;

import java.util.List;

import lombok.Data;

@Data
public class PointStd {
	private Long pointsStdNo;
	private Long pointsDetailNo;
	private String pointsStdNm;
	private String pointsStdDc;
	private String useYn;
	private String pointsStdId;
	private String actionDiv;
	private String actionDivName;
	private int banUnitScore = 0;
	private int limitUnitScore = 0;
	private int questionUnitScore = 0;
	private int passUnitScore = 0;
	private String insertDate;
	private String insertId;
	private String updateDate;
	private String updateId;
	
	private PointStd actionDiv0; //개봉/금지
	private PointStd actionDiv1; //미개봉/금지
	private PointStd actionDiv2; //개봉/제한
	private PointStd actionDiv3; //개봉/통과
	private PointStd actionDiv4; //미개봉/통과
	
	private List<Long> pointsStdNoList;
	
	private List<PointStd> updateList;

}
