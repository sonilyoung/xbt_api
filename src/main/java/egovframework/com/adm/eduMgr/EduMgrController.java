
package egovframework.com.adm.eduMgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.eduMgr.service.EduMgrService;
import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.eduMgr.vo.EduDate;
import egovframework.com.adm.eduMgr.vo.Student;
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
@RequestMapping("/adm/eduMgr")
@Api(tags = "Education Management API")
public class EduMgrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EduMgrController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private EduMgrService eduMgrService;
    
    
    /**
     * 차수목록조회 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectBaselineList.do")
    @ApiOperation(value = "차수목록조회", notes = "차수목록조회")
    public BaseResponse<List<Baseline>> selectBaselineList(HttpServletRequest request
    		,@RequestBody Baseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<Baseline> resultList = eduMgrService.selectBaselineList(params);
	        return new BaseResponse<List<Baseline>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
    /**
     * 차수상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectBaseline.do")
    @ApiOperation(value = "차수상세", notes = "차수상세조회.")
    public BaseResponse<Baseline> selectBaseline(HttpServletRequest request, @RequestBody Baseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<Baseline>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//차수조회
	        return new BaseResponse<Baseline>(eduMgrService.selectBaseline(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
    
    
    
    /**
     * 차수등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertBaseline.do")
    @ApiOperation(value = "차수", notes = "차수등록.")
    public BaseResponse<Integer> insertBaseline(HttpServletRequest request, @RequestBody Baseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}

		
		if(StringUtils.isEmpty(params.getProcSeq())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "procSeq" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "moduleId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getEduStartDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduStartDate" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getEduEndDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduEndDate" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		
		if(StringUtils.isEmpty(params.getTotStudyDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "totStudyDate" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "studyLvl" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		
		if(StringUtils.isEmpty(params.getLimitPersonCnt())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "limitPersonCnt" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getEndingStdScore())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "endingStdScore" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getUserIds())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userIds" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//차수등록
			params.setInsertId(login.getUserId());
			int result = eduMgrService.insertBaseline(params);
			
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
     * 차수수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateBaseline.do")
    @ApiOperation(value = "차수", notes = "차수수정.")
    public BaseResponse<Integer> updateBaseline(HttpServletRequest request, @RequestBody Baseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		

		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getProcYear())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcYear" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getProcSeq())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "procSeq" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "moduleId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getEduStartDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduStartDate" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getEduEndDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduEndDate" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		
		if(StringUtils.isEmpty(params.getTotStudyDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "totStudyDate" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		
		if(StringUtils.isEmpty(params.getLimitPersonCnt())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "limitPersonCnt" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getEndingStdScore())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "endingStdScore" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getEndingProcessEndYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "endingProcessEndYn" + BaseApiMessage.REQUIRED.getCode());
		}		
				
				
		
		try {
			//차수등록
			params.setUpdateId(login.getUserId());
			int result = eduMgrService.updateBaseline(params);
			
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
     * 차수삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteBaseline.do")
    @ApiOperation(value = "차수", notes = "차수삭제.")
    public BaseResponse<Integer> deleteBaseline(HttpServletRequest request, @RequestBody Baseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//차수삭제
			int result = eduMgrService.deleteBaseline(params);
			
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
     * 교육생조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectBaselineStudentList.do")
    @ApiOperation(value = "교육생", notes = "교육생목록조회.")
    public BaseResponse<List<Student>> selectBaselineStudentList(HttpServletRequest request, @RequestBody Student params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//교육생조회
	        return new BaseResponse<List<Student>>(eduMgrService.selectBaselineStudentList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
  
    
    
    /**
     * 교육생등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertBaselineStudent.do")
    @ApiOperation(value = "교육생", notes = "교육생등록.")
    public BaseResponse<Integer> insertBaselineStudent(HttpServletRequest request, @RequestBody Student params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getProcYear())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcYear" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getProcSeq())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcSeq" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getProcNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcNm" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getCompNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CompNm" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getDeptCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "DeptCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getDeptNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "DeptNm" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//교육생등록
			params.setInsertId(login.getUserId());
			int result = eduMgrService.insertBaselineStudent(params);
			
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
     * 교육생삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteBaselineStudent.do")
    @ApiOperation(value = "교육생", notes = "교육생삭제.")
    public BaseResponse<Integer> deleteBaselineStudent(HttpServletRequest request, @RequestBody Student params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//교육생삭제
			int result = eduMgrService.deleteBaselineStudent(params);
			
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
     * 교육일정조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectEduDateList.do")
    @ApiOperation(value = "교육일정", notes = "교육일정목록조회.")
    public BaseResponse<List<EduDate>> selectEduDateList(HttpServletRequest request, @RequestBody EduDate params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//교육일정조회
	        return new BaseResponse<List<EduDate>>(eduMgrService.selectEduDateList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
  
    
    
    /**
     * 교육일정등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertEduDate.do")
    @ApiOperation(value = "교육일정", notes = "교육일정등록.")
    public BaseResponse<Integer> insertEduDate(HttpServletRequest request, @RequestBody EduDate params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getProcNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ProcNm" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}			
				
		if(StringUtils.isEmpty(params.getEduDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "EduDate" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		try {
			//교육일정등록
			params.setInsertId(login.getUserId());
			int result = eduMgrService.insertEduDate(params);
			
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
     * 교육일정삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteEduDate.do")
    @ApiOperation(value = "교육일정", notes = "교육일정삭제.")
    public BaseResponse<Integer> deleteEduDate(HttpServletRequest request, @RequestBody EduDate params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getDayNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "DayNo" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//교육일정삭제
			int result = eduMgrService.deleteEduDate(params);
			
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
}
