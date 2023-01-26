package egovframework.com.api.edc.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.adm.eduMgr.vo.EduBaselineProc;
import egovframework.com.api.edc.service.EgovXtsEdcPseudoFilterService;
import egovframework.com.api.edc.service.EgovXtsEdcReinforcementService;
import egovframework.com.api.edc.service.EgovXtsEdcThreeDimensionService;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseResponse;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api")
@Api(tags = "XTS external API")
public class EgovXtsEdcApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsEdcApiController.class);
	
	@Autowired
	private EgovXtsEdcPseudoFilterService egovXtsEdcPseudoFilterService;
	
	@Autowired
	private EgovXtsEdcThreeDimensionService egovXtsEdcThreeDimensionService;
	
	@Autowired
	private EgovXtsEdcReinforcementService egovXtsEdcReinforcementService;
	
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
	@RequestMapping(value = {"/reinforcement.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public  BaseResponse<JsonNode> aiReinforcement(HttpServletRequest request
    		,@RequestBody EduBaselineProc params) throws Exception{		
		JsonNode json = null;
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Boolean> hash = new HashMap<String, Boolean>();
		boolean result = false;
		
		result = egovXtsEdcReinforcementService.reinforcementLearningAi();
		hash.put("Response", result);
		
		json = mapper.convertValue(hash, JsonNode.class);
		
		LOGGER.info(result + "");
		
		return new BaseResponse<JsonNode>(json);
	}
	
	
	
	
	
}