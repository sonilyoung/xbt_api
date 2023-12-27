package egovframework.com.adm.contents.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Contents implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long unitGroupNo;/*물품그룹코드*/
	private Long codeNo;/*물품언어셋내부no*/
	private String unitGroupCd; /*물품코드분류*/
	private String unitGroupName; /*물품분류상세*/
	private String unitGroupDesc; /*물품분류설명*/
	private String unitName; /*단품분류명칭*/
	private String unitDesc; /*단품분류상세*/
	private String actionDiv;/*action구분*/
	private String openYn;/*개봉여부*/
	private String passYn;/*통과여부*/
	private byte[] unitImg;/*물품이미지*/
	private String useYn;/*사용여부*/
	private String parentUnitGroupCd;/*상위물품분류코드*/
	private String insertDate;/*등록일*/
	private String insertId;/*등록자*/
	private String updateDate;/*수정일*/
	private String updateId;/*수정자*/
	private String languageCode;/*언어셋*/
	private String languageCodeName;/*언어셋명*/
	List<Contents> updateList;//수정목록
	
	private Object query;
	private Object queryDesc;
	
}
