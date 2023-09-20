package egovframework.com.api.edc.service;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.file.vo.AttachFile;

public interface SudoImgService {
	
	public JsonNode sudoImgExcute(LearningImg oj, ApiLogin al, MultipartFile frontImg, MultipartFile sideImg) throws Exception;
	
	public String transImages(LearningImg oj, ApiLogin al, AttachFile af1, AttachFile af2) throws Exception;
	
	public ApiLog sudoImgCmd(LearningImg oj, ApiLogin al) throws Exception;
	
	public JsonNode selectSudoImg(LearningImg oj, ApiLogin al) throws Exception;
	
	//public String sudoImg(LearningImg oj, ApiLogin al) throws Exception;
	
	public int insertApiLog(ApiLog oj) throws Exception;
	
	public ApiLog selectProgressPer(ApiLog params)throws Exception;
}
