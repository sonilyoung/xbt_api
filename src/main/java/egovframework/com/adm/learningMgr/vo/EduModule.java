package egovframework.com.adm.learningMgr.vo;

import lombok.Data;

@Data
public class EduModule {
	
	private Long xrayEduModuleId;          /*x-ray교육모듈id*/        
	private String xrayEduModuleNm;          /*x-ray교육모듈명칭*/        
	private String xrayEduModuleDc;          /*x-ray교육모듈설명*/        
	private String allCaseCnt;                /*전체건수*/        
	private String supportRequestCaseCnt;    /*지원요청건수*/        
	private String openLimitCaseCnt;         /*개봉제한건수*/        
	private String openPassCaseCnt;          /*개봉통과건수*/        
	private String passCaseCnt;               /*통과건수*/        
	private String useYn;                      /*사용여부*/        
	private String insertDt;                      /*등록일시*/        
	private String insertId;                      /*등록자*/        
	private String updateDt;                      /*수정일시*/        
	private String updateId;                      /*수정자*/        
	private String seteqQuestionCnt;          /*출제문제수*/ 

}
