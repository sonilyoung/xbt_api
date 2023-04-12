package egovframework.com.adm.contents.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Language implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codeNo; 
	private String codeId; 
	private String groupId;
	private String languageName;
	private String languageCode;
	private String useYn;
	private String insertId;
	private String insertDate;
	private String codeName;
	private String codeDesc;
	
}
