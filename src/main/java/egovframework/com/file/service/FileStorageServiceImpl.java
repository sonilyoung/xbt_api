package egovframework.com.file.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.EnumSet;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * FileStorageService 구현체로 서블릿 설치 경로에 파일 저장 (※파일 Storage 결정 시까지 임시로 사용)
 * 
 * @fileName : ServletFileStorageService.java
 * @author : YeongJun Lee
 * @date : 2022.07.12
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);
	
    @Autowired
    private ServletContext servletContext;

    private String realPath;
    
    /*단품 저장경로*/
    public static final String UNIT_ROOT_DIR = GlobalsProperties.getProperty("xray.unitImg.path");    

    /*xray 저장경로*/
    public static final String XRAY_ROOT_DIR = GlobalsProperties.getProperty("xray.img.path");
    
    /*이론 저장경로*/
    public static final String THEORY_ROOT_DIR = GlobalsProperties.getProperty("theory.img.path");
    
    /*파일업로드 저장경로*/
    public static final String FILE_UPLOAD_PATH = GlobalsProperties.getProperty("file.upload.path");
    
    /*파일업로드 저장경로*/
    public static final String FILE_DB_UPLOAD_PATH = GlobalsProperties.getProperty("file.db.upload.path");
    
    /*kaist xray API업로드 저장경로*/
    public static final String KAIST_SUDO_IMG_REQUEST_PATH = GlobalsProperties.getProperty("kaist.sudo.img.request.path");    
    
    /*kaist xray API RESPONSE 저장경로*/
    public static final String KAIST_SUDO_IMG_RESPONSE_PATH = GlobalsProperties.getProperty("kaist.sudo.img.response.path"); 
    
    /*kaist xray API RESPONSE 저장경로*/
    public static final String KAIST_TWOD_IMG_RESPONSE_PATH = GlobalsProperties.getProperty("kaist.twod.img.response.path");  
    
    /*kaist xray API REQUEST 저장경로*/
    public static final String KAIST_REQUEST_IMG_REQUEST_PATH = GlobalsProperties.getProperty("kaist.threed.img.request.path");
    
    /*kaist xray API REQUEST 저장경로*/
    public static final String KAIST_RESPONSE_IMG_REQUEST_PATH = GlobalsProperties.getProperty("kaist.threed.img.response.path");   
    
    /*단품 저장경로*/
    public static final String FACE_ROOT_DIR = GlobalsProperties.getProperty("face.img.path");      
    
    @PostConstruct
    public void initialize() {
        this.realPath = servletContext.getRealPath("/");
    }
    
	@Override
	public AttachFile createUnitImage(String imgType, AttachFile params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = UNIT_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+params.getTargetName();
    	File imgDir = new File(filePath+File.separator+params.getTargetName());
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(params.getTargetName()).append("-").append(imgType).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}       
    
	@Override
	public AttachFile createUnitImageFile(String targetName, AttachFile params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = UNIT_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+params.getTargetName();
    	File imgDir = new File(filePath+File.separator+params.getTargetName());
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(params.getTargetName()).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}        
    
	@Override
	public AttachFile createXrayImageFile(String targetName, AttachFile params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = XRAY_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+params.getTargetName();
    	File imgDir = new File(filePath+File.separator+params.getTargetName());
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(params.getTargetName()).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}    

    @Override
    public AttachFile createFile(MultipartFile file) throws Exception {
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String filePath = FILE_UPLOAD_PATH;
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //sb.append(originalFileName.substring(0, originalFileName.indexOf(fileExtension) - 1));
        //sb.append("_").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))
                .append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(filePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(FILE_DB_UPLOAD_PATH);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
    }

    @Override
    public boolean deleteFile(AttachFile AttachFile) {
        File file = getFile(AttachFile);
        boolean bDeleted = false;
        if (file.exists()) {
            bDeleted = file.delete();
        }
        return bDeleted;
    }

    @Override
    public File getFile(AttachFile AttachFile) {
        File file = new File(AttachFile.getFilePath(), AttachFile.getSaveFileName());
        return file;
    }
    
    /**
     * 이미지정보 가져오기
     */
    @Override
    public void getImage(String imgPath, HttpServletResponse response) throws Exception{
    	
    	File file = new File(imgPath);
    	if(!file.isFile()){
    		throw new BaseException(BaseResponseCode.DATA_IS_NULL);
    	}
    	
    	FileInputStream fis = null;
    	new FileInputStream(file);
    	
    	BufferedInputStream in = null;
    	ByteArrayOutputStream bStream = null;
    	try {
    		fis = new FileInputStream(file);
    		in = new BufferedInputStream(fis);
    		bStream = new ByteArrayOutputStream();
    		int imgByte;
    		while ((imgByte = in.read()) != -1) {
    			bStream.write(imgByte);
    		}

    		String type = "";
    		String ext = FilenameUtils.getExtension(file.getName());
    		if (ext != null && !"".equals(ext)) {
    			if ("jpg".equals(ext.toLowerCase())) {
    				type = "image/jpeg";
    			} else {
    				type = "image/" + ext.toLowerCase();
    			}

    		} else {
    			LOGGER.debug("Image fileType is null.");
    		}

    		response.setHeader("Content-Type", type);
    		response.setContentLength(bStream.size());

    		bStream.writeTo(response.getOutputStream());

    		response.getOutputStream().flush();
    		response.getOutputStream().close();

    	} catch (Exception e) {
    		LOGGER.debug("{}", e);
    	} finally {
    		if (bStream != null) {
    			try {
    				bStream.close();
    			} catch (Exception est) {
    				LOGGER.debug("IGNORED: {}", est.getMessage());
    			}
    		}
    		if (in != null) {
    			try {
    				in.close();
    			} catch (Exception ei) {
    				LOGGER.debug("IGNORED: {}", ei.getMessage());
    			}
    		}
    		if (fis != null) {
    			try {
    				fis.close();
    			} catch (Exception efis) {
    				LOGGER.debug("IGNORED: {}", efis.getMessage());
    			}
    		}
    	}
    }

	@Override
	public AttachFile createTheoryImageFile(String fileNameWithoutExtension, Theory params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = THEORY_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+params.getQuestionId();
    	File imgDir = new File(filePath+File.separator+params.getQuestionId());
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(params.getQuestionId()).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}

	@Override
	public AttachFile createXrayImageFiles(String targetName, String fileNameWithoutExtension, XrayImgContents params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = XRAY_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+targetName;
    	File imgDir = new File(filePath+File.separator+targetName);
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(targetName).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}

	@Override
	public AttachFile createKaistXrayImageFiles(String targetName, String fileNameWithoutExtension, LearningImg params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        //String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String fileExtension = "png";
        
        String filePath = KAIST_SUDO_IMG_REQUEST_PATH;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+targetName;
    	File imgDir = new File(filePath+File.separator+targetName);
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(targetName).append("-").append(fileNameWithoutExtension).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}

	@Override
	public void makeKaistSudoImages(JsonNode jdata) throws Exception {
		// TODO Auto-generated method stub
        try {
        	
        	
        	//ObjectMapper objectMapper = new ObjectMapper();
            //String bagScanId = objectMapper.writeValueAsString(jdata.get("bagScanId")).asText();;
        	String bagScanId = jdata.get("bagScanId").asText();
        	String responsePath = KAIST_SUDO_IMG_RESPONSE_PATH + File.separator + bagScanId;
        	String fileExtension = ".png";
            File fileDir = new File(responsePath);
            // root directory 없으면 생성
        	if (!fileDir.exists()) {
        		fileDir.mkdirs(); //폴더 생성합니다.
        	}        	
        	
        	String targetFile = responsePath + File.separator + bagScanId;
        	byte[] byteArray = null;
        	
            String imgFrontColor = jdata.get("imgFrontColor").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColor);
        	FileOutputStream fos = new FileOutputStream(targetFile + "-101" + fileExtension);
        	fos.write(byteArray);
        	fos.close();
        	
            String imgFrontColorMineral = jdata.get("imgFrontColorMineral").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorMineral);
            fos = new FileOutputStream(targetFile + "-102" + fileExtension);
            fos.write(byteArray);        	
            fos.close();        	
        	
            String imgFrontColorOrganism = jdata.get("imgFrontColorOrganism").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorOrganism);
            fos = new FileOutputStream(targetFile + "-103" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontColorReversal = jdata.get("imgFrontColorReversal").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorReversal);
            fos = new FileOutputStream(targetFile + "-104" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontColorBwRate1 = jdata.get("imgFrontColorBwRate1").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorBwRate1);
            fos = new FileOutputStream(targetFile + "-105" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontColorBwRate2 = jdata.get("imgFrontColorBwRate2").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorBwRate2);
            fos = new FileOutputStream(targetFile + "-106" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontColorBwRate3 = jdata.get("imgFrontColorBwRate3").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorBwRate3);
            fos = new FileOutputStream(targetFile + "-107" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontColorBwRate4 = jdata.get("imgFrontColorBwRate4").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorBwRate4);
            fos = new FileOutputStream(targetFile + "-108" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontColorBwRate5 = jdata.get("imgFrontColorBwRate5").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorBwRate5);
            fos = new FileOutputStream(targetFile + "-109" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontColorBwRate6 = jdata.get("imgFrontColorBwRate6").asText();
            byteArray = Base64.getDecoder().decode(imgFrontColorBwRate6);
            fos = new FileOutputStream(targetFile + "-110" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontBw = jdata.get("imgFrontBw").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBw);
            fos = new FileOutputStream(targetFile + "-111" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontBwMineral = jdata.get("imgFrontBwMineral").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwMineral);
            fos = new FileOutputStream(targetFile + "-112" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontBwOrganism = jdata.get("imgFrontBwOrganism").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwOrganism);
            fos = new FileOutputStream(targetFile + "-113" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontBwReversal = jdata.get("imgFrontBwReversal").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwReversal);
            fos = new FileOutputStream(targetFile + "-114" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontBwBwRate1 = jdata.get("imgFrontBwBwRate1").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwBwRate1);
            fos = new FileOutputStream(targetFile + "-115" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontBwBwRate2 = jdata.get("imgFrontBwBwRate2").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwBwRate2);
            fos = new FileOutputStream(targetFile + "-116" + fileExtension);
            fos.write(byteArray);        	
            fos.close();    
            
            String imgFrontBwBwRate3 = jdata.get("imgFrontBwBwRate3").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwBwRate3);
            fos = new FileOutputStream(targetFile + "-117" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgFrontBwBwRate4 = jdata.get("imgFrontBwBwRate4").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwBwRate4);
            fos = new FileOutputStream(targetFile + "-118" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgFrontBwBwRate5 = jdata.get("imgFrontBwBwRate5").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwBwRate5);
            fos = new FileOutputStream(targetFile + "-119" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgFrontBwBwRate6 = jdata.get("imgFrontBwBwRate6").asText();
            byteArray = Base64.getDecoder().decode(imgFrontBwBwRate6);
            fos = new FileOutputStream(targetFile + "-120" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColor = jdata.get("imgSideColor").asText();
            byteArray = Base64.getDecoder().decode(imgSideColor);
            fos = new FileOutputStream(targetFile + "-201" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorMineral = jdata.get("imgSideColorMineral").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorMineral);
            fos = new FileOutputStream(targetFile + "-202" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorOrganism = jdata.get("imgSideColorOrganism").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorOrganism);
            fos = new FileOutputStream(targetFile + "-203" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorReversal = jdata.get("imgSideColorReversal").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorReversal);
            fos = new FileOutputStream(targetFile + "-204" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorBwRate1 = jdata.get("imgSideColorBwRate1").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorBwRate1);
            fos = new FileOutputStream(targetFile + "-205" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorBwRate2 = jdata.get("imgSideColorBwRate2").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorBwRate2);
            fos = new FileOutputStream(targetFile + "-206" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorBwRate3 = jdata.get("imgSideColorBwRate3").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorBwRate3);
            fos = new FileOutputStream(targetFile + "-207" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorBwRate4 = jdata.get("imgSideColorBwRate4").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorBwRate4);
            fos = new FileOutputStream(targetFile + "-208" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorBwRate5 = jdata.get("imgSideColorBwRate5").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorBwRate5);
            fos = new FileOutputStream(targetFile + "-209" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideColorBwRate6 = jdata.get("imgSideColorBwRate6").asText();
            byteArray = Base64.getDecoder().decode(imgSideColorBwRate6);
            fos = new FileOutputStream(targetFile + "-210" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideBw = jdata.get("imgSideBw").asText();
            byteArray = Base64.getDecoder().decode(imgSideBw);
            fos = new FileOutputStream(targetFile + "-211" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideBwMinerals = jdata.get("imgSideBwMinerals").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwMinerals);
            fos = new FileOutputStream(targetFile + "-212" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideBwOrganism = jdata.get("imgSideBwOrganism").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwOrganism);
            fos = new FileOutputStream(targetFile + "-213" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideBwReversal = jdata.get("imgSideBwReversal").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwReversal);
            fos = new FileOutputStream(targetFile + "-214" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            String imgSideBwBwRate1 = jdata.get("imgSideBwBwRate1").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwBwRate1);
            fos = new FileOutputStream(targetFile + "-215" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
            String imgSideBwBwRate2 = jdata.get("imgSideBwBwRate2").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwBwRate2);
            fos = new FileOutputStream(targetFile + "-216" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            String imgSideBwBwRate3 = jdata.get("imgSideBwBwRate3").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwBwRate3);
            fos = new FileOutputStream(targetFile + "-217" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            String imgSideBwBwRate4 = jdata.get("imgSideBwBwRate4").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwBwRate4);
            fos = new FileOutputStream(targetFile + "-218" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            String imgSideBwBwRate5 = jdata.get("imgSideBwBwRate5").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwBwRate5);
            fos = new FileOutputStream(targetFile + "-219" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            String imgSideBwBwRate6 = jdata.get("imgSideBwBwRate6").asText();
            byteArray = Base64.getDecoder().decode(imgSideBwBwRate6);
            fos = new FileOutputStream(targetFile + "-220" + fileExtension);
            fos.write(byteArray);        	
            fos.close();     
            
            
    		/*imgFront;//정면
    		imgSide;//측면
    		imgThreed;//3d이미지
    		imgThreedAngle;//각조조절된3d이미지

    		imgFrontColor; //정면컬러 101
    		
    		imgFrontColorMineral;//정면무기물 102
    		imgFrontColorOrganism;//정면유기물 103
    		imgFrontColorReversal;//정면반전 104
    		imgFrontColorBwRate1;//정면채도 105
    		imgFrontColorBwRate2;//정면채도 106
    		imgFrontColorBwRate3;//정면채도 107
    		imgFrontColorBwRate4;//정면채도 108
    		imgFrontColorBwRate5;//정면채도 109
    		imgFrontColorBwRate6;//정면채도 110

    		imgFrontBw;//정면흑백 111
    		imgFrontBwMineral;//정면흑백무기물 112
    		imgFrontBwOrganism;//정면흑백유기물 113
    		imgFrontBwReversal;//정면흑백반전 114
    		imgFrontBwBwRate1;//정면흑백채도 115
    		imgFrontBwBwRate2;//정면흑백채도 116
    		imgFrontBwBwRate3;//정면흑백채도 117
    		imgFrontBwBwRate4;// 정면흑백채도118
    		imgFrontBwBwRate5;//정면흑백채도 119
    		imgFrontBwBwRate6;//정면흑백채도 120

    		imgSideColor;//측면컬러 201
    		imgSideColorMineral;//측면무기물 202
    		imgSideColorOrganism;//측면유기물 203
    		imgSideColorReversal;//측면반전 204
    		imgSideColorBwRate1;//측면채도 205
    		imgSideColorBwRate2;//측면채도206
    		imgSideColorBwRate3;//측면채도207
    		imgSideColorBwRate4;//측면채도208
    		imgSideColorBwRate5;//측면채도209
    		imgSideColorBwRate6;//측면채도210

    		imgSideBw;//측면흑백211
    		imgSideBwMinerals;//측면흑백무기물212
    		imgSideBwOrganism;//측면흑백유기물213
    		imgSideBwReversal;//측면흑백반전214
    		imgSideBwBwRate1;//측면흑백채도215
    		imgSideBwBwRate2;//측면흑백채도216
    		imgSideBwBwRate3;//측면흑백채도217
    		imgSideBwBwRate4;//측면흑백채도218
    		imgSideBwBwRate5;//측면흑백채도219
    		imgSideBwBwRate6;//측면흑백채도220
    		*/	   
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void fileCopy() throws Exception {
		// TODO Auto-generated method stub
        Path sourceDir = Paths.get(KAIST_SUDO_IMG_RESPONSE_PATH);
        Path targetDir = Paths.get(XRAY_ROOT_DIR);

        try {

            // 소스 디렉토리의 파일 및 하위 디렉토리 복사
            Files.walkFileTree(sourceDir, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path tg = targetDir.resolve(sourceDir.relativize(dir));
                    if (!Files.exists(tg)) {
                        Files.createDirectory(tg);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path tf = targetDir.resolve(sourceDir.relativize(file));
                    Files.copy(file, tf, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

		
	@Override
	public void makeKaistTwodImages(JsonNode jdata) throws Exception {
		// TODO Auto-generated method stub
        try {
        	//ObjectMapper objectMapper = new ObjectMapper();
            //String bagScanId = objectMapper.writeValueAsString(jdata.get("bagScanId")).asText();;
        	String fileName = jdata.get("fileName").asText();
        	String responsePath = KAIST_TWOD_IMG_RESPONSE_PATH + File.separator + fileName;
        	String fileExtension = ".png";
            File fileDir = new File(responsePath);
            // root directory 없으면 생성
        	if (!fileDir.exists()) {
        		fileDir.mkdirs(); //폴더 생성합니다.
        	}        	
        	
        	String targetFile = responsePath + File.separator + fileName;
        	byte[] byteArray = null;
        	
        	if(jdata.get("towdGenList")!=null) {
            	for(int i=0; i < jdata.get("towdGenList").size(); i++) {
            		
            		String twodImg = jdata.get("towdGenList").get(i).asText();
            		byteArray = Base64.getDecoder().decode(twodImg);
                	FileOutputStream fos = new FileOutputStream(targetFile + "-" + i + fileExtension);
                	fos.write(byteArray);
                	fos.close();            		
            	}        		
        	}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}	
	
	@Override
	public AttachFile createKaistThreedImageFiles(String targetName, String fileNameWithoutExtension, ThreedGeneration params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        //String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String fileExtension = "raw";
        
        String filePath = KAIST_REQUEST_IMG_REQUEST_PATH;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+targetName;
    	File imgDir = new File(filePath+File.separator+targetName);
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        sb.append(targetName).append("-").append(fileNameWithoutExtension).append("_" + params.getWidth() + "x" + params.getHeight()).append(".").append(fileExtension);
        String realFileName = sb.toString();
        
        try {
            
            // PNG 파일 로드
            newFile = new File(targetFilePath + File.separator + realFileName);
            file.transferTo(newFile);
            
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}	
	
	@Override
	public void makeKaistThreedImages(JsonNode jdata) throws Exception {
		// TODO Auto-generated method stub
        try {
        	//ObjectMapper objectMapper = new ObjectMapper();
            //String bagScanId = objectMapper.writeValueAsString(jdata.get("bagScanId")).asText();;
        	String fileName = jdata.get("unitId").asText();
        	String responsePath = KAIST_RESPONSE_IMG_REQUEST_PATH + File.separator + fileName;
        	String responsePathNew = UNIT_ROOT_DIR + File.separator + fileName;
        	String fileExtension = ".png";
        	String fileGifExtension = ".gif";
        	
            File fileDir = new File(responsePath);
            File fileDirNew = new File(responsePathNew);
            
            // root directory 없으면 생성
        	if (!fileDir.exists()) {
        		fileDir.mkdirs(); //폴더 생성합니다.
        	}        
        	
        	if (!fileDirNew.exists()) {
        		fileDirNew.mkdirs(); //폴더 생성합니다.
        	}        	
        	
        	String targetFile = responsePath + File.separator + fileName;
        	String targetFileNew = responsePathNew + File.separator + fileName;
        	byte[] byteArray = null;
        	
        	if(jdata.get("threedGenList")!=null) {
            	for(int i=0; i < jdata.get("threedGenList").size(); i++) {
            		
            		String twodImg = jdata.get("threedGenList").get(i).asText();
            		byteArray = Base64.getDecoder().decode(twodImg);
                	FileOutputStream fos = new FileOutputStream(targetFile + "-" + i + fileExtension);
                	fos.write(byteArray);
                	fos.close();            		
            	}        		
        	}
        	
    		String outputh = jdata.get("outputh").asText();
    		byteArray = Base64.getDecoder().decode(outputh);
        	//FileOutputStream fos = new FileOutputStream(targetFile + "-" + "3d" + fileExtension);
        	//fos.write(byteArray);
        	//fos.close();  
        	
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                // byte 배열을 FileOutputStream을 사용하여 새로운 파일로 저장
                FileOutputStream fileOutputStream = new FileOutputStream(new File(targetFile + "-" + "threed" + fileGifExtension));
                fileOutputStream.write(byteArray);
                fileOutputStream.close();
                
                FileOutputStream fileOutputStreamNew = new FileOutputStream(new File(targetFileNew + "-" + "threed" + fileGifExtension));
                fileOutputStreamNew.write(byteArray);                
                fileOutputStreamNew.close();
            }
        	
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}		
	
	@Override
	public AttachFile createImageFile(String userId, AttachFile params, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
        AttachFile attachFile = null;
        File newFile = null;
        String originalFileName = file.getOriginalFilename();
        //String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        
        String filePath = FACE_ROOT_DIR;
        
        File fileDir = new File(filePath);
        // root directory 없으면 생성
    	if (!fileDir.exists()) {
    		fileDir.mkdirs(); //폴더 생성합니다.
    	}        
        
    	String targetFilePath = filePath+File.separator+params.getTargetName();
    	File imgDir = new File(filePath+File.separator+params.getTargetName());
    	if (!imgDir.exists()) {
    		imgDir.mkdirs(); //폴더 생성합니다.
    	}        	
    	
        // 실제 파일명_현재시간 으로 rename
        StringBuilder sb = new StringBuilder();
        //X00353-204.jpg
        
        sb.append(userId).append("-").append(params.getCommand()).append(".").append(fileExtension);
        String realFileName = sb.toString();
        try {
            newFile = new File(targetFilePath, realFileName);
            file.transferTo(newFile);
            attachFile = new AttachFile();
            attachFile.setFileExt(fileExtension);
            attachFile.setFilePath(filePath);
            attachFile.setOriginalFileName(originalFileName);
            attachFile.setSaveFileName(realFileName);
            attachFile.setFileSize((int) file.getSize());
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return attachFile;
	}    	

}
