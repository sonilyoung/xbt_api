package egovframework.com.api.edc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api")
@Api(tags = "XTS external API")
public class XbtFaceApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XbtEdcApiController.class);
	
	@Autowired
	private EgovXtsEdcPseudoFilterService egovXtsEdcPseudoFilterService;
	
	@Autowired
	private EgovXtsEdcThreeDimensionService egovXtsEdcThreeDimensionService;
	
	@Autowired
	private EgovXtsEdcReinforcementService egovXtsEdcReinforcementService;
	
	@Autowired
	private XbtFaceApiService xbtFaceApiService;
	
	@Autowired
	private ApiLoginService apiLoginService;
	
	@Autowired
	private ContentsService contentsService;		
	
	@Autowired
	private XbtEdcApiService xbtEdcApiService;	
	
    @Autowired
    private LoginService loginService;	
    
    @Autowired
    private FileStorageService fileStorageService;    
    
    @Autowired
    private XbtImageService xbtImageService;    
    
	//외부 얼굴등록 api
	@ResponseBody
	@PostMapping(value="/insertFaceApi.do")
	public BaseResponse<FaceVO> insertFaceApi(
			HttpServletRequest request, HttpServletResponse response
			,@RequestBody FaceVO params) throws Exception {
		
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		FaceVO fv = xbtFaceApiService.insertFaceApi(params);
		return new BaseResponse<FaceVO>(fv);		
	}    
}
