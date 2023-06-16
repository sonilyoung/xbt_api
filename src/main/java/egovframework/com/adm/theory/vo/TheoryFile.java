package egovframework.com.adm.theory.vo;

import java.util.List;

import egovframework.com.file.vo.AttachFile;
import lombok.Data;

@Data
public class TheoryFile {

	private Long theoryNo;
	private String eduCode;
	private String eduName;
	private String title;
	private String contents;
	private String useYn;
	private String insertId;
	private String insertDate;
	private String updateId;
	private String updateDate;
	
	private List<Long> theoryNoList;
	private List<AttachFile> files;
}
