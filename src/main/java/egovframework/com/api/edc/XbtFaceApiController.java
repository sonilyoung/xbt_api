package egovframework.com.api.edc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.api.edc.service.EgovXtsEdcPseudoFilterService;
import egovframework.com.api.edc.service.EgovXtsEdcReinforcementService;
import egovframework.com.api.edc.service.EgovXtsEdcThreeDimensionService;
import egovframework.com.api.edc.service.XbtEdcApiService;
import egovframework.com.api.edc.service.XbtFaceApiService;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.edc.vo.FaceVO;
import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api")
@Api(tags = "XTS external API")
public class XbtFaceApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XbtFaceApiController.class);
	
	@Autowired
	private XbtFaceApiService xbtFaceApiService;
	
    @Autowired
    private FileStorageService fileStorageService;    	
	
    /*안면인식 api 이미지 저장경로*/
    public static final String FACE_ROOT_DIR = GlobalsProperties.getProperty("face.img.path");   	
   
	//외부 얼굴등록 api
    @ResponseBody
	@PostMapping(value="/insertFaceApi.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)	
	public BaseResponse<FaceVO> insertFaceApi(
			HttpServletRequest request, HttpServletResponse response
			,@RequestPart(value = "faceImg", required = true) MultipartFile faceImg
			,@RequestPart(value = "params", required = true )FaceVO params
	)throws Exception {
		
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
    	
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<FaceVO>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}		    	
		
		JsonNode json = xbtFaceApiService.insertFaceApi(faceImg, params);
		if("0".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.SUCCESS, json.get("desc").asText());
		}else if("-1000".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("desc").asText());
		}else if("-1001".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("desc").asText());
		}else if("-1002".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("desc").asText());
		}else {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}			

	}    
    
	//외부 얼굴삭제 api
    @ResponseBody
	@PostMapping(value="/removeFaceApi.do")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)	
	public BaseResponse<FaceVO> removeFaceApi(
			HttpServletRequest request, HttpServletResponse response
			,@RequestBody FaceVO params) throws Exception {
		
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
    	
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<FaceVO>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}	    	
    	
    	fileStorageService.fileDeleteAll(params.getUserId(), FACE_ROOT_DIR);
    	JsonNode deleteJson = xbtFaceApiService.deleteFaceApi(params);
		if("0".equals(deleteJson.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, deleteJson.get("data").get("result").asText());			
		}else if("-1000".equals(deleteJson.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, deleteJson.get("data").get("result").asText());
		}else if("-1001".equals(deleteJson.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, deleteJson.get("data").get("result").asText());
		}else if("-1002".equals(deleteJson.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, deleteJson.get("data").get("result").asText());
		}else {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}    	

	}       
    
	//외부 획득한 사진에서 얼굴이 진짜인지 여부를 체크 api
    @ResponseBody
	@PostMapping(value="/livenessFaceApi.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)	
	public BaseResponse<FaceVO> livenessFaceApi(
			HttpServletRequest request, HttpServletResponse response
			,@RequestPart(value = "faceImg", required = true) MultipartFile faceImg
			,@RequestPart(value = "params", required = true )FaceVO params
	)throws Exception {
		
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
    	
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<FaceVO>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}	    	
    	
		JsonNode json = xbtFaceApiService.livenessFaceApi(faceImg, params);
		//prdioction prdioction_desc
		//2 unknown
		//1 real
		//0 fake
		//-997 no face
		//-998 Multiple faces found
		//-999 Server Error
		if("0".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.SUCCESS, json.get("data").get("prdioction_desc").asText());
		}else if("-1000".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("data").get("prdioction_desc").asText());
		}else if("-1001".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("data").get("prdioction_desc").asText());
		}else if("-1002".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("data").get("prdioction_desc").asText());
		}else {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}
	}  
    
	//외부 얼굴일치 api
    @ResponseBody
	@PostMapping(value="/matchFaceApi.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)	
	public BaseResponse<FaceVO> matchFaceApi(
			HttpServletRequest request, HttpServletResponse response
			,@RequestPart(value = "faceImg", required = true) MultipartFile faceImg
			,@RequestPart(value = "params", required = true )FaceVO params
	)throws Exception {
		
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
    	
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<FaceVO>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}	    	
    	
		JsonNode json = xbtFaceApiService.matchFaceApi(faceImg, params);
		if("0".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.SUCCESS, json.get("data").get("similarity").asText());
		}else if("-1000".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("data").get("similarity").asText());
		}else if("-1001".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("data").get("similarity").asText());
		}else if("-1002".equals(json.get("error").asText())) {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, json.get("data").get("similarity").asText());
		}else {
			return new BaseResponse<FaceVO>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}					
    	
	}      
}
