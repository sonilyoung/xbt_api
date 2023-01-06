package egovframework.com.common.dao;

import java.util.List;

import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.common.vo.Common;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("CommonDAO")
public class CommonDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.common.dao.CommonDAO";
	
	public List<?> getCommonGroupList(Common params) {
		return (List<?>)selectList(Namespace + ".getCommonGroupList", params);
	}
	
	public List<?> getCommonList(Common params) {
		return (List<?>)selectList(Namespace + ".getCommonList", params);
	}
	

}
