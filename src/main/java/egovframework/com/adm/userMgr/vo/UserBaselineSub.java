package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class UserBaselineSub {
	private Long procCd;                           /* 과정코드 */
	private String userId;                           /* 사용자id */
	private String userNm;                           /* 사용자명 */
	private String goinStatusCd;                    /* 입교상태코드 */
	private String goinStatus;                       /* 입교상태 */
	private String procYear;  						/*교육년도*/
	private String procSeq;							/*교육차수*/
}
