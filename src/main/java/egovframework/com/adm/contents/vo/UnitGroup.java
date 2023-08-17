package egovframework.com.adm.contents.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.Data;

@Data
public class UnitGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long unitGroupNo;/*물품그룹코드*/
	private String unitGroupCd; /*물품코드분류*/
	private String groupName; /*물품분류상세*/
	private String groupDesc; /*물품분류설명*/
	private String groupNameEn; /*물품분류상세*/
	private String groupDescEn; /*물품분류설명*/
	private String groupNameJp; /*물품분류상세*/
	private String groupDescJp; /*물품분류설명*/
	private String groupNameCh; /*물품분류상세*/
	private String groupDescCh; /*물품분류설명*/
	private String actionDiv;/*action구분*/
	private String openYn;/*개봉여부*/
	private String passYn;/*통과여부*/
	private String useYn;/*사용여부*/
	private String parentUnitGroupCd;/*상위물품분류코드*/
	
	private Long codeNo;/*물품언어셋내부no*/
	private String insertDate;/*등록일*/
	private String insertId;/*등록자*/
	private String updateDate;/*수정일*/
	private String updateId;/*수정자*/
	private String languageCode;/*언어셋*/
	private String languageCodeName;/*언어셋명*/
	private byte[] imgFile;/*이미지*/
	private String searchval;
}
