package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduBaselineMenu {
	
	
	private String procCd /* 과정코드 */;
	private String procYear /* 과정년도 */;
	private String procSeq/*차수*/;
	private String procNm /* 과정명 */;
	private String menuNo /*메뉴번호*/;
	private String menuNm /* action구분명 */;
	private String menuCd/*메뉴코드*/;

	
}
