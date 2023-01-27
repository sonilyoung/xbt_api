package egovframework.com.adm.contents.vo;

import lombok.Data;

@Data
public class UnitImg {
	private Long unitScanId; //물품촬영id
	private String unitId;//물품id
	private String imgType;//이미지유형
	private String unitGroupCd;//그룹cd
	private String studyLvl;//학습레벨
	private String useYn;//사용여부
	private String decipMachineCd;//판독기코드
	private String imgRotate;//각도별사진
	private String realImg;//실물이미지
	private String colorImg;//컬러이미지
	private String bwImg;//흑백이미지
	private String langSet;//언어셋
	private String unitDesc;//유닛설명
	private String insertDate;//등록일
	private String insertId;//등록자
	private String updateDate;//수정일
	private String updateId;//수정자
}
