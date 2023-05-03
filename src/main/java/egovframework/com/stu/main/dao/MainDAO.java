package egovframework.com.stu.main.dao;

import java.util.List;

import egovframework.com.stu.main.vo.Schedule;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MainDAO")
public class MainDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.main.dao.MainDAO";
	
	
	public List<?> selectScheduleList(Schedule params) {
		return (List<?>)selectList(Namespace + ".selectScheduleList", params);
	}			
}
