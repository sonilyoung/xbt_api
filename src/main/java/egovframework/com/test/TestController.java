
package egovframework.com.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.service.UserService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.login.vo.LoginRequest;
import egovframework.com.adm.login.vo.User;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.cmm.vo.TokenResponse;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.exception.CustomBaseException;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 테스트
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private OfficeMessageSource officeMessageSource;

    @GetMapping("/arrange_level.do")
    @ApiOperation(value = "test api", notes = "test api.")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public BaseResponse<TestResultApi> login(HttpServletRequest request
    		,@RequestParam(required = false) String params) {
    	
    	
    	List<TestApi> unitInfoList = new ArrayList<TestApi>();
    	TestApi unitInfo = new TestApi();
    	unitInfo.setLangSet("kr");
    	unitInfo.setUnitDes("총");
    	unitInfoList.add(unitInfo);
    	unitInfo.setLangSet("en");
    	unitInfo.setUnitDes("건");
    	unitInfoList.add(unitInfo);
    	
    	
    	List<TestApi> cbtUnitImgList = new ArrayList<TestApi>();
    	TestApi cbtUnitImg = new TestApi();
    	cbtUnitImg.setImgType("COLOR");
    	cbtUnitImg.setUnitImg("Base64");
    	cbtUnitImgList.add(unitInfo);
    	cbtUnitImg.setImgType("COLOR");
    	cbtUnitImg.setUnitImg("Base64");
    	cbtUnitImgList.add(unitInfo);
    	
    	TestResultApi result = new TestResultApi();
    	result.setUnitGroupCd("0001");
    	result.setStudyLvl("1");
    	result.setDecipMachineCd("SCP100-1");    	
    	result.setUnitInfo(unitInfoList);
    	result.setCbtUnitImg(cbtUnitImgList);
    	
        return new BaseResponse<TestResultApi>(result);
    }
    
}
