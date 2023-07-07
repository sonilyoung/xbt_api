package egovframework.com.adm.eduMgr.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import egovframework.com.adm.eduMgr.dao.EduMgrDAO;
import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.eduMgr.vo.EduDate;
import egovframework.com.adm.eduMgr.vo.Student;
import egovframework.com.adm.system.dao.SystemDAO;
import egovframework.com.adm.system.vo.Menu;
import egovframework.com.adm.userMgr.dao.UserMgrDAO;
import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.com.stu.learning.vo.LearningProblem;
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
@Service("EduMgrService")
public class EduMgrServiceImpl implements EduMgrService {

    @Resource(name = "EduMgrDAO")
	private EduMgrDAO eduMgrDAO;

    @Resource(name = "UserMgrDAO")
	private UserMgrDAO userMgrDAO;
    
    @Resource(name = "SystemDAO")
	private SystemDAO systemDAO;
   

	@Override
	@SuppressWarnings("unchecked")
	public List<Baseline> selectBaselineList(Baseline params) {
		// TODO Auto-generated method stub
		return (List<Baseline>) eduMgrDAO.selectBaselineList(params);
	}
	
	@Override
	@Transactional
	public int insertBaseline(Baseline params) {
		// TODO Auto-generated method stub
		int result = eduMgrDAO.insertBaseline(params);
		Baseline baseline = eduMgrDAO.selectBaseline(params);
		
		for(String u : params.getUserList()) {
			
			Long moduleId = (long) 0;
			for(int i=0; i < params.getScheduleList().size();i++) {
				for(int j=0; j < params.getMenuList().get(i).size();j++) {
					EduDate sl = new EduDate();
					Menu m = new Menu();
					m.setMenuCd(params.getMenuList().get(i).get(j));
					
					//모듈정보추가
					m.setModuleId(params.getModuleList().get(i));
					
					Menu menu = systemDAO.selectModuleMenu(m);
					if(menu.getModuleId()!=null) {
						moduleId = menu.getModuleId();
					}
					sl.setModuleId(moduleId);
					sl.setProcCd(baseline.getProcCd());
					sl.setProcNm(baseline.getProcName());
					sl.setMenuCd(menu.getMenuCd());  
					sl.setMenuNm(menu.getMenuName());  
					sl.setModuleType(menu.getModuleType());  
					sl.setLearningType(menu.getLearningType()); 
					sl.setUserId(u);
					sl.setInsertId(params.getUserId());
					sl.setEduStartDate(params.getScheduleList().get(i).getEduStartDate()); 
					sl.setEduEndDate(params.getScheduleList().get(i).getEduEndDate());
					eduMgrDAO.insertEduDate(sl);						
				}
			}
			
			
			UserInfo ui = new UserInfo();
			ui.setUserId(u);
			UserInfo userInfo = userMgrDAO.selectUser(ui);
			
			Student s = new Student();
			s.setModuleId(moduleId);
			s.setProcCd(baseline.getProcCd());
			s.setProcYear(baseline.getProcYear());
			s.setProcSeq(baseline.getProcSeq());
			s.setProcNm(baseline.getProcName());
			s.setEduStartDate(baseline.getEduStartDate());
			s.setEduEndDate(baseline.getEduEndDate());
			s.setModuleId(baseline.getModuleId());
			
			s.setEduCode(userInfo.getEduCode());
			s.setEduName(userInfo.getEduName());
			s.setDeptNm(userInfo.getDept());
			s.setUserId(u);
			s.setUserNm(userInfo.getUserNm());
			s.setCompNm(userInfo.getCompany());
			s.setDeptNm(userInfo.getDept());
			s.setInsertId(params.getUserId());
			eduMgrDAO.insertBaselineStudent(s);				
		}
				
		
		return result;
	}

	@Override
	@Transactional
	public int updateBaseline(Baseline params) {
		// TODO Auto-generated method stub
		int result = eduMgrDAO.updateBaseline(params);
		
		Baseline baseline = eduMgrDAO.selectBaseline(params);
		
		//사용자삭제
		Student uiall = new Student();
		uiall.setProcCd(baseline.getProcCd());
		eduMgrDAO.deleteBaselineStudentAll(uiall);
		
		//일정삭제
		EduDate ed = new EduDate();
		ed.setProcCd(baseline.getProcCd());
		eduMgrDAO.deleteEduDateAll(ed);		
		
		for(String u : params.getUserList()) {
			Long moduleId = (long) 0;
			for(int i=0; i < params.getScheduleList().size();i++) {
				for(int j=0; j < params.getMenuList().get(i).size();j++) {
					EduDate sl = new EduDate();
					Menu m = new Menu();
					m.setMenuCd(params.getMenuList().get(i).get(j));
					
					//모듈정보추가
					m.setModuleId(params.getModuleList().get(i));
					
					Menu menu = systemDAO.selectModuleMenu(m);
					if(menu.getModuleId()!=null) {
						moduleId = menu.getModuleId();
					}
					sl.setModuleId(moduleId);
					sl.setProcCd(baseline.getProcCd());
					sl.setProcNm(baseline.getProcName());
					sl.setMenuCd(menu.getMenuCd());  
					sl.setMenuNm(menu.getMenuName());  
					sl.setModuleType(menu.getModuleType());  
					sl.setLearningType(menu.getLearningType()); 
					sl.setUserId(u);
					sl.setInsertId(params.getUserId());
					sl.setEduStartDate(params.getScheduleList().get(i).getEduStartDate()); 
					sl.setEduEndDate(params.getScheduleList().get(i).getEduEndDate());
					eduMgrDAO.insertEduDate(sl);						
				}
			}
			
			
			UserInfo ui = new UserInfo();
			ui.setUserId(u);
			UserInfo userInfo = userMgrDAO.selectUser(ui);
			
			Student s = new Student();
			s.setModuleId(moduleId);
			s.setProcCd(baseline.getProcCd());
			s.setProcYear(baseline.getProcYear());
			s.setProcSeq(baseline.getProcSeq());
			s.setProcNm(baseline.getProcName());
			s.setEduStartDate(baseline.getEduStartDate());
			s.setEduEndDate(baseline.getEduEndDate());
			s.setModuleId(baseline.getModuleId());
			
			s.setEduCode(userInfo.getEduCode());
			s.setEduName(userInfo.getEduName());			
			s.setDeptNm(userInfo.getDept());
			s.setUserId(u);
			s.setUserNm(userInfo.getUserNm());
			s.setCompNm(userInfo.getCompany());
			s.setDeptNm(userInfo.getDept());
			s.setInsertId(params.getUserId());
			eduMgrDAO.insertBaselineStudent(s);	
		}		
		
		return result;
	}

	@Override
	public int deleteBaseline(Baseline params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteBaseline(params);
	}

	@Override
	public Baseline selectBaseline(Baseline params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.selectBaseline(params);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Student> selectBaselineStudentList(Student params) {
		// TODO Auto-generated method stub
		return (List<Student>)eduMgrDAO.selectBaselineStudentList(params);
	}
	
	@Override
	public Student selectBaselineStudent(Student params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.selectBaselineStudent(params);
	}	

	@Override
	public int insertBaselineStudent(Student params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.insertBaselineStudent(params);
	}


	@Override
	public int deleteBaselineStudent(Student params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteBaselineStudent(params);
	}	
	
	@Override
	public int deleteBaselineStudentAll(Student params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteBaselineStudentAll(params);
	}		
		
	@Override
	@SuppressWarnings("unchecked")
	public List<EduDate> selectEduDateList(EduDate params) {
		// TODO Auto-generated method stub
		return (List<EduDate>)eduMgrDAO.selectEduDateList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EduDate> selectEduDateListPop(EduDate params) {
		// TODO Auto-generated method stub
		return (List<EduDate>)eduMgrDAO.selectEduDateListPop(params);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public EduDate selectEduModuleList(EduDate params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.selectEduModuleList(params);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EduDate> selectEduMenuList(EduDate params) {
		// TODO Auto-generated method stub
		return (List<EduDate>)eduMgrDAO.selectEduMenuList(params);
	}	

	@Override
	public int insertEduDate(EduDate params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.insertEduDate(params);
	}


	@Override
	public int deleteEduDate(EduDate params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteEduDate(params);
	}
	
	@Override
	public int deleteEduDateAll(EduDate params) {
		// TODO Auto-generated method stub
		return eduMgrDAO.deleteEduDateAll(params);
	}

	@Override
	@Transactional
	public int insertBaselineCopy(Baseline params) {
		// TODO Auto-generated method stub
		int result = eduMgrDAO.insertBaselineCopy(params);
		eduMgrDAO.insertBaselineDateCopy(params);
		eduMgrDAO.insertBaselineStudentCopy(params);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
		String targetStartDate = params.getEduStartDate();
		String targetEndDate = params.getEduEndDate();
		LocalDate startDate = LocalDate.parse(targetStartDate.replaceAll("-", "/"), formatter);
		LocalDate endDate = LocalDate.parse(targetEndDate.replaceAll("-", "/"), formatter);
		List<LocalDate> bdate = egovframework.com.global.util.ComUtils.getDatesBetweenTwoDates(startDate, endDate);
		for(LocalDate ld : bdate) {
			System.out.println(String.valueOf(ld));
		}
		
		List<Baseline> dateLit = (List<Baseline>) eduMgrDAO.selectEduDateInfoList(params);
		
		int i = 0;
		for(Baseline b : dateLit) {
			b.setProcCd(params.getProcCd());
			
			if(i<=dateLit.size()){
				b.setEduStartDateCopy(String.valueOf(bdate.get(i)));
				b.setEduEndDateCopy(String.valueOf(bdate.get(i)));
				eduMgrDAO.updateBaselineEduDate(b);
			}else {
				b.setEduStartDateCopy("");
				b.setEduEndDateCopy("");
				eduMgrDAO.updateBaselineEduDate(b);
			}
			i++;						
			

		}				
		
		return result;
	}


	public int selectBaselineDataCount(Baseline params) {
		return eduMgrDAO.selectBaselineDataCount(params);
	}
}
