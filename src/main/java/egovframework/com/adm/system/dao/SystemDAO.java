package egovframework.com.adm.system.dao;

import java.util.List;

import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.system.vo.Menu;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.adm.system.vo.XbtScore;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SystemDAO")
public class SystemDAO extends EgovAbstractMapper{
	
	private static final String Namespace = "egovframework.com.adm.system.dao.SystemDAO";
	
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
	}	

	public List<?> selectMenuList(Menu params) {
		return (List<?>)selectList(Namespace + ".selectMenuList", params);
	}
	
	public int insertMenu(Menu params) {
		return insert(Namespace + ".insertMenu", params);
	}
	
	public int updateMenu(Menu params) {
		return update(Namespace + ".updateMenu", params);
	}	
	
	public int deleteMenu(Menu params) {
		return delete(Namespace + ".deleteMenu", params);
	}	

	public Menu selectMenu(Menu params) {
		return selectOne(Namespace + ".selectMenu", params);
	}			
	
	public List<?> selectModuleMenuList(Menu params) {
		return (List<?>)selectList(Namespace + ".selectModuleMenuList", params);
	}
	
	public Menu selectModuleMenu(Menu params) {
		return selectOne(Namespace + ".selectModuleMenu", params);
	}		
	
	public List<?> selectXbtEndingUserList(XbtScore params) {
		return (List<?>)selectList(Namespace + ".selectXbtEndingUserList", params);
	}	
	
	public XbtScore selectXbtEndingUser(XbtScore params) {
		return selectOne(Namespace + ".selectXbtEndingUser", params);
	}	
	
	public XbtScore selectTheoryScore(XbtScore params) {
		return selectOne(Namespace + ".selectTheoryScore", params);
	}	

	public XbtScore selectEvaluationScore(XbtScore params) {
		return selectOne(Namespace + ".selectEvaluationScore", params);
	}	
	
	public XbtScore selectPracticeScore(XbtScore params) {
		return selectOne(Namespace + ".selectPracticeScore", params);
	}	
	
	public int updateXbtScore(XbtScore params) {
		return update(Namespace + ".updateXbtScore", params);
	}		
	
	public XbtScore selectProcessScore(XbtScore params) {
		return selectOne(Namespace + ".selectProcessScore", params);
	}	
	
	public XbtScore selectTheoryProcessScore(XbtScore params) {
		return selectOne(Namespace + ".selectTheoryProcessScore", params);
	}	
	
	public int updateXbtEndScore(XbtScore params) {
		return update(Namespace + ".updateXbtEndScore", params);
	}
	
	public int updateBaselineStatus(XbtScore params) {
		return update(Namespace + ".updateBaselineStatus", params);
	}
	
	public int updateXrayExcelData(XrayContents params) {
		return update(Namespace + ".updateXrayExcelData", params);
	}	
}
