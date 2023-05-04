
package egovframework.com.stu.practice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.global.util.ComUtils;
import egovframework.com.stu.practice.service.PracticeService;
import egovframework.com.stu.practice.vo.Practice;
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
@RequestMapping("/stu/practice")
@Api(tags = "Login Management API")
public class PracticeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PracticeController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private ContentsService contentsService;
  
    
    @Autowired
    private PracticeService practiceService;    

    /**
     * 반입금지물품목록조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectUnitGroupList.do")
    @ApiOperation(value = "반입금지물품목록조회", notes = "반입금지물품목록조회")
    public BaseResponse<List<UnitGroup>> selectUnitGroupList(HttpServletRequest request, @RequestBody UnitGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<List<UnitGroup>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			//그룹관리조회
			List<UnitGroup> resultList = contentsService.selectUnitGroupList(params);
	        return new BaseResponse<List<UnitGroup>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }        
    
    
    /**
     * 담품정보목록조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectUnitList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "담품정보목록조회", notes = "담품정보목록조회")
    public BaseResponse<List<Practice>> selectUnitList(HttpServletRequest request
    		, @RequestBody Practice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getUnitGroupCd() == null || "".contentEquals(params.getUnitGroupCd())){				
			return new BaseResponse<List<Practice>>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<List<Practice>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			List<Practice> resultList = practiceService.selectUnitList(params);
	        return new BaseResponse<List<Practice>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }       
    
    
    /**
     * 물품연습 물품상세조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectUnit.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "물품연습 물품상세조회", notes = "물품연습 물품상세조회")
    public BaseResponse<Practice> selectUnit(HttpServletRequest request
    		, @RequestBody Practice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getUnitScanId() == null || "".contentEquals(params.getUnitScanId())){				
			return new BaseResponse<Practice>(BaseResponseCode.PARAMS_ERROR, "UnitScanId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<Practice>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			Practice result = practiceService.selectUnit(params);
	        return new BaseResponse<Practice>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }   	

	
    
    /**
     * 물품연습 이미지목록조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectPracticeImgList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "물품연습 이미지목록조회", notes = "물품연습 이미지목록조회")
    public BaseResponse<Practice> selectPracticeImgList(HttpServletRequest request
    		, @RequestBody Practice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getUnitScanId() == null || "".contentEquals(params.getUnitScanId())){				
			return new BaseResponse<Practice>(BaseResponseCode.PARAMS_ERROR, "UnitScanId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<Practice>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			Practice result = practiceService.selectUnit(params);
	        return new BaseResponse<Practice>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }   	
	
            
    	    
}
