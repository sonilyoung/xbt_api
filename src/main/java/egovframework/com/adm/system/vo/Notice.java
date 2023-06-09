package egovframework.com.adm.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.Data;

@Data
public class Notice implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long noticeId;
	private String title;
	private String contents;
	private String insertId;
	private String insertDate;
	private String updateId;
	private String updateDate;
	private String languageCode;
	
	
}
