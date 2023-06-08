package egovframework.com.stu.main.vo;

import java.util.List;

import lombok.Data;

@Data
public class Schedule {
	
	
	private Long menuNo;
	private String pMenuCd;
	private String menuCd;
	private String menuName;
	private String menuUrl;
	private String menuLevel;
	private String useYn;
	private String menuOrder;
	private String languageCode;
	private String userId;
	private String moduleId;
	private String moduleType;
	private String learningType;
	private String menuFlag;
	
	private List<Schedule> menu1;
	private List<Schedule> menu2;
	private List<Schedule> menu3;
	private List<Schedule> menu4;



}
