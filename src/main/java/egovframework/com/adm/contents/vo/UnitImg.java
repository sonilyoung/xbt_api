package egovframework.com.adm.contents.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UnitImg {
	
	private Long unitInfoNo;//이미지스캔내부no
	private String unitScanId;//이미지스캔id
	private String unitId;//담품아이디
	private String unitGroupCd;//그룹코드
	private String unitGroupName;//그룹명
	private String useYn;//사용여부
	private byte[] realImg;//실사
	private byte[] frontImg;//정면이미지
	private byte[] sideImg;//측면이미지
	private byte[] threedImg;//3D
	private String angle;//각도
	private String unitName;//단품명
	private String unitDesc;//설명
	private String insertId;//등록자 
	private String insertDate;//등록일
	private String updateId;//수정자
	private String updateDate;//수정일
	private String studyLvl;//학습레벨
	private String seqInfo;
	private String imgType;//이미지유형 (real, front, side)
	private String languageCode;
	private String searchval;

	private MultipartFile realmImg;//실사
	private MultipartFile frontmImg;//정면이미지
	private MultipartFile sidemImg;//측면이미지	
}
