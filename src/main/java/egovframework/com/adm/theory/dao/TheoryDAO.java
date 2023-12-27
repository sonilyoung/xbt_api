package egovframework.com.adm.theory.dao;

import java.util.LinkedHashMap;
import java.util.List;

import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.adm.theory.vo.TheoryFile;
import egovframework.com.adm.theory.vo.TheoryGroup;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("TheoryDAO")
public class TheoryDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.theory.dao.TheoryDAO";
	
	public List<?> selectTheoryGroupList(TheoryGroup params) {
		return (List<?>)selectList(Namespace + ".selectTheoryGroupList", params);
	}
	
	public int insertTheoryGroup(TheoryGroup params) {
		return insert(Namespace + ".insertTheoryGroup", params);
	}
	
	public int updateTheoryGroup(TheoryGroup params) {
		return update(Namespace + ".updateTheoryGroup", params);
	}	
	
	public int deleteTheoryGroup(TheoryGroup params) {
		return delete(Namespace + ".deleteTheoryGroup", params);
	}	

	public TheoryGroup selectTheoryGroup(TheoryGroup params) {
		return selectOne(Namespace + ".selectTheoryGroup", params);
	}	

	public List<?> selectTheoryList(Theory params) {
		return (List<?>)selectList(Namespace + ".selectTheoryList", params);
	}
	
	public int insertTheory(Theory params) {
		return insert(Namespace + ".insertTheory", params);
	}
	
	public int updateTheory(Theory params) {
		return update(Namespace + ".updateTheory", params);
	}	
	
	public int deleteTheory(Theory params) {
		return delete(Namespace + ".deleteTheory", params);
	}	

	public Theory selectTheory(Theory params) {
		return selectOne(Namespace + ".selectTheory", params);
	}	

	
	public List<?> selectTheoryFileList(TheoryFile params) {
		
		return (List<?>)selectList(Namespace + ".selectTheoryFileList", params);
	}

	public TheoryFile selectTheoryFile(TheoryFile params) {
		
		return selectOne(Namespace + ".selectTheoryFile", params);
	}

	public int insertTheoryFile(TheoryFile params) {
		
		return insert(Namespace + ".insertTheoryFile", params);
	}

	public int updateTheoryFile(TheoryFile params) {
		
		return update(Namespace + ".updateTheoryFile", params);
	}

	public int deleteTheoryFile(TheoryFile params) {
		
		return delete(Namespace + ".deleteTheoryFile", params);
	}	
	
	public int insertTheoryExcel(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertTheoryExcel", params);
	}		
}
