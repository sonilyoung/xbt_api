package egovframework.com.test.dao;

import java.util.LinkedHashMap;
import java.util.List;

import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("TestDAO")
public class TestDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.test.dao.TestDAO";
	
	/*
	public List<?> selectNoticeList(Notice params) {
		return (List<?>)selectList(Namespace + ".selectNoticeList", params);
	}
	
	public int insertNotice(Notice params) {
		return insert(Namespace + ".insertNotice", params);
	}
	
	public int updateNotice(Notice params) {
		return update(Namespace + ".updateNotice", params);
	}	
	
	public int deleteNotice(Notice params) {
		return delete(Namespace + ".deleteNotice", params);
	}	

	public Notice selectNotice(Notice params) {
		return selectOne(Namespace + ".selectNotice", params);
	}*/
	
	
	public int insertXbtBagConstUnitTemp(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertXbtBagConstUnitTemp", params);
	}
	
	public int insertXbtBagInfoTemp(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertXbtBagInfoTemp", params);
	}
	
	public int insertUnitTemp(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertUnitTemp", params);
	}		
	
	public int insertXbtBagConstUnitRename(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertXbtBagConstUnitRename", params);
	}	
	
	public int insertXbtBagInfoRename(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertXbtBagInfoRename", params);
	}		
	
	public int insertUnitRename(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertUnitRename", params);
	}	
	
	public int insertTheoryExcel(LinkedHashMap<String, Object> params) {
		return insert(Namespace + ".insertTheoryExcel", params);
	}		
		
	
}
