package egovframework.com.adm.learningMgr.vo;

import lombok.Data;

@Data
public class EduModule {
	
	private Long moduleId; //모듈아이디 
	private String moduleNm; //모듈명
	private String moduleDesc;//모듈설명
	private String studyLvl;//학습레벨
	private int slideSpeed;//슬라이드속도
	private String moduleType;//모듈타입 (CUT / SLIDE)
	private int actionDiv0Count;//개봉금지
	private int actionDiv1Count;//금지
	private int actionDiv2Count;//개봉제한
	private int actionDiv3Count;//개봉통과
	private int actionDiv4Count;//통과
	private int actionDiv0Score;//개봉금지 점수
	private int actionDiv1Score;//금지 점수
	private int actionDiv2Score;//개봉제한 점수
	private int actionDiv3Score;//개봉통과 점수
	private int actionDiv4Score;//통과 점수
	private String useYn;
	private int questionCnt;
	private String insertId;
	private String insertDate;
	private String updateId;
	private String updateDate;




}
