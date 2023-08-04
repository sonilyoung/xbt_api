package egovframework.com.adm.statistics.dao;

import java.util.List;

import egovframework.com.adm.statistics.vo.StatisticsGroup;
import egovframework.com.adm.statistics.vo.StatisticsPerformance;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("StatisticsDAO")
public class StatisticsDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.statistics.dao.StatisticsDAO";
	
	public List<?> selectStatisticsLearningList(StatisticsPerformance params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsLearningList", params);
	}
	
	public List<?> selectStatisticsLearningGroupList(StatisticsGroup params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsLearningGroupList", params);
	}
		
	
	
}
