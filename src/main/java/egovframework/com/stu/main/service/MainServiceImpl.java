package egovframework.com.stu.main.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.learningMgr.dao.LearningMgrDAO;
import egovframework.com.stu.main.dao.MainDAO;
import egovframework.com.stu.main.vo.Schedule;
import egovframework.com.stu.main.vo.Statistics;
import egovframework.com.stu.main.vo.UserStInfo;
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
@Service("MainService")
public class MainServiceImpl implements MainService {

    @Resource(name = "MainDAO")
	private MainDAO mainDAO;

	@Override
	public UserStInfo selectBaselineUserInfo(UserStInfo params) {
		// TODO Auto-generated method stub
		return mainDAO.selectBaselineUserInfo(params);
	}

    @Override
	@SuppressWarnings("unchecked")
	public List<Schedule> selectScheduleList(Schedule params) {
		// TODO Auto-generated method stub
		return (List<Schedule>)mainDAO.selectScheduleList(params);
	}

	@Override
	public List<Statistics> selectStatisticsTitleList(Statistics params) {
		// TODO Auto-generated method stub
		return (List<Statistics>)mainDAO.selectStatisticsTitleList(params);
	}

	@Override
	public List<Statistics> selectStatisticsContensList(Statistics params) {
		// TODO Auto-generated method stub
		return (List<Statistics>)mainDAO.selectStatisticsContensList(params);
	}
	
	@Override
	public List<Statistics> selectStatisticsWrongAnswerContentsList(Statistics params) {
		// TODO Auto-generated method stub
		return (List<Statistics>)mainDAO.selectStatisticsWrongAnswerContentsList(params);
	}


	

}
