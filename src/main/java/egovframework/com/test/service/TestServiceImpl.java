package egovframework.com.test.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.dao.ContentsDAO;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.common.vo.SeqGroupCode;
import egovframework.com.test.dao.TestDAO;
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
@Service("TestService")
public class TestServiceImpl implements TestService {

    @Resource(name = "TestDAO")
	private TestDAO testDAO;

	@Override
	public int insertXbtBagConstUnitTemp(LinkedHashMap<String,Object> params) {
		// TODO Auto-generated method stub
		return testDAO.insertXbtBagConstUnitTemp(params);
	}

	@Override
	public int insertXbtBagInfoTemp(LinkedHashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return testDAO.insertXbtBagInfoTemp(params);
	}
	
	
	@Override
	public int insertUnitTemp(LinkedHashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return testDAO.insertUnitTemp(params);
	}
		
	@Override
	public int insertXbtBagConstUnitRename(LinkedHashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return testDAO.insertXbtBagConstUnitRename(params);
	}
	
	@Override
	public int insertXbtBagInfoRename(LinkedHashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return testDAO.insertXbtBagInfoRename(params);
	}


	@Override
	public int insertUnitRename(LinkedHashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return testDAO.insertUnitRename(params);
	}


}
