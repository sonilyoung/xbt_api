
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.contents.vo.Contents;
import egovframework.com.adm.contents.vo.ContentsMgr;
import egovframework.com.adm.contents.vo.UnitInformation;
import egovframework.com.adm.contents.vo.Xrayformation;
import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.UnitImg;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.CommonSystemMessage;
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<UnitGroup>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			//그룹관리조회
			UnitGroup resultList = contentsService.selectUnitGroup(params);
	        return new BaseResponse<UnitGroup>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
		
		
		try {
			//그룹관리수정
			contentsService.updateUnitGroup(params);
			return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
		
		try {
			int result = contentsService.deleteUnitGroup(params);
			
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
    public BaseResponse<List<UnitImg>> selectUnitList(HttpServletRequest request
    		, @RequestBody UnitImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<List<UnitImg>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			List<UnitImg> resultList = contentsService.selectUnitList(params);
	        return new BaseResponse<List<UnitImg>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
		
		if(params.getLanguageCode() == null || "".contentEquals(params.getLanguageCode())){				
			return new BaseResponse<UnitImg>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			UnitImg result = contentsService.selectUnit(params);
	        return new BaseResponse<UnitImg>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
		
		if(StringUtils.isEmpty(params.getUnitGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitGroupCd" + BaseApiMessage.REQUIRED.getMessage());
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
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
			,@RequestPart(value = "realImg", required = true) MultipartFile realImg
			,@RequestPart(value = "frontImg", required = true) MultipartFile frontImg
			,@RequestPart(value = "sideImg", required = true) MultipartFile sideImg
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
	
		if(StringUtils.isEmpty(params.getUnitScanId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "getUnitScanId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
	    try { 
	    	//params.setInsertId(login.getFirstLogin());
	    	params.setUpdateId("admin");
	    	contentsService.updateUnitImg(params);
			return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
	    }catch(Exception e) {
	       LOGGER.error("error:", e);
	       throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
		
		if(StringUtils.isEmpty(params.getUnitScanId())){			
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UnitScanId" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
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
			return new BaseResponse<List<Xrayformation>>(BaseResponseCode.PARAMS_ERROR, "bagScanId" + BaseApiMessage.REQUIRED.getMessage());
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
			return new BaseResponse<List<UnitInformation>>(BaseResponseCode.PARAMS_ERROR, "languageCode" + BaseApiMessage.REQUIRED.getMessage());
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
