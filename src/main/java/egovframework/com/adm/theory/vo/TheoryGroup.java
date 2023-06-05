package egovframework.com.adm.theory.vo;

import java.util.List;

import lombok.Data;

@Data
public class TheoryGroup {

	private Long groupNo;
	private String groupType;
	private String theoryGroupCd;
	private String theoryGroupName;
	private String theoryParentGroupCd;
	private String useYn;
	private String insertId;
	private String insertDate;
	
	private List<Long> groupNoList;

}
