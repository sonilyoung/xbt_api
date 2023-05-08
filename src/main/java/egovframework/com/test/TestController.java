
package egovframework.com.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.exception.CustomBaseException;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import egovframework.com.global.util.FileReader;

/**
 * 테스트
 */
@RestController
//@RequestMapping("/")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private OfficeMessageSource officeMessageSource;
    
    
    
    @GetMapping("/index.do")
    @ApiOperation(value = "test", notes = "test")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public BaseResponse<Integer> index(HttpServletRequest request
    		,@RequestParam(required = false) String params) {
    	
    	return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
    }    
    
    @GetMapping("/fileList.do")
    @ApiOperation(value = "test", notes = "test")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public BaseResponse<Integer> fileList(HttpServletRequest request
    		,@RequestParam(required = false) String params) throws IOException {
    	String xrayPath = GlobalsProperties.getProperty("xray.img.path");
    	
    	String scanId = "X00241";
        String strDirPath = xrayPath+scanId; 
        File[] fileList = null;
		fileList = FileReader.ListFile( strDirPath );
			
        byte[] fileByte;/*이미지*/
        
        //결과유기물
        System.out.println("result count : " + fileList.length);
        
        //byte변환
        for( int i = 0; i < fileList.length; i++ ) { 
        	System.out.println("result : "+fileList[i]);
        	fileByte = Files.readAllBytes(fileList[i].toPath());
        	System.out.println("fileByte : "+fileByte);
        }        	
    	
    	return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
    }        
    
    
    @GetMapping("/arrange_level.do")
    @ApiOperation(value = "test api", notes = "test api.")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public BaseResponse<TestResultApi> test2(HttpServletRequest request
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
    
    
    @GetMapping("/selectEmpUnitImage.do")
    @ApiOperation(value = "test api", notes = "test api.")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public JSONObject selectEmpUnitImage(HttpServletRequest request
    		,@RequestParam(required = false) String params) throws ParseException {
    	String response = "{\"unitImages\": [ {\"cbtUnitImg\": [ {\"imgType\": \"COLOR\", \"imgRotate\": \"90º\", \"unitImg\": \"string\"}],\"decipMachineCd\": \"0000\", \"studyLvl\": \"1\", \"unitGroupCd\": \"1001\", \"unitId\": \"101\", \"unitInfo\": [{\"langSet\": \"kr\", \"unitDes\": \"gun\"}]}]}";
    	JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);
        return jsonObject;
    }       
    
}
