
package egovframework.com.adm.contents;

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
import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.Xrayformation;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
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
@RequestMapping("/adm/contents")
@Api(tags = "Login Management API")
public class ContentsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentsController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private ContentsService contentsService;
    
    
    /**
     * 언어관리조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getLanguageList.do")
    @ApiOperation(value = "언어", notes = "언어를 관리한다.")
    public BaseResponse<List<Language>> getLanguageList(HttpServletRequest request, @RequestBody Language params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<Language> resultList = contentsService.getLanguageList(params);
	        return new BaseResponse<List<Language>>(resultList);
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
    @PostMapping("/getGroupList.do")
    @ApiOperation(value = "그룹관리", notes = "그룹을 조회한다.")
    public BaseResponse<ContentsMgr> getScaleInfo(HttpServletRequest request, @RequestBody Contents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<ContentsMgr>(BaseResponseCode.PARAMS_ERROR, "LanguageCode는 필수값입니다");
		}	
		
		try {
			//그룹관리조회
			ContentsMgr resultList = contentsService.getGroupList(params);
	        return new BaseResponse<ContentsMgr>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    /**
     * 그룹관리수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateGroup.do")
    @ApiOperation(value = "그룹관리", notes = "그룹을 수정한다.")
    public BaseResponse<Integer> updateGroup(HttpServletRequest request, @RequestBody Contents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getLanguageCode() == null || "".equals(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode는 필수값입니다");
		}	
		
		if(params.getUnitGroupNo() == null || params.getUnitGroupNo()==0){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "unitGroupNo는 필수값입니다");
		}			
		
		try {
			//그룹관리수정
			contentsService.updateGroup(params);
			return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  
    
    /**
     * 이미지 저장
     * 
     * @param param
     * @return Company
     */
	@PostMapping(value="/saveUnitImage" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	@ApiOperation(value = "이미지저장", notes = "이미지를 저장 관리한다.")
	public BaseResponse<Integer> saveUnitImage(
			HttpServletRequest request
			,@RequestPart(value = "imgFile", required = true) MultipartFile imgFile
			,@RequestPart(value = "unitGroupNo", required = true )Contents params
	) throws Exception{
		//LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	    //MultipartFile imgFile = request.getFile("imgFile");
	    
		//임시주석처리
    	/*Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}*/	   
		
		if(params.getUnitGroupNo() == null || params.getUnitGroupNo()==0){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "unitGroupNo는 필수값입니다");
		}	
		
		
        String originalFileName = imgFile.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        if(!fileExtension.toUpperCase().equals("JPG") 
       		&& !fileExtension.toUpperCase().equals("GIF")
       		&& !fileExtension.toUpperCase().equals("JPEG")
        ) {
        	return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
        }		
		
	    try { 
	        if(imgFile != null && !imgFile.isEmpty()) {
	            int result = contentsService.saveUnitImage(imgFile, params);
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
	        }else {
	        	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL, BaseResponseCode.SAVE_SUCCESS.getMessage());
	        }
	    }catch(Exception e) {
	       LOGGER.error("error:", e);
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
	    } 
	    
	}	
	
    /**
     * 정보관리조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/getInformationList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "정보관리", notes = "정보관리 조회한다.")
    public BaseResponse<List<UnitInformation>> getInformationList(HttpServletRequest request
    		, @RequestBody UnitInformation params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<List<UnitInformation>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode는 필수값입니다");
		}	
		
		try {
			List<UnitInformation> resultList = contentsService.getInformationList(params);
	        return new BaseResponse<List<UnitInformation>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    	
	
	
	
    /**
     * xray콘텐츠관리-정보관리
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/getXrayInformationList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "xray콘텐츠관리-정보관리", notes = "xray콘텐츠관리-정보관리 조회한다.")
    public BaseResponse<List<Xrayformation>> getXrayInformationList(HttpServletRequest request
    		, @RequestBody Xrayformation params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<Xrayformation> resultList = contentsService.getXrayInformationList(params);
	        return new BaseResponse<List<Xrayformation>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  
	
	
    /**
     * xray콘텐츠관리-상세목록
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/getXrayDetailList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "xray콘텐츠관리-상세목록", notes = "xray콘텐츠관리-상세목록 조회한다.")
    public BaseResponse<List<Xrayformation>> getXrayDetailList(HttpServletRequest request
    		, @RequestBody Xrayformation params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getBagScanId() == null){				
			return new BaseResponse<List<Xrayformation>>(BaseResponseCode.PARAMS_ERROR, "bagScanId는 필수값입니다");
		}	
		
		try {
			List<Xrayformation> resultList = contentsService.getXrayDetailList(params);
	        return new BaseResponse<List<Xrayformation>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  
	
	
	
    /**
     * 물품콘텐츠관리 정보관리 가방물품정보
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/getBagUnitInfoList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "가방물품정보", notes = "가방물품정보 조회한다.")
    public BaseResponse<List<UnitInformation>> getBagUnitInfoList(HttpServletRequest request
    		, @RequestBody UnitInformation params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<List<UnitInformation>>(BaseResponseCode.PARAMS_ERROR, "languageCode는 필수값입니다");
		}	
		
		try {
			List<UnitInformation> resultList = contentsService.getBagUnitInfoList(params);
	        return new BaseResponse<List<UnitInformation>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }  	
	    
}
