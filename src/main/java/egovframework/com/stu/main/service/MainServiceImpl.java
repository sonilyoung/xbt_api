package egovframework.com.stu.main.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.stu.main.dao.MainDAO;
import egovframework.com.stu.main.vo.Schedule;
import egovframework.com.stu.main.vo.Statistics;
import egovframework.com.stu.main.vo.UserStInfo;
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
@Service("MainService")
public class MainServiceImpl implements MainService {

    @Resource(name = "MainDAO")
	private MainDAO mainDAO;

	@Override
	public UserStInfo selectBaselineUserInfo(UserStInfo params) {
		
		return mainDAO.selectBaselineUserInfo(params);
	}
	
    @Override
	public Schedule selectDefaultMenu(Schedule params) {
		
		return mainDAO.selectDefaultMenu(params);
	}	

    @Override
	@SuppressWarnings("unchecked")
	public List<Schedule> selectScheduleList(Schedule params) {
		
		return (List<Schedule>)mainDAO.selectScheduleList(params);
	}
    
	@Override
	@SuppressWarnings("unchecked")
	public List<Statistics> selectStatisticsMainTitleList(Statistics params) {
		
		return (List<Statistics>)mainDAO.selectStatisticsMainTitleList(params);
	}    

	@Override
	@SuppressWarnings("unchecked")
	public List<Statistics> selectStatisticsTitleList(Statistics params) {
		
		return (List<Statistics>)mainDAO.selectStatisticsTitleList(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Statistics> selectStatisticsContensList(Statistics params) {
		
		return (List<Statistics>)mainDAO.selectStatisticsContensList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Statistics> selectStatisticsWrongAnswerList1(Statistics params) {
		
		return (List<Statistics>)mainDAO.selectStatisticsWrongAnswerList1(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Statistics> selectStatisticsWrongAnswerList2(Statistics params) {
		
		return (List<Statistics>)mainDAO.selectStatisticsWrongAnswerList2(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Statistics> selectStatisticsWrongAnswerList3(Statistics params) {
		
		return (List<Statistics>)mainDAO.selectStatisticsWrongAnswerList3(params);
	}


	

}
