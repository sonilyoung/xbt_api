package egovframework.com.api.edc.dao;

import egovframework.com.api.edc.vo.UnitImages;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("EgovXtsEdcApiDAO")
public class EgovXtsEdcApiDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.api.edc.dao.EgovXtsEdcApiDAO";
	
	public int saveEmpUnitImage(UnitImages params) {
		return update(Namespace + ".saveEmpUnitImage", params);
	}	
	
}
