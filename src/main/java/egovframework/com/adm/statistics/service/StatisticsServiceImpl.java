package egovframework.com.adm.statistics.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.adm.statistics.dao.StatisticsDAO;
import egovframework.com.adm.statistics.vo.StatisticsGroup;
import egovframework.com.adm.statistics.vo.StatisticsPerformance;
import egovframework.com.adm.statistics.vo.StatisticsPerformanceDetail;
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
@Service("StatisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource(name = "StatisticsDAO")
	private StatisticsDAO statisticsDAO;


	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsPerformance> selectStatisticsLearningList(StatisticsPerformance params) {
		// TODO Auto-generated method stub
		return (List<StatisticsPerformance>) statisticsDAO.selectStatisticsLearningList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsPerformanceDetail> selectStatisticsLearning(StatisticsPerformanceDetail params) {
		// TODO Auto-generated method stub
		return (List<StatisticsPerformanceDetail>) statisticsDAO.selectStatisticsLearning(params);
	}	

	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsPerformanceDetail> selectStatisticsLearningDetail(StatisticsPerformanceDetail params) {
		// TODO Auto-generated method stub
		return (List<StatisticsPerformanceDetail>) statisticsDAO.selectStatisticsLearningDetail(params);
	}		
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsGroup> selectStatisticsLearningGroupList(StatisticsGroup params) {
		// TODO Auto-generated method stub
		return (List<StatisticsGroup>) statisticsDAO.selectStatisticsLearningGroupList(params);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsPerformance> selectStatisticsEvaluationList(StatisticsPerformance params) {
		// TODO Auto-generated method stub
		return (List<StatisticsPerformance>) statisticsDAO.selectStatisticsEvaluationList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsPerformanceDetail> selectStatisticsEvaluation(StatisticsPerformanceDetail params) {
		// TODO Auto-generated method stub
		return (List<StatisticsPerformanceDetail>) statisticsDAO.selectStatisticsEvaluation(params);
	}	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsGroup> selectStatisticsEvaluationGroupList(StatisticsGroup params) {
		// TODO Auto-generated method stub
		return (List<StatisticsGroup>) statisticsDAO.selectStatisticsEvaluationGroupList(params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsPerformance> selectStatisticsTheoryList(StatisticsPerformance params) {
		// TODO Auto-generated method stub
		return (List<StatisticsPerformance>) statisticsDAO.selectStatisticsTheoryList(params);
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<StatisticsPerformanceDetail> selectStatisticsTheory(StatisticsPerformanceDetail params) {
		// TODO Auto-generated method stub
		return (List<StatisticsPerformanceDetail>) statisticsDAO.selectStatisticsTheory(params);
	}	
	
}
