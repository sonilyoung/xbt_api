package egovframework.com.test.service;

import java.util.LinkedHashMap;
import java.util.List;

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
public interface TestService {
	public int insertXbtBagConstUnitTemp(LinkedHashMap<String, Object> params);
	
	public int insertXbtBagInfoTemp(LinkedHashMap<String, Object> params);
	
	public int insertUnitTemp(LinkedHashMap<String, Object> params);
	
	
	public int insertXbtBagConstUnitRename(LinkedHashMap<String, Object> params);
	
	public int insertXbtBagInfoRename(LinkedHashMap<String, Object> params);
	
	public int insertUnitRename(LinkedHashMap<String, Object> params);
}
