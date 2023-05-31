package egovframework.com.adm.theory.service;

import java.util.List;

import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.adm.theory.vo.TheoryGroup;

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
public interface TheoryService {
	
	public List<TheoryGroup> selectTheoryGroupList(TheoryGroup params);
	
	public int insertTheoryGroup(TheoryGroup params);
	
	public int updateTheoryGroup(TheoryGroup params);
	
	public int deleteTheoryGroup(TheoryGroup params);

	public TheoryGroup selectTheoryGroup(TheoryGroup params);
	
	public List<Theory> selectTheoryList(Theory params);
	
	public int insertTheory(Theory params);
	
	public int updateTheory(Theory params);
	
	public int deleteTheory(Theory params);

	public Theory selectTheory(Theory params);	
		
}
