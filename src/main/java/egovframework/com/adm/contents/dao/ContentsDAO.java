package egovframework.com.adm.contents.dao;

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
	
	public int insertUnitBasicInfo(UnitImg params) {
		return insert(Namespace + ".insertUnitBasicInfo", params);
	}	
	
	public int selectUnitBasicInfoCount(UnitImg params) {
		return selectOne(Namespace + ".selectUnitBasicInfoCount", params);
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
	
	

	

	
	public List<?> selectXrayContentsList(XrayContents params) {
		return (List<?>)selectList(Namespace + ".selectXrayContentsList", params);
	}
	
	public int insertXrayContents(XrayContents params) {
		return insert(Namespace + ".insertXrayContents", params);
	}
	
	public int updateXrayContents(XrayContents params) {
		return update(Namespace + ".updateXrayContents", params);
	}	
	
	public int deleteXrayContents(XrayContents params) {
		return delete(Namespace + ".deleteXrayContents", params);
	}	

	public XrayImgContents selectXrayImgContents(XrayImgContents params) {
		return selectOne(Namespace + ".selectXrayImgContents", params);
	}	
	
	
	public List<?> selectXrayUnitList(XrayContents params) {
		return (List<?>)selectList(Namespace + ".selectXrayUnitList", params);
	}		
	
	public int insertXrayUnit(XrayContents params) {
		return insert(Namespace + ".insertXrayUnit", params);
	}
	
	public int deleteAllXrayUnit(XrayContents params) {
		return delete(Namespace + ".deleteAllXrayUnit", params);
	}			
	
	public int deleteXrayUnit(XrayContents params) {
		return delete(Namespace + ".deleteXrayUnit", params);
	}		
	
	
	
	public List<?> selectUnitPopupList(UnitInformation params) {
		return (List<?>)selectList(Namespace + ".selectUnitPopupList", params);
	}	   
	
    public int updateXrayContentsImg(XrayImgContents params) {
    	return update(Namespace + ".updateXrayContentsImg", params);
    }
		
	public int selectXrayImgContentsCount(XrayImgContents params) {
		return selectOne(Namespace + ".selectXrayImgContentsCount", params);
	}	

	public int insertXrayImgContents(XrayImgContents params) {
		return insert(Namespace + ".insertXrayImgContents", params);
	}	
	
}
