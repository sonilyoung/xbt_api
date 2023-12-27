package egovframework.com.stu.main.dao;

import java.util.List;

import egovframework.com.stu.main.vo.Schedule;
import egovframework.com.stu.main.vo.Statistics;
import egovframework.com.stu.main.vo.UserStInfo;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MainDAO")
public class MainDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.main.dao.MainDAO";
	
	
	public UserStInfo selectBaselineUserInfo(UserStInfo params) {
		return selectOne(Namespace + ".selectBaselineUserInfo", params);
	}		
	
	public Schedule selectDefaultMenu(Schedule params) {
		return selectOne(Namespace + ".selectDefaultMenu", params);
	}		
	
	public List<?> selectScheduleList(Schedule params) {
		return (List<?>)selectList(Namespace + ".selectScheduleList", params);
	}			
	
	public List<?> selectStatisticsMainTitleList(Statistics params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsMainTitleList", params);
	}	
	
	public List<?> selectStatisticsTitleList(Statistics params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsTitleList", params);
	}
	
	public List<?> selectStatisticsContensList(Statistics params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsContensList", params);
	}
	
	public List<?> selectStatisticsWrongAnswerList1(Statistics params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsWrongAnswerList1", params);
	}			
	
	public List<?> selectStatisticsWrongAnswerList2(Statistics params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsWrongAnswerList2", params);
	}			
	
	public List<?> selectStatisticsWrongAnswerList3(Statistics params) {
		return (List<?>)selectList(Namespace + ".selectStatisticsWrongAnswerList3", params);
	}			
}
