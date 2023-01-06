package egovframework.com.adm.contents.vo;

import lombok.Data;

@Data
public class Xrayformation{
	private Long bagScanId;                 /*가방촬영id*/
	private String unitId;                     /*물품id*/
	private String unitName;                     /*물품명*/
	private String unitDesc;                     /*물품설명*/
	private String openYn;                     /*개봉여부*/        
	private String passYn;                     /*통과여부*/        
	private String actionDiv;                  /*action구분*/        
	private String studyLvl;                   /*학습레벨*/        
	private String useYn;                      /*사용여부*/        
	private String frontUseYn;                /*정면사용여부*/        
	private String sideUseYn;                 /*측면사용여부*/        
	private String decipMachineCd;            /*판독기기코드*/        
	private String duplexYn;                   /*양방향여부*/        
	private String seq;                         /*순번*/        
	private String insertDate;                      /*등록일시*/        
	private String insertId;                      /*등록자*/        
	private String updateDate;                      /*수정일시*/        
	private String updateId;                      /*수정자*/
	
}
