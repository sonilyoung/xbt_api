package egovframework.com.adm.contents.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.dao.ContentsDAO;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.Xrayformation;
import egovframework.com.common.vo.SeqGroupCode;
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
	public List<Language> selectLanguageList(Language params) {
		// TODO Auto-generated method stub
		return (List<Language>) contentsDAO.selectLanguageList(params);
	}
	
	@Override
	public Language selectLanguage(Language params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectLanguage(params);
	}
	
	@Override
	public int updateLanguage(Language params) {
		// TODO Auto-generated method stub
		return contentsDAO.updateLanguage(params);
	}	
	
	@Override
	public int insertLanguage(Language params) {
		// TODO Auto-generated method stub
		return contentsDAO.insertLanguage(params);
	}
	
	@Override
	public int deleteLanguage(Language params) {
		// TODO Auto-generated method stub
		return contentsDAO.deleteLanguage(params);
	}    
    
    
	@Override
	public List<UnitGroup> selectUnitGroupList(UnitGroup params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectUnitGroupList(params);
	}

	@Override
	public UnitGroup selectUnitGroup(UnitGroup params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectUnitGroup(params);
	}	
	

	@Override
	@Transactional
	public int insertUnitGroup(UnitGroup params) {
		// TODO Auto-generated method stub
		
		XbtSeq seq = new XbtSeq();
		seq.setSeqInfo(SeqGroupCode.XBT_UNIT_GROUP_ID.getCode());
		XbtSeq unitId = selectXbtSeq(seq);
		params.setUnitGroupCd(unitId.getSeqId());		
		
		//공통코드아디시생성
		/*
		Language param = new Language();
		param.setCodeId(unitId.getSeqId());
		param.setLanguageCode(params.getLanguageCode());
		param.setCodeName(params.getGroupName());
		param.setCodeDesc(params.getGroupDesc());
		param.setInsertId(params.getInsertId());
		return contentsDAO.insertUnitGroupLanguage(param);*/
		return contentsDAO.insertUnitGroup(params);
	}

	@Override
	public int insertUnitGroupLanguage(Language params) {
		// TODO Auto-generated method stub
		return contentsDAO.insertUnitGroupLanguage(params);
	}

	
	
	@Override
	public void updateUnitGroup(UnitGroup params) {
		// TODO Auto-generated method stub
		contentsDAO.updateUnitGroup(params);
		//Language param = new Language();
		
		//param.setCodeId(params.getUnitGroupCd());
		//param.setLanguageCode(params.getLanguageCode());
		//param.setCodeName(params.getGroupName());
		//param.setCodeDesc(params.getGroupDesc());
		//param.setInsertId(params.getInsertId());
		//contentsDAO.updateUnitGroupLanguage(param);
	}	
	
	@Override
	public void updateUnitGroupLanguage(Language params) {
		// TODO Auto-generated method stub
		contentsDAO.updateUnitGroupLanguage(params);
	}	
	
	

	@Override
	public int insertUnitGroupImg(MultipartFile imgFile, UnitGroup params) throws IOException {
		// TODO Auto-generated method stub
		//String imageStr = Base64.encodeBase64String(imgFile.getBytes());
		//String imageStr = new B(Base64.encodeBase64(imgFile.getBytes()));
		//log.info("데이터: " + imageStr);
		//String imgString = Base64.encodeToString(bytes, Base64.NO_WRAP);
		
		contentsDAO.deleteUnitGroupImg(params);
		params.setImgFile(imgFile.getBytes());
		int result = contentsDAO.insertUnitGroupImg(params);
		return result;
	}
	
	@Override
	public List<UnitImg> selectUnitList(UnitImg params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectUnitList(params);
	}
	
	@Override
	public UnitImg selectUnit(UnitImg params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectUnit(params);
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
	

	@Override
	public XbtSeq selectXbtSeq(XbtSeq params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectXbtSeq(params);
	}		
	
	
	@Override
	public int insertUnitImage(MultipartFile imgFile,UnitImg params) throws IOException {
		// TODO Auto-generated method stub
		//String imageStr = Base64.encodeBase64String(imgFile.getBytes());
		//String imageStr = new B(Base64.encodeBase64(imgFile.getBytes()));
		//log.info("데이터: " + imageStr);
		//String imgString = Base64.encodeToString(bytes, Base64.NO_WRAP);
		
		if("real".equals(params.getImgType())) {
			params.setRealImg(imgFile.getBytes());	
		}else if("front".equals(params.getImgType())) {
			params.setFrontImg(imgFile.getBytes());	
		}else {
			params.setSideImg(imgFile.getBytes());	
		}
		return contentsDAO.insertUnitImage(params);
	}

	
	@Override
	@Transactional
	public String insertUnit(UnitImg params) {
		// TODO Auto-generated method stub
		
		XbtSeq seq = new XbtSeq();
		seq.setSeqInfo(SeqGroupCode.XBT_UNIT_ID.getCode());
		XbtSeq unitId = selectXbtSeq(seq);
		params.setUnitId(unitId.getSeqId());
		
		seq.setSeqInfo(SeqGroupCode.XBT_UNIT_SCAN_ID.getCode());
		XbtSeq unitScanId = selectXbtSeq(seq);		
		params.setUnitScanId(unitScanId.getSeqId());
		
		contentsDAO.insertUnit(params);
		contentsDAO.updateUnitBasicInfo(params);
		
		//insertUnitMaster(params);
		//insertUnitDetail(params);
		//insertUnit3dDetail(params);
		//Long unitScanNo = insertUnitMaster(params);
		return unitScanId.getSeqId();
	}	
	
	
	
	@Override
	@Transactional
	public void updateUnitImg(UnitImg params) {
		// TODO Auto-generated method stub
		if(params.getFrontmImg()!=null) {
			try {
				params.setImgType("front");
				params.setFrontImg(params.getFrontmImg().getBytes());
				contentsDAO.updateUnitImg(params);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(params.getRealmImg()!=null) {
			try {
				params.setImgType("real");
				params.setRealImg(params.getRealmImg().getBytes());
				contentsDAO.updateUnitImg(params);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		if(params.getSidemImg()!=null) {
			try {
				params.setImgType("side");
				params.setSideImg(params.getSidemImg().getBytes());
				contentsDAO.updateUnitImg(params);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public int insertUnitMaster(UnitImg params) {
		// TODO Auto-generated method stub
		
		if(params.getFrontmImg()!=null) {
			try {
				params.setFrontImg(params.getFrontmImg().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(params.getRealmImg()!=null) {
			try {
				params.setRealImg(params.getRealmImg().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		if(params.getSidemImg()!=null) {
			try {
				params.setSideImg(params.getSidemImg().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return contentsDAO.insertUnitMaster(params);
	}

	@Override
	public int insertUnitDetail(UnitImg params) {
		// TODO Auto-generated method stub
		return contentsDAO.insertUnitDetail(params);
	}

	@Override
	public int insertUnit3dDetail(UnitImg params) {
		// TODO Auto-generated method stub
		return contentsDAO.insertUnit3dDetail(params);
	}

	@Override
	public int deleteUnitGroup(UnitGroup params) {
		// TODO Auto-generated method stub
		contentsDAO.deleteUnitGroup(params);
		Language param = new Language();
		param.setCodeId(params.getUnitGroupCd());
		return contentsDAO.deleteUnitGroupLanguage(param);
	}

	@Override
	public int deleteUnit(UnitImg params) {
		// TODO Auto-generated method stub
		return contentsDAO.deleteUnit(params);
	}

	@Override
	public int insertUnitGroupImg(UnitGroup params) {
		// TODO Auto-generated method stub
		return contentsDAO.insertUnitGroupImg(params);
	}

	@Override
	public int deleteUnitGroupImg(UnitGroup params) {
		// TODO Auto-generated method stub
		return contentsDAO.deleteUnitGroupImg(params);
	}

	@Override
	public int updateUnit(UnitImg params) {
		// TODO Auto-generated method stub
		return contentsDAO.updateUnit(params);
	}




	

}
