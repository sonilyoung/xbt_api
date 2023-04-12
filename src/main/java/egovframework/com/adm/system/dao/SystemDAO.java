package egovframework.com.adm.system.dao;

import java.util.List;

import egovframework.com.adm.system.vo.Notice;
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

		
	

}
