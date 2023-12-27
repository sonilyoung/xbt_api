package egovframework.com.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Common implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codeNo;/*시퀀스*/
	private String groupId;/*그룹아이디*/
	private Long codeId;/*코드아이디*/
	private String codeValue;/*코드값*/
	private String sortOrder;/*정렬*/
	private String codeName;/*코드명*/
	private String remarks;/*비고*/
	private String memo1;/*메모1*/
	private String memo2;/*메모2*/
	private String useYn;/*사용유무*/
	private String isDelete;/*삭제*/
	private String insertId;/*등록자*/
	private String insertDate;/*등록일*/
	private String updateId;/*수정자*/
	private String updateDate;/*수정일*/
	private String codeDesc;/*공콩코드상세설명*/	
	private String languageCode;/*언어셋*/
	private String mainYn;/*메인그룹*/
	private String parentsGroupId;/*부모그룹id*/
	private String command;//조건문에서사용
	
	List<Common> subList;//하위목록
	
}
