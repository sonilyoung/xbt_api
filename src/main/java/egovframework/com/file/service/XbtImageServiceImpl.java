package egovframework.com.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.TowdGeneration;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.common.vo.LearningMainImg;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.util.FileReader;
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
@Service("xbtImageService")
public class XbtImageServiceImpl implements XbtImageService {

	/*
	 * 				//실사이미지 403
					//정면컬러 101
					//정면무기물 102
					//정면유기물 103
					//정면반전 104
					//정면채도 105
					//정면채도 106
					//정면채도 107
					//정면채도 108
					//정면채도 109
					//정면채도 110

					//정면흑백 111
					//정면흑백무기물 112
					//정면흑백유기물 113
					//정면흑백반전 114
					//정면흑백채도 115
					//정면흑백채도 116
					//정면흑백채도 117
					// 정면흑백채도118
					//정면흑백채도 119
					//정면흑백채도 120

					//측면컬러 201
					//측면무기물 202
					//측면유기물 203
					//측면반전 204
					//측면채도 205
					//측면채도206
					//측면채도207
					//측면채도208
					//측면채도209
					//측면채도210

					//측면흑백211
					//측면흑백무기물212
					//측면흑백유기물213
					//측면흑백반전214
					//측면흑백채도215
					//측면흑백채도216
					//측면흑백채도217
					//측면흑백채도218
					//측면흑백채도219
					//측면흑백채도220
	*/
	
	//복합물품 xray이미지
	public static final String XRAY_IMG_PATH = GlobalsProperties.getProperty("xray.img.path");
	
	//단품이미지
	public static final String XRAY_UNITIMG_PATH = GlobalsProperties.getProperty("xray.unitImg.path");
	
	//이론이미지
	public static final String THEORY_IMG_PATH = GlobalsProperties.getProperty("theory.img.path");
    
	/*kaist xray API RESPONSE 저장경로*/
    public static final String KAIST_SUDO_IMG_RESPONSE_PATH = GlobalsProperties.getProperty("kaist.sudo.img.response.path");
   
    /*kaist xray 2D API RESPONSE 저장경로*/
    public static final String KAIST_TWOD_IMG_RESPONSE_PATH = GlobalsProperties.getProperty("kaist.twod.img.response.path"); 

    /*kaist xray 3D API RESPONSE 저장경로*/
    public static final String KAIST_THREED_IMG_RESPONSE_PATH = GlobalsProperties.getProperty("kaist.threed.img.response.path");    
    
    
    
	@Override
	public List<LearningProblem> selectLeaningImgList(List<LearningProblem> pList) {
		// TODO Auto-generated method stub
		
    	String xrayPath = XRAY_IMG_PATH;
    	List<LearningProblem> result = new ArrayList<LearningProblem>();
    	for(LearningProblem p : pList) {
    		String scanId = p.getBagScanId();	
            String strDirPath = xrayPath+File.separator+scanId; 
            File[] fileList = null;
    		fileList = FileReader.ListFile( strDirPath );
    			
            byte[] fileByte = null;/*이미지*/
            
            //결과유기물
            //////System.out.println("result count : " + fileList.length);
            
            if(fileList==null) {
            	continue;
            }            
            
            //byte변환
            LearningImg params = new LearningImg();
            for( int i = 0; i < fileList.length; i++ ) { 
            	params.setBagScanId(scanId);
            	//System.out.println("result : "+fileList[i]);
            	try {
            		fileByte = Files.readAllBytes(fileList[i].toPath());
            		//System.out.println("fileByte : "+fileByte);
            		if(fileList[i].getName().contains("101")) {//정면
            			p.setImgFront(fileByte);
            		}else if(fileList[i].getName().contains("201")) {//측면
            			p.setImgSide(fileByte);
            		}
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
            
            //result.setImgFrontList(imgFrontList);
            //result.setImgSideList(imgSideList);
            result.add(p);
    	}
		return result;
	}
	
	/**
		공통 이미지 조회 (단껀씩 조회 가능)
	**/
	@Override
	public LearningImg selectLeaningImg(LearningImg params) {
		// TODO Auto-generated method stub
		
    	String xrayPath = XRAY_IMG_PATH;
    	
    	String scanId = params.getBagScanId();
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        //결과유기물
        ////System.out.println("result count : " + fileList.length);
        
        if(fileList==null) {
        	return params;
        }        
        
        //byte변환
        byte[] tempFileByte = null;/*정면이미지*/
        for( int i = 0; i < fileList.length; i++ ) { 
        	//System.out.println("result : "+fileList[i]);
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		//System.out.println("fileByte : "+fileByte);
        		
        		if(fileList[i].getName().contains("101")) {
        			tempFileByte = fileByte;
        		}
        		
        		if("101".equals(params.getCommand()) && fileList[i].getName().contains("101")) {//정면
        			params.setImgFrontColor(fileByte);break;
        		}else if("102".equals(params.getCommand()) && fileList[i].getName().contains("102")) {
        			params.setImgFrontColorMineral(fileByte);break;
        		}else if("103".equals(params.getCommand()) && fileList[i].getName().contains("103")) {
        			params.setImgFrontColorOrganism(fileByte);break;
        		}else if("104".equals(params.getCommand()) && fileList[i].getName().contains("104")) {
        			params.setImgFrontColorReversal(fileByte);break;
        		}else if("105".equals(params.getCommand()) && fileList[i].getName().contains("105")) {
        			params.setImgFrontColorBwRate1(fileByte);break;
        		}else if("106".equals(params.getCommand()) && fileList[i].getName().contains("106")) {
        			params.setImgFrontColorBwRate2(fileByte);break;
        		}else if("107".equals(params.getCommand()) && fileList[i].getName().contains("107")) {
        			params.setImgFrontColorBwRate3(fileByte);break;
        		}else if("108".equals(params.getCommand()) && fileList[i].getName().contains("108")) {
        			params.setImgFrontColorBwRate4(fileByte);break;
        		}else if("109".equals(params.getCommand()) && fileList[i].getName().contains("109")) {
        			params.setImgFrontColorBwRate5(fileByte);break;
        		}else if("110".equals(params.getCommand()) && fileList[i].getName().contains("110")) {
        			params.setImgFrontColorBwRate6(fileByte);break;
        		}else if("111".equals(params.getCommand()) && fileList[i].getName().contains("111")) {
        			params.setImgFrontBw(fileByte);break;
        		}else if("112".equals(params.getCommand()) && fileList[i].getName().contains("112")) {
        			params.setImgFrontBwMineral(fileByte);break;
        		}else if("113".equals(params.getCommand()) && fileList[i].getName().contains("113")) {
        			params.setImgFrontBwOrganism(fileByte);break;
        		}else if("114".equals(params.getCommand()) && fileList[i].getName().contains("114")) {
        			params.setImgFrontBwReversal(fileByte);break;
        		}else if("115".equals(params.getCommand()) && fileList[i].getName().contains("115")) {
        			params.setImgFrontBwBwRate1(fileByte);break;
        		}else if("116".equals(params.getCommand()) && fileList[i].getName().contains("116")) {
        			params.setImgFrontBwBwRate2(fileByte);break;
        		}else if("117".equals(params.getCommand()) && fileList[i].getName().contains("117")) {
        			params.setImgFrontBwBwRate3(fileByte);break;
        		}else if("118".equals(params.getCommand()) && fileList[i].getName().contains("118")) {
        			params.setImgFrontBwBwRate4(fileByte);break;
        		}else if("119".equals(params.getCommand()) && fileList[i].getName().contains("119")) {
        			params.setImgFrontBwBwRate5(fileByte);break;
        		}else if("120".equals(params.getCommand()) && fileList[i].getName().contains("120")) {
        			params.setImgFrontBwBwRate6(fileByte);break;
        		}else if("201".equals(params.getCommand()) && fileList[i].getName().contains("201")) {//측면
        			params.setImgSideColor(fileByte);break;
        		}else if("202".equals(params.getCommand()) && fileList[i].getName().contains("202")) {
        			params.setImgSideColorMineral(fileByte);break;
        		}else if("203".equals(params.getCommand()) && fileList[i].getName().contains("203")) {
        			params.setImgSideColorOrganism(fileByte);break;
        		}else if("204".equals(params.getCommand()) && fileList[i].getName().contains("204")) {
        			params.setImgSideColorReversal(fileByte);break;
        		}else if("205".equals(params.getCommand()) && fileList[i].getName().contains("205")) {
        			params.setImgSideColorBwRate1(fileByte);break;
        		}else if("206".equals(params.getCommand()) && fileList[i].getName().contains("206")) {
        			params.setImgSideColorBwRate2(fileByte);break;
        		}else if("207".equals(params.getCommand()) && fileList[i].getName().contains("207")) {
        			params.setImgSideColorBwRate3(fileByte);break;
        		}else if("208".equals(params.getCommand()) && fileList[i].getName().contains("208")) {
        			params.setImgSideColorBwRate4(fileByte);break;
        		}else if("209".equals(params.getCommand()) && fileList[i].getName().contains("209")) {
        			params.setImgSideColorBwRate5(fileByte);break;
        		}else if("210".equals(params.getCommand()) && fileList[i].getName().contains("210")) {
        			params.setImgSideColorBwRate6(fileByte);break;
        		}else if("211".equals(params.getCommand()) && fileList[i].getName().contains("211")) {
        			params.setImgSideBw(fileByte);break;
        		}else if("212".equals(params.getCommand()) && fileList[i].getName().contains("212")) {
        			params.setImgSideBwMinerals(fileByte);break;
        		}else if("213".equals(params.getCommand()) && fileList[i].getName().contains("213")) {
        			params.setImgSideBwOrganism(fileByte);break;
        		}else if("214".equals(params.getCommand()) && fileList[i].getName().contains("214")) {
        			params.setImgSideBwReversal(fileByte);break;
        		}else if("215".equals(params.getCommand()) && fileList[i].getName().contains("215")) {
        			params.setImgSideBwBwRate1(fileByte);break;
        		}else if("216".equals(params.getCommand()) && fileList[i].getName().contains("216")) {
        			params.setImgSideBwBwRate2(fileByte);break;
        		}else if("217".equals(params.getCommand()) && fileList[i].getName().contains("217")) {
        			params.setImgSideBwBwRate3(fileByte);break;
        		}else if("218".equals(params.getCommand()) && fileList[i].getName().contains("218")) {
        			params.setImgSideBwBwRate4(fileByte);break;
        		}else if("219".equals(params.getCommand()) && fileList[i].getName().contains("219")) {
        			params.setImgSideBwBwRate5(fileByte);break;
        		}else if("220".equals(params.getCommand()) && fileList[i].getName().contains("220")) {
        			params.setImgSideBwBwRate6(fileByte);break;
        		}else if("403".equals(params.getCommand()) && fileList[i].getName().contains("403")) {//실사이미지
        			params.setImgReal(fileByte);break;
        		}else if("401".equals(params.getCommand()) && fileList[i].getName().contains("401")) {//정면위험물품
        			params.setImgFrontDanger(fileByte);break;
        		}else if("402".equals(params.getCommand()) && fileList[i].getName().contains("402")) {//측면위험물품
        			params.setImgSideDanger(fileByte);break;
        		}
        		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //실사이미지가 없을경우
        if("403".equals(params.getCommand()) && params.getImgReal()==null) {
        	params.setImgReal(tempFileByte);
        }
        
        
		return params;
	}	
	
	@Override
	public LearningImg selectPracticeImg(LearningImg params) {
		// TODO Auto-generated method stub
		
    	String xrayPath = XRAY_UNITIMG_PATH;
    	
    	String scanId = params.getBagScanId();
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        //결과유기물
        ////System.out.println("result count : " + fileList.length);
        
        if(fileList==null) {
        	return params;
        }
        
        //byte변환
        for( int i = 0; i < fileList.length; i++ ) { 
        	//System.out.println("result : "+fileList[i]);
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		//System.out.println("fileByte : "+fileByte);
        		
        		if("101".equals(params.getCommand()) && fileList[i].getName().contains("101")) {//정면
        			params.setImgFrontColor(fileByte);break;
        		}else if("102".equals(params.getCommand()) && fileList[i].getName().contains("102")) {
        			params.setImgFrontColorMineral(fileByte);break;
        		}else if("103".equals(params.getCommand()) && fileList[i].getName().contains("103")) {
        			params.setImgFrontColorOrganism(fileByte);break;
        		}else if("104".equals(params.getCommand()) && fileList[i].getName().contains("104")) {
        			params.setImgFrontColorReversal(fileByte);break;
        		}else if("105".equals(params.getCommand()) && fileList[i].getName().contains("105")) {
        			params.setImgFrontColorBwRate1(fileByte);break;
        		}else if("106".equals(params.getCommand()) && fileList[i].getName().contains("106")) {
        			params.setImgFrontColorBwRate2(fileByte);break;
        		}else if("107".equals(params.getCommand()) && fileList[i].getName().contains("107")) {
        			params.setImgFrontColorBwRate3(fileByte);break;
        		}else if("108".equals(params.getCommand()) && fileList[i].getName().contains("108")) {
        			params.setImgFrontColorBwRate4(fileByte);break;
        		}else if("109".equals(params.getCommand()) && fileList[i].getName().contains("109")) {
        			params.setImgFrontColorBwRate5(fileByte);break;
        		}else if("110".equals(params.getCommand()) && fileList[i].getName().contains("110")) {
        			params.setImgFrontColorBwRate6(fileByte);break;
        		}else if("111".equals(params.getCommand()) && fileList[i].getName().contains("111")) {
        			params.setImgFrontBw(fileByte);break;
        		}else if("112".equals(params.getCommand()) && fileList[i].getName().contains("112")) {
        			params.setImgFrontBwMineral(fileByte);break;
        		}else if("113".equals(params.getCommand()) && fileList[i].getName().contains("113")) {
        			params.setImgFrontBwOrganism(fileByte);break;
        		}else if("114".equals(params.getCommand()) && fileList[i].getName().contains("114")) {
        			params.setImgFrontBwReversal(fileByte);break;
        		}else if("115".equals(params.getCommand()) && fileList[i].getName().contains("115")) {
        			params.setImgFrontBwBwRate1(fileByte);break;
        		}else if("116".equals(params.getCommand()) && fileList[i].getName().contains("116")) {
        			params.setImgFrontBwBwRate2(fileByte);break;
        		}else if("117".equals(params.getCommand()) && fileList[i].getName().contains("117")) {
        			params.setImgFrontBwBwRate3(fileByte);break;
        		}else if("118".equals(params.getCommand()) && fileList[i].getName().contains("118")) {
        			params.setImgFrontBwBwRate4(fileByte);break;
        		}else if("119".equals(params.getCommand()) && fileList[i].getName().contains("119")) {
        			params.setImgFrontBwBwRate5(fileByte);break;
        		}else if("120".equals(params.getCommand()) && fileList[i].getName().contains("120")) {
        			params.setImgFrontBwBwRate6(fileByte);break;
        		}else if("201".equals(params.getCommand()) && fileList[i].getName().contains("201")) {//측면
        			params.setImgSideColor(fileByte);break;
        		}else if("202".equals(params.getCommand()) && fileList[i].getName().contains("202")) {
        			params.setImgSideColorMineral(fileByte);break;
        		}else if("203".equals(params.getCommand()) && fileList[i].getName().contains("203")) {
        			params.setImgSideColorOrganism(fileByte);break;
        		}else if("204".equals(params.getCommand()) && fileList[i].getName().contains("204")) {
        			params.setImgSideColorReversal(fileByte);break;
        		}else if("205".equals(params.getCommand()) && fileList[i].getName().contains("205")) {
        			params.setImgSideColorBwRate1(fileByte);break;
        		}else if("206".equals(params.getCommand()) && fileList[i].getName().contains("206")) {
        			params.setImgSideColorBwRate2(fileByte);break;
        		}else if("207".equals(params.getCommand()) && fileList[i].getName().contains("207")) {
        			params.setImgSideColorBwRate3(fileByte);break;
        		}else if("208".equals(params.getCommand()) && fileList[i].getName().contains("208")) {
        			params.setImgSideColorBwRate4(fileByte);break;
        		}else if("209".equals(params.getCommand()) && fileList[i].getName().contains("209")) {
        			params.setImgSideColorBwRate5(fileByte);break;
        		}else if("210".equals(params.getCommand()) && fileList[i].getName().contains("210")) {
        			params.setImgSideColorBwRate6(fileByte);break;
        		}else if("211".equals(params.getCommand()) && fileList[i].getName().contains("211")) {
        			params.setImgSideBw(fileByte);break;
        		}else if("212".equals(params.getCommand()) && fileList[i].getName().contains("212")) {
        			params.setImgSideBwMinerals(fileByte);break;
        		}else if("213".equals(params.getCommand()) && fileList[i].getName().contains("213")) {
        			params.setImgSideBwOrganism(fileByte);break;
        		}else if("214".equals(params.getCommand()) && fileList[i].getName().contains("214")) {
        			params.setImgSideBwReversal(fileByte);break;
        		}else if("215".equals(params.getCommand()) && fileList[i].getName().contains("215")) {
        			params.setImgSideBwBwRate1(fileByte);break;
        		}else if("216".equals(params.getCommand()) && fileList[i].getName().contains("216")) {
        			params.setImgSideBwBwRate2(fileByte);break;
        		}else if("217".equals(params.getCommand()) && fileList[i].getName().contains("217")) {
        			params.setImgSideBwBwRate3(fileByte);break;
        		}else if("218".equals(params.getCommand()) && fileList[i].getName().contains("218")) {
        			params.setImgSideBwBwRate4(fileByte);break;
        		}else if("219".equals(params.getCommand()) && fileList[i].getName().contains("219")) {
        			params.setImgSideBwBwRate5(fileByte);break;
        		}else if("220".equals(params.getCommand()) && fileList[i].getName().contains("220")) {
        			params.setImgSideBwBwRate6(fileByte);break;
        		}else if("403".equals(params.getCommand()) && fileList[i].getName().contains("403")) {//실사이미지
        			params.setImgReal(fileByte);break;
        		}else if("401".equals(params.getCommand()) && fileList[i].getName().contains("401")) {//정면위험물품
        			params.setImgFrontDanger(fileByte);break;
        		}else if("402".equals(params.getCommand()) && fileList[i].getName().contains("402")) {//측면위험물품
        			params.setImgSideDanger(fileByte);break;
        		}
        		
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
		return params;
	}		
	
	
	@Override
	public LearningImg selectThreedAngle(LearningImg params) {
		// TODO Auto-generated method stub
		
    	String xrayPath = XRAY_UNITIMG_PATH;
    	
    	String scanId = params.getUnitId();
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        //결과유기물
        ////System.out.println("result count : " + fileList.length);
        
        if(fileList==null) {
        	return params;
        }        
        
        //byte변환
        for( int i = 0; i < fileList.length; i++ ) { 
        	//System.out.println("result : "+fileList[i]);
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		//System.out.println("fileByte : "+fileByte);
        		
        		if("101".equals(params.getCommand()) && fileList[i].getName().contains("101")) {//정면
        			params.setImgFrontColor(fileByte);break;
        		}else if("102".equals(params.getCommand()) && fileList[i].getName().contains("102")) {
        			params.setImgFrontColorMineral(fileByte);break;
        		}else if("103".equals(params.getCommand()) && fileList[i].getName().contains("103")) {
        			params.setImgFrontColorOrganism(fileByte);break;
        		}else if("104".equals(params.getCommand()) && fileList[i].getName().contains("104")) {
        			params.setImgFrontColorReversal(fileByte);break;
        		}else if("105".equals(params.getCommand()) && fileList[i].getName().contains("105")) {
        			params.setImgFrontColorBwRate1(fileByte);break;
        		}else if("106".equals(params.getCommand()) && fileList[i].getName().contains("106")) {
        			params.setImgFrontColorBwRate2(fileByte);break;
        		}else if("107".equals(params.getCommand()) && fileList[i].getName().contains("107")) {
        			params.setImgFrontColorBwRate3(fileByte);break;
        		}else if("108".equals(params.getCommand()) && fileList[i].getName().contains("108")) {
        			params.setImgFrontColorBwRate4(fileByte);break;
        		}else if("109".equals(params.getCommand()) && fileList[i].getName().contains("109")) {
        			params.setImgFrontColorBwRate5(fileByte);break;
        		}else if("110".equals(params.getCommand()) && fileList[i].getName().contains("110")) {
        			params.setImgFrontColorBwRate6(fileByte);break;
        		}else if("111".equals(params.getCommand()) && fileList[i].getName().contains("111")) {
        			params.setImgFrontBw(fileByte);break;
        		}else if("112".equals(params.getCommand()) && fileList[i].getName().contains("112")) {
        			params.setImgFrontBwMineral(fileByte);break;
        		}else if("113".equals(params.getCommand()) && fileList[i].getName().contains("113")) {
        			params.setImgFrontBwOrganism(fileByte);break;
        		}else if("114".equals(params.getCommand()) && fileList[i].getName().contains("114")) {
        			params.setImgFrontBwReversal(fileByte);break;
        		}else if("115".equals(params.getCommand()) && fileList[i].getName().contains("115")) {
        			params.setImgFrontBwBwRate1(fileByte);break;
        		}else if("116".equals(params.getCommand()) && fileList[i].getName().contains("116")) {
        			params.setImgFrontBwBwRate2(fileByte);break;
        		}else if("117".equals(params.getCommand()) && fileList[i].getName().contains("117")) {
        			params.setImgFrontBwBwRate3(fileByte);break;
        		}else if("118".equals(params.getCommand()) && fileList[i].getName().contains("118")) {
        			params.setImgFrontBwBwRate4(fileByte);break;
        		}else if("119".equals(params.getCommand()) && fileList[i].getName().contains("119")) {
        			params.setImgFrontBwBwRate5(fileByte);break;
        		}else if("120".equals(params.getCommand()) && fileList[i].getName().contains("120")) {
        			params.setImgFrontBwBwRate6(fileByte);break;
        		}else if("201".equals(params.getCommand()) && fileList[i].getName().contains("201")) {//측면
        			params.setImgSideColor(fileByte);break;
        		}else if("202".equals(params.getCommand()) && fileList[i].getName().contains("202")) {
        			params.setImgSideColorMineral(fileByte);break;
        		}else if("203".equals(params.getCommand()) && fileList[i].getName().contains("203")) {
        			params.setImgSideColorOrganism(fileByte);break;
        		}else if("204".equals(params.getCommand()) && fileList[i].getName().contains("204")) {
        			params.setImgSideColorReversal(fileByte);break;
        		}else if("205".equals(params.getCommand()) && fileList[i].getName().contains("205")) {
        			params.setImgSideColorBwRate1(fileByte);break;
        		}else if("206".equals(params.getCommand()) && fileList[i].getName().contains("206")) {
        			params.setImgSideColorBwRate2(fileByte);break;
        		}else if("207".equals(params.getCommand()) && fileList[i].getName().contains("207")) {
        			params.setImgSideColorBwRate3(fileByte);break;
        		}else if("208".equals(params.getCommand()) && fileList[i].getName().contains("208")) {
        			params.setImgSideColorBwRate4(fileByte);break;
        		}else if("209".equals(params.getCommand()) && fileList[i].getName().contains("209")) {
        			params.setImgSideColorBwRate5(fileByte);break;
        		}else if("210".equals(params.getCommand()) && fileList[i].getName().contains("210")) {
        			params.setImgSideColorBwRate6(fileByte);break;
        		}else if("211".equals(params.getCommand()) && fileList[i].getName().contains("211")) {
        			params.setImgSideBw(fileByte);break;
        		}else if("212".equals(params.getCommand()) && fileList[i].getName().contains("212")) {
        			params.setImgSideBwMinerals(fileByte);break;
        		}else if("213".equals(params.getCommand()) && fileList[i].getName().contains("213")) {
        			params.setImgSideBwOrganism(fileByte);break;
        		}else if("214".equals(params.getCommand()) && fileList[i].getName().contains("214")) {
        			params.setImgSideBwReversal(fileByte);break;
        		}else if("215".equals(params.getCommand()) && fileList[i].getName().contains("215")) {
        			params.setImgSideBwBwRate1(fileByte);break;
        		}else if("216".equals(params.getCommand()) && fileList[i].getName().contains("216")) {
        			params.setImgSideBwBwRate2(fileByte);break;
        		}else if("217".equals(params.getCommand()) && fileList[i].getName().contains("217")) {
        			params.setImgSideBwBwRate3(fileByte);break;
        		}else if("218".equals(params.getCommand()) && fileList[i].getName().contains("218")) {
        			params.setImgSideBwBwRate4(fileByte);break;
        		}else if("219".equals(params.getCommand()) && fileList[i].getName().contains("219")) {
        			params.setImgSideBwBwRate5(fileByte);break;
        		}else if("220".equals(params.getCommand()) && fileList[i].getName().contains("220")) {
        			params.setImgSideBwBwRate6(fileByte);break;
        		}else if("403".equals(params.getCommand()) && fileList[i].getName().contains("403")) {//실사이미지
        			params.setImgReal(fileByte);break;
        		}else if("401".equals(params.getCommand()) && fileList[i].getName().contains("401")) {//정면위험물품
        			params.setImgFrontDanger(fileByte);break;
        		}else if("402".equals(params.getCommand()) && fileList[i].getName().contains("402")) {//측면위험물품
        			params.setImgSideDanger(fileByte);break;
        		}
        		
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
		return params;
	}		
	
	
	@Override
	public LearningMainImg selectCommonLearningImg(LearningImg params) {
		// TODO Auto-generated method stub
		LearningMainImg result = new LearningMainImg();
    	String xrayPath = XRAY_IMG_PATH;
    	
    	String scanId = params.getBagScanId();
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        //결과유기물
        ////System.out.println("result count : " + fileList.length);
        
        if(fileList==null) {
        	return result;
        }
        
        //byte변환
        for( int i = 0; i < fileList.length; i++ ) { 
        	//System.out.println("result : "+fileList[i]);
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		//System.out.println("fileByte : "+fileByte);
        		
        		if(fileList[i].getName().contains("101")) {//정면
        			result.setImgFront(fileByte);
        		}
        		
        		if(fileList[i].getName().contains("201")) {//측면
        			result.setImgSide(fileByte);
        		}
        		
        		if(fileList[i].getName().contains("401")) {//정면위험물품
        			result.setImgFront(fileByte);
        		}
        		
        		if(fileList[i].getName().contains("402")) {//측면위험물품
        			result.setImgSide(fileByte);
        		}        		
        		
        		if(fileList[i].getName().contains("403")) {//실사이미지
        			result.setImgReal(fileByte);
        		}
        		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
		return result;
	}
	
	
	@Override
	public LearningMainImg selectCommonPracticeImg(LearningImg params) {
		// TODO Auto-generated method stub
		LearningMainImg result = new LearningMainImg();
    	String xrayPath = XRAY_UNITIMG_PATH;
    	
    	String scanId = params.getUnitId();
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        //결과유기물
        ////System.out.println("result count : " + fileList.length);
        
        if(fileList==null) {
        	return result;
        }
        
        //byte변환
        for( int i = 0; i < fileList.length; i++ ) { 
        	//System.out.println("result : "+fileList[i]);
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		//System.out.println("fileByte : "+fileByte);
        		
        		if(fileList[i].getName().contains("-C") || fileList[i].getName().contains("-101")) {//정면
        			result.setImgFront(fileByte);
        		}else if(fileList[i].getName().contains("-B")|| fileList[i].getName().contains("-201")) {//측면
        			result.setImgSide(fileByte);
        		}else if(fileList[i].getName().contains("-R")|| fileList[i].getName().contains("-403")|| fileList[i].getName().contains("-401")) {//실사이미지
        			result.setImgReal(fileByte);
        		}else if(fileList[i].getName().contains("threed")) {//3D
        			result.setImgThreed(fileByte);
        		}
        		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
		return result;
	}



	@Override
	public LearningImg selectAdmAllUnitImg(LearningImg params) {
		// TODO Auto-generated method stub
		String xrayPath = XRAY_UNITIMG_PATH;
		String scanId = params.getUnitId();	
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        //결과유기물
        for( int i = 0; i < fileList.length; i++ ) { 
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
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
        			params.setImgSideColorBwRate2(fileByte);
        		}else if(fileList[i].getName().contains("207")) {
        			params.setImgSideColorBwRate3(fileByte);
        		}else if(fileList[i].getName().contains("208")) {
        			params.setImgSideColorBwRate4(fileByte);
        		}else if(fileList[i].getName().contains("209")) {
        			params.setImgSideColorBwRate5(fileByte);
        		}else if(fileList[i].getName().contains("210")) {
        			params.setImgSideColorBwRate6(fileByte);
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
        		}else if(fileList[i].getName().contains("403")) {//실사이미지
        			params.setImgReal(fileByte);
        		}else if(fileList[i].getName().contains("401")) {//정면위험물품
        			params.setImgFrontDanger(fileByte);
        		}else if(fileList[i].getName().contains("402")) {//측면위험물품
        			params.setImgSideDanger(fileByte);
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
		return params;
	}



	@Override
	public LearningImg selectAdmAllBagImg(LearningImg params, String command) {
		// TODO Auto-generated method stub
    	String xrayPath = XRAY_IMG_PATH;
    	if("kaist".equals(command)) {
    		xrayPath = KAIST_SUDO_IMG_RESPONSE_PATH;
    	}else {
    		xrayPath = XRAY_IMG_PATH;
    	}
    	
    	
		String scanId = params.getBagScanId();	
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        if(fileList==null) {
        	return params;
        }   
        
        //결과유기물
        for( int i = 0; i < fileList.length; i++ ) { 
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		
        		System.out.println("이미지명:"+fileList[i].getName());
        		System.out.println("실사이미지:"+fileList[i].getName().contains("403")); 
        		
        		if(fileList[i].getName().contains("101")) {//정면
        			params.setImgFront(fileByte);
        			params.setImgFrontColor(fileByte);
        		}
        		if(fileList[i].getName().contains("102")) {
        			params.setImgFrontColorMineral(fileByte);
        		}
        		if(fileList[i].getName().contains("103")) {
        			params.setImgFrontColorOrganism(fileByte);
        		}
        		if(fileList[i].getName().contains("104")) {
        			params.setImgFrontColorReversal(fileByte);
        		}
        		if(fileList[i].getName().contains("105")) {
        			params.setImgFrontColorBwRate1(fileByte);
        		}
        		if(fileList[i].getName().contains("106")) {
        			params.setImgFrontColorBwRate2(fileByte);
        		}
        		if(fileList[i].getName().contains("107")) {
        			params.setImgFrontColorBwRate3(fileByte);
        		}
        		if(fileList[i].getName().contains("108")) {
        			params.setImgFrontColorBwRate4(fileByte);
        		}
        		if(fileList[i].getName().contains("109")) {
        			params.setImgFrontColorBwRate5(fileByte);
        		}
        		if(fileList[i].getName().contains("110")) {
        			params.setImgFrontColorBwRate6(fileByte);
        		}
        		if(fileList[i].getName().contains("111")) {
        			params.setImgFrontBw(fileByte);
        		}
        		if(fileList[i].getName().contains("112")) {
        			params.setImgFrontBwMineral(fileByte);
        		}
        		if(fileList[i].getName().contains("113")) {
        			params.setImgFrontBwOrganism(fileByte);
        		}
        		if(fileList[i].getName().contains("114")) {
        			params.setImgFrontBwReversal(fileByte);
        		}
        		if(fileList[i].getName().contains("115")) {
        			params.setImgFrontBwBwRate1(fileByte);
        		}
        		if(fileList[i].getName().contains("116")) {
        			params.setImgFrontBwBwRate2(fileByte);
        		}
        		if(fileList[i].getName().contains("117")) {
        			params.setImgFrontBwBwRate3(fileByte);
        		}
        		if(fileList[i].getName().contains("118")) {
        			params.setImgFrontBwBwRate4(fileByte);
        		}
        		if(fileList[i].getName().contains("119")) {
        			params.setImgFrontBwBwRate5(fileByte);
        		}
        		if(fileList[i].getName().contains("120")) {
        			params.setImgFrontBwBwRate6(fileByte);
        		}
        		if(fileList[i].getName().contains("201")) {//측면
        			params.setImgSide(fileByte);
        			params.setImgSideColor(fileByte);
        		}
        		if(fileList[i].getName().contains("202")) {
        			params.setImgSideColorMineral(fileByte);
        		}
        		if(fileList[i].getName().contains("203")) {
        			params.setImgSideColorOrganism(fileByte);
        		}
        		if(fileList[i].getName().contains("204")) {
        			params.setImgSideColorReversal(fileByte);
        		}
        		if(fileList[i].getName().contains("205")) {
        			params.setImgSideColorBwRate1(fileByte);
        		}
        		if(fileList[i].getName().contains("206")) {
        			params.setImgSideColorBwRate2(fileByte);
        		}
        		if(fileList[i].getName().contains("207")) {
        			params.setImgSideColorBwRate3(fileByte);
        		}
        		if(fileList[i].getName().contains("208")) {
        			params.setImgSideColorBwRate4(fileByte);
        		}
        		if(fileList[i].getName().contains("209")) {
        			params.setImgSideColorBwRate5(fileByte);
        		}
        		if(fileList[i].getName().contains("210")) {
        			params.setImgSideColorBwRate6(fileByte);
        		}
        		if(fileList[i].getName().contains("211")) {
        			params.setImgSideBw(fileByte);
        		}
        		if(fileList[i].getName().contains("212")) {
        			params.setImgSideBwMinerals(fileByte);
        		}
        		if(fileList[i].getName().contains("213")) {
        			params.setImgSideBwOrganism(fileByte);
        		}
        		if(fileList[i].getName().contains("214")) {
        			params.setImgSideBwReversal(fileByte);
        		}
        		if(fileList[i].getName().contains("215")) {
        			params.setImgSideBwBwRate1(fileByte);
        		}
        		if(fileList[i].getName().contains("216")) {
        			params.setImgSideBwBwRate2(fileByte);
        		}
        		if(fileList[i].getName().contains("217")) {
        			params.setImgSideBwBwRate3(fileByte);
        		}
        		if(fileList[i].getName().contains("218")) {
        			params.setImgSideBwBwRate4(fileByte);
        		}
        		if(fileList[i].getName().contains("219")) {
        			params.setImgSideBwBwRate5(fileByte);
        		}if(fileList[i].getName().contains("220")) {
        			params.setImgSideBwBwRate6(fileByte);
        		}
        		if(fileList[i].getName().contains("403")) {//실사이미지
        			System.out.println("실사이미지~~~~~~~~");
        			params.setImgReal(fileByte);
        		}
        		if(fileList[i].getName().contains("401")) {//정면위험물품
        			params.setImgFrontDanger(fileByte);
        		}
        		if(fileList[i].getName().contains("402")) {//측면위험물품
        			params.setImgSideDanger(fileByte);
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
		return params;
	}



	@Override
	public Theory selectTheoryImg(Theory params) {
		// TODO Auto-generated method stub
    	String xrayPath = THEORY_IMG_PATH;
		String scanId = params.getQuestionId();	
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        if(fileList==null) {
        	return params;
        }   
        
        //결과유기물
        for( int i = 0; i < fileList.length; i++ ) { 
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        		if(fileList[i].getName().contains("-1")) {//정면
        			params.setChoiceImg1(fileByte);
        		}else if(fileList[i].getName().contains("-2")) {
        			params.setChoiceImg2(fileByte);
        		}else if(fileList[i].getName().contains("-3")) {
        			params.setChoiceImg3(fileByte);
        		}else if(fileList[i].getName().contains("-4")) {
        			params.setChoiceImg4(fileByte);
        		}else if(fileList[i].getName().contains("-Q")) {
        			params.setMultiPlusImg(fileByte);
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return params;
	}	
		
	

	@Override
	public TowdGeneration selectKaistTwodImg(TowdGeneration params) {
		// TODO Auto-generated method stub
    	String xrayPath = KAIST_TWOD_IMG_RESPONSE_PATH;
		String scanId = params.getFileName();	
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
        List<byte[]> towdGenList = new ArrayList<byte[]>();  
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        
        if(fileList==null) {
        	return params;
        }   
        
        //결과유기물
        for( int i = 0; i < fileList.length; i++ ) { 
        	try {
        		fileByte = Files.readAllBytes(fileList[i].toPath());
        			towdGenList.add(fileByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        params.setTowdGenList(towdGenList);
        return params;
	}	
	
	
	@Override
	public ThreedGeneration selectKaistThreedImg(ThreedGeneration params) {
		// TODO Auto-generated method stub
    	String xrayPath = KAIST_THREED_IMG_RESPONSE_PATH;
		String scanId = params.getUnitId();	
        String strDirPath = xrayPath+File.separator+scanId; 
        File[] fileList = null;
        List<byte[]> threedGenList = new ArrayList<byte[]>();  
		fileList = FileReader.ListFileSort( strDirPath );
			
        byte[] fileByte = null;/*이미지*/
        byte[] fileThreedByte = null;/*3d 이미지*/
        
        if(fileList==null) {
        	return params;
        }   
        
        //결과유기물
        for( int i = 0; i < fileList.length; i++ ) { 
        	try {
        		if(fileList[i].getName().contains("-threed")) {
        			fileThreedByte = Files.readAllBytes(fileList[i].toPath());
                }else {
            		fileByte = Files.readAllBytes(fileList[i].toPath());
            		threedGenList.add(fileByte);                	
                }        		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        params.setThreedGenList(threedGenList);
        params.setOutputh(fileThreedByte);
        return params;
	}		
		
}
