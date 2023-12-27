package egovframework.com.adm.theory.service;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.theory.dao.TheoryDAO;
import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.adm.theory.vo.TheoryFile;
import egovframework.com.adm.theory.vo.TheoryGroup;
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
@Service("theoryService")
public class TheoryServiceImpl implements TheoryService {

    @Resource(name = "TheoryDAO")
	private TheoryDAO theoryDAO;

	@Override
	@SuppressWarnings("unchecked")
	public List<TheoryGroup> selectTheoryGroupList(TheoryGroup params) {
		
		return (List<TheoryGroup>)theoryDAO.selectTheoryGroupList(params);
	}

	@Override
	public int insertTheoryGroup(TheoryGroup params) {
		
		return theoryDAO.insertTheoryGroup(params);
	}

	@Override
	public int updateTheoryGroup(TheoryGroup params) {
		
		return theoryDAO.updateTheoryGroup(params);
	}

	@Override
	public int deleteTheoryGroup(TheoryGroup params) {
		
		return theoryDAO.deleteTheoryGroup(params);
	}

	@Override
	public TheoryGroup selectTheoryGroup(TheoryGroup params) {
		
		return theoryDAO.selectTheoryGroup(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Theory> selectTheoryList(Theory params) {
		
		return (List<Theory>)theoryDAO.selectTheoryList(params);
	}

	@Override
	public int insertTheory(Theory params) {
		
		return theoryDAO.insertTheory(params);
	}

	@Override
	public int updateTheory(Theory params) {
		
		return theoryDAO.updateTheory(params);
	}

	@Override
	public int deleteTheory(Theory params) {
		
		return theoryDAO.deleteTheory(params);
	}

	@Override
	public Theory selectTheory(Theory params) {
		
		return theoryDAO.selectTheory(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TheoryFile> selectTheoryFileList(TheoryFile params) {
		
		return (List<TheoryFile>) theoryDAO.selectTheoryFileList(params);
	}

	@Override
	public TheoryFile selectTheoryFile(TheoryFile params) {
		
		return theoryDAO.selectTheoryFile(params);
	}

	@Override
	public int insertTheoryFile(TheoryFile params) {
		
		return theoryDAO.insertTheoryFile(params);
	}

	@Override
	public int updateTheoryFile(TheoryFile params) {
		
		return theoryDAO.updateTheoryFile(params);
	}

	@Override
	public int deleteTheoryFile(TheoryFile params) {
		
		return theoryDAO.deleteTheoryFile(params);
	}

	@Override
	public int insertTheoryExcel(LinkedHashMap<String, Object> params) {
		
		return theoryDAO.insertTheoryExcel(params);
	}	
	
}
