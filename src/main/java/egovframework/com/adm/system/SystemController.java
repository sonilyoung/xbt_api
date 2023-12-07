
package egovframework.com.adm.system;

import java.io.File;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.contents.vo.UnitGroup;
import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.system.service.SystemService;
import egovframework.com.adm.system.vo.Menu;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.adm.userMgr.vo.UserBaselinePop;
import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.com.common.service.CommonService;
import egovframework.com.common.vo.Common;
import egovframework.com.excel.ExcelRead;
import egovframework.com.excel.ExcelReadOption;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.global.util.AES256Util;
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
@RequestMapping("/adm/system")
@Api(tags = "Notice Management API")
public class SystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private SystemService systemService;   

    @Autowired
    private ContentsService contentsService;
    
    
    /*파일업로드 저장경로*/
    public static final String FILE_UPLOAD_PATH = GlobalsProperties.getProperty("file.upload.path");    
    				
    /**
     * 공지사항조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectNoticeList.do")
    @ApiOperation(value = "공지사항", notes = "공지사항목록조회.")
    public BaseResponse<List<Notice>> selectNoticeList(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			//공지사항조회
	        return new BaseResponse<List<Notice>>(systemService.selectNoticeList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    /**
     * 공지사항상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectNotice.do")
    @ApiOperation(value = "공지사항상세", notes = "공지사항상세조회.")
    public BaseResponse<Notice> selectNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getNoticeId())){				
			return new BaseResponse<Notice>(BaseResponseCode.PARAMS_ERROR, "NoticeId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			//공지사항조회
	        return new BaseResponse<Notice>(systemService.selectNotice(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    
    
    /**
     * 공지사항등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertNotice.do")
    @ApiOperation(value = "공지사항", notes = "공지사항등록.")
    public BaseResponse<Integer> insertNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getTitle())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Title" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getContents())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Contents" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		//if(StringUtils.isEmpty(params.getLanguageCode())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		//}			
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}
		
		try {
			//공지사항등록
			params.setInsertId(login.getUserId());
			int result = systemService.insertNotice(params);
			
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
     * 공지사항수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateNotice.do")
    @ApiOperation(value = "공지사항", notes = "공지사항수정.")
    public BaseResponse<Integer> updateNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		

		if(StringUtils.isEmpty(params.getNoticeId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "NoticeId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getTitle())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Title" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getContents())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Contents" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		//if(StringUtils.isEmpty(params.getLanguageCode())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		//}		
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
				
		
		try {
			//공지사항등록
			params.setUpdateId(login.getUserId());
			int result = systemService.updateNotice(params);
			
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
     * 공지사항삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteNotice.do")
    @ApiOperation(value = "공지사항", notes = "공지사항삭제.")
    public BaseResponse<Integer> deleteNotice(HttpServletRequest request, @RequestBody Notice params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getNoticeIdList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "NoticeIdList" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//공지사항삭제
			int result = 0;
			
			for(Long id : params.getNoticeIdList()) {
				params.setNoticeId(id);
				result = systemService.deleteNotice(params);
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
     * 메뉴조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectMenuList.do")
    @ApiOperation(value = "메뉴", notes = "메뉴목록조회.")
    public BaseResponse<List<Menu>> selectMenuList(HttpServletRequest request, @RequestBody Menu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<List<Menu>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//메뉴조회
	        return new BaseResponse<List<Menu>>(systemService.selectMenuList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    /**
     * 메뉴상세
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectMenu.do")
    @ApiOperation(value = "메뉴상세", notes = "메뉴상세조회.")
    public BaseResponse<Menu> selectMenu(HttpServletRequest request, @RequestBody Menu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Menu>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Menu>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//메뉴조회
	        return new BaseResponse<Menu>(systemService.selectMenu(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    
    
    /**
     * 메뉴등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertMenu.do")
    @ApiOperation(value = "메뉴", notes = "메뉴등록.")
    public BaseResponse<Integer> insertMenu(HttpServletRequest request, @RequestBody Menu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getPmenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PMenuCd" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		if(StringUtils.isEmpty(params.getMenuName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuName" + BaseApiMessage.REQUIRED.getCode());
		}			
		if(StringUtils.isEmpty(params.getMenuOrder())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuOrder" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//메뉴등록
			params.setInsertId(login.getUserId());
			int result = systemService.insertMenu(params);
			
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
     * 메뉴수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateMenu.do")
    @ApiOperation(value = "메뉴", notes = "메뉴수정.")
    public BaseResponse<Integer> updateMenu(HttpServletRequest request, @RequestBody Menu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		

		if(StringUtils.isEmpty(params.getPmenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PMenuCd" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getCode());
		}			
		if(StringUtils.isEmpty(params.getMenuName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuName" + BaseApiMessage.REQUIRED.getCode());
		}			
		if(StringUtils.isEmpty(params.getMenuOrder())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuOrder" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//메뉴등록
			params.setUpdateId(login.getUserId());
			int result = systemService.updateMenu(params);
			
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
     * 메뉴삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteMenu.do")
    @ApiOperation(value = "메뉴", notes = "메뉴삭제.")
    public BaseResponse<Integer> deleteMenu(HttpServletRequest request, @RequestBody Menu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			//메뉴삭제
			int result = systemService.deleteMenu(params);
			
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
     * 모듈적용메뉴가져오기
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectModuleMenuList.do")
    @ApiOperation(value = "모듈적용메뉴가져오기", notes = "모듈적용메뉴가져오기.")
    public BaseResponse<List<Menu>> selectModuleMenuList(HttpServletRequest request, @RequestBody Menu params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			//메뉴조회
	        return new BaseResponse<List<Menu>>(systemService.selectModuleMenuList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }     
    
    
    
    /**
     * 모듈적용메뉴가져오기
     * 
     * @param param
     * @return XrayContents
     */
	@ResponseBody
	@PostMapping(value="/updateXrayExcelData.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)	
	public BaseResponse<XrayContents> updateXrayExcelData(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
			,@RequestPart(value = "params", required = false) XrayContents params
	) throws Exception{
		LOGGER.debug("========= insertXrayExcelData 엑스레이가방데이터 ========="+ excelFile);

    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}		
		
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
			String[] coloumNm = {"E", "F", "G", "H", "I", "J"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(6); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
			
			int result = 1;
			int errorCnt = 0;
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new XrayContents();
				
				if(null == excelData.get("E") && "".equals(excelData.get("E"))){//가방아이디
					return new BaseResponse<XrayContents>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getCode());					
				}
				
				if(null == excelData.get("F") && "".equals(excelData.get("F"))){//action_div_name
					return new BaseResponse<XrayContents>(BaseResponseCode.PARAMS_ERROR, "ActionDivName(정답구분한글)" + BaseApiMessage.REQUIRED.getCode());
				}
				
				if(null == excelData.get("G") && "".equals(excelData.get("G"))){//action_div
					return new BaseResponse<XrayContents>(BaseResponseCode.PARAMS_ERROR, "ActionDiv(정답구분)" + BaseApiMessage.REQUIRED.getCode());
				}
				
				if(null == excelData.get("H") && "".equals(excelData.get("H"))){//물품명
					return new BaseResponse<XrayContents>(BaseResponseCode.PARAMS_ERROR, "UnitName(물품명)" + BaseApiMessage.REQUIRED.getCode());
				}
				
				if(null == excelData.get("I") && "".equals(excelData.get("I"))){//물품그룹
					return new BaseResponse<XrayContents>(BaseResponseCode.PARAMS_ERROR, "UnitGroupName(물품그룹)" + BaseApiMessage.REQUIRED.getCode());
				}
				
				if(null != excelData.get("J") && "".equals(excelData.get("J"))){//이미지레벨
					return new BaseResponse<XrayContents>(BaseResponseCode.PARAMS_ERROR, "ImageLevel(이미지레벨)" + BaseApiMessage.REQUIRED.getCode());
				}								
				
				params.setBagScanId(excelData.get("E"));
				
				//정답구분한글처리
	            Common cp = new Common();
	            cp.setLanguageCode("kr");
	            cp.setGroupId("actionDiv");
	            cp.setCodeValue(excelData.get("G")); //개봉여부
	            Common cr = commonService.selectCommonDetail(cp);				
				params.setActionDivName(cr.getCodeName());
				//String [] arrAct = excelData.get("F").split("/");
				params.setOpenYn(cr.getMemo1());
				params.setPassYn(cr.getMemo2());	
				
				//정답구분
				params.setActionDiv(excelData.get("G"));
				
				//단품명
				params.setUnitName(excelData.get("H"));
				
				//물품그룹처리 DB조회해서 처리
				params.setUnitGroupName(excelData.get("I"));
				
				//그룹관리조회
				UnitGroup ug = new UnitGroup();
				ug.setLanguageCode("kr");
				ug.setGroupName(params.getUnitGroupName());
				UnitGroup uGresult = contentsService.selectUnitGroupName(ug);
				params.setUnitGroupCd(uGresult.getUnitGroupCd());
				
				//이미지레벨
				params.setStudyLvl(excelData.get("J"));
				
				errorCnt = systemService.updateXrayExcelData(params);
				
				if(errorCnt == 0) {
					result = errorCnt;
				}
			}
			
            if(result > 0 ) {
	            return new BaseResponse<XrayContents>(BaseResponseCode.UPDATE_SUCCESS, BaseResponseCode.UPDATE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<XrayContents>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
            }
	    }catch(Exception e) {
	    	return new BaseResponse<XrayContents>(BaseResponseCode.UNKONWN_ERROR);
	    } 
	    
	}        
	    
}
