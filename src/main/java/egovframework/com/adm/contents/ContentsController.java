
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
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.common.vo.LearningMainImg;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.global.util.ComUtils;
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

    @Autowired
    private XbtImageService xbtImageService;
    
    @Autowired
    private FileStorageService fileStorageService; 
    
    
    
    /**
     * 언어관리조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectLanguageList.do")
    @ApiOperation(value = "언어", notes = "언어를 관리한다.")
    public BaseResponse<List<Language>> selectLanguageList(HttpServletRequest request, @RequestBody Language params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<Language> resultList = contentsService.selectLanguageList(params);
	        return new BaseResponse<List<Language>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 언어관리상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectLanguage.do")
    @ApiOperation(value = "언어관리상세", notes = "언어를 관리한다.")
    public BaseResponse<Language> selectLanguage(HttpServletRequest request, @RequestBody Language params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getCodeNo())){				
			return new BaseResponse<Language>(BaseResponseCode.PARAMS_ERROR, "CodeNo" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			Language resultList = contentsService.selectLanguage(params);
	        return new BaseResponse<Language>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }        
    
    
    /**
     * 언어관리등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertLanguage.do")
    @ApiOperation(value = "언어등록", notes = "언어등록.")
    public BaseResponse<Integer> insertLanguage(HttpServletRequest request, @RequestBody Language params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getLanguageName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageName" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			
			params.setInsertId(login.getUserId());
			int result = contentsService.insertLanguage(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    /**
     * 언어관리수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateLanguage.do")
    @ApiOperation(value = "언어수정", notes = "언어수정")
    public BaseResponse<Integer> updateLanguage(HttpServletRequest request, @RequestBody Language params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getCodeNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeNo" + BaseApiMessage.REQUIRED.getMessage());
		}			
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		if(StringUtils.isEmpty(params.getLanguageName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageName" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			
			params.setInsertId(login.getUserId());
			int result = contentsService.updateLanguage(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.UPDATE_SUCCESS, BaseResponseCode.UPDATE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.UPDATE_ERROR, BaseResponseCode.UPDATE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }       
    
    /**
     * 언어삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteLanguage.do")
    @ApiOperation(value = "언어삭제", notes = "언어삭제")
    public BaseResponse<Integer> deleteLanguage(HttpServletRequest request, @RequestBody Language params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getCodeNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "CodeNo" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			//공통코드등록
			int result = contentsService.deleteLanguage(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }      
    
        

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
		
		
		try {
			//그룹관리조회
			List<UnitGroup> resultList = contentsService.selectUnitGroupList(params);
	        return new BaseResponse<List<UnitGroup>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    /**
     * 반입금지물품조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectUnitGroup.do")
    @ApiOperation(value = "반입금지물품조회", notes = "반입금지물품조회")
    public BaseResponse<UnitGroup> selectUnitGroup(HttpServletRequest request, @RequestBody UnitGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
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
			UnitGroup resultList = contentsService.selectUnitGroup(params);
	        return new BaseResponse<UnitGroup>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    /**
     * 반입금지물품등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertUnitGroup.do")
    @ApiOperation(value = "반입금지물품등록", notes = "반입금지물품등록")
    public BaseResponse<Integer> insertUnitGroup(HttpServletRequest request, @RequestBody UnitGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getGroupName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "GroupName" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getGroupDesc())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "GroupDesc" + BaseApiMessage.REQUIRED.getMessage());
		}			
		if(StringUtils.isEmpty(params.getOpenYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "OpenYn" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getPassYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PassYn" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		

		try {
			
			params.setInsertId(login.getUserId());
			int result = contentsService.insertUnitGroup(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }       
    
    /**
     * 반입금지물품수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateUnitGroup.do")
    @ApiOperation(value = "반입금지물품수정", notes = "반입금지물품수정.")
    public BaseResponse<Integer> updateUnitGroup(HttpServletRequest request, @RequestBody UnitGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//그룹관리수정
			contentsService.updateUnitGroup(params);
			return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  
    
    
    /**
     * 반입금지물품삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteUnitGroup.do")
    @ApiOperation(value = "반입금지물품삭제", notes = "반입금지물품삭제")
    public BaseResponse<Integer> deleteUnitGroup(HttpServletRequest request, @RequestBody UnitGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){			
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "getUnitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			int result = contentsService.deleteUnitGroup(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }          
    
    
    /**
     * 이미지 저장
     * 
     * @param param
     * @return Company
     */
	@PostMapping(value="/insertUnitGroupImg.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	@ApiOperation(value = "이미지저장", notes = "이미지를 저장 관리한다.")
	public BaseResponse<Integer> insertUnitGroupImg(
			HttpServletRequest request
			,@RequestPart(value = "imgFile", required = true) MultipartFile imgFile
			,@RequestPart(value = "params", required = true )UnitGroup params
	) throws Exception{
		//LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	    //MultipartFile imgFile = request.getFile("imgFile");
	    
		//임시주석처리
    	/*Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}*/	   
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "unitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		
        String originalFileName = imgFile.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        if(!fileExtension.toUpperCase().equals("JPG") 
       		&& !fileExtension.toUpperCase().equals("GIF")
       		&& !fileExtension.toUpperCase().equals("JPEG")
       		&& !fileExtension.toUpperCase().equals("PNG")
       		&& !fileExtension.toUpperCase().equals("SVG")
        ) {
        	return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
        }		
		
	    try { 
	        if(imgFile != null && !imgFile.isEmpty()) {
	            int result = contentsService.insertUnitGroupImg(imgFile, params);
				if(result>0) {
					return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
				}else {
					return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
				}	            
	        }else {
	        	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL, BaseResponseCode.SAVE_SUCCESS.getMessage());
	        }
	    }catch(Exception e) {
	       LOGGER.error("error:", e);
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
    public BaseResponse<List<UnitImg>> selectUnitList(HttpServletRequest request
    		, @RequestBody UnitImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){
			return new BaseResponse<List<UnitImg>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}
		
		try {
			List<UnitImg> resultList = contentsService.selectUnitList(params);
	        return new BaseResponse<List<UnitImg>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   
	
    
    /**
     * 담품정보조회
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectUnit.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "담품정보조회", notes = "담품정보조회")
    public BaseResponse<UnitImg> selectUnit(HttpServletRequest request
    		, @RequestBody UnitImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<UnitImg>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getCode());
		}						
		
		if(StringUtils.isEmpty(params.getLanguageCode())){
			return new BaseResponse<UnitImg>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			UnitImg result = contentsService.selectUnit(params);
			
			LearningImg li = new LearningImg();
			li.setUnitId(params.getUnitId());
			LearningMainImg resultImg = xbtImageService.selectCommonPracticeImg(li);
			result.setFrontImg(resultImg.getImgFront()); 
			result.setSideImg(resultImg.getImgSide());
			result.setRealImg(resultImg.getImgReal());
			
	        return new BaseResponse<UnitImg>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   	
		
	
	
    /**
     * 단품정보저장
     * 
     * @param param
     * @return Company
     */
	@PostMapping(value="/insertUnit.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	@ApiOperation(value = "단품정보저장", notes = "단품정보저장")
	public BaseResponse<UnitImg> insertUnit(HttpServletRequest request, @RequestBody UnitImg params) {
		//LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	    //MultipartFile imgFile = request.getFile("imgFile");
	    
		//임시주석처리
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}	   
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){				
			return new BaseResponse<UnitImg>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		
		if(StringUtils.isEmpty(params.getUnitDesc())){				
			return new BaseResponse<UnitImg>(BaseResponseCode.PARAMS_ERROR, "UnitDesc" + BaseApiMessage.REQUIRED.getMessage());
		}
		
	    try { 
	    	params.setInsertId(login.getFirstLogin());
	    	String result = contentsService.insertUnit(params);
	    	params.setUnitScanId(result);
            return new BaseResponse<UnitImg>(params);	    	
	    }catch(Exception e) {
	       LOGGER.error("error:", e);
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
	    } 
	    
	}	   
	
	
	
	
    /**
     * 단품정보수정
     * 
     * @param param
     * @return Company
     */
	@PostMapping(value="/updateUnit.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	@ApiOperation(value = "단품정보수정", notes = "단품정보수정")
	public BaseResponse<Integer> updateUnit(HttpServletRequest request, @RequestBody UnitImg params) {
		//LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	    //MultipartFile imgFile = request.getFile("imgFile");
	    
		//임시주석처리
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}	   
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "getUnitId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		
		if(StringUtils.isEmpty(params.getUnitDesc())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitDesc" + BaseApiMessage.REQUIRED.getMessage());
		}
		
	    try { 
	    	params.setUpdateId(login.getFirstLogin());
	    	int result = contentsService.updateUnit(params);
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}	    	
	    }catch(Exception e) {
	       LOGGER.error("error:", e);
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
	    } 
	    
	}	   	
	
	
	
	
    /**
     * 단품 이미지 저장
     * 
     * @param param
     * @return Company
     */
	@PostMapping(value="/saveUnitImg.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	@ApiOperation(value = "단품 이미지 저장", notes = "단품 이미지 저장")
	public BaseResponse<Integer> saveUnitImg(
			HttpServletRequest request
			,@RequestPart(value = "realImg", required = false) MultipartFile realImg
			,@RequestPart(value = "frontImg", required = false) MultipartFile frontImg
			,@RequestPart(value = "sideImg", required = false) MultipartFile sideImg
			,@RequestPart(value = "params", required = true )UnitImg params
	) throws Exception{
	
		//LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	    //MultipartFile imgFile = request.getFile("imgFile");
	    
		//임시주석처리
    	/*Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}*/
		
		//MultipartFile realImg = mRequest.getFile("realImg");
		//MultipartFile frontImg = mRequest.getFile("frontImg");
		//MultipartFile sideImg = mRequest.getFile("sideImg");
		if(realImg != null){
			params.setRealmImg(realImg);
			if(!ComUtils.imgExtentionCheck(realImg)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
		}
	
		if(frontImg != null){
			params.setFrontmImg(frontImg);
			if(!ComUtils.imgExtentionCheck(frontImg)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
		}
		
		if(sideImg != null){
			params.setSidemImg(sideImg);
			if(!ComUtils.imgExtentionCheck(sideImg)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
		}
	
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
	    try { 
	    	//params.setInsertId(login.getFirstLogin());
	    	params.setUpdateId("admin");
	    	contentsService.updateUnitImg(params);
			return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
	    }catch(Exception e) {
	       LOGGER.error("error:", e);
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
	    } 
	    
	    
	}	   	
	
	
    
 
    
    /**
     * 단품삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteUnit.do")
    @ApiOperation(value = "단품삭제", notes = "단품삭제")
    public BaseResponse<Integer> deleteUnit(HttpServletRequest request, @RequestBody UnitImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitId())){			
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		
		try {
			int result = contentsService.deleteUnit(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   	
 	
	
	
	
    /**
     * xray콘텐츠관리-정보관리
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectXrayContentsList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "xray콘텐츠관리-정보관리", notes = "xray콘텐츠관리-정보관리 조회한다.")
    public BaseResponse<List<XrayContents>> selectXrayContentsList(HttpServletRequest request
    		, @RequestBody XrayContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<XrayContents> resultList = contentsService.selectXrayContentsList(params);
			
	        return new BaseResponse<List<XrayContents>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  
	
    /**
     * 의사색체이미지
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectImg.do")
    @ApiOperation(value = "의사색체이미지", notes = "의사색체이미지")
    public BaseResponse<LearningImg> selectImg(HttpServletRequest request, @RequestBody LearningImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<LearningImg>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		if(StringUtils.isEmpty(params.getCommand())){				
			return new BaseResponse<LearningImg>(BaseResponseCode.PARAMS_ERROR, "Command" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			LearningImg result = xbtImageService.selectLeaningImg(params); 
			return new BaseResponse<LearningImg>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   	

    
    
    
    /**
     * xray콘텐츠관리-정보관리등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertXrayContents.do")
    @ApiOperation(value = "xray콘텐츠관리-정보관리", notes = "xray콘텐츠관리-정보관리등록.")
    public BaseResponse<Integer> insertXrayContents(HttpServletRequest request, @RequestBody XrayContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "StudyLvl" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		/*
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getCode());
		}					
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}
		*/	
		
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UseYn" + BaseApiMessage.REQUIRED.getCode());
		}			
				
		
		try {
			//xray콘텐츠관리-정보관리등록
			params.setInsertId(login.getUserId());
			int result = contentsService.insertXrayContents(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * xray콘텐츠관리-정보관리수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateXrayContents.do")
    @ApiOperation(value = "xray콘텐츠관리-정보관리", notes = "xray콘텐츠관리-정보관리수정.")
    public BaseResponse<Integer> updateXrayContents(HttpServletRequest request, @RequestBody XrayContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		/*
		if(StringUtils.isEmpty(params.getXrayContentsId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "XrayContentsId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getTitle())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Title" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getContents())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Contents" + BaseApiMessage.REQUIRED.getCode());
		}*/			
				
		
		try {
			//xray콘텐츠관리-정보관리등록
			params.setUpdateId(login.getUserId());
			int result = contentsService.updateXrayContents(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * xray콘텐츠관리-정보관리삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteXrayContents.do")
    @ApiOperation(value = "xray콘텐츠관리-정보관리", notes = "xray콘텐츠관리-정보관리삭제.")
    public BaseResponse<Integer> deleteXrayContents(HttpServletRequest request, @RequestBody XrayContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagScanIds())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//xray콘텐츠관리-정보관리삭제
			int result = contentsService.deleteXrayContents(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }	
	
	
	
    /**
     * xray콘텐츠관리-상세목록
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectXrayUnitList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "xray콘텐츠관리-상세목록", notes = "xray콘텐츠관리-상세목록 조회한다.")
    public BaseResponse<List<XrayContents>> selectXrayUnitList(HttpServletRequest request
    		, @RequestBody XrayContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<List<XrayContents>>(BaseResponseCode.PARAMS_ERROR, "bagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			List<XrayContents> resultList = contentsService.selectXrayUnitList(params);
	        return new BaseResponse<List<XrayContents>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  	
	
	
	 /**
     * xray콘텐츠관리-상세목록등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertXrayUnit.do")
    @ApiOperation(value = "xray콘텐츠관리-상세목록 등록", notes = "xray콘텐츠관리-상세목록 등록.")
    public BaseResponse<Integer> insertXrayUnit(HttpServletRequest request, @RequestBody XrayContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getParamList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ParamList" + BaseApiMessage.REQUIRED.getCode());
		}			
				
		
		/*
		if(StringUtils.isEmpty(params.getSeq())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Seq" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getAnswerItem())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "AnswerItem" + BaseApiMessage.REQUIRED.getCode());
		}*/			
				
		
		try {
			//xray콘텐츠관리-상세목록등록
			params.setInsertId(login.getUserId());
			int result = contentsService.insertXrayUnit(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
	
	
    /**
     * xray콘텐츠관리-상세목록삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteXrayUnit.do")
    @ApiOperation(value = "xray콘텐츠관리-상세목록", notes = "xray콘텐츠관리-상세목록삭제.")
    public BaseResponse<Integer> deleteXrayUnit(HttpServletRequest request, @RequestBody XrayContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagContNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagContNo" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//xray콘텐츠관리-상세목록삭제
			int result = contentsService.deleteXrayUnit(params);
			
			if(result>0) {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_SUCCESS, BaseResponseCode.DELETE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<Integer>(BaseResponseCode.DELETE_ERROR, BaseResponseCode.DELETE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }		
	
	
    /**
     * 물품콘텐츠관리 정보관리 가방물품정보
     * 
     * @param param
     * @return Company
     */
	@ResponseBody
    @RequestMapping(value = {"/selectUnitPopupList.do"}, method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ApiOperation(value = "가방물품정보", notes = "가방물품정보 조회한다.")
    public BaseResponse<List<UnitInformation>> selectUnitPopupList(HttpServletRequest request
    		, @RequestBody UnitInformation params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){
			return new BaseResponse<List<UnitInformation>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			List<UnitInformation> resultList = contentsService.selectUnitPopupList(params);
	        return new BaseResponse<List<UnitInformation>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  	
	    
 	
	
	
    /**
     * xray콘텐츠관리-정보관리상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectXrayImgContents.do")
    @ApiOperation(value = "xray콘텐츠관리-정보관리상세", notes = "xray콘텐츠관리-정보관리상세조회.")
    public BaseResponse<XrayImgContents> selectXrayImgContents(HttpServletRequest request, @RequestBody XrayImgContents params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<XrayImgContents>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//xray콘텐츠관리-정보관리조회
			//XrayImgContents result = contentsService.selectXrayImgContents(params);
			
			LearningImg li = new LearningImg();
			li.setBagScanId(params.getBagScanId());
			LearningImg resultImg = xbtImageService.selectAdmAllBagImg(li);
			params.setResultImg(resultImg);
	        return new BaseResponse<XrayImgContents>(params);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        	
	
    /**
     * xray 이미지 저장
     * 
     * @param param
     * @return Company
     */
	@PostMapping(value="/updateXrayContentsImg.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	@ApiOperation(value = "xray 이미지 저장", notes = "xray 이미지 저장")
	public BaseResponse<Integer> updateXrayContentsImg(
			HttpServletRequest request
			,@RequestPart(value = "imgReal", required = false) MultipartFile imgReal
			,@RequestPart(value = "imgFront", required = false) MultipartFile imgFront
			,@RequestPart(value = "imgSide", required = false) MultipartFile imgSide
			,@RequestPart(value = "imgFrontCollar", required = false) MultipartFile imgFrontCollar
			,@RequestPart(value = "imgFrontOrganism", required = false) MultipartFile imgFrontOrganism
			,@RequestPart(value = "imgFrontCollarOutline", required = false) MultipartFile imgFrontCollarOutline
			,@RequestPart(value = "imgFrontCollarReversal", required = false) MultipartFile imgFrontCollarReversal
			,@RequestPart(value = "imgFrontCollarBwRate1", required = false) MultipartFile imgFrontCollarBwRate1
			,@RequestPart(value = "imgFrontCollarBwRate2", required = false) MultipartFile imgFrontCollarBwRate2
			,@RequestPart(value = "imgFrontCollarBwRate3", required = false) MultipartFile imgFrontCollarBwRate3
			,@RequestPart(value = "imgFrontCollarBwRate4", required = false) MultipartFile imgFrontCollarBwRate4
			,@RequestPart(value = "imgFrontCollarBwRate5", required = false) MultipartFile imgFrontCollarBwRate5
			,@RequestPart(value = "imgFrontCollarBwRate6", required = false) MultipartFile imgFrontCollarBwRate6
			,@RequestPart(value = "imgFrontBw", required = false) MultipartFile imgFrontBw
			,@RequestPart(value = "imgFrontMinerals", required = false) MultipartFile imgFrontMinerals
			,@RequestPart(value = "imgFrontBwOutline", required = false) MultipartFile imgFrontBwOutline
			,@RequestPart(value = "imgFrontBwReversal", required = false) MultipartFile imgFrontBwReversal
			,@RequestPart(value = "imgFrontBwBwRate1", required = false) MultipartFile imgFrontBwBwRate1
			,@RequestPart(value = "imgFrontBwBwRate2", required = false) MultipartFile imgFrontBwBwRate2
			,@RequestPart(value = "imgFrontBwBwRate3", required = false) MultipartFile imgFrontBwBwRate3
			,@RequestPart(value = "imgFrontBwBwRate4", required = false) MultipartFile imgFrontBwBwRate4
			,@RequestPart(value = "imgFrontBwBwRate5", required = false) MultipartFile imgFrontBwBwRate5
			,@RequestPart(value = "imgFrontBwBwRate6", required = false) MultipartFile imgFrontBwBwRate6
			,@RequestPart(value = "imgSideCollar", required = false) MultipartFile imgSideCollar
			,@RequestPart(value = "imgSideOrganism", required = false) MultipartFile imgSideOrganism
			,@RequestPart(value = "imgSideCollarOutline", required = false) MultipartFile imgSideCollarOutline
			,@RequestPart(value = "imgSideCollarReversal", required = false) MultipartFile imgSideCollarReversal
			,@RequestPart(value = "imgSideCollarBwRate1", required = false) MultipartFile imgSideCollarBwRate1
			,@RequestPart(value = "imgSideCollarBwRate2", required = false) MultipartFile imgSideCollarBwRate2
			,@RequestPart(value = "imgSideCollarBwRate3", required = false) MultipartFile imgSideCollarBwRate3
			,@RequestPart(value = "imgSideCollarBwRate4", required = false) MultipartFile imgSideCollarBwRate4
			,@RequestPart(value = "imgSideCollarBwRate5", required = false) MultipartFile imgSideCollarBwRate5
			,@RequestPart(value = "imgSideCollarBwRate6", required = false) MultipartFile imgSideCollarBwRate6
			,@RequestPart(value = "imgSideBw", required = false) MultipartFile imgSideBw
			,@RequestPart(value = "imgSideMinerals", required = false) MultipartFile imgSideMinerals
			,@RequestPart(value = "imgSideBwOutline", required = false) MultipartFile imgSideBwOutline
			,@RequestPart(value = "imgSideBwReversal", required = false) MultipartFile imgSideBwReversal
			,@RequestPart(value = "imgSideBwBwRate1", required = false) MultipartFile imgSideBwBwRate1
			,@RequestPart(value = "imgSideBwBwRate2", required = false) MultipartFile imgSideBwBwRate2
			,@RequestPart(value = "imgSideBwBwRate3", required = false) MultipartFile imgSideBwBwRate3
			,@RequestPart(value = "imgSideBwBwRate4", required = false) MultipartFile imgSideBwBwRate4
			,@RequestPart(value = "imgSideBwBwRate5", required = false) MultipartFile imgSideBwBwRate5
			,@RequestPart(value = "imgSideBwBwRate6", required = false) MultipartFile imgSideBwBwRate6				
			,@RequestPart(value = "params", required = true )XrayImgContents params
	) throws Exception{
	
		//LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	    //MultipartFile imgFile = request.getFile("imgFile");
	    
		//임시주석처리
    	/*Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}*/
		
		//MultipartFile realImg = mRequest.getFile("realImg");
		//MultipartFile frontImg = mRequest.getFile("frontImg");
		//MultipartFile sideImg = mRequest.getFile("sideImg");

		if(imgReal != null){
			params.setMimgReal(imgReal);
			if(!ComUtils.imgExtentionCheck(imgReal)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}
			
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "403", params, imgReal);
		}
		
		if(imgFront != null){
			params.setMimgFront(imgFront);
			if(!ComUtils.imgExtentionCheck(imgFront)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "101", params, imgFront);
		}
		
		if(imgSide != null){
			params.setMimgSide(imgSide);
			if(!ComUtils.imgExtentionCheck(imgSide)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "201", params, imgSide);
		}		
	
		if(imgFrontCollar != null){
			params.setMimgFrontCollar(imgFrontCollar);
			if(!ComUtils.imgExtentionCheck(imgFrontCollar)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "101", params, imgFront);
		}
		
		if(imgFrontOrganism != null){
			params.setMimgFrontOrganism(imgFrontOrganism);
			if(!ComUtils.imgExtentionCheck(imgFrontOrganism)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "102", params, imgFront);
		}		
		
		
		if(imgFrontCollarOutline != null){
			params.setMimgFrontCollarOutline(imgFrontCollarOutline);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarOutline)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "103", params, imgFront);
		}		
		
		
		
		if(imgFrontCollarReversal != null){
			params.setMimgFrontCollarReversal(imgFrontCollarReversal);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarReversal)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "104", params, imgFront);
		}		
		
		
		
		if(imgFrontCollarBwRate1 != null){
			params.setMimgFrontCollarBwRate1(imgFrontCollarBwRate1);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarBwRate1)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "105", params, imgFront);
		}		
		
		
		
		if(imgFrontCollarBwRate2 != null){
			params.setMimgFrontCollarBwRate2(imgFrontCollarBwRate2);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarBwRate2)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "106", params, imgFront);
		}		
		
		
		
		if(imgFrontCollarBwRate3 != null){
			params.setMimgFrontCollarBwRate3(imgFrontCollarBwRate3);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarBwRate3)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "107", params, imgFront);
		}		
		
		
		
		if(imgFrontCollarBwRate4 != null){
			params.setMimgFrontCollarBwRate4(imgFrontCollarBwRate4);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarBwRate4)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "108", params, imgFront);
		}		
		
		
		
		if(imgFrontCollarBwRate5 != null){
			params.setMimgFrontCollarBwRate5(imgFrontCollarBwRate5);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarBwRate5)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "109", params, imgFront);
		}		
		
		
		
		if(imgFrontCollarBwRate6 != null){
			params.setMimgFrontCollarBwRate6(imgFrontCollarBwRate6);
			if(!ComUtils.imgExtentionCheck(imgFrontCollarBwRate6)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "110", params, imgFront);
		}		
		
		
		
		if(imgFrontBw != null){
			params.setMimgFrontBw(imgFrontBw);
			if(!ComUtils.imgExtentionCheck(imgFrontBw)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}			
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "111", params, imgFront);
		}		
		
		
		
		if(imgFrontMinerals != null){
			params.setMimgFrontMinerals(imgFrontMinerals);
			if(!ComUtils.imgExtentionCheck(imgFrontMinerals)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "112", params, imgFront);
		}		
		
		
		
		if(imgFrontBwOutline != null){
			params.setMimgFrontBwOutline(imgFrontBwOutline);
			if(!ComUtils.imgExtentionCheck(imgFrontBwOutline)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "113", params, imgFront);
		}		
		
		
		
		if(imgFrontBwReversal != null){
			params.setMimgFrontBwReversal(imgFrontBwReversal);
			if(!ComUtils.imgExtentionCheck(imgFrontBwReversal)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "114", params, imgFront);
		}		
		
		
		
		if(imgFrontBwBwRate1 != null){
			params.setMimgFrontBwBwRate1(imgFrontBwBwRate1);
			if(!ComUtils.imgExtentionCheck(imgFrontBwBwRate1)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "115", params, imgFront);
		}		

		
		
		if(imgFrontBwBwRate2 != null){
			params.setMimgFrontBwBwRate2(imgFrontBwBwRate2);
			if(!ComUtils.imgExtentionCheck(imgFrontBwBwRate2)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "116", params, imgFront);
		}		
		
		
		
		if(imgFrontBwBwRate3 != null){
			params.setMimgFrontBwBwRate3(imgFrontBwBwRate3);
			if(!ComUtils.imgExtentionCheck(imgFrontBwBwRate3)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}			
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "117", params, imgFront);
		}		
		
		
		
		if(imgFrontBwBwRate4 != null){
			params.setMimgFrontBwBwRate4(imgFrontBwBwRate4);
			if(!ComUtils.imgExtentionCheck(imgFrontBwBwRate4)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "118", params, imgFront);
		}		
		
		
		
		if(imgFrontBwBwRate5 != null){
			params.setMimgFrontBwBwRate5(imgFrontBwBwRate5);
			if(!ComUtils.imgExtentionCheck(imgFrontBwBwRate5)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "119", params, imgFront);
		}		
		
		
		
		if(imgFrontBwBwRate6 != null){
			params.setMimgFrontBw(imgFrontBwBwRate6);
			if(!ComUtils.imgExtentionCheck(imgFrontBwBwRate6)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "120", params, imgFront);
		}		
		
		if(imgSideCollar != null){
			params.setMimgSideCollar(imgSideCollar);
			if(!ComUtils.imgExtentionCheck(imgSideCollar)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "201", params, imgFront);
		}
		
		if(imgSideOrganism != null){
			params.setMimgSideOrganism(imgSideOrganism);
			if(!ComUtils.imgExtentionCheck(imgSideOrganism)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}		
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "202", params, imgFront);
		}		
		
		
		if(imgSideCollarOutline != null){
			params.setMimgSideCollarOutline(imgSideCollarOutline);
			if(!ComUtils.imgExtentionCheck(imgSideCollarOutline)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "203", params, imgFront);
		}		
		
		
		
		if(imgSideCollarReversal != null){
			params.setMimgSideCollarReversal(imgSideCollarReversal);
			if(!ComUtils.imgExtentionCheck(imgSideCollarReversal)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "204", params, imgFront);
		}		
		
		
		
		if(imgSideCollarBwRate1 != null){
			params.setMimgSideCollarBwRate1(imgSideCollarBwRate1);
			if(!ComUtils.imgExtentionCheck(imgSideCollarBwRate1)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "205", params, imgFront);
		}		
		
		
		
		if(imgSideCollarBwRate2 != null){
			params.setMimgSideCollarBwRate2(imgSideCollarBwRate2);
			if(!ComUtils.imgExtentionCheck(imgSideCollarBwRate2)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "206", params, imgFront);
		}		
		
		
		
		if(imgSideCollarBwRate3 != null){
			params.setMimgSideCollarBwRate3(imgSideCollarBwRate3);
			if(!ComUtils.imgExtentionCheck(imgSideCollarBwRate3)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "207", params, imgFront);
		}		
		
		
		
		if(imgSideCollarBwRate4 != null){
			params.setMimgSideCollarBwRate4(imgSideCollarBwRate4);
			if(!ComUtils.imgExtentionCheck(imgSideCollarBwRate4)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}		
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "208", params, imgFront);
		}		
		
		
		
		if(imgSideCollarBwRate5 != null){
			params.setMimgSideCollarBwRate5(imgSideCollarBwRate5);
			if(!ComUtils.imgExtentionCheck(imgSideCollarBwRate5)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}			
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "209", params, imgFront);
		}		
		
		
		
		if(imgSideCollarBwRate6 != null){
			params.setMimgSideCollarBwRate6(imgSideCollarBwRate6);
			if(!ComUtils.imgExtentionCheck(imgSideCollarBwRate6)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "210", params, imgFront);
		}		
		
		
		
		if(imgSideBw != null){
			params.setMimgSideBw(imgSideBw);
			if(!ComUtils.imgExtentionCheck(imgSideBw)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "211", params, imgFront);
		}		
		
		
		
		if(imgSideMinerals != null){
			params.setMimgSideMinerals(imgSideMinerals);
			if(!ComUtils.imgExtentionCheck(imgSideMinerals)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}			
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "212", params, imgFront);
		}		
		
		
		
		if(imgSideBwOutline != null){
			params.setMimgSideBwOutline(imgSideBwOutline);
			if(!ComUtils.imgExtentionCheck(imgSideBwOutline)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "213", params, imgFront);
		}		
		
		
		
		if(imgSideBwReversal != null){
			params.setMimgSideBwReversal(imgSideBwReversal);
			if(!ComUtils.imgExtentionCheck(imgSideBwReversal)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}		
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "214", params, imgFront);
		}		
		
		
		
		if(imgSideBwBwRate1 != null){
			params.setMimgSideBwBwRate1(imgSideBwBwRate1);
			if(!ComUtils.imgExtentionCheck(imgSideBwBwRate1)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "215", params, imgFront);
		}		

		
		
		if(imgSideBwBwRate2 != null){
			params.setMimgSideBwBwRate2(imgSideBwBwRate2);
			if(!ComUtils.imgExtentionCheck(imgSideBwBwRate2)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}		
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "216", params, imgFront);
		}		
		
		
		
		if(imgSideBwBwRate3 != null){
			params.setMimgSideBwBwRate3(imgSideBwBwRate3);
			if(!ComUtils.imgExtentionCheck(imgSideBwBwRate3)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "217", params, imgFront);
		}		
		
		
		
		if(imgSideBwBwRate4 != null){
			params.setMimgSideBwBwRate4(imgSideBwBwRate4);
			if(!ComUtils.imgExtentionCheck(imgSideBwBwRate4)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}		
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "218", params, imgFront);
		}		
		
		
		
		if(imgSideBwBwRate5 != null){
			params.setMimgSideBwBwRate5(imgSideBwBwRate5);
			if(!ComUtils.imgExtentionCheck(imgSideBwBwRate5)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}				
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "219", params, imgFront);
		}		
		
		
		
		if(imgSideBwBwRate6 != null){
			params.setMimgSideBw(imgSideBwBwRate6);
			if(!ComUtils.imgExtentionCheck(imgSideBwBwRate6)) {
				return new BaseResponse<Integer>(BaseResponseCode.EXTENSION_ERROR);
			}	
			fileStorageService.createXrayImageFiles(params.getBagScanId(), "220", params, imgFront);
		}				
		
		
	
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
	    try { 
	    	//params.setInsertId(login.getFirstLogin());
	    	params.setUpdateId("admin");
	    	//contentsService.updateXrayContentsImg(params);
	    	contentsService.updateXrayContentsDbImg(params);
			return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
	    }catch(Exception e) {
	       LOGGER.error("error:", e);
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
	    } 
	    
	    
	}	   		
	
	    
}
