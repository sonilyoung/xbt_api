package egovframework.com.common.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import egovframework.com.common.dao.CommonDAO;
import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.CommonSystemMessage;
import lombok.extern.log4j.Log4j2;


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
@Log4j2
@Service("commonService")
@SuppressWarnings("unchecked")
public class CommonServiceImpl implements CommonService {

    @Resource(name = "CommonDAO")
	private CommonDAO commonDAO;
    

	@Override
	public List<Common> getCommonGroupList(Common params) {
		// TODO Auto-generated method stub
		return (List<Common>) commonDAO.getCommonGroupList(params);
	}

	@Override
	public List<Common> selectCommonList(Common params) {
		// TODO Auto-generated method stub
		
		List<Common> mainList = (List<Common>) commonDAO.getCommonGroupList(params);
		for(Common m : mainList) {
			m.setLanguageCode(params.getLanguageCode());
			List<Common> subList = (List<Common>) commonDAO.selectCommonList(m);
			m.setSubList(subList);
		}
		
		return mainList;
	}

	@Override
	@Transactional
	public int insertCommonCode(Common params) {
		// TODO Auto-generated method stub
		//commonDAO.insertCommonCode(params);
		int result = commonDAO.insertCommonCodeDetail(params);
		return result;
	}

	@Override
	public int insertCommonCodeDetail(Common params) {
		// TODO Auto-generated method stub
		return commonDAO.insertCommonCodeDetail(params);
	}

	@Override
	public int updateCommonCode(Common params) {
		// TODO Auto-generated method stub
		return commonDAO.updateCommonCode(params);
	}

	@Override
	public int deleteCommonCode(Common params) {
		// TODO Auto-generated method stub
		int result = commonDAO.deleteCommonCode(params);
		commonDAO.deleteCommonCodeDetail(params);
		return result; 
	}

	@Override
	public CommonSystemMessage selectSystemMessage(CommonSystemMessage params) {
		// TODO Auto-generated method stub
		return commonDAO.selectSystemMessage(params);
	}

	@Override
	public List<Common> selectLanguageApplyList(Common params) {
		// TODO Auto-generated method stub
		return (List<Common>) commonDAO.selectLanguageApplyList(params);
	}

	@Override
	public Common selectLanguageApply(Common params) {
		// TODO Auto-generated method stub
		return commonDAO.selectLanguageApply(params);
	}

	@Override
	public int insertLanguageApply(Common params) {
		// TODO Auto-generated method stub
		return commonDAO.insertLanguageApply(params);
	}

	@Override
	public int deleteLanguageApply(Common params) {
		// TODO Auto-generated method stub
		return commonDAO.deleteLanguageApply(params);
	}



}
