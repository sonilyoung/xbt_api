package egovframework.com.api.edc.service;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.http.BaseResponse;

public interface SudoImgService {
	
	public JsonNode sudoImgExcute(XrayImgContents oj, ApiLogin al, MultipartFile frontImg, MultipartFile sideImg) throws Exception;
	
	public String transImages(XrayImgContents oj, ApiLogin al, AttachFile af1, AttachFile af2) throws Exception;
	
	public ApiLog sudoImgCmd(XrayImgContents oj, ApiLogin al) throws Exception;
	
	public ApiLog selectSudoImg(XrayImgContents oj, ApiLogin al) throws Exception;
	
	//public String sudoImg(XrayImgContents oj, ApiLogin al) throws Exception;
	
	//public String sudoImg(XrayImgContents oj, ApiLogin al) throws Exception;
	
	public int insertApiLog(ApiLog oj) throws Exception;
	
	public ApiLog selectProgressPer(ApiLog params)throws Exception;
}
