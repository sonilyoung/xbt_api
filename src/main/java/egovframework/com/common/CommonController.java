
package egovframework.com.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.service.UserService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.common.service.CommonService;
import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.CommonSystemMessage;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.global.util.DateHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

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
@RequestMapping("/common")
@Api(tags = "Login Management API")
public class CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private CommonService commonService;
    
    
    /**
     * 화면ui다국어처리
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectLanguageApplyList.do")
    @ApiOperation(value = "화면ui다국어처리", notes = "화면ui다국어처리")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public BaseResponse<List<HashMap<String, Object>>> selectLanguageApplyList(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<List<HashMap<String, Object>>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//다국어처리조회
			List<Common> result = commonService.selectLanguageApplyList(params);
			List<HashMap<String, Object>> resultMessage = new ArrayList<HashMap<String, Object>>(); 
			if(result!=null) {
				for(Common c : result) {
					HashMap<String, Object> languageApply = new HashMap<String, Object>();
					languageApply.put("codeDesc", c.getCodeDesc());//구분
					languageApply.put("groupId", c.getGroupId());//구분코드
					languageApply.put("codeName", c.getCodeName());//메세지코드
					languageApply.put(c.getCodeName(), c.getCodeValue());//메세지
					resultMessage.add(languageApply);
				}
			}
	        return new BaseResponse<List<HashMap<String, Object>>>(resultMessage);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }        
    
    
    /**
     * 화면ui다국어처리상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectLanguageApply.do")
    @ApiOperation(value = "다국어처리상세", notes = "다국어처리상세")
    public BaseResponse<HashMap<String, Object>> selectLanguageApply(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getCodeId())){				
			return new BaseResponse<HashMap<String, Object>>(BaseResponseCode.PARAMS_ERROR, "CodeId" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<HashMap<String, Object>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//다국어처리조회
			HashMap<String, Object> languageApply = new HashMap<String, Object>();
			languageApply.put("massgetest", "test");
	        return new BaseResponse<HashMap<String, Object>>(languageApply);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }       
    
    
    /**
     * 다국어처리등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertLanguageApply.do")
    @ApiOperation(value = "다국어처리", notes = "다국어처리등록.")
    public BaseResponse<Integer> insertLanguageApply(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(StringUtils.isEmpty(params.getGroupId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "GroupId" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getCodeName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeName" + BaseApiMessage.REQUIRED.getMessage());
		}		
		if(StringUtils.isEmpty(params.getCodeValue())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeValue" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getSortOrder())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "SortOrder" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		
		try {
			//다국어처리등록
			params.setInsertId(login.getUserId());
			int result = commonService.insertLanguageApply(params);
			
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
     * 다국어처리삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteLanguageApply.do")
    @ApiOperation(value = "다국어처리", notes = "다국어처리삭제.")
    public BaseResponse<Integer> deleteLanguageApply(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(StringUtils.isEmpty(params.getCodeNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeNo" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			//다국어처리등록
			int result = commonService.deleteLanguageApply(params);
			
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
     * 그룹관리조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectCommonList.do")
    @ApiOperation(value = "공통코드", notes = "공통코드목록조회.")
    public BaseResponse<List<Common>> selectCommonList(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//그룹관리조회
	        return new BaseResponse<List<Common>>(commonService.selectCommonList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    
    
    /**
     * 공통코드등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertCommonCode.do")
    @ApiOperation(value = "공통코드", notes = "공통코드등록.")
    public BaseResponse<Integer> insertCommonCode(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(StringUtils.isEmpty(params.getParentsGroupId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "parentsGroupId" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getGroupId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "groupId" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getSortOrder())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "sortOrder" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getRemarks())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "remarks" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getCodeValue())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "codeValue" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getMainYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "mainYn" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getCodeName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeName" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		
		try {
			//공통코드등록
			params.setInsertId(login.getUserId());
			int result = commonService.insertCommonCode(params);
			
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
     * 공통코드수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateCommonCode.do")
    @ApiOperation(value = "공통코드", notes = "공통코드수정.")
    public BaseResponse<Integer> updateCommonCode(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		

		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(StringUtils.isEmpty(params.getCodeId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		if(StringUtils.isEmpty(params.getParentsGroupId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "parentsGroupId" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getGroupId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "groupId" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getSortOrder())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "sortOrder" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getRemarks())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "remarks" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getCodeValue())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "codeValue" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getMainYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "mainYn" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getCodeName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeName" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UseYn" + BaseApiMessage.REQUIRED.getMessage());
		}		
				
		
		try {
			//공통코드등록
			params.setUpdateId(login.getUserId());
			int result = commonService.updateCommonCode(params);
			
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
     * 공통코드삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteCommonCode.do")
    @ApiOperation(value = "공통코드", notes = "공통코드삭제.")
    public BaseResponse<Integer> deleteCommonCode(HttpServletRequest request, @RequestBody Common params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(StringUtils.isEmpty(params.getCodeId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			//공통코드등록
			int result = commonService.deleteCommonCode(params);
			
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
