package egovframework.com.adm.system.service;

import java.util.List;

import egovframework.com.adm.system.vo.Menu;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.adm.system.vo.XbtScore;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *
 *      </pre>
 */
public interface SystemService {
	
	public List<Notice> selectNoticeList(Notice params);
	
	public int insertNotice(Notice params);
	
	public int updateNotice(Notice params);
	
	public int deleteNotice(Notice params);

	public Notice selectNotice(Notice params);
	
	
	public List<Menu> selectMenuList(Menu params);
	
	public int insertMenu(Menu params);
	
	public int updateMenu(Menu params);
	
	public int deleteMenu(Menu params);

	public Menu selectMenu(Menu params);	
	
	public List<Menu> selectModuleMenuList(Menu params);
	
	public Menu selectModuleMenu(Menu params);	
	
	public List<XbtScore> selectXbtEndingUserList(XbtScore params);
	
	public XbtScore selectTheoryScore(XbtScore params);
	
	public XbtScore selectEvaluationScore(XbtScore params);
	
	public XbtScore selectPracticeScore(XbtScore params);
	
	public int updateXbtScore(XbtScore params);
	
	public XbtScore selectProcessScore(XbtScore params);
	
	public XbtScore selectTheoryProcessScore(XbtScore params);
	
	public int updateXbtEndScore(XbtScore params);
	
	public int updateBaselineStatus(XbtScore params);
		
}
