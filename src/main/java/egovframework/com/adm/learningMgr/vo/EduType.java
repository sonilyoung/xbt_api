package egovframework.com.adm.learningMgr.vo;

import lombok.Data;

@Data
public class EduType {
	
	private Long eduTypeId; /* 교육타입ID */
	private String eduTypeNm;/* 교육타입명 */
	private String eduTypeDc;/* 교육타입 설명 */
	private String useYn;/* 사용유무 */
	private String pointsStdId;/* 배점기준ID */
	private String pointsStdNm;/* 배점기준명칭 */
	private String insertDate;/* 등록일자 */
	private String insertId;/* 등록자ID */
	private String updateDate;/* 수정일자 */
	private String updateId;/* 수정자ID */

}
