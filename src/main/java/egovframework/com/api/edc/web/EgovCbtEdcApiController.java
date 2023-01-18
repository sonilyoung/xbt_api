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
import egovframework.com.api.edc.service.EgovCbtEdcPseudoFilterService;
import egovframework.com.api.edc.service.EgovCbtEdcReinforcementService;
import egovframework.com.api.edc.service.EgovCbtEdcThreeDimensionService;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseResponse;

@Controller
public class EgovCbtEdcApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovCbtEdcApiController.class);
	
	@Autowired
	private EgovCbtEdcPseudoFilterService egovCbtEdcPseudoFilterService;
	
	@Autowired
	private EgovCbtEdcThreeDimensionService egovCbtEdcThreeDimensionService;
	
	@Autowired
	private EgovCbtEdcReinforcementService egovCbtEdcReinforcementService;
	
	@ResponseBody
	@RequestMapping(value = "/thngManagetApi.do", method = RequestMethod.POST, produces = "application/json")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public JsonNode thngManagetApi(@RequestBody final LinkedHashMap<String, Object> linkedHashMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long testTime = System.currentTimeMillis();
		JsonNode jsonNode = null;
		HashMap<String, Object> hash = new HashMap<String, Object>();//리턴 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			linkedHashMap.forEach((k, v) -> {
				try {
					if(k.equals("d")) {
						hash.put(k, egovCbtEdcThreeDimensionService.threeDimension(v));
					} else {
						hash.put(k, egovCbtEdcPseudoFilterService.pseudoFilter(v));
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
		return jsonNode;
	}
	
	@RequestMapping(value = {"/reinforcement.do"}, method = RequestMethod.POST)
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public JsonNode aiReinforcement(HttpServletRequest request
    		,@RequestBody EduBaselineProc params) throws Exception{		
		JsonNode json = null;
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Boolean> hash = new HashMap<String, Boolean>();
		boolean result = false;
		
		result = egovCbtEdcReinforcementService.reinforcementLearningAi();
		hash.put("Response", result);
		
		json = mapper.convertValue(hash, JsonNode.class);
		
		LOGGER.info(result + "");
		
		return json;
	}
	
}
