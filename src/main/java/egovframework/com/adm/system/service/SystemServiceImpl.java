package egovframework.com.adm.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.system.dao.SystemDAO;
import egovframework.com.adm.system.vo.Menu;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.adm.system.vo.XbtScore;
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
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Resource(name = "SystemDAO")
	private SystemDAO systemDAO;

	@Override
	@SuppressWarnings("unchecked")
	public List<Notice> selectNoticeList(Notice params) {
		
		return (List<Notice>)systemDAO.selectNoticeList(params);
	}

	@Override
	public int insertNotice(Notice params) {
		
		return systemDAO.insertNotice(params);
	}

	@Override
	public int updateNotice(Notice params) {
		
		return systemDAO.updateNotice(params);
	}

	@Override
	public int deleteNotice(Notice params) {
		
		return systemDAO.deleteNotice(params);
	}

	@Override
	public Notice selectNotice(Notice params) {
		
		return systemDAO.selectNotice(params);
	}
    
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Menu> selectMenuList(Menu params) {
		
		return (List<Menu>)systemDAO.selectMenuList(params);
	}
	
	@Override
	public Menu selectModuleMenu(Menu params) {
		
		return systemDAO.selectModuleMenu(params);
	}		

	@Override
	public int insertMenu(Menu params) {
		
		return systemDAO.insertMenu(params);
	}

	@Override
	public int updateMenu(Menu params) {
		
		return systemDAO.updateMenu(params);
	}

	@Override
	public int deleteMenu(Menu params) {
		
		return systemDAO.deleteMenu(params);
	}

	@Override
	public Menu selectMenu(Menu params) {
		
		return systemDAO.selectMenu(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Menu> selectModuleMenuList(Menu params) {
		
		return (List<Menu>) systemDAO.selectModuleMenuList(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<XbtScore> selectXbtEndingUserList(XbtScore params) {
		
		return (List<XbtScore>) systemDAO.selectXbtEndingUserList(params);
	}
	

	@Override
	public XbtScore selectXbtEndingUser(XbtScore params) {
		
		return systemDAO.selectXbtEndingUser(params);
	}
	

	@Override
	public XbtScore selectTheoryScore(XbtScore params) {
		
		return systemDAO.selectTheoryScore(params);
	}

	@Override
	public XbtScore selectEvaluationScore(XbtScore params) {
		
		return systemDAO.selectEvaluationScore(params);
	}

	@Override
	public XbtScore selectPracticeScore(XbtScore params) {
		
		return systemDAO.selectPracticeScore(params);
	}

	@Override
	public int updateXbtScore(XbtScore params) {
		
		return systemDAO.updateXbtScore(params);
	}
	
	@Override
	public XbtScore selectProcessScore(XbtScore params) {
		
		return systemDAO.selectProcessScore(params);
	}
	
	@Override
	public XbtScore selectTheoryProcessScore(XbtScore params) {
		
		return systemDAO.selectTheoryProcessScore(params);
	}	

	@Override
	public int updateXbtEndScore(XbtScore params) {
		
		return systemDAO.updateXbtEndScore(params);
	}
	
	@Override
	public int updateLearningStatus(XbtScore params) {
		
		return systemDAO.updateLearningStatus(params);
	}
	
	@Override
	public int updateBaselineStatus(XbtScore params) {
		
		return systemDAO.updateBaselineStatus(params);
	}
	
	@Override
	public int updateXrayExcelData(XrayContents params) {
		
		return systemDAO.updateXrayExcelData(params);
	}

	@Override
	public int updateEvaluationProgressingBackup(XbtScore params) {
		
		return systemDAO.updateEvaluationProgressingBackup(params);
	}

	@Override
	public int updateBaselineEvaluationBackup(XbtScore params) {
		
		return systemDAO.updateBaselineEvaluationBackup(params);
	}

	@Override
	public int updateTheoryProgressingBackup(XbtScore params) {
		
		return systemDAO.updateTheoryProgressingBackup(params);
	}

	@Override
	public int updateBaselineTheoryBackup(XbtScore params) {
		
		return systemDAO.updateBaselineTheoryBackup(params);
	}

	@Override
	public int updateBaselineStudentInfoBackup(XbtScore params) {
		
		return systemDAO.updateBaselineStudentInfoBackup(params);
	}
	
}
