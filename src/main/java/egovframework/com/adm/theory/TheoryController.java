
package egovframework.com.adm.theory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import egovframework.com.adm.theory.vo.TheoryFile;
import egovframework.com.adm.theory.vo.TheoryGroup;
import egovframework.com.common.vo.SeqGroupCode;
import egovframework.com.excel.ExcelRead;
import egovframework.com.excel.ExcelReadOption;
import egovframework.com.file.service.FileService;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.common.GlobalsProperties;
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
    
    @Autowired
    private XbtImageService xbtImageService;
    
    @Autowired
    private FileService fileService;       
    
    /*파일업로드 저장경로*/
    public static final String FILE_UPLOAD_PATH = GlobalsProperties.getProperty("file.upload.path");
    				
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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

		if(StringUtils.isEmpty(params.getGroupNoList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "GroupNoList" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			int result = 0;
			for(Long tg : params.getGroupNoList()) {
				//이론그룹삭제
				params.setGroupNo(tg);
				result = theoryService.deleteTheoryGroup(params);
			}
			
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
			
			if(!StringUtils.isEmpty(params.getSearchval())){
				if("사지선다".contains(params.getSearchval())){
					params.setSearchval("A");
				}
				
				if("O/X".contains(params.getSearchval())){
					params.setSearchval("B");
				}
				
				if("이미지선다".contains(params.getSearchval())){
					params.setSearchval("C");
				}
				
				if("이미지+사지선다".contains(params.getSearchval())){
					params.setSearchval("D");
				}
			}
				
			
			//이론조회
	        return new BaseResponse<List<Theory>>(theoryService.selectTheoryList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
			Theory result = theoryService.selectTheory(params);
			xbtImageService.selectTheoryImg(result);
			
	        return new BaseResponse<Theory>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            @RequestPart(value = "params", required = false) Theory params)throws Exception {
    	
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
		
		if(StringUtils.isEmpty(params.getQuestion())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Question" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		XbtSeq seq = new XbtSeq();
		seq.setSeqInfo(SeqGroupCode.XBT_THEORY_ID.getCode());
		XbtSeq unitId = contentsService.selectXbtSeq(seq);
		params.setQuestionId(unitId.getSeqId());		

		
		//사지선다(A), OX(B), 이미지사선다(C), 이미지+사지선다형(D)
		if("A".equals(params.getQuestionType())) {
			if(StringUtils.isEmpty(params.getChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice1" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice2" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice3" + BaseApiMessage.REQUIRED.getCode());
			}		
			
			if(StringUtils.isEmpty(params.getChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice4" + BaseApiMessage.REQUIRED.getCode());
			}				
		}else if("B".equals(params.getQuestionType())) {//OX(B)
		}else if("C".equals(params.getQuestionType())) {//이미지사선다(C)
			
	        if (files != null) {
	        	int i = 1;
	            for (MultipartFile file : files) {
	                // 파일 생성
	            	AttachFile detail = fileStorageService.createTheoryImageFile(String.valueOf(i), params, file);
	            	if(i==1) {
	            		params.setChoice1(detail.getSaveFileName());
	            	}else if(i==2) {
	            		params.setChoice2(detail.getSaveFileName());
	            	}else if(i==3) {
	            		params.setChoice3(detail.getSaveFileName());
	            	}else if(i==4) {
	            		params.setChoice4(detail.getSaveFileName());
	            	}
	            	i++;
	            }
	        }
		}else if("D".equals(params.getQuestionType())) {//이미지+사지선다형(D)
			if(StringUtils.isEmpty(params.getChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice1" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice2" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice3" + BaseApiMessage.REQUIRED.getCode());
			}		
			
			if(StringUtils.isEmpty(params.getChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice4" + BaseApiMessage.REQUIRED.getCode());
			}	
			
	        if (files != null) {
	            for (MultipartFile file : files) {
	                // 파일 생성
	            	AttachFile detail = fileStorageService.createTheoryImageFile("Q", params, file);
	            	params.setMultiPlusImgName(detail.getSaveFileName());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
		
		if(StringUtils.isEmpty(params.getQuestion())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Question" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getUseYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UseYn" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		//사지선다(A), OX(B), 이미지사선다(C), 이미지+사지선다형(D)
		if("A".equals(params.getQuestionType())) {
			if(StringUtils.isEmpty(params.getChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice1" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice2" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice3" + BaseApiMessage.REQUIRED.getCode());
			}		
			
			if(StringUtils.isEmpty(params.getChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice4" + BaseApiMessage.REQUIRED.getCode());
			}				
		}else if("B".equals(params.getQuestionType())) {//OX(B)
		}else if("C".equals(params.getQuestionType())) {//이미지사선다(C)
			
			if(StringUtils.isEmpty(params.getCommand())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Command" + BaseApiMessage.REQUIRED.getCode());
			}			
			
			if("true".equals(params.getCommand())) {
		        if (files != null) {
		        	int i = 1;
		            for (MultipartFile file : files) {
		                // 파일 생성
		            	AttachFile detail = fileStorageService.createTheoryImageFile(String.valueOf(i), params, file);
		            	if(i==1) {
		            		params.setChoice1(detail.getSaveFileName());
		            	}else if(i==2) {
		            		params.setChoice2(detail.getSaveFileName());
		            	}else if(i==3) {
		            		params.setChoice3(detail.getSaveFileName());
		            	}else if(i==4) {
		            		params.setChoice4(detail.getSaveFileName());
		            	}
		            	i++;
		            }
		        }
			}
			

		}else if("D".equals(params.getQuestionType())) {//이미지+사지선다형(D)
			if(StringUtils.isEmpty(params.getCommand())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Command" + BaseApiMessage.REQUIRED.getCode());
			}			
			
			if(StringUtils.isEmpty(params.getChoice1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice1" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice2())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice2" + BaseApiMessage.REQUIRED.getCode());
			}	
			
			if(StringUtils.isEmpty(params.getChoice3())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice3" + BaseApiMessage.REQUIRED.getCode());
			}		
			
			if(StringUtils.isEmpty(params.getChoice4())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Choice4" + BaseApiMessage.REQUIRED.getCode());
			}				
			
			if("true".equals(params.getCommand())) {
		        if (files != null) {
		            for (MultipartFile file : files) {
		                // 파일 생성
		            	AttachFile detail = fileStorageService.createTheoryImageFile("Q", params, file);
		            	params.setMultiPlusImgName(detail.getSaveFileName());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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

		if(StringUtils.isEmpty(params.getQuestionIdList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "QuestionIdList" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			int result = 0;
			for(String t : params.getQuestionIdList()) {
				//이론삭제
				params.setQuestionId(t);
				result = theoryService.deleteTheory(params);
			}

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
     * 이론파일조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTheoryFileList.do")
    @ApiOperation(value = "이론파일조회", notes = "이론파일조회")
    public BaseResponse<List<TheoryFile>> selectTheoryFileList(HttpServletRequest request, @RequestBody TheoryFile params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//이론조회
			List<TheoryFile> result = theoryService.selectTheoryFileList(params);
			
			
			for(TheoryFile tf : result) {
				
	            AttachFile af = new AttachFile();
	            af.setFileTarget(tf.getTheoryNo());
	            List<AttachFile> existFileList = fileService.selectFileAll(af);				
	            tf.setFiles(existFileList);			
			}
	        return new BaseResponse<List<TheoryFile>>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 이론파일업로드 
     * 
     * @param files
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value="/insertTheoryFile.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "공통파일업로드", notes = "공통파일업로드")
    public BaseResponse<TheoryFile> fileUpload(
    		HttpServletRequest request,
            @RequestPart(value = "files", required = true) MultipartFile[] files,
            @RequestPart(value = "params", required = false) TheoryFile params)
            throws Exception {
    	
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
        List<AttachFile> saveFiles = null;
        
		if(StringUtils.isEmpty(files)){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "Files" + BaseApiMessage.REQUIRED.getCode());
		}	   
		
		if(StringUtils.isEmpty(params.getEduCode())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "EduCode" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getTitle())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "Title" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getContents())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "Contents" + BaseApiMessage.REQUIRED.getCode());
		}			
        
        if (files != null) {
        	int i = 1;
            saveFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                // 파일 생성
            	AttachFile detail = fileStorageService.createFile(file);
                if (detail != null) {
                    detail.setFileSn(i++);
                    saveFiles.add(detail);
                }
            }
        }
        
		//이론파일강사등록
		params.setInsertId(login.getUserId());
		int result = theoryService.insertTheoryFile(params);        
        
        for(AttachFile af : saveFiles) {
        	// 파일 정보 생성
        	af.setFileTarget(params.getTheoryNo());
        	af.setInsertId(login.getUserId());
        	fileService.insertFile(af);
        }
        
        List<AttachFile> fileList = saveFiles != null ? saveFiles : new ArrayList<AttachFile>();
        params.setFiles(fileList);
		if(result>0) {
			return new BaseResponse<TheoryFile>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage(), params);
		}else {
			return new BaseResponse<TheoryFile>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage(), params);
		}
    }
    
    /**
     * 이론파일강사상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTheoryFile.do")
    @ApiOperation(value = "이론파일강사상세", notes = "이론파일강사상세조회.")
    public BaseResponse<TheoryFile> selectTheoryFile(HttpServletRequest request, @RequestBody TheoryFile params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getTheoryNo())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "TheoryNo" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//이론파일강사조회
			TheoryFile result = theoryService.selectTheoryFile(params);
            AttachFile af = new AttachFile();
            af.setFileTarget(params.getTheoryNo());
            List<AttachFile> existFileList = fileService.selectFileAll(af);
            result.setFiles(existFileList);
	        return new BaseResponse<TheoryFile>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    
    /**
     * 이론파일강사수정
     * 
     * @param param
     * @return Company
     * @throws Exception 
     */
    @PostMapping("/updateTheoryFile.do")
    @ApiOperation(value = "이론파일강사", notes = "이론파일강사수정.")
    public BaseResponse<TheoryFile> updateTheoryFile(
    		HttpServletRequest request,
            @RequestPart(value = "files", required = true) MultipartFile[] files,
            @RequestPart(value = "params", required = false) TheoryFile params) throws Exception{    	
    	
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
        List<AttachFile> saveFiles = null;
        
		if(StringUtils.isEmpty(files)){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "Files" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getEduCode())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "EduCode" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getTheoryNo())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "TheoryNo" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getTitle())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "Title" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getContents())){				
			return new BaseResponse<TheoryFile>(BaseResponseCode.PARAMS_ERROR, "Contents" + BaseApiMessage.REQUIRED.getCode());
		}			
        
        if (files != null) {
        	int i = 1;
            saveFiles = new ArrayList<>();
            
            AttachFile af = new AttachFile();
            af.setFileTarget(params.getTheoryNo());
            List<AttachFile> existFileList = fileService.selectFileAll(af);
            for(AttachFile ef : existFileList) {
            	fileStorageService.deleteFile(ef);
            }
            
            fileService.deleteFileAll(af);
            
            for (MultipartFile file : files) {
                // 파일 생성
            	AttachFile detail = fileStorageService.createFile(file);
                if (detail != null) {
                    detail.setFileSn(i++);
                    saveFiles.add(detail);
                }
            }
        }
        
		//이론파일강사등록
		params.setUpdateId(login.getUserId());
		int result = theoryService.updateTheoryFile(params);       
        
        for(AttachFile af : saveFiles) {
        	// 파일 정보 생성
        	af.setFileTarget(params.getTheoryNo());
        	af.setInsertId(login.getUserId());
        	fileService.insertFile(af);
        }
        
        List<AttachFile> fileList = saveFiles != null ? saveFiles : new ArrayList<AttachFile>();
        params.setFiles(fileList);
		if(result>0) {
			return new BaseResponse<TheoryFile>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage(), params);
		}else {
			return new BaseResponse<TheoryFile>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage(), params);
		}
    }    
    
    
    
    /**
     * 이론파일강사삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteTheoryFile.do")
    @ApiOperation(value = "이론파일강사", notes = "이론파일강사삭제.")
    public BaseResponse<Integer> deleteTheoryFile(HttpServletRequest request, @RequestBody TheoryFile params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getTheoryNoList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "TheoryNoList" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			
			int result = 0;
			for(Long t : params.getTheoryNoList()) {
				//이론삭제
				params.setTheoryNo(t);
				result = theoryService.deleteTheoryFile(params);
				
	            AttachFile af = new AttachFile();
	            af.setFileTarget(params.getTheoryNo());
	            List<AttachFile> existFileList = fileService.selectFileAll(af);
	            for(AttachFile ef : existFileList) {
	            	fileStorageService.deleteFile(ef);
	            }
	            
	            fileService.deleteFileAll(af);				
			}			
			
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
    
    
	
	//이론문제 등록
	@PostMapping(value="/insertTheoryExcel.do")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertTheoryExcel(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= insertTheoryExcel 이론문제등록 ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "N"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
			
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int result = 0;
			
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
				
				XbtSeq seq = new XbtSeq();
				seq.setSeqInfo(SeqGroupCode.XBT_THEORY_ID.getCode());
				XbtSeq unitId = contentsService.selectXbtSeq(seq);
				
	            params.put("questionId", unitId.getSeqId());//문제아이디
	            params.put("questionType", excelData.get("B"));//B문제타입
	            params.put("studyLvl", excelData.get("C"));//C학습레벨
	            params.put("useYn", excelData.get("D"));//D사용여부
	            
	            /*
	            Common cp = new Common();
	            cp.setLanguageCode("kr");
	            cp.setGroupId("eduName");
	            cp.setCodeName(excelData.get("E"));
	            cp.setCommand("codeName");
	            Common cr = commonService.selectCommon(cp);
	            */
	            
	            params.put("lageGroupCd", excelData.get("E"));//대그룹
	            
	            params.put("middleGroupCd", excelData.get("F"));//F중그룹
	            params.put("smallGroupCd", excelData.get("G"));//G소그룹
	            params.put("question", excelData.get("H"));//H문제
	            params.put("actionDiv", excelData.get("I"));//I정답
	            params.put("choice1", excelData.get("J"));//J지문1
	            params.put("choice2", excelData.get("K"));//K지문2
	            params.put("choice3", excelData.get("L"));//L지문3
	            params.put("choice4", excelData.get("M"));//M지문4
	            params.put("memo", excelData.get("N"));//N메모
	            
	            result = theoryService.insertTheoryExcel(params);

			}
			
			
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}			
	    
	    
}
