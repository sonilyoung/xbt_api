package egovframework.com.adm.contents.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.dao.ContentsDAO;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.common.service.CommonService;
import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.SeqGroupCode;
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

    @Autowired
    private CommonService commonService;    
    
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
	public UnitGroup selectUnitGroupName(UnitGroup params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectUnitGroupName(params);
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
	public List<UnitInformation> selectUnitPopupList(UnitInformation params) {
		// TODO Auto-generated method stub
		return (List<UnitInformation>) contentsDAO.selectUnitPopupList(params);
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
		//contentsDAO.insertUnitBasicInfo(params);
		
		//insertUnitMaster(params);
		//insertUnitDetail(params);
		//insertUnit3dDetail(params);
		//Long unitScanNo = insertUnitMaster(params);
		return unitScanId.getSeqId();
	}	
	
	
	
	@Override
	@Transactional
	public void updateUnitImg(UnitImg params) throws Exception{
		// TODO Auto-generated method stub
		
		if(contentsDAO.selectUnitBasicInfoCount(params) <= 0) {
			contentsDAO.insertUnitBasicInfo(params);	
		}
		
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




	@Override
	@SuppressWarnings("unchecked")
	public List<XrayContents> selectXrayContentsList(XrayContents params) {
		// TODO Auto-generated method stub
		return (List<XrayContents>)contentsDAO.selectXrayContentsList(params);
	}

	@Override
	public int insertXrayContents(XrayContents params) {
		// TODO Auto-generated method stub
		
		XbtSeq seq = new XbtSeq();
		seq.setSeqInfo(SeqGroupCode.XBT_BAG_ID.getCode());
		XbtSeq unitId = selectXbtSeq(seq);
		params.setBagScanId(unitId.getSeqId());		
		//params.setSeq("1");
		//params.setAnswerItem("Y");
		//return contentsDAO.insertXrayUnit(params);
		return contentsDAO.insertXrayContents(params);
	}

	@Override
	public int updateXrayContents(XrayContents params) {
		// TODO Auto-generated method stub
		
		for(XrayContents x : params.getParamList()) {
			x.setUpdateId(params.getUpdateId());
			
            Common cp = new Common();
            cp.setLanguageCode("kr");
            cp.setGroupId("actionDiv");
            cp.setCodeValue(x.getActionDiv());
            Common cr = commonService.selectCommonDetail(cp);			
			x.setOpenYn(cr.getMemo1());
			x.setPassYn(cr.getMemo2());
			contentsDAO.updateXrayContents(x);
		}
		
		return 1; 
	}

	@Override
	public int deleteXrayContents(XrayContents params) {
		// TODO Auto-generated method stub
		
		if(!StringUtils.isEmpty(params.getBagScanIds())){
			for(int i=0; i<params.getBagScanIds().length; i++) {
				params.setBagScanId(params.getBagScanIds()[i]);
				contentsDAO.deleteAllXrayUnit(params);
				contentsDAO.deleteXrayContents(params);						
			}
		}
		return 1;
	}

	@Override
	public XrayImgContents selectXrayImgContents(XrayImgContents params) {
		// TODO Auto-generated method stub
		return contentsDAO.selectXrayImgContents(params);
	}
	
	@Override
	public List<XrayContents> selectXrayUnitList(XrayContents params) {
		// TODO Auto-generated method stub
		return (List<XrayContents>) contentsDAO.selectXrayUnitList(params);
	}	

	@Override
	public int insertXrayUnit(XrayContents params) {
		// TODO Auto-generated method stub
		
		contentsDAO.deleteAllXrayUnit(params);
		
		if(params.getParamList().size() > 0) {
			for(XrayContents p : params.getParamList()) {
				p.setBagScanId(params.getBagScanId());
				contentsDAO.insertXrayUnit(p);
			}
		}
		
		return 1;
	}
	
	@Override
	public int deleteAllXrayUnit(XrayContents params) {
		// TODO Auto-generated method stub
		return contentsDAO.deleteAllXrayUnit(params);
	}		

	@Override
	public int deleteXrayUnit(XrayContents params) {
		// TODO Auto-generated method stub
		if(params.getBagConstList().size() > 0) {
			for(Long p : params.getBagConstList()) {
				params.setBagContNo(p);
				contentsDAO.deleteXrayUnit(params);
			}
		}
		
		return 1;		
	}
	
	@Override
	public int updateXrayContentsImg(XrayImgContents params) {
		int result = 1;
		
		return result;
	}

	@Override
	public int updateXrayContentsDbImg(XrayImgContents params) {
		// TODO Auto-generated method stub
		
		if(contentsDAO.selectXrayImgContentsCount(params) <= 0) {
			contentsDAO.insertXrayImgContents(params);	
		}
				
		if(params.getMimgReal()!=null) {
			try {
				params.setImgReal(params.getMimgReal().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(params.getMimgSide()!=null) {
			try {
				params.setImgSide(params.getMimgSide().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(params.getMimgFront()!=null) {
			try {
				params.setImgFront(params.getMimgFront().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		

		if(params.getMimgFrontCollar()!=null) {
			try {
				params.setImgFrontCollar(params.getMimgFrontCollar().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		if(params.getMimgFrontOrganism()!=null) {
			try {
				params.setImgFrontOrganism(params.getMimgFrontOrganism().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		if(params.getMimgFrontCollarOutline()!=null) {
			try {
				params.setImgFrontCollarOutline(params.getMimgFrontCollarOutline().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontCollarReversal()!=null) {
			try {
				params.setImgFrontCollarReversal(params.getMimgFrontCollarReversal().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontCollarBwRate1()!=null) {
			try {
				params.setImgFrontCollarBwRate1(params.getMimgFrontCollarBwRate1().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontCollarBwRate2()!=null) {
			try {
				params.setImgFrontCollarBwRate2(params.getMimgFrontCollarBwRate2().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontCollarBwRate3()!=null) {
			try {
				params.setImgFrontCollarBwRate3(params.getMimgFrontCollarBwRate3().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontCollarBwRate4()!=null) {
			try {
				params.setImgFrontCollarBwRate4(params.getMimgFrontCollarBwRate4().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontCollarBwRate5()!=null) {
			try {
				params.setImgFrontCollarBwRate5(params.getMimgFrontCollarBwRate5().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontCollarBwRate6()!=null) {
			try {
				params.setImgFrontCollarBwRate6(params.getMimgFrontCollarBwRate6().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBw()!=null) {
			try {
				params.setImgFrontBw(params.getMimgFrontBw().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontMinerals()!=null) {
			try {
				params.setImgFrontMinerals(params.getMimgFrontMinerals().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBwOutline()!=null) {
			try {
				params.setImgFrontOrganism(params.getMimgFrontBwOutline().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBwReversal()!=null) {
			try {
				params.setImgFrontBwReversal(params.getMimgFrontBwReversal().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
		
		
		if(params.getMimgFrontBwBwRate1()!=null) {
			try {
				params.setImgFrontBwBwRate1(params.getMimgFrontBwBwRate1().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBwBwRate2()!=null) {
			try {
				params.setImgFrontBwBwRate2(params.getMimgFrontBwBwRate2().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBwBwRate3()!=null) {
			try {
				params.setImgFrontBwBwRate3(params.getMimgFrontBwBwRate3().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBwBwRate4()!=null) {
			try {
				params.setImgFrontBwBwRate4(params.getMimgFrontBwBwRate4().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBwBwRate5()!=null) {
			try {
				params.setImgFrontBwBwRate5(params.getMimgFrontBwBwRate5().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgFrontBwBwRate6()!=null) {
			try {
				params.setImgFrontBwBwRate6(params.getMimgFrontBwBwRate6().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollar()!=null) {
			try {
				params.setImgSideCollar(params.getMimgSideCollar().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		if(params.getMimgSideOrganism()!=null) {
			try {
				params.setImgSideOrganism(params.getMimgSideOrganism().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		if(params.getMimgSideCollarOutline()!=null) {
			try {
				params.setImgSideCollarOutline(params.getMimgSideCollarOutline().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollarReversal()!=null) {
			try {
				params.setImgSideCollarReversal(params.getMimgSideCollarReversal().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollarBwRate1()!=null) {
			try {
				params.setImgSideCollarBwRate1(params.getMimgSideCollarBwRate1().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollarBwRate2()!=null) {
			try {
				params.setImgSideCollarBwRate2(params.getMimgSideCollarBwRate2().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollarBwRate3()!=null) {
			try {
				params.setImgSideCollarBwRate3(params.getMimgSideCollarBwRate3().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollarBwRate4()!=null) {
			try {
				params.setImgSideCollarBwRate4(params.getMimgSideCollarBwRate4().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollarBwRate5()!=null) {
			try {
				params.setImgSideCollarBwRate5(params.getMimgSideCollarBwRate5().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideCollarBwRate6()!=null) {
			try {
				params.setImgSideCollarBwRate6(params.getMimgSideCollarBwRate6().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBw()!=null) {
			try {
				params.setImgSideBw(params.getMimgSideBw().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideMinerals()!=null) {
			try {
				params.setImgSideMinerals(params.getMimgSideMinerals().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBwOutline()!=null) {
			try {
				params.setImgSideOrganism(params.getMimgSideBwOutline().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBwReversal()!=null) {
			try {
				params.setImgSideBwReversal(params.getMimgSideBwReversal().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
		
		
		if(params.getMimgSideBwBwRate1()!=null) {
			try {
				params.setImgSideBwBwRate1(params.getMimgSideBwBwRate1().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBwBwRate2()!=null) {
			try {
				params.setImgSideBwBwRate2(params.getMimgSideBwBwRate2().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBwBwRate3()!=null) {
			try {
				params.setImgSideBwBwRate3(params.getMimgSideBwBwRate3().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBwBwRate4()!=null) {
			try {
				params.setImgSideBwBwRate4(params.getMimgSideBwBwRate4().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBwBwRate5()!=null) {
			try {
				params.setImgSideBwBwRate5(params.getMimgSideBwBwRate5().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		if(params.getMimgSideBwBwRate6()!=null) {
			try {
				params.setImgSideBwBwRate6(params.getMimgSideBwBwRate6().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
		
		return contentsDAO.updateXrayContentsImg(params);
	}	
	

}
