package egovframework.com.adm.learningMgr.vo;

import lombok.Data;

@Data
public class XrayPointDetail {
	
	private Long pointsStdId; /* 배점기준id */
	private String actionDiv; /* action구분 */
	private String actionNm; /* action구분명 */
	private String banUnitScore; /* 금지물품점수 */
	private String limitUnitScore; /* 제한물품점수 */
	private String questionUnitScore; /* 의심물품점수 */
	private String passUnitScore; /* 통과물품점수 */	

}
