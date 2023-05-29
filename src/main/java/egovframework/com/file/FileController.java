
package egovframework.com.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.common.service.CommonService;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
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
@RequestMapping("/file")
@Api(tags = "Login Management API")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private FileStorageService fileStorageService;
    

    /**
     * xray 이미지 업로드 
     * 
     * @param files
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value="/xrayImageUpload.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    @ApiOperation(value = "xray 이미지 업로드", notes = "xray 이미지 업로드")
    public BaseResponse<List<AttachFile>> xrayImageUpload(
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestPart(value = "params", required = false) AttachFile params)
            throws Exception {
        Long atchFileId = null;
        List<AttachFile> saveFiles = null;
        
		
		if(StringUtils.isEmpty(params.getCommand())){				
			return new BaseResponse<List<AttachFile>>(BaseResponseCode.PARAMS_ERROR, "command" + BaseApiMessage.REQUIRED.getCode());
		}	        
        
        if (files != null) {
        	int i = 1;
            saveFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                // 파일 생성
            	AttachFile detail;
            	if("unit".equals(params.getCommand())){
            		detail = fileStorageService.createUnitImageFile(params.getCommand(), params, file);
            	}else {
            		detail = fileStorageService.createXrayImageFile(params.getCommand(), params, file);
            	}
                
                if (detail != null) {
                    // 기존 파일첨부 아이디가 있는 경우 해당 아이디로 파일 정보 생성
                    if (atchFileId != null) {
                        detail.setAtchFileId(atchFileId);
                    }
                    else {
                        detail.setFileSn(i++);
                    }
                    saveFiles.add(detail);
                }
            }
        }
        // 파일 정보 생성
        //fileService.saveFiles(saveFiles, deleteFiles);

        List<AttachFile> result = saveFiles != null ? saveFiles : new ArrayList<AttachFile>();
        return new BaseResponse<>(result);
    }  
    
    /**
     * 교육생화면 ui다국어처리
     * 
     * @param param
     * @return Company
     
    @PostMapping("/selectLanguageApplyInfo.do")
    @ApiOperation(value = "교육생화면 ui다국어처리", notes = "교육생화면 ui다국어처리")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public BaseResponse<HashMap<String, Object>> selectLanguageApplyInfo(HttpServletRequest request, @RequestBody Common params) {
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<HashMap<String, Object>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//다국어처리조회
			List<Common> result = commonService.selectLanguageApplyList(params);
			HashMap<String, Object> languageApply = new HashMap<String, Object>();
			if(result!=null) {
				for(Common c : result) {
					languageApply.put("groupId", c.getGroupId());//구분코드
					languageApply.put(c.getCodeName(), c.getCodeValue());//메세지
					languageApply.put("languageCode", c.getLanguageCode());// 언어코드
				}
			}
	        return new BaseResponse<HashMap<String, Object>>(languageApply);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }        
      */
    
}
