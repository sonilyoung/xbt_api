package egovframework.com.file.service;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.file.vo.AttachFile;


/**
 * 파일 Storage 관련 Service
 * 
 * @fileName : FileStorageService.java
 * @author : YeongJun Lee
 * @date : 2022.07.12
 */
public interface FileStorageService {
	
	AttachFile createUnitImage(String imgType, AttachFile params ,MultipartFile file) throws Exception;
	
	AttachFile createUnitImageFile(String targetName, AttachFile params ,MultipartFile file) throws Exception;

	AttachFile createXrayImageFile(String targetName, AttachFile params ,MultipartFile file) throws Exception;
	
	AttachFile createXrayImageFiles(String targetName, String fileNameWithoutExtension, XrayImgContents params,MultipartFile file) throws Exception;
	
    File getFile(AttachFile AttachFile);

    AttachFile createFile(MultipartFile file) throws Exception;

    boolean deleteFile(AttachFile AttachFile);
    
    void getImage(String filePath, HttpServletResponse response) throws Exception;

    AttachFile createTheoryImageFile(String fileNameWithoutExtension, Theory params ,MultipartFile file) throws Exception;
    
    AttachFile createKaistXrayImageFiles(String targetName, String fileNameWithoutExtension, LearningImg params, MultipartFile file) throws Exception;
    
    public void makeKaistSudoImages(JsonNode jdata) throws Exception;
    
    public void fileCopy() throws Exception;
    
    public void makeKaistTwodImages(JsonNode jdata) throws Exception;
    
    public AttachFile createKaistThreedImageFiles(String targetName, String fileNameWithoutExtension, ThreedGeneration params, MultipartFile file) throws Exception ;
    
    public void makeKaistThreedImages(JsonNode jdata) throws Exception;
}
