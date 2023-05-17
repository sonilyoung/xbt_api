package egovframework.com.adm.contents.vo;

import java.util.List;

import lombok.Data;

@Data
public class XrayContents{
	private Long bagScanNo;
	private String bagScanId;
	private String[] bagScanIds;
	private String studyLvl;
	private String unitId;
	private String unitName;                     /*물품명*/
	private String unitDesc;                     /*물품설명*/	
	private String unitGroupCd;
	private String openYn;
	private String passYn;
	private String actionDiv;                  /*action구분*/
	private String actionDivName;                  /*action구분*/
	private String useYn;
	
	private Long bagContNo;
	private String seq;
	private String answerItem;	
	
	private String insertDate;
	private String insertId;
	private String updateDate;
	private String updateId;	
	private String languageCode;	
	
	private List<XrayContents> paramList;
	
}
