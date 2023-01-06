package egovframework.com.common.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.dao.ContentsDAO;
import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.common.dao.CommonDAO;
import egovframework.com.common.vo.Common;
import egovframework.rte.psl.dataaccess.util.EgovMap;
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
public class CommonServiceImpl implements CommonService {

    @Resource(name = "CommonDAO")
	private CommonDAO commonDAO;
    

	@Override
	public List<Common> getCommonGroupList(Common params) {
		// TODO Auto-generated method stub
		return (List<Common>) commonDAO.getCommonGroupList(params);
	}

	@Override
	public List<Common> getCommonList(Common params) {
		// TODO Auto-generated method stub
		
		List<Common> mainList = (List<Common>) commonDAO.getCommonGroupList(params);
		List<Common> resultList = null;
		for(Common m : mainList) {
			m.setLanguageCode(params.getLanguageCode());
			List<Common> subList = (List<Common>) commonDAO.getCommonList(m);
			m.setSubList(subList);
		}
		
		return mainList;
	}



}
