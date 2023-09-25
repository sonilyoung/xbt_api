package egovframework.com.api.edc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.TowdGeneration;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.file.vo.AttachFile;

public interface XbtEdcApiService {
	
	public JsonNode sudoImgExcute(LearningImg oj, ApiLogin al, MultipartFile frontImg, MultipartFile sideImg) throws Exception;
	
	public String transImages(LearningImg oj, ApiLogin al, AttachFile af1, AttachFile af2) throws Exception;
	
	public JsonNode commandExcute(LearningImg oj, ApiLogin al) throws Exception;
	
	public JsonNode selectSudoImg(LearningImg oj, ApiLogin al) throws Exception;
	
	public JsonNode commandTwodGenExcute(TowdGeneration oj, ApiLogin al) throws Exception;
	
	//public String sudoImg(LearningImg oj, ApiLogin al) throws Exception;
	
	public int insertApiLog(ApiLog oj) throws Exception;
	
	public ApiLog selectProgressPer(ApiLog params)throws Exception;

	public List<XrayContents> selectKaistXrayContentsList(XrayContents params);
	
	public int insertKaistXrayContents(XrayContents params);
	
	public JsonNode selectTwodImg(TowdGeneration oj, ApiLogin al) throws Exception;
	
	/*3d생성*/
	public JsonNode threedImgExcute(ThreedGeneration oj, ApiLogin al, MultipartFile frontImg, MultipartFile sideImg) throws Exception;
	
	/*3d 이미지 전송*/
	public String transThreedImages(ThreedGeneration oj, ApiLogin al, AttachFile af1, AttachFile af2) throws Exception;
	
	/*3d 명령어수행*/
	public JsonNode commandThreedGenExcute(ThreedGeneration oj, ApiLogin al) throws Exception;
	
	/*3d 이미지가져오기*/
	public JsonNode selectThreedImg(ThreedGeneration oj, ApiLogin al) throws Exception;
	
}
