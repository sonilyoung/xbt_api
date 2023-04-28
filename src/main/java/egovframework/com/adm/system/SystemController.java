
package egovframework.com.adm.system;

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

import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.system.service.SystemService;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.common.service.CommonService;
import egovframework.com.common.vo.CommonSystemMessage;
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
@RequestMapping("/adm/system")
@Api(tags = "Notice Management API")
public class SystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private SystemService systemService;    
    				
    /**
     * 공지사항조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectNoticeList.do")
    @ApiOperation(value = "공지사항", notes = "공지사항목록조회.")
    public BaseResponse<List<Notice>> selectNoticeList(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<List<Notice>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//공지사항조회
	        return new BaseResponse<List<Notice>>(systemService.selectNoticeList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    /**
     * 공지사항상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectNotice.do")
    @ApiOperation(value = "공지사항상세", notes = "공지사항상세조회.")
    public BaseResponse<Notice> selectNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getNoticeId())){				
			return new BaseResponse<Notice>(BaseResponseCode.PARAMS_ERROR, "NoticeId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Notice>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//공지사항조회
	        return new BaseResponse<Notice>(systemService.selectNotice(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
    
    
    
    /**
     * 공지사항등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertNotice.do")
    @ApiOperation(value = "공지사항", notes = "공지사항등록.")
    public BaseResponse<Integer> insertNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getTitle())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Title" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getContents())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Contents" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		
		
		try {
			//공지사항등록
			params.setInsertId(login.getUserId());
			int result = systemService.insertNotice(params);
			
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
     * 공지사항수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateNotice.do")
    @ApiOperation(value = "공지사항", notes = "공지사항수정.")
    public BaseResponse<Integer> updateNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		

		if(StringUtils.isEmpty(params.getNoticeId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "NoticeId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getTitle())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Title" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getContents())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Contents" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
				
		
		try {
			//공지사항등록
			params.setUpdateId(login.getUserId());
			int result = systemService.updateNotice(params);
			
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
     * 공지사항삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteNotice.do")
    @ApiOperation(value = "공지사항", notes = "공지사항삭제.")
    public BaseResponse<Integer> deleteNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getNoticeId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "NoticeId" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//공지사항삭제
			int result = systemService.deleteNotice(params);
			
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
