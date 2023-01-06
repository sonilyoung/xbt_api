
package egovframework.com.adm.learningMgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.learningMgr.service.LearningMgrService;
import egovframework.com.adm.learningMgr.vo.EduModule;
import egovframework.com.adm.learningMgr.vo.XrayPoint;
import egovframework.com.adm.learningMgr.vo.XrayPointDetail;
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
     * 학습관리-xray판독모듈구성
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/getXrayModuleList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "학습관리-xray판독모듈구성", notes = "학습관리-xray판독모듈구성 조회한다.")
    public BaseResponse<List<EduModule>> getXrayModuleList(HttpServletRequest request
    		, @RequestBody EduModule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			List<EduModule> resultList = learningMgrService.getXrayModuleList(params);
	        return new BaseResponse<List<EduModule>>(resultList);
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
	    
}
