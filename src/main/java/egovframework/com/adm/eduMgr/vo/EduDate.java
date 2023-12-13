package egovframework.com.adm.eduMgr.vo;

import java.util.List;

import lombok.Data;

@Data
public class EduDate {
	private Long dayNo;
	private Long procCd;
	private Long moduleId;
	private Long evaluationModuleId;
	private String moduleNm;
	private String procYear;
	private String procSeq;
	private String procNm;
	private String userId;
	private String menuCd;
	private String menuNm;
	private String learningType;
	private String moduleType;
	private String eduStartDate;
	private String eduEndDate;
	private String insertId;
	private String insertDate;
	private String learnYn;
	private String command;
	private int trySeq;
	
	private List<List<EduDate>> menuList;

}
