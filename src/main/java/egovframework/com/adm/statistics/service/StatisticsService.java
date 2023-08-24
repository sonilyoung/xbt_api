package egovframework.com.adm.statistics.service;

import java.util.List;

import egovframework.com.adm.statistics.vo.StatisticsGroup;
import egovframework.com.adm.statistics.vo.StatisticsMainEdu;
import egovframework.com.adm.statistics.vo.StatisticsMainYear;
import egovframework.com.adm.statistics.vo.StatisticsPerformance;
import egovframework.com.adm.statistics.vo.StatisticsPerformanceDetail;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
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
 *
 *      </pre>
 */
public interface StatisticsService {
	
	public List<StatisticsMainEdu> selectMainEduStatistics(StatisticsMainEdu params);
	
	public List<StatisticsMainYear> selectMainYearStatistics(StatisticsMainYear params);
	
	public List<StatisticsPerformance> selectStatisticsLearningList(StatisticsPerformance params);
	
	public List<StatisticsPerformanceDetail> selectStatisticsLearning(StatisticsPerformanceDetail params);
	
	public List<StatisticsPerformanceDetail> selectStatisticsLearningDetail(StatisticsPerformanceDetail params);
	
	public List<StatisticsGroup> selectStatisticsLearningGroupList(StatisticsGroup params);
	
	public List<StatisticsPerformance> selectStatisticsEvaluationList(StatisticsPerformance params);
	
	public List<StatisticsPerformanceDetail> selectStatisticsEvaluation(StatisticsPerformanceDetail params);
	
	public List<StatisticsGroup> selectStatisticsEvaluationGroupList(StatisticsGroup params);		
	
	public List<StatisticsPerformance> selectStatisticsTheoryList(StatisticsPerformance params);
	
	public List<StatisticsPerformanceDetail> selectStatisticsTheory(StatisticsPerformanceDetail params);
}
