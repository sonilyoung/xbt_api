package egovframework.com.adm.contents.vo;

import java.util.List;

import lombok.Data;

@Data
public class UnitInformation{

	private String unitScanId;/*정보관리Id*/
	private String unitId; /*물품아이디*/
	private String unitName; /*물품분류명칭*/
	private String unitDesc; /*물품분류상세*/
	private String unitGroupCd;/*물품그룹코드*/
	private String unitGroupName;/*물품그룹코드*/
	private String useYn;/*사용여부*/
	private String useYnNm;/*사용여부*/
	private String parentUnitGroupCd;/*상위물품분류코드*/
	private String insertDate;/*등록일*/
	private String insertId;/*등록자*/
	private String updateDate;/*수정일*/
	private String updateId;/*수정자*/
	private String languageCode;/*언어셋*/
	
	List<UnitInformation> updateList;//수정목록
}
