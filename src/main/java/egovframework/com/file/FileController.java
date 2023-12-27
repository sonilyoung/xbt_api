
package egovframework.com.file;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
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

    //private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    //private OfficeMessageSource officeMessageSource;

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
        //Long atchFileId = null;
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
                    detail.setFileSn(i++);
                    saveFiles.add(detail);
                }
            }
        }
        // 파일 정보 생성
        //fileStorageService.saveFiles(saveFiles, deleteFiles);

        List<AttachFile> result = saveFiles != null ? saveFiles : new ArrayList<AttachFile>();
        return new BaseResponse<>(result);
    }  


    
}
