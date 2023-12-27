package egovframework.com.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.common.dao.CommonDAO;
import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.CommonSystemMessage;
//import lombok.extern.log4j.Log4j2;


/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
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
 *   2014.12.08	 이기하			암호화방식 변경(OfficeFileScrty.encryptPassword)
 *
 *      </pre>
 */
//@Log4j2
@Service("commonService")
@SuppressWarnings("unchecked")
public class CommonServiceImpl implements CommonService {

    @Resource(name = "CommonDAO")
	private CommonDAO commonDAO;
    

	@Override
	public List<Common> getCommonGroupList(Common params) {
		
		return (List<Common>) commonDAO.getCommonGroupList(params);
	}

	@Override
	public List<Common> selectCommonList(Common params) {
		
		return (List<Common>) commonDAO.selectCommonList(params);
	}

	@Override
	public Common selectCommon(Common params) {
		
		return commonDAO.selectCommon(params);
	}	
	

	@Override
	public Common selectCommonDetail(Common params) {
		
		return commonDAO.selectCommonDetail(params);
	}	
		
	
	@Override
	public int insertCommonCode(Common params) {
		
		//commonDAO.insertCommonCode(params);
		int result = commonDAO.insertCommonCodeDetail(params);
		return result;
	}

	@Override
	public int insertCommonCodeDetail(Common params) {
		
		return commonDAO.insertCommonCodeDetail(params);
	}

	@Override
	public int updateCommonCode(Common params) {
		
		return commonDAO.updateCommonCode(params);
	}

	@Override
	public int deleteCommonCode(Common params) {
		
		int result = commonDAO.deleteCommonCode(params);
		commonDAO.deleteCommonCodeDetail(params);
		return result; 
	}

	@Override
	public CommonSystemMessage selectSystemMessage(CommonSystemMessage params) {
		
		return commonDAO.selectSystemMessage(params);
	}

	@Override
	public List<Common> selectLanguageApplyList(Common params) {
		
		return (List<Common>) commonDAO.selectLanguageApplyList(params);
	}

	@Override
	public Common selectLanguageApply(Common params) {
		
		return commonDAO.selectLanguageApply(params);
	}

	@Override
	public int insertLanguageApply(Common params) {
		
		return commonDAO.insertLanguageApply(params);
	}

	@Override
	public int deleteLanguageApply(Common params) {
		
		return commonDAO.deleteLanguageApply(params);
	}

	@Override
	public int updateLanguageApply(Common params) {
		
		return commonDAO.updateLanguageApply(params);
	}

	@Override
	public List<Common> selectCommonDetailList(Common params) {
		
		return (List<Common>) commonDAO.selectCommonDetailList(params);
	}

}
