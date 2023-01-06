package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduClass {
	private Long procGroupDtlGroupCd; /* 과정분류상세분류코드 */
	private String procGroupDtlGroupNm; /* 과정분류상세분류명 */  
	private String largeGroupCd; /* 대분류코드 */
	private String middleGroupCd; /* 중분류코드 */                                               
	private String smallGroupCd; /* 소분류코드 */                                               
	private String largeGroupNm;  /* 대분류명 */
	private String middleGroupNm;  /* 중분류명 */                                               
	private String smallGroupNm;  /* 소분류명 */                                                   
	private String insertDate;    /* 등록일시 */
	private String insertId;     /* 등록자 */
	private String updateDate;  /* 수정일시 */
	private String updateId;     /* 수정자 */
	
}
