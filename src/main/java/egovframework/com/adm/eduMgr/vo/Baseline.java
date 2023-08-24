package egovframework.com.adm.eduMgr.vo;

import java.util.List;

import lombok.Data;

@Data
public class Baseline {
	private Long procCd;
	private Long targetProcCd;
	private String procYear;
	private String procSeq;
	private String procName;
	private Long moduleId;
	private String eduDate;
	private String eduStartDate;
	private String eduEndDate;
	private String eduStartDateCopy;
	private String eduEndDateCopy;	
	private int totStudyDate;
	private int limitPersonCnt;
	private int endingStdScore;
	private String endingProcessEndYn;
	private int totTimeDiff;
	private int studyLvl;
	private int practiceTotalScore;
	private int evaluationTotalScore;
	private int theoryTotalScore;
	
	private int passScore;
	private int passTheoryScore;
	private int passPracticeScore;	
	
	private String timeDiff;
	private String insertDate;
	private String insertId;
	private String updateDate;
	private String updateId;
	private String userId;
	private String searchval;
	private String teacherId;
	
	private List<Student> stuList;
	private List<List<String>> menuList;
	private List<Long> modulesList;
	private List<EduDate> scheduleList;
	private List<String> userList;
	private List<Long> moduleList;
	private List<Long> procCdList;
	

}

