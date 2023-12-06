package egovframework.com.adm.contents.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.contents.vo.XrayImgContents;

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
	
	public List<Language> selectLanguageList(Language params);
	
	public Language selectLanguage(Language params);
	
	public int insertLanguage(Language params);
	
	public int updateLanguage(Language params);
	
	public int deleteLanguage(Language params);
	
	public List<UnitGroup> selectUnitGroupList(UnitGroup params);
	
	public UnitGroup selectUnitGroup(UnitGroup params);
	
	public UnitGroup selectUnitGroupName(UnitGroup params);
	
    public int insertUnitGroup(UnitGroup params);
	
	public int insertUnitGroupLanguage(Language params);
	
	public void updateUnitGroup(UnitGroup params);
	
	public void updateUnitGroupLanguage(Language params);
	
	public int deleteUnitGroup(UnitGroup params);
	
	public int insertUnitGroupImg(MultipartFile imgFile, UnitGroup params) throws IOException;
	
	public List<UnitImg> selectUnitList(UnitImg params);
	
	public UnitImg selectUnit(UnitImg params);
	
	public List<UnitInformation> selectUnitPopupList(UnitInformation params);
	
	public XbtSeq selectXbtSeq(XbtSeq params);
	
	public String insertUnit(UnitImg params);
	
	public int updateUnit(UnitImg params);
	
	public int insertUnitGroupImg(UnitGroup params);

	public int deleteUnit(UnitImg params);
	
	public int deleteUnitGroupImg(UnitGroup params);
	
	public void updateUnitImg(UnitImg params)throws Exception;
    
    public int insertUnitMaster(UnitImg params);
    
    public int insertUnitDetail(UnitImg params);

    public int insertUnit3dDetail(UnitImg params);
    
	public int insertUnitImage(MultipartFile imgFile ,UnitImg params) throws IOException;
	
	public List<XrayContents> selectXrayContentsList(XrayContents params);
	
	public int insertXrayContents(XrayContents params);
	
	public int updateXrayContents(XrayContents params);
	
	public int deleteXrayContents(XrayContents params);

	public XrayImgContents selectXrayImgContents(XrayImgContents params);
	
	public List<XrayContents> selectXrayUnitList(XrayContents params);
	
	public int insertXrayUnit(XrayContents params);
	
	public int deleteAllXrayUnit(XrayContents params);
	
	public int deleteXrayUnit(XrayContents params);
	
	public int updateXrayContentsImg(XrayImgContents params);
	
	public int updateXrayContentsDbImg(XrayImgContents params);
		
	
}
