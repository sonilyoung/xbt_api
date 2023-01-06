
package egovframework.com.adm.eduMgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.eduMgr.service.EduMgrService;
import egovframework.com.adm.eduMgr.vo.EduBaselineDetailProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineMenu;
import egovframework.com.adm.eduMgr.vo.EduBaselineProc;
import egovframework.com.adm.eduMgr.vo.EduBaselineSubProc;
import egovframework.com.adm.eduMgr.vo.EduClass;
import egovframework.com.adm.eduMgr.vo.EduGroupMgr;
import egovframework.com.adm.eduMgr.vo.EduProc;
import egovframework.com.adm.eduMgr.vo.EduProcDetail;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.global.OfficeMessageSource;
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
     * 교육과정관리 - 그룹관리 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduGroupList.do")
    @ApiOperation(value = "교육과정관리-그룹관리", notes = "교육과정관리-그룹관리를 조회한다.")
    public BaseResponse<List<EduGroupMgr>> getEduGroupList(HttpServletRequest request
    		,@RequestBody EduGroupMgr params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getSearchType() == null || "".equals(params.getSearchType())){				
			return new BaseResponse<List<EduGroupMgr>>(BaseResponseCode.PARAMS_ERROR, "searchType는 필수값입니다");	
		}			
		
		try {
			List<EduGroupMgr> resultList = eduMgrService.getEduGroupList(params);
	        return new BaseResponse<List<EduGroupMgr>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
	  
    
    
    /**
     * 교육과정관리 - 과정분류
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduClassList.do")
    @ApiOperation(value = "교육과정관리-과정분류", notes = "교육과정관리-과정분류를 조회한다.")
    public BaseResponse<List<EduClass>> getEduClassList(HttpServletRequest request
    		,@RequestBody EduClass params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<EduClass> resultList = eduMgrService.getEduClassList(params);
	        return new BaseResponse<List<EduClass>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }        
    
    /**
     * 교육과정관리-과정등록 조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduProcList.do")
    @ApiOperation(value = "교육과정관리-과정등록", notes = "교육과정관리-과정등록 조회")
    public BaseResponse<List<EduProc>> getEduProcList(HttpServletRequest request
    		,@RequestBody EduProc params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<EduProc> resultList = eduMgrService.getEduProcList(params);
	        return new BaseResponse<List<EduProc>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    /**
     * 교육과정관리-과정등록 상세조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduProcDetail.do")
    @ApiOperation(value = "교육과정관리-과정등록 상세", notes = "교육과정관리-과정등록 상세조회")
    public BaseResponse<EduProcDetail> getEduProcDetail(HttpServletRequest request
    		,@RequestBody EduProcDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getProcCd() ==null){				
			return new BaseResponse<EduProcDetail>(BaseResponseCode.PARAMS_ERROR, "procCd는 필수값입니다");	
		}			
		
		try {
			EduProcDetail resultList = eduMgrService.getEduProcDetail(params);
	        return new BaseResponse<EduProcDetail>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }        
    
    
    /**
     * 교육차수관리-차수등록조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduBaselineList.do")
    @ApiOperation(value = "교육차수관리-차수등록", notes = "교육차수관리-차수등록조회")
    public BaseResponse<List<EduBaselineProc>> getEduBaselineList(HttpServletRequest request
    		,@RequestBody EduBaselineProc params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<EduBaselineProc> resultList = eduMgrService.getEduBaselineList(params);
	        return new BaseResponse<List<EduBaselineProc>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }   
    
    
    /**
     * 교육차수관리-차수등록상세조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduBaselineDetail.do")
    @ApiOperation(value = "교육차수관리-차수등록상세", notes = "교육차수관리-차수등록상세조회")
    public BaseResponse<EduBaselineDetailProc> getEduBaselineDetail(HttpServletRequest request
    		,@RequestBody EduBaselineDetailProc params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getProcCd() ==null){				
			return new BaseResponse<EduBaselineDetailProc>(BaseResponseCode.PARAMS_ERROR, "procCd는 필수값입니다");	
		}			
		
		try {
			EduBaselineDetailProc resultList = eduMgrService.getEduBaselineDetail(params);
	        return new BaseResponse<EduBaselineDetailProc>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }   
    
    
    /**
     * 교육차수관리-차수등록상세 하위 조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduBaselineSubList.do")
    @ApiOperation(value = "교육차수관리-차수등록상세 하위", notes = "교육차수관리-차수등록상세 하위 조회")
    public BaseResponse<List<EduBaselineSubProc>> getEduBaselineSubList(HttpServletRequest request
    		,@RequestBody EduBaselineSubProc params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getProcCd() ==null){				
			return new BaseResponse<List<EduBaselineSubProc>>(BaseResponseCode.PARAMS_ERROR, "procCd는 필수값입니다");	
		}			
		
		if(params.getProcYear() ==null){				
			return new BaseResponse<List<EduBaselineSubProc>>(BaseResponseCode.PARAMS_ERROR, "procYear는 필수값입니다");	
		}
		
		if(params.getProcSeq() ==null){				
			return new BaseResponse<List<EduBaselineSubProc>>(BaseResponseCode.PARAMS_ERROR, "procSeq는 필수값입니다");	
		}		
		
		try {
			List<EduBaselineSubProc> resultList = eduMgrService.getEduBaselineSubList(params);
	        return new BaseResponse<List<EduBaselineSubProc>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }   
    
    
    
    /**
     * 교육차수관리-차수별 메뉴관리조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduBaselineMenuList.do")
    @ApiOperation(value = "교육차수관리-차수별 메뉴관리", notes = "교육차수관리-차수별 메뉴관리조회")
    public BaseResponse<List<EduBaselineMenu>> getEduBaselineMenuList(HttpServletRequest request
    		,@RequestBody EduBaselineMenu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<EduBaselineMenu> resultList = eduMgrService.getEduBaselineMenuList(params);
	        return new BaseResponse<List<EduBaselineMenu>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    
    
    /**
     * 교육차수관리-차수별 메뉴관리 하위조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getEduBaselineMenuSubList.do")
    @ApiOperation(value = "교육차수관리-차수별 메뉴관리", notes = "교육차수관리-차수별 메뉴관리 하위조회")
    public BaseResponse<List<EduBaselineMenu>> getEduBaselineMenuSubList(HttpServletRequest request
    		,@RequestBody EduBaselineMenu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getProcCd() ==null){				
			return new BaseResponse<List<EduBaselineMenu>>(BaseResponseCode.PARAMS_ERROR, "procCd는 필수값입니다");	
		}			
		
		if(params.getProcYear() ==null){				
			return new BaseResponse<List<EduBaselineMenu>>(BaseResponseCode.PARAMS_ERROR, "procYear는 필수값입니다");	
		}
		
		if(params.getProcSeq() ==null){				
			return new BaseResponse<List<EduBaselineMenu>>(BaseResponseCode.PARAMS_ERROR, "procSeq는 필수값입니다");	
		}		
		
		try {
			List<EduBaselineMenu> resultList = eduMgrService.getEduBaselineMenuSubList(params);
	        return new BaseResponse<List<EduBaselineMenu>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }      
}
