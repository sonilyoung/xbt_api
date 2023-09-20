package egovframework.com.file.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;

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
    public static final String KAIST_XRAY_IMG_REQUEST_PATH = GlobalsProperties.getProperty("kaist.xray.img.request.path");    
    
    /*kaist xray API RESPONSE 저장경로*/
    public static final String KAIST_XRAY_IMG_RESPONSE_PATH = GlobalsProperties.getProperty("kaist.xray.img.response.path");    
    
    @PostConstruct
    public void initialize() {
        this.realPath = servletContext.getRealPath("/");
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
        
        String filePath = KAIST_XRAY_IMG_REQUEST_PATH;
        
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
        	
        	
        	ObjectMapper objectMapper = new ObjectMapper();
            String bagScanId = objectMapper.writeValueAsString(jdata.get("RET_DATA").get("bagScanId"));
        	
        	String responsePath = KAIST_XRAY_IMG_RESPONSE_PATH + File.separator + bagScanId;
            File fileDir = new File(responsePath);
            // root directory 없으면 생성
        	if (!fileDir.exists()) {
        		fileDir.mkdirs(); //폴더 생성합니다.
        	}        	
        	
        	byte[] byteArray = null;
        	
            String imgFront = objectMapper.writeValueAsString(jdata.get("RET_DATA").get("imgFront"));
            byteArray = imgFront.getBytes();
        	
        	FileOutputStream fos = new FileOutputStream(responsePath);
        	//byteArray = jdata.get("RET_DATA").get("imgFront").getBytes(StandardCharsets.UTF_8);
        	fos.write(byteArray);
        	
        	
            //fos.write(fileBytes);
        	
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

	

}
