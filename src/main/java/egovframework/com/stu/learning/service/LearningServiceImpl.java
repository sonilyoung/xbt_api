package egovframework.com.stu.learning.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.annotation.Resource;

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
import egovframework.com.common.vo.SeqGroupCode;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.util.FileReader;
import egovframework.com.stu.learning.dao.LearningDAO;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningImg;
import egovframework.com.stu.learning.vo.LearningProblem;
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
@Service("LearningService")
public class LearningServiceImpl implements LearningService {

    @Resource(name = "LearningDAO")
	private LearningDAO learningDAO;


	@Override
	public Learning selectBaseline(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectBaseline(params);
	}

	@Override
	public Learning selectLearning(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearning(params);
	}	
	
	@Override
	public Learning selectModuleInfo(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectModuleInfo(params);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>)learningDAO.selectLearningProblems(params);
	}

	@Override
	public int selectLearningProblemsCount(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearningProblemsCount(params);
	}

	@Override
	public int insertLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.insertLearningProblems(params);
	}
	
	
	@Override
	public int updateLearningProblems(LearningProblem params) {
		// TODO Auto-generated method stub
		return learningDAO.updateLearningProblems(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LearningProblem> selectLearnProblemsList(LearningProblem params) {
		// TODO Auto-generated method stub
		return (List<LearningProblem>) learningDAO.selectLearnProblemsList(params);
	}

	@Override
	public LearningImg selectLeaningImgList(LearningImg params) {
		// TODO Auto-generated method stub
		
    	String xrayPath = GlobalsProperties.getProperty("xray.img.path");
    	
    	String scanId = params.getBagScanId();
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte;/*이미지*/
        
        //결과유기물
        System.out.println("result count : " + fileList.length);
        
        //byte변환
        for( int i = 0; i < fileList.length; i++ ) { 
        	System.out.println("result : "+fileList[i]);
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		System.out.println("fileByte : "+fileByte);
        		if(fileList[i].getName().contains("101")) {//정면
        			params.setImgFront(fileByte);
        			params.setImgFrontColor(fileByte);
        		}else if(fileList[i].getName().contains("102")) {
        			params.setImgFrontColorMineral(fileByte);
        		}else if(fileList[i].getName().contains("103")) {
        			params.setImgFrontColorOrganism(fileByte);
        		}else if(fileList[i].getName().contains("104")) {
        			params.setImgFrontColorReversal(fileByte);
        		}else if(fileList[i].getName().contains("105")) {
        			params.setImgFrontColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("106")) {
        			params.setImgFrontColorBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("107")) {
        			params.setImgFrontColorBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("108")) {
        			params.setImgFrontColorBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("109")) {
        			params.setImgFrontColorBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("110")) {
        			params.setImgFrontColorBwRate6(fileByte);
        		}else if(fileList[i].getName().contains("111")) {
        			params.setImgFrontBw(fileByte);
        		}else if(fileList[i].getName().contains("112")) {
        			params.setImgFrontBwMineral(fileByte);
        		}else if(fileList[i].getName().contains("113")) {
        			params.setImgFrontBwOrganism(fileByte);
        		}else if(fileList[i].getName().contains("114")) {
        			params.setImgFrontBwReversal(fileByte);
        		}else if(fileList[i].getName().contains("115")) {
        			params.setImgFrontBwBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("116")) {
        			params.setImgFrontBwBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("117")) {
        			params.setImgFrontBwBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("118")) {
        			params.setImgFrontBwBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("119")) {
        			params.setImgFrontBwBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("120")) {
        			params.setImgFrontBwBwRate6(fileByte);
        		}else if(fileList[i].getName().contains("201")) {//측면
        			params.setImgSide(fileByte);
        			params.setImgSideColor(fileByte);
        		}else if(fileList[i].getName().contains("202")) {
        			params.setImgSideColorMineral(fileByte);
        		}else if(fileList[i].getName().contains("203")) {
        			params.setImgSideColorOrganism(fileByte);
        		}else if(fileList[i].getName().contains("204")) {
        			params.setImgSideColorReversal(fileByte);
        		}else if(fileList[i].getName().contains("205")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("206")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("207")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("208")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("209")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("210")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("211")) {
        			params.setImgSideBw(fileByte);
        		}else if(fileList[i].getName().contains("212")) {
        			params.setImgSideBwMinerals(fileByte);
        		}else if(fileList[i].getName().contains("213")) {
        			params.setImgSideBwOrganism(fileByte);
        		}else if(fileList[i].getName().contains("214")) {
        			params.setImgSideBwReversal(fileByte);
        		}else if(fileList[i].getName().contains("215")) {
        			params.setImgSideBwBwRate1(fileByte);
        		}else if(fileList[i].getName().contains("216")) {
        			params.setImgSideBwBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("217")) {
        			params.setImgSideBwBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("218")) {
        			params.setImgSideBwBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("219")) {
        			params.setImgSideBwBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("220")) {
        			params.setImgSideBwBwRate6(fileByte);
        		}
        		
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
		return params;
	}
	
	@Override
	public Learning selectLearnAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLearnAnswer(params);
	}	

	@Override
	public int updateLeanAnswer(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateLeanAnswer(params);
	}

	@Override
	public int insertLearningResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.insertLearningResult(params);
	}

	@Override
	public Learning selectLeaningSum(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.selectLeaningSum(params);
	}

	@Override
	public int updateLearningResult(Learning params) {
		// TODO Auto-generated method stub
		return learningDAO.updateLearningResult(params);
	}


	
}
