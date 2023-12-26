package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class MenuPin {
	private long pwdNo;
	private String pinTitle;
	private String pinkey;
	private String pinNumber;
	private String memo;
	private String insertId;
	private String insertDate;
	private String updateId;
	private String updateDate;
	
	private String[] pinData;

}
