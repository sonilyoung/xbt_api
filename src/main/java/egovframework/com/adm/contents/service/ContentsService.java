package egovframework.com.adm.contents.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.Xrayformation;
import egovframework.com.adm.contents.vo.Language;

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
public interface ContentsService {
	
	public List<Language> getLanguageList(Language params);
	
	public ContentsMgr getGroupList(Contents params);
	
	public void updateGroup(Contents params);
	
	public int saveUnitImage(MultipartFile imgFile, Contents params) throws IOException;
	
	public List<UnitInformation> getInformationList(UnitInformation params);
	
	public List<Xrayformation> getXrayInformationList(Xrayformation params);
	
	public List<Xrayformation> getXrayDetailList(Xrayformation params);
	
	public List<UnitInformation> getBagUnitInfoList(UnitInformation params);
}
