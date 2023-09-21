package egovframework.com.api.edc.vo;

import lombok.Data;

@Data
public class ApiLog {
	private Long logId;
	private String seqId;
	private String apiUrl;
	private String apiCommand;
	private int progressPer;
	private String requestCode;
	private String requestContents;
	private String responseCode;
	private String responseContents;
	private String insertId;
	private String insertDate;
}
