package egovframework.com.adm.contents.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.Data;

@Data
public class Contents implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long unitGroupNo;
	private String unitGroupCd; /*물품코드분류*/
	private String unitName; /*물품분류명칭*/
	private String unitDesc; /*물품분류상세*/
	private String actionDiv;/*action구분*/
	private String openYn;/*개봉여부*/
	private String passYn;/*통과여부*/
	private byte[] unitImg;/*물품이미지*/
	private String useYn;/*사용여부*/
	private String parentUnitGroupCd;/*상위물품분류코드*/
	private String INSERT_DATE;/*등록일*/
	private String INSERT_ID;/*등록자*/
	private String UPDATE_DATE;/*수정일*/
	private String UPDATE_ID;/*수정자*/
	private String languageCode;/*언어셋*/
	List<Contents> updateList;//수정목록
	
	private Object query;
	private Object queryDesc;
	
}
