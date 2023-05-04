package egovframework.com.stu.practice.dao;

import java.util.List;

import egovframework.com.stu.practice.vo.Practice;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PracticeDAO")
public class PracticeDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.stu.practice.dao.PracticeDAO";
	
	
	public List<?> selectUnitList(Practice params) {
		return (List<?>)selectList(Namespace + ".selectUnitList", params);
	}			
	
	
	public Practice selectUnit(Practice params) {
		return selectOne(Namespace + ".selectUnit", params);
	}	
		
}
