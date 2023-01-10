package egovframework.com.adm.userMgr.vo;

import lombok.Data;

@Data
public class UserBaselineDetail {

	private String procYear;                         /* 교육년도 */
	private String procGainGrade;                   /* 이수학점 */
	private String procNm;                           /* 과정명 */
	private String procCd;                           /* 과정코드 */
	private String procSeq;                          /* 과정차수 */
	private String startDate;                          /* 학습시작일자 */
	private String endDate;                          /* 학습종료일자 */
	private String company;                           /* 회사명 */
	private String dept;                           /* 부서명 */
	private String goinStatus;                       /* 입교상태 */
	private String attendPercent;                    /* 출석율 */
	private String unGoinReasonCd;                /* 미입교사유코드 */
	private String unGoinReason;                    /* 미입교사유 */
	private String gainScore;                        /* 취득점수 */
	private String xtsRank;                              /* 석차 */
	private String endingYn;                         /* 수료여부 */
	private String passYn;                           /* 합격여부 */
	private String unEndingReasonCd;               /* 미수료사유코드 */
	private String unEndingReasonNm;               /* 미수료사유명 */
	private String userId;               /* 미수료사유명 */

	
}
