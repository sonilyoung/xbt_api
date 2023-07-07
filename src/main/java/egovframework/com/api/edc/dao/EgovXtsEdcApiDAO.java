package egovframework.com.api.edc.dao;

import java.util.List;

import egovframework.com.api.edc.vo.AiForceLearning;
import egovframework.com.api.edc.vo.AiForceLearningResult;
import egovframework.com.api.edc.vo.UnitImages;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("EgovXtsEdcApiDAO")
public class EgovXtsEdcApiDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.api.edc.dao.EgovXtsEdcApiDAO";
	
	public int saveEmpUnitImage(UnitImages params) {
		return update(Namespace + ".saveEmpUnitImage", params);
	}	

	public List<?> selectLearningList(AiForceLearning params) {
		return (List<?>)selectList(Namespace + ".selectLearningList", params);
	}	
	
	public List<?> selectLearningResultList(AiForceLearningResult params) {
		return (List<?>)selectList(Namespace + ".selectLearningResultList", params);
	}		
	
}
