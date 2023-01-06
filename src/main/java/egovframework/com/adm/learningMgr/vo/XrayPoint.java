package egovframework.com.adm.learningMgr.vo;

import lombok.Data;

@Data
public class XrayPoint {
	
	private Long pointsStdId;/* 배점기준id */
	private String pointsStdNm;/* 배점기준명칭 */
	private String pointsStdDc;/* 배점기준설명 */
	private String useYn;       /* 사용여부 */  

}
