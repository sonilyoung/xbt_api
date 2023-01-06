package egovframework.com.common.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.common.vo.Common;

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
public interface CommonService {
	
	public List<Common> getCommonGroupList(Common params);
	
	public List<Common> getCommonList(Common params);
	
}
