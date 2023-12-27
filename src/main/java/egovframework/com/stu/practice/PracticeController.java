
package egovframework.com.stu.practice;

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

import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.stu.practice.service.PracticeService;
import egovframework.com.stu.practice.vo.Practice;
import egovframework.com.stu.practice.vo.UnitGroup;
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

    //private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
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
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<List<UnitGroup>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//그룹관리조회
			List<UnitGroup> resultList = practiceService.selectUnitGroupList(params);
	        return new BaseResponse<List<UnitGroup>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }       
    
    /**
     * 반입금지물품정답조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectUnitGroupAnswer.do")
    @ApiOperation(value = "반입금지물품정답조회", notes = "반입금지물품정답조회")
    public BaseResponse<UnitGroup> selectUnitGroupAnswer(HttpServletRequest request, @RequestBody UnitGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){				
			return new BaseResponse<UnitGroup>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		if(StringUtils.isEmpty(params.getUserActionDiv())){				
			return new BaseResponse<UnitGroup>(BaseResponseCode.PARAMS_ERROR, "UserActionDiv" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<UnitGroup>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//그룹관리조회
			UnitGroup resultList = practiceService.selectUnitGroupAnswer(params);
	        return new BaseResponse<UnitGroup>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }     
    
    
    /**
     * 물품연습 담품정보목록조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectUnitList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "물품연습 담품정보목록조회", notes = "물품연습 담품정보목록조회")
    public BaseResponse<List<Practice>> selectUnitList(HttpServletRequest request
    		, @RequestBody Practice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){				
			return new BaseResponse<List<Practice>>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<List<Practice>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			List<Practice> resultList = practiceService.selectUnitList(params);
	        return new BaseResponse<List<Practice>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<Practice>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Practice>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			Practice result = practiceService.selectUnit(params);
	        return new BaseResponse<Practice>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   	

  	
	
            
    	    
}
