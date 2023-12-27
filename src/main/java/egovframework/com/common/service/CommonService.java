package egovframework.com.common.service;

import java.util.List;

import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.CommonSystemMessage;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *
 *      </pre>
 */
public interface CommonService {
	
	public List<Common> getCommonGroupList(Common params);
	
	public List<Common> selectCommonList(Common params);
	
	public Common selectCommon(Common params);
	
	public Common selectCommonDetail(Common params);
	
	public int insertCommonCode(Common params);
	
	public int insertCommonCodeDetail(Common params);
	
	public int updateCommonCode(Common params);
	
	public int deleteCommonCode(Common params);
	
	public CommonSystemMessage selectSystemMessage(CommonSystemMessage params);
	
	List<Common> selectLanguageApplyList(Common params);
	
	Common selectLanguageApply(Common params);
	
	public int insertLanguageApply(Common params);
	
	public int deleteLanguageApply(Common params);
	
	public int updateLanguageApply(Common params);
	
	public List<Common> selectCommonDetailList(Common params);
	
}
