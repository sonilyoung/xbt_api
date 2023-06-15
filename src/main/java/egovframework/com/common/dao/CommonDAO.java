package egovframework.com.common.dao;

import java.util.HashMap;
import java.util.List;

import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.CommonSystemMessage;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CommonDAO")
public class CommonDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.common.dao.CommonDAO";
	
	public List<?> getCommonGroupList(Common params) {
		return (List<?>)selectList(Namespace + ".getCommonGroupList", params);
	}
	
	public List<?> selectCommonList(Common params) {
		return (List<?>)selectList(Namespace + ".selectCommonList", params);
	}
	
	public Common selectCommon(Common params) {
		return selectOne(Namespace + ".selectCommon", params);
	}
	
	public Common selectCommonDetail(Common params) {
		return selectOne(Namespace + ".selectCommonDetail", params);
	}
	
	public Long insertCommonCode(Common params) {
		return (long) insert(Namespace + ".insertCommonCode", params);
	}
	
	public int insertCommonCodeDetail(Common params) {
		return insert(Namespace + ".insertCommonCodeDetail", params);
	}
	
	public int updateCommonCode(Common params) {
		return update(Namespace + ".updateCommonCode", params);
	}	
	
	public int deleteCommonCode(Common params) {
		return delete(Namespace + ".deleteCommonCode", params);
	}	

	public int deleteCommonCodeDetail(Common params) {
		return delete(Namespace + ".deleteCommonCodeDetail", params);
	}

	public CommonSystemMessage selectSystemMessage(CommonSystemMessage params) {
		// TODO Auto-generated method stub
		return selectOne(Namespace + ".selectSystemMessage", params);
	}	
	
	public List<?> selectLanguageApplyList(Common params) {
		return (List<?>)selectList(Namespace + ".selectLanguageApplyList", params);
	}
	
	public Common selectLanguageApply(Common params) {
		return selectOne(Namespace + ".selectLanguageApply", params);
	}	
	
	public int insertLanguageApply(Common params) {
		return insert(Namespace + ".insertLanguageApply", params);
	}
		
	public int deleteLanguageApply(Common params) {
		return delete(Namespace + ".deleteLanguageApply", params);
	}	
	
	public int updateLanguageApply(Common params) {
		return update(Namespace + ".updateLanguageApply", params);
	}	
	
	
	
	

}
