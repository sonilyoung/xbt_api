package egovframework.com.adm.statistics.dao;

import java.util.List;

import egovframework.com.adm.statistics.vo.StatisticsGroup;
import egovframework.com.adm.statistics.vo.StatisticsMainEdu;
import egovframework.com.adm.statistics.vo.StatisticsMainYear;
import egovframework.com.adm.statistics.vo.StatisticsPerformance;
import egovframework.com.adm.statistics.vo.StatisticsPerformanceDetail;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("StatisticsDAO")
public class StatisticsDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.statistics.dao.StatisticsDAO";
	
	public List<?> selectMainEduStatistics(StatisticsMainEdu params) {
		return (List<?>)selectList(Namespace + ".selectMainEduStatistics", params);
	}
	
	public List<?> selectMainYearStatistics(StatisticsMainYear params) {
		return (List<?>)selectList(Namespace + ".selectMainYearStatistics", params);
	}
	
	public List<?> selectStatisticsLearningList(StatisticsPerformance params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsLearningList", params);
	}
	
	public List<?> selectStatisticsLearning(StatisticsPerformanceDetail params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsLearning", params);
	}	
	
	public List<?> selectStatisticsLearningDetail(StatisticsPerformanceDetail params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsLearningDetail", params);
	}		
	
	public List<?> selectStatisticsLearningGroupList(StatisticsGroup params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsLearningGroupList", params);
	}
		
	public List<?> selectStatisticsEvaluationList(StatisticsPerformance params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsEvaluationList", params);
	}
	
	
	public List<?> selectStatisticsEvaluation(StatisticsPerformanceDetail params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsEvaluation", params);
	}	
		
	
	public List<?> selectStatisticsEvaluationGroupList(StatisticsGroup params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsEvaluationGroupList", params);
	}	

	public List<?> selectStatisticsTheoryList(StatisticsPerformance params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsTheoryList", params);
	}	
	
	
	public List<?> selectStatisticsTheory(StatisticsPerformanceDetail params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsTheory", params);
	}	
		
}
