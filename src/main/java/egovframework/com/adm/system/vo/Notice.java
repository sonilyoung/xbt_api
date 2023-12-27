package egovframework.com.adm.system.vo;

import java.io.Serializable;
import java.util.List;

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
	private String useYn;
	private List<Long> noticeIdList;
	
}
