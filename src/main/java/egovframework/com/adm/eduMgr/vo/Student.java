package egovframework.com.adm.eduMgr.vo;

import java.util.List;

import lombok.Data;

@Data
public class Student {
	private Long studentNo;
	private Long procCd;
	private String procYear;
	private String procSeq;
	private String userId;
	private String userNm;
	private String procNm;
	private String compNm;
	private String deptCd;
	private String deptNm;
	private String gainScore;
	private String passYn;
	private String endingProcessDate;
	private String endingYn;
	private String eduStartDate;
	private String eduEndDate;
	private Long moduleId;
	
	private String insertDate;
	private String insertId;
	private String updateDate;
	private String updateId;	
	
	private List<String> userIdList; 
}
