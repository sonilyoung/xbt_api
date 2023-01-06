package egovframework.com.adm.eduMgr.vo;

import lombok.Data;

@Data
public class EduGroupMgr {
	private Long procGroupNo;/* 과정분류코드번호 */
	private String procGroupCd;/* 과정분류코드 */
	private String procGroupNm;/* 과정분류명칭 */
	private String procGroupDc;/* 과정분류설명 */
	private String procGroupSort;/* 과정분류순서 */
	private String useYn; /* 사용여부 */
	private String parentProcGroupCd; /* 상위과정분류코드 */
	private String topProcGroupCd; /* 최상위과정분류코드 */  
	
	private String searchType; /*분류 조회 조건 searchType:1 대분류 , searchType:2 중분류 searchType:3 소분류*/
}
