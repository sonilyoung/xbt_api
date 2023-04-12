package egovframework.com.adm.contents.dao;

import java.util.List;

import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.Xrayformation;
import egovframework.com.common.vo.Common;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("ContentsDAO")
public class ContentsDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.contents.dao.ContentsDAO";
	
	public List<?> selectLanguageList(Language params) {
		return (List<?>)selectList(Namespace + ".selectLanguageList", params);
	}
	
	public Language selectLanguage(Language params) {
		return selectOne(Namespace + ".selectLanguage", params);
	}
	
    public int updateLanguage(Language params) {
    	return update(Namespace + ".updateLanguage", params);
    }		
	
    public int insertLanguage(Language params) {
    	return insert(Namespace + ".insertLanguage", params);
    }		
	
	public int deleteLanguage(Language params) {
		return delete(Namespace + ".deleteLanguage", params);
	}	    
    
	public List<UnitGroup> selectUnitGroupList(UnitGroup params) {
		return selectList(Namespace + ".selectUnitGroupList", params);
	}

	public UnitGroup selectUnitGroup(UnitGroup params) {
		return selectOne(Namespace + ".selectUnitGroup", params);
	}
	
    public int insertUnitGroup(UnitGroup params) {
    	return insert(Namespace + ".insertUnitGroup", params);
    	
    }	
    
	
	public int insertUnitGroupLanguage(Language params) {
		return insert(Namespace + ".insertUnitGroupLanguage", params);
	}
	
	
	
	public int updateUnitGroup(UnitGroup params) {
		return update(Namespace + ".updateUnitGroup", params);
	}	
	
	public int updateUnitGroupLanguage(Language params) {
		return update(Namespace + ".updateUnitGroupLanguage", params);
	}	
	
	public int deleteUnitGroup(UnitGroup params) {
		return delete(Namespace + ".deleteUnitGroup", params);
	}	   
	
	public int deleteUnitGroupImg(UnitGroup params) {
		return delete(Namespace + ".deleteUnitGroupImg", params);
	}	   	
	
	
	public int deleteUnitGroupLanguage(Language params) {
		return delete(Namespace + ".deleteUnitGroupLanguage", params);
	}	   	
	
	
    public int insertUnitGroupImg(UnitGroup params) {
    	return update(Namespace + ".insertUnitGroupImg", params);
    }

	public List<UnitImg> selectUnitList(UnitImg params) {
		return selectList(Namespace + ".selectUnitList", params);
	}
	
	public UnitImg selectUnit(UnitImg params) {
		return selectOne(Namespace + ".selectUnit", params);
	}
	
	public XbtSeq selectXbtSeq(XbtSeq params) {
		return selectOne(Namespace + ".selectXbtSeq", params);
	}	
	
	public Long insertUnit(UnitImg params) {
		return (long) insert(Namespace + ".insertUnit", params);
	}
	
	public int updateUnitBasicInfo(UnitImg params) {
		return update(Namespace + ".updateUnitBasicInfo", params);
	}	
	
	public int updateUnit(UnitImg params) {
		return update(Namespace + ".updateUnit", params);
	}	
    
    public int insertUnitMaster(UnitImg params) {
    	return insert(Namespace + ".insertUnitMaster", params);
    }
    
	public int deleteUnit(UnitImg params) {
		return delete(Namespace + ".deleteUnit", params);
	}	      
    
	
	public int updateUnitImg(UnitImg params) {
		return update(Namespace + ".updateUnitImg", params);
	}	
	    
    
    public int insertUnitDetail(UnitImg params) {
    	return insert(Namespace + ".insertUnitDetail", params);
    }	   
    
    public int insertUnit3dDetail(UnitImg params) {
    	return insert(Namespace + ".insertUnit3dDetail", params);
    }   
    
	
    public int insertUnitImage(UnitImg params) {
    	return update(Namespace + ".insertUnitImage", params);
    }
    
    
	
	public List<?> getXrayInformationList(Xrayformation params) {
		return (List<?>)selectList(Namespace + ".getXrayInformationList", params);
	}
	
	public List<?> getXrayDetailList(Xrayformation params) {
		return (List<?>)selectList(Namespace + ".getXrayDetailList", params);
	}	
	
	public List<?> getBagUnitInfoList(UnitInformation params) {
		return (List<?>)selectList(Namespace + ".getBagUnitInfoList", params);
	}	    
}
