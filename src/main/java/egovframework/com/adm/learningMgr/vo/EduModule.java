package egovframework.com.adm.learningMgr.vo;

import java.util.List;

import lombok.Data;

@Data
public class EduModule {
	
	private Long moduleId;
	private String moduleNm;
	private String moduleDesc;
	private int gunCnt;
	private int explosivesCnt;
	private int liveAmmunitionCnt;
	private int swordCnt;
	private int weaponCnt;
	private int camouflageWeaponCnt;
	private int toolLifeCnt;
	private int flammabilityCnt;
	private int dangerMaterialsCnt;
	private int liquidGelCnt;
	private int passCnt;
	private int studyLvl;
	private int slideSpeed;
	private int questionCnt;
	private int gunScore;
	private int explosivesScore;
	private int liveAmmunitionScore;
	private int swordScore;
	private int weaponScore;
	private int camouflageWeaponScore;
	private int toolLifeScore;
	private int flammabilityScore;
	private int dangerMaterialsScore;
	private int liquidGelScore;
	private int passScore;
	private String failToPass;
	private int timeLimit;
	private String moduleType;
	private String learningType;
	private String useYn;
	private String insertId;
	private String insertDate;
	private String updateId;
	private String updateDate;

	private String bagScanId;
	private String questionType;
	private String unitId;
	private String unitGroupCd;
	private String groupName;
	private String actionDiv;
	
	private Long moduleDetailId;

	private List<String> bagList;
	private List<Long> moduleIdList;
	
	private Long bagScanNo;
	private String unitName;                     /*물품명*/
	private String unitDesc;                     /*물품설명*/	
	private String openYn;
	private String passYn;
	private String actionDivName;                  /*action구분*/
	private int menuCd;
}
