package egovframework.com.api.edc.service;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.http.BaseResponse;

public interface SudoImgService {
	
	public BaseResponse<Integer> sudoImgExcute(XrayImgContents oj, ApiLogin al, MultipartFile frontImg, MultipartFile sideImg) throws Exception;
	
	public ApiLog transImages(XrayImgContents oj, ApiLogin al, AttachFile af) throws Exception;
	
	public ApiLog sudoImgCmd(XrayImgContents oj, ApiLogin al) throws Exception;
	
	public ApiLog getImages(XrayImgContents oj, ApiLogin al) throws Exception;
	
	//public String sudoImg(XrayImgContents oj, ApiLogin al) throws Exception;
	
	//public String sudoImg(XrayImgContents oj, ApiLogin al) throws Exception;
	
	public int insertApiLog(ApiLog oj) throws Exception;
}
