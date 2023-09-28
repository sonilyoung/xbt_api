package egovframework.com.api.edc;

import java.io.File;
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
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.api.edc.service.EgovXtsEdcApiService;
import egovframework.com.api.edc.service.EgovXtsEdcPseudoFilterService;
import egovframework.com.api.edc.service.EgovXtsEdcReinforcementService;
import egovframework.com.api.edc.service.EgovXtsEdcThreeDimensionService;
import egovframework.com.api.edc.service.XbtEdcApiService;
import egovframework.com.api.edc.vo.AiForceLearning;
import egovframework.com.api.edc.vo.AiForceLearningResult;
import egovframework.com.api.edc.vo.AiForceUserScore;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.TowdGeneration;
import egovframework.com.api.edc.vo.UnitImages;
import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.common.vo.SeqGroupCode;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api")
@Api(tags = "XTS external API")
public class XbtEdcApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XbtEdcApiController.class);
	
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
	private XbtEdcApiService xbtEdcApiService;	
	
    @Autowired
    private LoginService loginService;	
    
    @Autowired
    private FileStorageService fileStorageService;    
    
    @Autowired
    private XbtImageService xbtImageService;    

	//kaist 진행률가져오기
	@ResponseBody
	@PostMapping(value="/selectProgressPer.do")
	public BaseResponse<ApiLog> selectProgressPer(
			HttpServletRequest request, HttpServletResponse response
			,@RequestBody ApiLog params) throws Exception {
		
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		if(StringUtils.isEmpty(params.getSeqId())){				
			return new BaseResponse<ApiLog>(BaseResponseCode.PARAMS_ERROR, "SeqId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		ApiLog ai = xbtEdcApiService.selectProgressPer(params);
		return new BaseResponse<ApiLog>(ai);		
	}		
	
	//kaist 슈도이미지업로드실행 
	@ResponseBody
	@PostMapping(value="/sudoImgExcute.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<LearningImg> sudoImgExcute(
			HttpServletRequest request, HttpServletResponse response
			,@RequestPart(value = "frontImg", required = true) MultipartFile frontImg
			,@RequestPart(value = "sideImg", required = true) MultipartFile sideImg
			,@RequestPart(value = "params", required = true )LearningImg params) throws Exception {
		
		ApiLogin login = apiLoginService.createToken(request);
		
		XbtSeq seq = new XbtSeq();
		seq.setSeqInfo(SeqGroupCode.XBT_BAG_ID.getCode());
		XbtSeq unitId = contentsService.selectXbtSeq(seq);	
		params.setBagScanId(unitId.getSeqId());

		//슈도이미지업로드
		JsonNode result1 = xbtEdcApiService.sudoImgExcute(params, login, frontImg, sideImg);
		
		//카이스트명령어
		String[] sudoImgCmd = {
				"cd /home/jun/project/gwansae-unified/color2multi/color2multi_v1/script ; "
				+ "python ../test_PC_multi.py --dataroot /home/jun/project/gwansae-unified/color2multi --input_folder test_images --dataset_mode baggage_multi --name Pix2PixBaggageMulti_230418 --model pix2pix_baggage_multi --gpu_ids 0 --ngf 64 --ndf 16 --batchSize 1 --input_nc 3 --output_nc 27 --which_epoch latest "
				//+ "touch /home/jun/project/gwansae-unified/color2multi/color/fileY.txt"		
		};
		params.setKaistCommand(sudoImgCmd);
		
		LOGGER.info("====================================================");
		LOGGER.info("sudoImgExcute command : " + sudoImgCmd[0]);
		LOGGER.info("====================================================");
		
		JsonNode result2 = xbtEdcApiService.commandExcute(params, login);
		
		LOGGER.info("정면이미지처리 수행결과:" + result1);
		LOGGER.info("카이스트명령어 수행결과:" + result2);
		
		if("0000".equals(result2.get("RET_CODE").asText())) {
			return new BaseResponse<LearningImg>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), params);
		}else {
			return new BaseResponse<LearningImg>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage(), params);
		}	
	}		
	
    /**
     * 카이스트 xray콘텐츠관리-정보관리
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectKaistXrayContentsList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "Kaist xray콘텐츠관리-정보관리", notes = "Kaist xray콘텐츠관리-정보관리 조회한다.")
    public BaseResponse<List<XrayContents>> selectKaistXrayContentsList(HttpServletRequest request
    		, @RequestBody XrayContents params) {
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		try {
			List<XrayContents> resultList = xbtEdcApiService.selectKaistXrayContentsList(params);
			
	        return new BaseResponse<List<XrayContents>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  	
	
    /**
     * 카이스트xray콘텐츠관리-정보관리상세
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectKaistXrayImgContents.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "Kaistxray콘텐츠관리-정보관리상세", notes = "Kaistxray콘텐츠관리-정보관리상세.")    
    public BaseResponse<LearningImg> selectKaistXrayImgContents(HttpServletRequest request, @RequestBody LearningImg params) {
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<LearningImg>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//xray콘텐츠관리-정보관리조회
			//XrayImgContents result = contentsService.selectXrayImgContents(params);
			
			LearningImg li = new LearningImg();
			li.setBagScanId(params.getBagScanId());
			LearningImg resultImg = xbtImageService.selectAdmAllBagImg(li, "kaist");
	        return new BaseResponse<LearningImg>(resultImg);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   	
	
	
	@ResponseBody
	@PostMapping(value="/syncImages.do")
	public BaseResponse<Integer> syncImages(
			HttpServletRequest request, HttpServletResponse response, @RequestBody LearningImg params) throws Exception {
		
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		try {
			fileStorageService.fileCopy();
			return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<Integer>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}		
	}
	
	
	/*
	 * kaist 슈도이미지 명렁어실행 테스트용
	 * */
	@ResponseBody
	@RequestMapping(value = {"/commandExcuteTest.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> commandExcuteTest(
			HttpServletRequest request, HttpServletResponse response
			,@RequestBody LearningImg command) throws Exception {
		ApiLogin login = apiLoginService.createToken(request);
		
		JsonNode result = xbtEdcApiService.commandExcute(command, login);
		if("0000".equals(result.get("RET_CODE").asText())) {
			return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
		}else {
			return new BaseResponse<Integer>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}
		
	}		
	
	
	/*
	 * kaist 슈도이미지 명렁어실행
	 * */
	@ResponseBody
	@RequestMapping(value = {"/commandExcute.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> commandExcute(
			HttpServletRequest request, HttpServletResponse response
			,@RequestBody LearningImg command) throws Exception {
		ApiLogin login = apiLoginService.createToken(request);
		
		JsonNode result = xbtEdcApiService.commandExcute(command, login);
		if("0000".equals(result.get("RET_CODE").asText())) {
			return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
		}else {
			return new BaseResponse<Integer>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}
	}		
	
	//kaist 슈도이미지 가져오기 
    /**
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
				//JsonNode resultData = sudoImgService.selectSudoImg(params, login);
				JsonNode json = xbtEdcApiService.selectSudoImg(params, login);
				
				if("0000".equals(json.get("RET_CODE").asText()) && json.get("RET_DATA")!=null) {
					XrayContents xc = new XrayContents();
					xc.setBagScanId(params.getBagScanId());
					xc.setStudyLvl("1");
					xc.setInsertId(login.getLoginId()); 
					xbtEdcApiService.insertKaistXrayContents(xc);
					LOGGER.info("슈도이미지가져오기 수행결과:" + json);							
					return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_SUCCESS, BaseResponseCode.GET_IMAGE_SUCCESS.getMessage());
				}else {
					return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_FAIL, BaseResponseCode.GET_IMAGE_FAIL.getMessage());
				}
				
				//json = mapper.convertValue(result, JsonNode.class);
				//return new BaseResponse<JsonNode>(json);			
			}catch(Exception e) {
				e.printStackTrace();
				return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_FAIL, BaseResponseCode.GET_IMAGE_FAIL.getMessage());
				//json = mapper.convertValue(result, JsonNode.class);
				//return new BaseResponse<JsonNode>(json);			
			}
	}
	
	
	
	//KAIST API 2d생성 커맨드실행
	@ResponseBody
	@RequestMapping(value = {"/twodGeneration.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<JsonNode> twodGeneration(
			HttpServletRequest request, HttpServletResponse response
			,@RequestBody TowdGeneration params) throws Exception {
		
		ApiLogin login = apiLoginService.createToken(request);
		
		if(StringUtils.isEmpty(params.getCategory())){
			return new BaseResponse<JsonNode>(BaseResponseCode.PARAMS_ERROR, "Category" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		if(StringUtils.isEmpty(params.getCategoryCnt())){
			return new BaseResponse<JsonNode>(BaseResponseCode.PARAMS_ERROR, "CategoryCnt" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		if(StringUtils.isEmpty(params.getFileName())){
			return new BaseResponse<JsonNode>(BaseResponseCode.PARAMS_ERROR, "FileName" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		//KAIST 카테고리정보
		/*
		 *  Aerosol
			Alcohol
			Axe
			Bat
			Battery
			Bullet
			Chisel
			'Electronic cigarettes'
			'Electronic cigarettes(Liquid)'
			Firecracker
			Gun
			GunParts
			Hammer
			HandCuffs
			HDD
			Knife
			Laptop
			Lighter
			Liquid
			Match
			MetalPipe
			NailClippers
			Plier
			PrtableGas
			Saw
			Scissors
			Screwdriver
			SmartPhone
			SolidFuel
			Spanner
			SSD
			'stun gun'
			SupplymentaryBattery
			TabletPC
			Thinner
			'Throwing Knife'
			USB
			ZippoOil
		 */

		//카이스트명령어
		String twodGenCd = "cd /home/jun/project/gwansae-unified/2d_generation";
		String twodGenExe = "GEN_CATEGORY="+params.getCategory()+" GEN_COUNT="+params.getCategoryCnt()+" GEN_PATH=../output_generated_image RESULT_PATH=../output_final_image ./demo_gen_only.sh";  
		String[] kaistCmd = {twodGenCd+ ";" +twodGenExe};
		
		params.setKaistCommand(kaistCmd);
		
		LOGGER.info("====================================================");
		LOGGER.info("twodGeneration command : " + kaistCmd[0]);
		LOGGER.info("====================================================");		
		
		JsonNode result = xbtEdcApiService.commandTwodGenExcute(params, login);
		
		//xbtEdcApiService.selectTwodImg(params, login);
		
		LOGGER.info("2dGeneration 수행결과:" + result);
		
		if("0000".equals(result.get("RET_CODE").asText())) {
			return new BaseResponse<JsonNode>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage(), result.get("RET_DATA"));
		}else {
			return new BaseResponse<JsonNode>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage(), result.get("RET_DATA"));
		}	
	}		
	
	//2d이미지 가져오기 
    /**
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectTwodImg.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public  BaseResponse<JsonNode> selectTwodImg(HttpServletRequest request
    		,@RequestBody TowdGeneration params) throws Exception{		
			ApiLogin login = apiLoginService.createToken(request);
			LOGGER.info("params : " + params);
			
			//JsonNode json = null;
			//ObjectMapper mapper = new ObjectMapper();
			
			if(StringUtils.isEmpty(params.getFileName())){
				return new BaseResponse<JsonNode>(BaseResponseCode.PARAMS_ERROR, "FileName" + BaseApiMessage.REQUIRED.getMessage());
			}				
			
			try {
				//JsonNode resultData = sudoImgService.selectSudoImg(params, login);
				JsonNode json = xbtEdcApiService.selectTwodImg(params, login);
				if("0000".equals(json.get("RET_CODE").asText()) && json.get("RET_DATA")!=null) {
					LOGGER.info("2d이미지 가져오기 수행결과:" + json);							
					return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_SUCCESS, BaseResponseCode.GET_IMAGE_SUCCESS.getMessage());
				}else {
					return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_FAIL, BaseResponseCode.GET_IMAGE_FAIL.getMessage());
				}
				
				//json = mapper.convertValue(result, JsonNode.class);
				//return new BaseResponse<JsonNode>(json);			
			}catch(Exception e) {
				e.printStackTrace();
				return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_FAIL, BaseResponseCode.GET_IMAGE_FAIL.getMessage());
				//json = mapper.convertValue(result, JsonNode.class);
				//return new BaseResponse<JsonNode>(json);			
			}
	}	
	
	
    /**
     * 카이스트 2D합성이미지상세
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectKaistTwodGeneration.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "Kaist 2D합성이미지상세", notes = "Kaist 2D합성이미지상세")    
    public BaseResponse<TowdGeneration> selectKaistTwodGeneration(HttpServletRequest request, @RequestBody TowdGeneration params) {
    	//Login login = loginService.getLoginInfo(request);
		//if (login == null) {
			//throw new BaseException(BaseResponseCode.AUTH_FAIL);
		//}
		
		if(StringUtils.isEmpty(params.getFileName())){				
			return new BaseResponse<TowdGeneration>(BaseResponseCode.PARAMS_ERROR, "FileName" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			TowdGeneration resultImg = xbtImageService.selectKaistTwodImg(params);
	        return new BaseResponse<TowdGeneration>(resultImg);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   	
	
	
	
	
	
	//kaist threed생성
	@ResponseBody
	@PostMapping(value="/threedImgExcute.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<JsonNode> threedImgExcute(
			HttpServletRequest request, HttpServletResponse response
			,@RequestPart(value = "frontImg", required = true) MultipartFile frontImg
			,@RequestPart(value = "sideImg", required = true) MultipartFile sideImg
			,@RequestPart(value = "params", required = true )ThreedGeneration params) throws Exception {
		
		ApiLogin login = apiLoginService.createToken(request);
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<JsonNode>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getCode());
		}			
				
		LOGGER.info("kaist threed생성:"+ params);
		
		//3d이미지전송
		Map<String, Object> result = xbtEdcApiService.threedImgExcute(params, login, frontImg, sideImg);
		
		AttachFile af1 = (AttachFile)result.get("frontImg");
		AttachFile af2 = (AttachFile)result.get("sideImg");
		
		//카이스트명령어
		LOGGER.info("3d이미지전송 카이스트명령어 시작");
		String kaistThreedUploadPath = "/home/jun/project/gwansae-unified/3d_generation/demo_data/raw/"+params.getUnitId();
		String[] threedImgCmd = {
			"cd /home/jun/project/gwansae-unified/3d_generation ;" +
			"rm -r workdir ;" +
			"DISPLAY= python main.py " +
			//kaistThreedUploadPath+ params.getUnitId() + File.separator + af1.getSaveFileName()+ " " +
			//kaistThreedUploadPath+ params.getUnitId() + File.separator + af2.getSaveFileName()+ " " +
			//kaistThreedUploadPath+ params.getUnitId() + File.separator + af1.getSaveFileName()+ " " +
			//kaistThreedUploadPath+ params.getUnitId() + File.separator + af2.getSaveFileName()+ " " +
			kaistThreedUploadPath+ File.separator + af1.getSaveFileName()+ " " +
			kaistThreedUploadPath+ File.separator + af2.getSaveFileName()+ " " +
			kaistThreedUploadPath+ File.separator + af1.getSaveFileName()+ " " +
			kaistThreedUploadPath+ File.separator + af2.getSaveFileName()+ " " +
			"workdir"
		};
		params.setKaistCommand(threedImgCmd);
		
		LOGGER.info("====================================================");
		LOGGER.info("threedImgExcute command : " + threedImgCmd[0]);
		LOGGER.info("====================================================");			
		
		JsonNode result2 = xbtEdcApiService.commandThreedGenExcute(params, login);
		
		if("0000".equals(result2.get("RET_CODE").asText())) {
			return new BaseResponse<JsonNode>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
		}else {
			return new BaseResponse<JsonNode>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
		}	
	}	
	
	
	//kaist 3d이미지 기져오기 
    /**
     * @param param
     * @return Company
     */	
	@ResponseBody
	@RequestMapping(value = {"/selectThreedImg.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public  BaseResponse<JsonNode> selectSudoImg(HttpServletRequest request
    		,@RequestBody ThreedGeneration params) throws Exception{		
			ApiLogin login = apiLoginService.createToken(request);
			LOGGER.info("params : " + params);
			
			//JsonNode json = null;
			//ObjectMapper mapper = new ObjectMapper();
			
			if(StringUtils.isEmpty(params.getUnitId())){
				return new BaseResponse<JsonNode>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getMessage());
			}				
			
			try {
				JsonNode json = xbtEdcApiService.selectThreedImg(params, login);
				
				if("0000".equals(json.get("RET_CODE").asText()) && json.get("RET_DATA")!=null) {
					return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_SUCCESS, BaseResponseCode.GET_IMAGE_SUCCESS.getMessage());
				}else {
					return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_FAIL, BaseResponseCode.GET_IMAGE_FAIL.getMessage());
				}
				
				//json = mapper.convertValue(result, JsonNode.class);
				//return new BaseResponse<JsonNode>(json);			
			}catch(Exception e) {
				e.printStackTrace();
				return new BaseResponse<JsonNode>(BaseResponseCode.GET_IMAGE_FAIL, BaseResponseCode.GET_IMAGE_FAIL.getMessage());
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
