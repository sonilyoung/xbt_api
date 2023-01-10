package egovframework.com.adm.contents.dao;

import java.util.List;

import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.Xrayformation;
import egovframework.com.adm.contents.vo.Language;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("ContentsDAO")
public class ContentsDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.contents.dao.ContentsDAO";
	
	public List<?> getLanguageList(Language params) {
		return (List<?>)selectList(Namespace + ".getLanguageList", params);
	}
	
	public List<EgovMap> getGroupList(Contents params) {
		return selectList(Namespace + ".getGroupList", params);
	}
	
	public int updateGroup(Contents params) {
		return update(Namespace + ".updateGroup", params);
	}	
	
	public int updateGroupLanguage(Contents params) {
		return update(Namespace + ".updateGroupLanguage", params);
	}	
	
    public int saveUnitImage(Contents params) {
    	return update(Namespace + ".saveUnitImage", params);
    }

	public List<?> getInformationList(UnitInformation params) {
		return (List<?>)selectList(Namespace + ".getInformationList", params);
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
