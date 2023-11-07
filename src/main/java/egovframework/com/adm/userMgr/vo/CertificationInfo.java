package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class CertificationInfo {
	private Long certificationNo;
	private String certificationId;
	private String procCd;
	private String procYear;
	private String procSeq;
	private String eduCode;
	private String eduName;
	private String eduType;
	private String userId;
	private String userNm;
	private String practiceScore;
	private String theoryScore;
	private String evaluationScore;
	private String evaluationYn;
	private String passYn;
	private String eduStartDate;
	private String eduEndDate;
	private String endingYn;
	private String insertDate;
	private String insertId;
}
