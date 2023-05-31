
package egovframework.com.adm.theory;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.theory.service.TheoryService;
import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.adm.theory.vo.TheoryGroup;
import egovframework.com.common.vo.SeqGroupCode;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
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
@RequestMapping("/adm/theory")
@Api(tags = "TheoryGroup Management API")
public class TheoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TheoryController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private TheoryService theoryService;    
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private ContentsService contentsService;
    
    				
    /**
     * 이론그룹조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTheoryGroupList.do")
    @ApiOperation(value = "이론그룹", notes = "이론그룹목록조회.")
    public BaseResponse<List<TheoryGroup>> selectTheoryGroupList(HttpServletRequest request, @RequestBody TheoryGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getGroupType())){				
			return new BaseResponse<List<TheoryGroup>>(BaseResponseCode.PARAMS_ERROR, "GroupType" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//이론그룹조회
	        return new BaseResponse<List<TheoryGroup>>(theoryService.selectTheoryGroupList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    /**
     * 이론그룹상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTheoryGroup.do")
    @ApiOperation(value = "이론그룹상세", notes = "이론그룹상세조회.")
    public BaseResponse<TheoryGroup> selectTheoryGroup(HttpServletRequest request, @RequestBody TheoryGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getGroupNo())){				
			return new BaseResponse<TheoryGroup>(BaseResponseCode.PARAMS_ERROR, "GroupNo" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			//이론그룹조회
	        return new BaseResponse<TheoryGroup>(theoryService.selectTheoryGroup(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
    
    
    
    /**
     * 이론그룹등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertTheoryGroup.do")
    @ApiOperation(value = "이론그룹", notes = "이론그룹등록.")
    public BaseResponse<Integer> insertTheoryGroup(HttpServletRequest request, @RequestBody TheoryGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getGroupType())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "GroupType" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getTheoryGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "TheoryGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getTheoryGroupName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "TheoryGroupName" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		
		try {
			//이론그룹등록
			params.setInsertId(login.getUserId());
			int result = theoryService.insertTheoryGroup(params);
			
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
     * 이론그룹수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateTheoryGroup.do")
    @ApiOperation(value = "이론그룹", notes = "이론그룹수정.")
    public BaseResponse<Integer> updateTheoryGroup(HttpServletRequest request, @RequestBody TheoryGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getGroupNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "GroupNo" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getTheoryGroupName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "TheoryGroupName" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UseYn" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//이론그룹등록
			int result = theoryService.updateTheoryGroup(params);
			
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
     * 이론그룹삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteTheoryGroup.do")
    @ApiOperation(value = "이론그룹", notes = "이론그룹삭제.")
    public BaseResponse<Integer> deleteTheoryGroup(HttpServletRequest request, @RequestBody TheoryGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}

		if(StringUtils.isEmpty(params.getGroupNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "GroupNo" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			//이론그룹삭제
			int result = theoryService.deleteTheoryGroup(params);
			
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
     * 이론조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTheoryList.do")
    @ApiOperation(value = "이론", notes = "이론목록조회.")
    public BaseResponse<List<Theory>> selectTheoryList(HttpServletRequest request, @RequestBody Theory params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//이론조회
	        return new BaseResponse<List<Theory>>(theoryService.selectTheoryList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    /**
     * 이론상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTheory.do")
    @ApiOperation(value = "이론상세", notes = "이론상세조회.")
    public BaseResponse<Theory> selectTheory(HttpServletRequest request, @RequestBody Theory params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getQuestionId())){				
			return new BaseResponse<Theory>(BaseResponseCode.PARAMS_ERROR, "QuestionId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			//이론조회
	        return new BaseResponse<Theory>(theoryService.selectTheory(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
    
    
    
    /**
     * 이론등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping(value="/insertTheory.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "이론", notes = "이론등록")
    public BaseResponse<Integer> insertTheory(
    		HttpServletRequest request, 
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestPart(value = "params", required = true) Theory params)throws Exception {
    	
    	Login login = loginService.getLoginInfo(request);
    	
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getQuestionType())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "QuestionType" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "StudyLvl" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getLageGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LageGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getMiddleGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MiddleGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getSmallGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "SmallGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getActionDiv())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ActionDiv" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		XbtSeq seq = new XbtSeq();
		seq.setSeqInfo(SeqGroupCode.XBT_THEORY_ID.getCode());
		XbtSeq unitId = contentsService.selectXbtSeq(seq);
		params.setQuestionId(unitId.getSeqId());		

		
		//사지선다(A), OX(B), 이미지선다(C), 이미지사지선다(D)
		if("A".equals(params.getQuestionType())) {
			
			if(StringUtils.isEmpty(params.getMultiQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiQuestion" + BaseApiMessage.REQUIRED.getCode());
			}				
			
			if(StringUtils.isEmpty(params.getMultiChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice1" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getMultiChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice2" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getMultiChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice3" + BaseApiMessage.REQUIRED.getCode());
			}		
			
			if(StringUtils.isEmpty(params.getMultiChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice4" + BaseApiMessage.REQUIRED.getCode());
			}				
		}else if("B".equals(params.getQuestionType())) {
			
			if(StringUtils.isEmpty(params.getOxQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "OxQuestion" + BaseApiMessage.REQUIRED.getCode());
			}	
			
		}else if("C".equals(params.getQuestionType())) {
			if(StringUtils.isEmpty(params.getActionDiv())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ActionDiv" + BaseApiMessage.REQUIRED.getCode());
			}			
			
			if(StringUtils.isEmpty(params.getMultiImgQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiImgQuestion" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			
	        if (files != null) {
	            for (MultipartFile file : files) {
	                // 파일 생성
	            	AttachFile detail = fileStorageService.createTheoryImageFile(params, file);
	            	if(detail.getSaveFileName().contains("1")) {
	            		params.setMultiImgChoice1(detail.getSaveFileName());
	            	}else if(detail.getSaveFileName().contains("2")) {
	            		params.setMultiImgChoice2(detail.getSaveFileName());
	            	}else if(detail.getSaveFileName().contains("3")) {
	            		params.setMultiImgChoice3(detail.getSaveFileName());
	            	}else if(detail.getSaveFileName().contains("4")) {
	            		params.setMultiImgChoice4(detail.getSaveFileName());
	            	}
	            }
	        }
		}else if("D".equals(params.getQuestionType())) {
			if(StringUtils.isEmpty(params.getActionDiv())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ActionDiv" + BaseApiMessage.REQUIRED.getCode());
			}			
			
			if(StringUtils.isEmpty(params.getMultiPlusQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiPlusQuestion" + BaseApiMessage.REQUIRED.getCode());
			}
			
			if(StringUtils.isEmpty(params.getMultiPlusChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice1" + BaseApiMessage.REQUIRED.getCode());
			}
			if(StringUtils.isEmpty(params.getMultiPlusChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice2" + BaseApiMessage.REQUIRED.getCode());
			}
			if(StringUtils.isEmpty(params.getMultiPlusChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice3" + BaseApiMessage.REQUIRED.getCode());
			}			
			if(StringUtils.isEmpty(params.getMultiPlusChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice4" + BaseApiMessage.REQUIRED.getCode());
			}			
			
	        if (files != null) {
	            for (MultipartFile file : files) {
	                // 파일 생성
	            	AttachFile detail = fileStorageService.createTheoryImageFile(params, file);
	            	if(detail.getSaveFileName().contains("Q")) {
	            		params.setMultiPlusImg(detail.getSaveFileName());
	            	}	            	
	            }
	        }
		}
		
		try {
			//이론등록
			params.setInsertId(login.getUserId());
			int result = theoryService.insertTheory(params);
			
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
     * 이론수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping(value="/updateTheory.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "이론", notes = "이론수정")
    public BaseResponse<Integer> updateTheory(
    		HttpServletRequest request, 
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestPart(value = "params", required = true) Theory params)throws Exception {    	
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getQuestionId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "QuestionId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getStudyLvl())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "StudyLvl" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getLageGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LageGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getMiddleGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MiddleGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getSmallGroupCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "SmallGroupCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getActionDiv())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "ActionDiv" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		//사지선다(A), OX(B), 이미지선다(C), 이미지사지선다(D)
		if("A".equals(params.getQuestionType())) {
			if(StringUtils.isEmpty(params.getMultiQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiQuestion" + BaseApiMessage.REQUIRED.getCode());
			}				
			
			
			if(StringUtils.isEmpty(params.getMultiChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice1" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getMultiChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice2" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getMultiChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice3" + BaseApiMessage.REQUIRED.getCode());
			}		
			
			if(StringUtils.isEmpty(params.getMultiChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiChoice4" + BaseApiMessage.REQUIRED.getCode());
			}				
		}else if("B".equals(params.getQuestionType())) {
			if(StringUtils.isEmpty(params.getOxQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "OxQuestion" + BaseApiMessage.REQUIRED.getCode());
			}	
			
		}else if("C".equals(params.getQuestionType())) {
			
			if(StringUtils.isEmpty(params.getMultiImgQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiImgQuestion" + BaseApiMessage.REQUIRED.getCode());
			}	
			
	        if (files != null) {
	            for (MultipartFile file : files) {
	                // 파일 생성
	            	AttachFile detail = fileStorageService.createTheoryImageFile(params, file);
	            	if(detail.getSaveFileName().contains("1")) {
	            		params.setMultiImgChoice1(detail.getSaveFileName());
	            	}else if(detail.getSaveFileName().contains("2")) {
	            		params.setMultiImgChoice2(detail.getSaveFileName());
	            	}else if(detail.getSaveFileName().contains("3")) {
	            		params.setMultiImgChoice3(detail.getSaveFileName());
	            	}else if(detail.getSaveFileName().contains("4")) {
	            		params.setMultiImgChoice4(detail.getSaveFileName());
	            	}
	            }
	        }
		}else if("D".equals(params.getQuestionType())) {
			if(StringUtils.isEmpty(params.getMultiPlusQuestion())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MultiPlusQuestion" + BaseApiMessage.REQUIRED.getCode());
			}
			
			if(StringUtils.isEmpty(params.getMultiPlusChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice1" + BaseApiMessage.REQUIRED.getCode());
			}
			if(StringUtils.isEmpty(params.getMultiPlusChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice2" + BaseApiMessage.REQUIRED.getCode());
			}
			if(StringUtils.isEmpty(params.getMultiPlusChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice3" + BaseApiMessage.REQUIRED.getCode());
			}			
			if(StringUtils.isEmpty(params.getMultiPlusChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "multiPlusChoice4" + BaseApiMessage.REQUIRED.getCode());
			}			
			
	        if (files != null) {
	            for (MultipartFile file : files) {
	                // 파일 생성
	            	AttachFile detail = fileStorageService.createTheoryImageFile(params, file);
	            	if(detail.getSaveFileName().contains("Q")) {
	            		params.setMultiPlusImg(detail.getSaveFileName());
	            	}	            	
	            }
	        }
		}
						
		
		try {
			//이론등록
			int result = theoryService.updateTheory(params);
			
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
     * 이론삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteTheory.do")
    @ApiOperation(value = "이론", notes = "이론삭제.")
    public BaseResponse<Integer> deleteTheory(HttpServletRequest request, @RequestBody Theory params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}

		if(StringUtils.isEmpty(params.getQuestionId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "QuestionId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//이론삭제
			int result = theoryService.deleteTheory(params);
			
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
