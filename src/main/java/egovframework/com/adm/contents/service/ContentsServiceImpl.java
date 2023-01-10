package egovframework.com.adm.contents.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.dao.ContentsDAO;
import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.Xrayformation;
import egovframework.com.adm.contents.vo.Language;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.extern.log4j.Log4j2;


/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
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
 *   2014.12.08	 이기하			암호화방식 변경(OfficeFileScrty.encryptPassword)
 *
 *      </pre>
 */
@Log4j2
@Service("contentsService")
public class ContentsServiceImpl implements ContentsService {

    @Resource(name = "ContentsDAO")
	private ContentsDAO contentsDAO;

	@Override
	public ContentsMgr getGroupList(Contents params) {
		// TODO Auto-generated method stub
		
		//컨텐츠셋팅
		/*
		Language lparams = new Language();
		List<Language> lanList = (List<Language>) contentsDAO.getLanguageList(lparams);
		String query = "";
		String queryDesc = "";
		for(Language l : lanList) {
			query += "(SELECT CODE_NAME FROM XTS_LANGUAGE_DETAIL WHERE LANGUAGE_CODE= '"+l.getLanguageCode()+"' AND XUG.UNIT_GROUP_CD = CODE_ID) AS "+l.getLanguageCode()+",";
			queryDesc += "(SELECT CODE_DESC FROM XTS_LANGUAGE_DETAIL WHERE LANGUAGE_CODE= '"+l.getLanguageCode()+"' AND XUG.UNIT_GROUP_CD = CODE_ID) AS "+l.getLanguageCode()+"_DESC"+",";
		}
		params.setQuery(query);
		params.setQueryDesc(queryDesc);
		List<EgovMap> resultList = contentsDAO.getContentsList(params);
		
		ContentsMgr result = new ContentsMgr();
		result.setResultList(resultList);
		
		//헤더셋팅
		List<String> headerInfo = new ArrayList<String>();
		headerInfo.add("No");
		headerInfo.add("물품분류코드");
		for(Language l : lanList) {
			headerInfo.add("물품분류명칭("+l.getLanguageName()+")");
		}
		for(Language l : lanList) {
			headerInfo.add("물품분류설명("+l.getLanguageName()+")");
		}		
		headerInfo.add("Action구분");
		headerInfo.add("개봉여부");
		headerInfo.add("통과여부");
		headerInfo.add("사용여부");
		headerInfo.add("이미지파일");
		headerInfo.add("하위물품분류");
		result.setHeaderInfo(headerInfo);
		*/
		
		List<EgovMap> resultList = contentsDAO.getGroupList(params);
		ContentsMgr result = new ContentsMgr();
		result.setResultList(resultList);
		return result;
	}
	
	@Override
	public void updateGroup(Contents params) {
		// TODO Auto-generated method stub
		contentsDAO.updateGroup(params);
		contentsDAO.updateGroupLanguage(params);
	}	

	@Override
	public List<Language> getLanguageList(Language params) {
		// TODO Auto-generated method stub
		return (List<Language>) contentsDAO.getLanguageList(params);
	}

	@Override
	public int saveUnitImage(MultipartFile imgFile, Contents params) throws IOException {
		// TODO Auto-generated method stub
		//String imageStr = Base64.encodeBase64String(imgFile.getBytes());
		//String imageStr = new B(Base64.encodeBase64(imgFile.getBytes()));
		//log.info("데이터: " + imageStr);
		//String imgString = Base64.encodeToString(bytes, Base64.NO_WRAP);
		params.setUnitImg(imgFile.getBytes());
		return contentsDAO.saveUnitImage(params);
	}
	
	@Override
	public List<UnitInformation> getInformationList(UnitInformation params) {
		// TODO Auto-generated method stub
		return (List<UnitInformation>) contentsDAO.getInformationList(params);
	}

	@Override
	public List<Xrayformation> getXrayInformationList(Xrayformation params) {
		// TODO Auto-generated method stub
		return (List<Xrayformation>) contentsDAO.getXrayInformationList(params);
	}

	@Override
	public List<Xrayformation> getXrayDetailList(Xrayformation params) {
		// TODO Auto-generated method stub
		return (List<Xrayformation>) contentsDAO.getXrayDetailList(params);
	}

	@Override
	public List<UnitInformation> getBagUnitInfoList(UnitInformation params) {
		// TODO Auto-generated method stub
		return (List<UnitInformation>) contentsDAO.getBagUnitInfoList(params);
	}

}
