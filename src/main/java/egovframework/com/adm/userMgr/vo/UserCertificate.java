package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class UserCertificate {
	private Long procCd;
	private String procYear;
	private String procSeq;
	private String eduName;
	private String userId;
	private String userNm;
	private int practiceScore;
	private int theoryScore;
	private int evaluationScore;
	private String passYn;
	private String eduStartDate;
	private String eduEndDate;
	private String endingYn;
}
