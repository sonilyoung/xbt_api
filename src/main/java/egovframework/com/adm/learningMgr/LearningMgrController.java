
package egovframework.com.adm.learningMgr;

import java.util.ArrayList;
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
import egovframework.com.adm.learningMgr.vo.EduModulePop;
import egovframework.com.adm.learningMgr.vo.EduType;
import egovframework.com.adm.learningMgr.vo.PointStd;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
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

    //private OfficeMessageSource officeMessageSource;

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
    public BaseResponse<EduModule> selectModuleList(HttpServletRequest request
    		, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//학습목록
			params.setLearningType("l");
			List<EduModule> lList = learningMgrService.selectModuleList(params);
			params.setModuleList(lList);
			
			//평가목록
			params.setLearningType("e");
			List<EduModule> eList = learningMgrService.selectModuleList(params);
			params.setEvaluationList(eList);
			
	        return new BaseResponse<EduModule>(params);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  	
	
    /**
     * 학습관리>모듈관리 리스트 조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectModuleMgrList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "학습관리>모듈관리 리스트 조회", notes = "학습관리>모듈관리 리스트 조회.")
    public BaseResponse<List<EduModule>> selectModuleMgrList(HttpServletRequest request
    		, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<EduModule> lList = learningMgrService.selectModuleList(params);
	        return new BaseResponse<List<EduModule>>(lList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
			List<EduModule> bagResultList = learningMgrService.selectModuleQuestion(params);
			List<String> bagList = new ArrayList<String>();
			for(EduModule b : bagResultList) {
				bagList.add(b.getBagScanId());
			}
			
			resultList.setBagList(bagList);
	        return new BaseResponse<EduModule>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
    public BaseResponse<EduModule> insertModule(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getModuleNm())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "ModuleNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getModuleDesc())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "ModuleDesc" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "StudyLvl" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getSlideSpeed())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "slideSpeed" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getModuleType())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "moduleType" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "useYn" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getQuestionCnt())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "questionCnt" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getModuleDesc())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "ModuleDesc" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		
		
		try {
			//모듈등록
			params.setInsertId(login.getUserId());
			int result = learningMgrService.insertModule(params);
			
			if(result>0) {
				return new BaseResponse<EduModule>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage(), params);
			}else {
				return new BaseResponse<EduModule>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage(), params);
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
    public BaseResponse<EduModule> updateModule(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "studyLvl" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getModuleType())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "moduleType" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getSlideSpeed())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "slideSpeed" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "useYn" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getModuleNm())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "ModuleNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getModuleDesc())){				
			return new BaseResponse<EduModule>(BaseResponseCode.PARAMS_ERROR, "ModuleDesc" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//모듈등록
			params.setInsertId(login.getUserId());
			int result = learningMgrService.updateModule(params);
			
			if(result>0) {
				return new BaseResponse<EduModule>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage(), params);
			}else {
				return new BaseResponse<EduModule>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage(), params);
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
		
		if(StringUtils.isEmpty(params.getModuleIdList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleIdList" + BaseApiMessage.REQUIRED.getCode());
		}
		
		try {
			//모듈삭제
			params.setInsertId(login.getUserId());
			int result = 0;
			
			for(Long id : params.getModuleIdList()) {
				//params.setModuleId(new Long());
				params.setModuleId(Long.valueOf(id));			
				result = learningMgrService.deleteModule(params);
			}
					
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }
	
	   
    /**
     * 모듈복사
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertModuleCopy.do")
    @ApiOperation(value = "모듈복사", notes = "모듈복사.")
    public BaseResponse<Integer> insertModuleCopy(HttpServletRequest request, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getTargetModuleId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "getTargetModuleId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getModuleNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		try {
			//모듈문제삭제
			params.setInsertId(login.getUserId());
			int result = learningMgrService.insertModuleMasterCopy(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }      
    
    
    /**
     * 학습관리-xray판독모듈 랜덤추출 가져오기
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectModuleRandom.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "학습관리-xray판독모듈 랜덤추출 가져오기", notes = "학습관리-xray판독모듈 랜덤추출 가져오기")
    public BaseResponse<List<EduModulePop>> selectModuleRandom(HttpServletRequest request
    		, @RequestBody EduModulePop params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<List<EduModulePop>>(BaseResponseCode.PARAMS_ERROR, "StudyLvl" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getQuestionCnt())){				
			return new BaseResponse<List<EduModulePop>>(BaseResponseCode.PARAMS_ERROR, "QuestionCnt" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			List<EduModulePop> resultList = learningMgrService.selectModuleRandom(params);
	        return new BaseResponse<List<EduModulePop>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
    public BaseResponse<List<EduModulePop>> selectModuleXrayPopList(HttpServletRequest request
    		, @RequestBody EduModulePop params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		List<EduModulePop> resultDataList = new ArrayList<EduModulePop>();
		try {
			
			if(StringUtils.isEmpty(params.getBagLists())) {
				resultDataList = learningMgrService.selectModuleXrayPopList(params);
			}else {
				List<EduModulePop> resultList = learningMgrService.selectModuleXrayPopList(params);
				if(!StringUtils.isEmpty(params.getBagLists())){
					String[] bList = params.getBagLists().split(",");
					List<String> bl = new ArrayList<String>(); 
					for(String s : bList) {
						bl.add(s);
					}
					params.setBagList(bl);
				}
				
				for(String bl : params.getBagList()) {
					for(EduModulePop rl : resultList) {
						if(bl.equals(rl.getBagScanId())) {
							resultDataList.add(rl);
							continue;
						}
					}
				}				
			}
			
	        return new BaseResponse<List<EduModulePop>>(resultDataList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }     	
    
    
    
    /**
     * 배점기준정보마스터목록조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectPointStdList.do")
    @ApiOperation(value = "배점기준정보마스터목록", notes = "배점기준정보마스터목록조회.")
    public BaseResponse<List<PointStd>> selectPointStdList(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//배점기준정보마스터조회
	        return new BaseResponse<List<PointStd>>(learningMgrService.selectPointStdList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    /**
     * 배점기준정보마스터상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectPointStd.do")
    @ApiOperation(value = "배점기준정보마스터상세", notes = "배점기준정보마스터상세조회.")
    public BaseResponse<PointStd> selectPointStd(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsStdNo())){				
			return new BaseResponse<PointStd>(BaseResponseCode.PARAMS_ERROR, "PointsStdNo" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//배점기준정보마스터조회
	        return new BaseResponse<PointStd>(learningMgrService.selectPointStd(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    
    
    /**
     * 배점기준정보마스터등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertPointStd.do")
    @ApiOperation(value = "배점기준정보마스터", notes = "배점기준정보마스터등록.")
    public BaseResponse<Integer> insertPointStd(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsStdNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsStdNm" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getPointsStdDc())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsStdDc" + BaseApiMessage.REQUIRED.getCode());
		}
		
		try {
			//배점기준정보마스터등록
			params.setInsertId(login.getUserId());
			int result = learningMgrService.insertPointStd(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 배점기준정보마스터수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updatePointStd.do")
    @ApiOperation(value = "배점기준정보마스터", notes = "배점기준정보마스터수정.")
    public BaseResponse<Integer> updatePointStd(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsStdNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsStdNo" + BaseApiMessage.REQUIRED.getCode());
		}				

		if(StringUtils.isEmpty(params.getPointsStdNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsStdNm" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getPointsStdDc())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsStdDc" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UseYn" + BaseApiMessage.REQUIRED.getCode());
		}			
				
		
		try {
			//배점기준정보마스터등록
			params.setUpdateId(login.getUserId());
			int result = learningMgrService.updatePointStd(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 배점기준정보마스터삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deletePointStd.do")
    @ApiOperation(value = "배점기준정보마스터", notes = "배점기준정보마스터삭제.")
    public BaseResponse<Integer> deletePointStd(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsStdNoList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsStdNoList" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			int result = 0;
			for(Long id : params.getPointsStdNoList()) {
				params.setPointsStdNo(id);
				//배점기준정보마스터삭제
				result = learningMgrService.deletePointStd(params);
				//배점기준정보상세삭제
				learningMgrService.deletePointStdDetail(params);				
			}

			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
	    
    
    /**
     * 배점기준정보상세상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectPointStdDetailList.do")
    @ApiOperation(value = "배점기준정보상세목록", notes = "배점기준정보상세상세목록.")
    public BaseResponse<List<PointStd>> selectPointStdDetailList(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsStdNo())){				
			return new BaseResponse<List<PointStd>>(BaseResponseCode.PARAMS_ERROR, "PointsStdNo" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//배점기준정보상세조회
	        return new BaseResponse<List<PointStd>>(learningMgrService.selectPointStdDetailList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  
    
    
    /**
     * 배점기준정보상세목록조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectPointStdDetail.do")
    @ApiOperation(value = "배점기준정보상세", notes = "배점기준정보상세.")
    public BaseResponse<PointStd> selectPointStdDetail(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsStdNo())){				
			return new BaseResponse<PointStd>(BaseResponseCode.PARAMS_ERROR, "PointsStdNo" + BaseApiMessage.REQUIRED.getCode());
		}			

		List<PointStd> resultList = learningMgrService.selectPointStdDetailList(params);
		
		for(PointStd p : resultList) {
			if("0".equals(p.getActionDiv())) {
				params.setActionDiv0(p);
			}else if("1".equals(p.getActionDiv())) {
				params.setActionDiv1(p);
			}else if("2".equals(p.getActionDiv())) {
				params.setActionDiv2(p);
			}else if("3".equals(p.getActionDiv())) {
				params.setActionDiv3(p);
			}else if("4".equals(p.getActionDiv())) {
				params.setActionDiv4(p);
			}
		}
		
		try {
			//배점기준정보상세조회
	        return new BaseResponse<PointStd>(params);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 배점기준정보상세등록
     * 
     * @param param
     * @return Company
     
    @PostMapping("/insertPointStdDetail.do")
    @ApiOperation(value = "배점기준정보상세", notes = "배점기준정보상세등록.")
    public BaseResponse<Integer> insertPointStdDetail(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsStdNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsStdNo" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getActionDiv())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ActionDiv" + BaseApiMessage.REQUIRED.getCode());
		}					
		
		if(StringUtils.isEmpty(params.getBanUnitScore())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BanUnitScore" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLimitUnitScore())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LimitUnitScore" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getQuestionUnitScore())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "QuestionUnitScore" + BaseApiMessage.REQUIRED.getCode());
		}			
				
		if(StringUtils.isEmpty(params.getPassUnitScore())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PassUnitScore" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		
		
		try {
			//배점기준정보상세등록
			params.setInsertId(login.getUserId());
			int result = learningMgrService.insertPointStdDetail(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    */
    
    
    /**
     * 배점기준정보상세수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updatePointStdDetail.do")
    @ApiOperation(value = "배점기준정보상세수정", notes = "배점기준정보상세수정.")
    public BaseResponse<Integer> updatePointStdDetail(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		if(StringUtils.isEmpty(params.getUpdateList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UpdateList" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			int result = 0;
			//배점기준정보상세등록
			for(PointStd ps: params.getUpdateList()) {
				ps.setUpdateId(login.getUserId());
				result = learningMgrService.updatePointStdDetail(ps);				
			}
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 배점기준정보상세삭제
     * 
     * @param param
     * @return Company
     
    @PostMapping("/deletePointStdDetail.do")
    @ApiOperation(value = "배점기준정보상세", notes = "배점기준정보상세삭제.")
    public BaseResponse<Integer> deletePointStdDetail(HttpServletRequest request, @RequestBody PointStd params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPointsDetailNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PointsDetailNo" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//배점기준정보상세삭제
			int result = learningMgrService.deletePointStdDetail(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  
    */      
}
