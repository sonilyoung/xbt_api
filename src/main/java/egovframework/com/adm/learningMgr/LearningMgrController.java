
package egovframework.com.adm.learningMgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.learningMgr.service.LearningMgrService;
import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 컨텐츠 컨트롤러 클래스
 * 
 * @author iyson
 * @since 2022.12.28
 * @version 1.0
 * @see
 *
 */
@RestController
@RequestMapping("/adm/learningMgr")
@Api(tags = "learningMgr Management API")
public class LearningMgrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LearningMgrController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private LearningMgrService learningMgrService;
    
	
	
    /**
     * 학습관리-xray판독모듈목록
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectModuleList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "학습관리-xray판독모듈목록", notes = "학습관리-xray판독모듈목록 조회한다.")
    public BaseResponse<List<EduModule>> selectModuleList(HttpServletRequest request
    		, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<EduModule> resultList = learningMgrService.selectModuleList(params);
	        return new BaseResponse<List<EduModule>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  	
	
	
    /**
     * 학습관리-xray판독모듈상세
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectModule.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "학습관리-xray판독모듈구성", notes = "학습관리-xray판독모듈구성 조회한다.")
    public BaseResponse<EduModule> selectModule(HttpServletRequest request
    		, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			EduModule resultList = learningMgrService.selectModule(params);
	        return new BaseResponse<EduModule>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  	
		
    
    /**
     * 모듈등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertModule.do")
    @ApiOperation(value = "모듈", notes = "모듈등록.")
    public BaseResponse<Integer> insertModule(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagList" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getModuleNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getModuleDesc())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleDesc" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//모듈등록
			params.setInsertId(login.getUserId());
			int result = learningMgrService.insertModule(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    /**
     * 모듈수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateModule.do")
    @ApiOperation(value = "모듈수정", notes = "모듈수정.")
    public BaseResponse<Integer> updateModule(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "studyLvl" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getModuleType())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "moduleType" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getSlideSpeed())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "slideSpeed" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "useYn" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getModuleNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getModuleDesc())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleDesc" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//모듈등록
			params.setInsertId(login.getUserId());
			int result = learningMgrService.updateModule(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }       
    
    
    /**
     * 모듈삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteModule.do")
    @ApiOperation(value = "모듈삭제", notes = "모듈삭제.")
    public BaseResponse<Integer> deleteModule(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		try {
			//모듈삭제
			params.setInsertId(login.getUserId());
			int result = learningMgrService.deleteModule(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    /**
     * 모듈문제삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteModuleQuestion.do")
    @ApiOperation(value = "모듈문제삭제", notes = "모듈문제삭제.")
    public BaseResponse<Integer> deleteModuleQuestion(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getModuleDetailId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleDetailId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		try {
			//모듈문제삭제
			params.setInsertId(login.getUserId());
			int result = learningMgrService.deleteModuleQuestion(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }      
    	
	
	
    /**
     * 학습관리-xray판독모듈 문제목록 가져오기
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectModuleQuestion.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "학습관리-xray판독모듈 문제목록 가져오기", notes = "학습관리-xray판독모듈 문제목록 가져오기")
    public BaseResponse<List<EduModule>> selectModuleQuestion(HttpServletRequest request
    		, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<List<EduModule>>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			List<EduModule> resultList = learningMgrService.selectModuleQuestion(params);
	        return new BaseResponse<List<EduModule>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }     
    
	
	
    /**
     * 학습관리-xray판독모듈의 등록할 문제가져오기
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectModuleXrayPopList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "학습관리-xray판독모듈의 등록할 문제가져오기", notes = "학습관리-xray판독모듈의 등록할 문제가져오기")
    public BaseResponse<List<EduModule>> selectModuleXrayPopList(HttpServletRequest request
    		, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<EduModule> resultList = learningMgrService.selectModuleXrayPopList(params);
	        return new BaseResponse<List<EduModule>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }     
    
    
		
    
	
		
    
    /**
     * 모듈문제등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertModuleQuestion.do")
    @ApiOperation(value = "모듈문제등록", notes = "모듈문제등록.")
    public BaseResponse<Integer> insertModuleQuestion(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "StudyLvl" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		try {
			//모듈등록
			params.setInsertId(login.getUserId());
			int result = learningMgrService.insertModuleQuestion(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    	
	
	
    /**
     * XRAY 판독 배점관리 조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/getXrayPointList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "XRAY 판독 배점관리", notes = "XRAY 판독 배점관리 조회")
    public BaseResponse<List<XrayPoint>> getXrayPointList(HttpServletRequest request
    		, @RequestBody XrayPoint params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<XrayPoint> resultList = learningMgrService.getXrayPointList(params);
	        return new BaseResponse<List<XrayPoint>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  		
	
	
	
    /**
     * XRAY 판독 배점관리 하위 조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/getXrayPointDetailList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "XRAY 판독 배점관리 하위", notes = "XRAY 판독 배점관리 하위 조회")
    public BaseResponse<List<XrayPointDetail>> getXrayPointDetailList(HttpServletRequest request
    		, @RequestBody XrayPointDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<XrayPointDetail> resultList = learningMgrService.getXrayPointDetailList(params);
	        return new BaseResponse<List<XrayPointDetail>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  		
	
    /**
     * 교육타입관리 조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduTypeList.do")
    @ApiOperation(value = "교육타입관리 조회", notes = "교육타입관리 조회")
    public BaseResponse<List<EduType>> getEduTypeList(HttpServletRequest request
    		,@RequestBody EduType params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<EduType> resultList = learningMgrService.getEduTypeList(params);
	        return new BaseResponse<List<EduType>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }     	
	    
}
