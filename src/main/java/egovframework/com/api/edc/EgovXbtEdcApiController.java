package egovframework.com.api.edc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.api.edc.service.EgovXtsEdcApiService;
import egovframework.com.api.edc.service.EgovXtsEdcPseudoFilterService;
import egovframework.com.api.edc.service.EgovXtsEdcReinforcementService;
import egovframework.com.api.edc.service.EgovXtsEdcThreeDimensionService;
import egovframework.com.api.edc.service.SudoImgService;
import egovframework.com.api.edc.vo.AiForceLearning;
import egovframework.com.api.edc.vo.AiForceLearningResult;
import egovframework.com.api.edc.vo.AiForceUserScore;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.edc.vo.UnitImages;
import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.common.vo.SeqGroupCode;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api")
@Api(tags = "XTS external API")
public class EgovXbtEdcApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXbtEdcApiController.class);
	
	@Autowired
	private EgovXtsEdcPseudoFilterService egovXtsEdcPseudoFilterService;
	
	@Autowired
	private EgovXtsEdcThreeDimensionService egovXtsEdcThreeDimensionService;
	
	@Autowired
	private EgovXtsEdcReinforcementService egovXtsEdcReinforcementService;
	
	@Autowired
	private EgovXtsEdcApiService egovXtsEdcApiService;
	
	@Autowired
	private ApiLoginService apiLoginService;
	
	@Autowired
	private ContentsService contentsService;		
	
	@Autowired
	private SudoImgService sudoImgService;	
	
    @Autowired
    private LoginService loginService;	
    
    @Autowired
    private FileStorageService fileStorageService;    

    
	//kaist 진행률가져오기
	@ResponseBody
	@PostMapping(value="/selectProgressPer.do")
	public BaseResponse<ApiLog> selectProgressPer(
			HttpServletRequest request, HttpServletResponse response
			,@RequestBody ApiLog params) throws Exception {
		
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getSeqId())){				
			return new BaseResponse<ApiLog>(BaseResponseCode.PARAMS_ERROR, "SeqId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		ApiLog ai = sudoImgService.selectProgressPer(params);
		return new BaseResponse<ApiLog>(ai);		
	}		
	
	//kaist 슈도이미지업로드실행 
	@ResponseBody
	@PostMapping(value="/sudoImgExcute.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<JsonNode> sudoImgExcute(
			HttpServletRequest request, HttpServletResponse response
			,@RequestPart(value = "frontImg", required = true) MultipartFile frontImg
			,@RequestPart(value = "sideImg", required = true) MultipartFile sideImg
			,@RequestPart(value = "params", required = true )XrayImgContents params) throws Exception {
		
		ApiLogin login = apiLoginService.createToken(request);
		
		XbtSeq seq = new XbtSeq();
		seq.setSeqInfo(SeqGroupCode.XBT_BAG_ID.getCode());
		XbtSeq unitId = contentsService.selectXbtSeq(seq);
		params.setBagScanId(unitId.getSeqId());

		//정면이미지처리
		JsonNode resultData = sudoImgService.sudoImgExcute(params, login, frontImg, sideImg);
		return new BaseResponse<JsonNode>(resultData);		
	}		
	
	//kaist 슈도이미지 생성수행실행
	
	//kaist 슈도이미지 가져오기 
    /**
     * 엘폴 api
     * 
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectSudoImg.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public  BaseResponse<JsonNode> selectSudoImg(HttpServletRequest request
    		,@RequestBody LearningImg params) throws Exception{		
			ApiLogin login = apiLoginService.createToken(request);
			LOGGER.info("params : " + params);
			
			//JsonNode json = null;
			//ObjectMapper mapper = new ObjectMapper();
			
			if(StringUtils.isEmpty(params.getBagScanId())){
				return new BaseResponse<JsonNode>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
			}				
			
			try {
				//LOGGER.info("=========정면이미지 가져오기");
				//if(!StringUtils.isEmpty(params.getImgFront())){
					//fileStorageService.ByteToFile(params.getImgFront(), params.getImgFrontName());
				//}
				
				//LOGGER.info("=========측면이미지 가져오기");
				//if(!StringUtils.isEmpty(params.getImgSide())){
					//fileStorageService.ByteToFile(params.getImgSide(), params.getImgSideName());
				//}		
				
				JsonNode resultData = sudoImgService.selectSudoImg(params, login);
				
				return new BaseResponse<JsonNode>(resultData);
				
				//json = mapper.convertValue(result, JsonNode.class);
				//return new BaseResponse<JsonNode>(json);			
			}catch(Exception e) {
				e.printStackTrace();
				return new BaseResponse<JsonNode>(BaseResponseCode.UPLOAD_FAIL, BaseResponseCode.UPLOAD_FAIL.getMessage());
				//json = mapper.convertValue(result, JsonNode.class);
				//return new BaseResponse<JsonNode>(json);			
			}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = {"/thngManagetApi.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<JsonNode> thngManagetApi(@RequestBody final LinkedHashMap<String, Object> linkedHashMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long testTime = System.currentTimeMillis();
		JsonNode jsonNode = null;
		HashMap<String, Object> hash = new HashMap<String, Object>();//리턴 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			linkedHashMap.forEach((k, v) -> {
				try {
					if(k.equals("d")) {
						hash.put(k, egovXtsEdcThreeDimensionService.threeDimension(v));
					} else {
						hash.put(k, egovXtsEdcPseudoFilterService.pseudoFilter(v));
					}
				} catch(Exception e) {
					e.printStackTrace();
					LOGGER.error(e.toString());
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
			hash.put("error", e.getMessage());
		} finally {
			jsonNode = mapper.convertValue(hash, JsonNode.class);
		}
		LOGGER.info("End Current Time : " + (System.currentTimeMillis() - testTime ) + "ms");
		return new BaseResponse<JsonNode>(jsonNode);
	}
	
	
    /**
     * 강화학습 api
     * 
     * @param param
     * @return Company
    */
	@ResponseBody
	@RequestMapping(value = {"/selectLearningList.do"}, method = RequestMethod.GET)
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public  BaseResponse<JsonNode> selectLearningList(HttpServletRequest request
    		,AiForceLearning params) throws Exception{		
		JsonNode json = null;
		ObjectMapper mapper = new ObjectMapper();
		List<AiForceLearning> result = egovXtsEdcReinforcementService.selectLearningList(params);
		json = mapper.convertValue(result, JsonNode.class);
		
		//LOGGER.info(result + "");
		return new BaseResponse<JsonNode>(json);
	} 	
	
	
	   /**
     * 강화학습 api
     * 
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectLearningResultList.do"}, method = RequestMethod.GET)
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public  BaseResponse<JsonNode> selectLearningResultList(HttpServletRequest request
    		,AiForceLearningResult params) throws Exception{		
		JsonNode json = null;
		ObjectMapper mapper = new ObjectMapper();
		List<AiForceLearningResult> result = egovXtsEdcReinforcementService.selectLearningResultList(params);
		json = mapper.convertValue(result, JsonNode.class);
		
		//LOGGER.info(result + "");
		return new BaseResponse<JsonNode>(json);
	}	
	
	
	   /**
  * 강화학습 api
  * 
  * @param param
  * @return Company
  */	
	@ResponseBody
	@RequestMapping(value = {"/selectUserScoreResultList.do"}, method = RequestMethod.GET)
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public  BaseResponse<JsonNode> selectUserScoreResultList(HttpServletRequest request
 		,AiForceUserScore params) throws Exception{		
		JsonNode json = null;
		ObjectMapper mapper = new ObjectMapper();
		List<AiForceUserScore> result = egovXtsEdcReinforcementService.selectUserScoreResultList(params);
		json = mapper.convertValue(result, JsonNode.class);
		
		//LOGGER.info(result + "");
		return new BaseResponse<JsonNode>(json);
	}	
	
	
	
    /**
     * 엘폴 api
     * 
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectEmpUnitImage.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public  BaseResponse<JsonNode> selectEmpUnitImage(HttpServletRequest request
    		,@RequestBody UnitImages params) throws Exception{		
		JsonNode json = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = egovXtsEdcApiService.selectEmpUnitImage(params);
		json = mapper.convertValue(result, JsonNode.class);
		return new BaseResponse<JsonNode>(json);
	}
		
	
	
	
	
}
