package egovframework.com.adm.eduMgr.vo;

import java.util.List;

import lombok.Data;

@Data
public class Baseline {
	private Long procCd;
	private String procYear;
	private String procSeq;
	private String procName;
	private Long moduleId;
	private String eduDate;
	private String eduStartDate;
	private String eduEndDate;
	private int totStudyDate;
	private int limitPersonCnt;
	private int endingStdScore;
	private String endingProcessEndYn;
	private int totTimeDiff;
	private int studyLvl;
	private int practiceTotalScore;
	private int evaluationTotalScore;
	private int theoryTotalScore;
	private String timeDiff;
	private String insertDate;
	private String insertId;
	private String updateDate;
	private String updateId;
	private String userId;
	
	private List<Student> stuList;
	private List<EduDate> scheduleList;
	private List<String> userList;
	private List<Long> procCdList;
	

}

