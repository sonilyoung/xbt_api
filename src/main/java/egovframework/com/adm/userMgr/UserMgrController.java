
package egovframework.com.adm.userMgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.userMgr.service.UserMgrService;
import egovframework.com.adm.userMgr.vo.UserBaseline;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.adm.userMgr.vo.UserBaselineSub;
import egovframework.com.adm.userMgr.vo.UserBaselineSubInfo;
import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.com.global.OfficeMessageSource;
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
@RequestMapping("/adm/userMgr")
@Api(tags = "User Management API")
public class UserMgrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMgrController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserMgrService userMgrService;
    
    
    /**
     * 교육생 정보조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectUserList.do")
    @ApiOperation(value = "교육생정보관리", notes = "교육생정보를 관리한다.")
    public BaseResponse<List<UserInfo>> getUserList(HttpServletRequest request, @RequestBody UserInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<UserInfo> resultList = userMgrService.selectUserList(params);
			
			for(UserInfo u : resultList) {
	        	AES256Util aesUtil = new AES256Util();
	            String pwEnc = aesUtil.decrypt(u.getUserPw());
	            u.setUserPw(pwEnc);					
			}
			
	        return new BaseResponse<List<UserInfo>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    
    /**
     * 교육생 상세정보조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectUser.do")
    @ApiOperation(value = "교육생 상세정보조회", notes = "교육생 상세정보조회 관리한다.")
    public BaseResponse<UserInfo> selectUser(HttpServletRequest request, @RequestBody UserInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			UserInfo resultList = userMgrService.selectUser(params);
        	AES256Util aesUtil = new AES256Util();
            String pwEnc = aesUtil.decrypt(resultList.getUserPw());
            resultList.setUserPw(pwEnc);			
	        return new BaseResponse<UserInfo>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
    /**
     * 교육생등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertUser.do")
    @ApiOperation(value = "교육생", notes = "교육생등록.")
    public BaseResponse<Integer> insertUser(HttpServletRequest request, @RequestBody UserInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getEduName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduName" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userId" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserNmCh())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNmCh" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNmEn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNmEn" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getSex())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "sex" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getBirthDay())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "birthDay" + BaseApiMessage.REQUIRED.getCode());
		}		

		if(StringUtils.isEmpty(params.getAge())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "age" + BaseApiMessage.REQUIRED.getCode());
		}		


		if(StringUtils.isEmpty(params.getAddress())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "address" + BaseApiMessage.REQUIRED.getCode());
		}		

		if(StringUtils.isEmpty(params.getUserPw())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userPw" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getDept())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "dept" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getPosition())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "position" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getWork())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "work" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareer1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career1" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareer2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career2" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getRegistNumber())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "registNumber" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getEmployStatusYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "employStatusYn" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getLastEdu())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEdu" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getWriteDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "writeDate" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCompany())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "company" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getHpNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "hpNo" + BaseApiMessage.REQUIRED.getCode());
		}


		if(StringUtils.isEmpty(params.getEmail())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "email" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareerYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerYn" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareerStartDate1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerStartDate1" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareerEndDate1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerEndDate1" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getCareerCompany1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerCompany1" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerPosition1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerPosition1" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerStartDate2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerStartDate2" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerEndDate2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerEndDate2" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerCompany2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerCompany2" + BaseApiMessage.REQUIRED.getCode());
		}	

		if(StringUtils.isEmpty(params.getCareerPosition2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerPosition2" + BaseApiMessage.REQUIRED.getCode());
		}	

		if(StringUtils.isEmpty(params.getLastEduName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduName" + BaseApiMessage.REQUIRED.getCode());
		}	
		if(StringUtils.isEmpty(params.getLastEduDept())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduDept" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLastEduYear())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduYear" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getLastEduEnd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduEnd" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		
		try {
			//교육생등록
			params.setInsertId(login.getUserId());
        	AES256Util aesUtil = new AES256Util();
            String pwEnc = aesUtil.encrypt(params.getUserPw());
            params.setUserPw(pwEnc);
			int result = userMgrService.insertUser(params);
			
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
     * 교육생수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateUser.do")
    @ApiOperation(value = "교육생", notes = "교육생수정.")
    public BaseResponse<Integer> updateUser(HttpServletRequest request, @RequestBody UserInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getEduName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduName" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userId" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserNmCh())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNmCh" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNmEn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNmEn" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getSex())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "sex" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getBirthDay())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "birthDay" + BaseApiMessage.REQUIRED.getCode());
		}		

		if(StringUtils.isEmpty(params.getAge())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "age" + BaseApiMessage.REQUIRED.getCode());
		}		


		if(StringUtils.isEmpty(params.getAddress())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "address" + BaseApiMessage.REQUIRED.getCode());
		}		

		if(StringUtils.isEmpty(params.getUserPw())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userPw" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getDept())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "dept" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getPosition())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "position" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getWork())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "work" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareer1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career1" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareer2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career2" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getRegistNumber())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "registNumber" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getEmployStatusYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "employStatusYn" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getLastEdu())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEdu" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getWriteDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "writeDate" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCompany())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "company" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getHpNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "hpNo" + BaseApiMessage.REQUIRED.getCode());
		}


		if(StringUtils.isEmpty(params.getEmail())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "email" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareerYn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerYn" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareerStartDate1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerStartDate1" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCareerEndDate1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerEndDate1" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getCareerCompany1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerCompany1" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerPosition1())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerPosition1" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerStartDate2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerStartDate2" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerEndDate2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerEndDate2" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getCareerCompany2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerCompany2" + BaseApiMessage.REQUIRED.getCode());
		}	

		if(StringUtils.isEmpty(params.getCareerPosition2())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerPosition2" + BaseApiMessage.REQUIRED.getCode());
		}	

		if(StringUtils.isEmpty(params.getLastEduName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduName" + BaseApiMessage.REQUIRED.getCode());
		}	
		if(StringUtils.isEmpty(params.getLastEduDept())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduDept" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		if(StringUtils.isEmpty(params.getLastEduYear())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduYear" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		if(StringUtils.isEmpty(params.getLastEduEnd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduEnd" + BaseApiMessage.REQUIRED.getCode());
		}	
		
		
		try {
			//교육생등록
			params.setUpdateId(login.getUserId());
        	AES256Util aesUtil = new AES256Util();
            String pwEnc = aesUtil.encrypt(params.getUserPw());
            params.setUserPw(pwEnc);			
			int result = userMgrService.updateUser(params);
			
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
     * 교육생삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteUser.do")
    @ApiOperation(value = "교육생", notes = "교육생삭제.")
    public BaseResponse<Integer> deleteUser(HttpServletRequest request, @RequestBody UserInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//교육생삭제
			int result = userMgrService.deleteUser(params);
			
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
     * 교육생 차수관리 상단목록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getUserBaselineList.do")
    @ApiOperation(value = "교육생 차수관리 상단목록", notes = "교육생 차수관리 상단목록을 조회한다.")
    public BaseResponse<List<UserBaseline>> getUserBaselineList(HttpServletRequest request
    		, @RequestBody UserBaseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<UserBaseline> resultList = userMgrService.getUserBaselineList(params);
	        return new BaseResponse<List<UserBaseline>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    
    /**
     * 교육생 차수관리 교육생정보
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getUserBaselineSubList.do")
    @ApiOperation(value = "교육생 차수관리 교육생정보", notes = "교육생 차수관리 교육생정보를 조회한다.")
    public BaseResponse<List<UserBaselineSub>> getUserBaselineSubList(HttpServletRequest request
    		, @RequestBody UserBaselineSub params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getProcYear() == null || "".equals(params.getProcYear())){				
			return new BaseResponse<List<UserBaselineSub>>(BaseResponseCode.PARAMS_ERROR, "procYear는 필수값입니다");	
		}			
		
		if(params.getProcCd() == null || "".equals(params.getProcCd())){				
			return new BaseResponse<List<UserBaselineSub>>(BaseResponseCode.PARAMS_ERROR, "procCd는 필수값입니다");	
		}			

		if(params.getProcSeq() == null || "".equals(params.getProcSeq())){				
			return new BaseResponse<List<UserBaselineSub>>(BaseResponseCode.PARAMS_ERROR, "procSeq는 필수값입니다");	
		}					
		
		try {
			List<UserBaselineSub> resultList = userMgrService.getUserBaselineSubList(params);
	        return new BaseResponse<List<UserBaselineSub>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    
    /**
     * 교육생 차수관리 교육생 상세정보
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getUserBaselineSubDetail.do")
    @ApiOperation(value = "교육생 차수관리 교육생정보", notes = "교육생 차수관리 교육생정보를 조회한다.")
    public BaseResponse<UserBaselineDetail> getUserBaselineSubDetail(HttpServletRequest request
    		, @RequestBody UserBaselineDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(params.getProcYear() == null || "".equals(params.getProcYear())){				
			return new BaseResponse<UserBaselineDetail>(BaseResponseCode.PARAMS_ERROR, "procYear는 필수값입니다");	
		}			
		
		if(params.getProcCd() == null || "".equals(params.getProcCd())){				
			return new BaseResponse<UserBaselineDetail>(BaseResponseCode.PARAMS_ERROR, "procCd는 필수값입니다");	
		}			

		if(params.getProcSeq() == null || "".equals(params.getProcSeq())){				
			return new BaseResponse<UserBaselineDetail>(BaseResponseCode.PARAMS_ERROR, "procSeq는 필수값입니다");	
		}			
		
		try {
			UserBaselineDetail result = (UserBaselineDetail) userMgrService.getUserBaselineSubDetail(params);
	        return new BaseResponse<UserBaselineDetail>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
        
    
    
    /**
     * 교육생 차수관리 교육생 상세 하위정보목록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/getUserBaselineSubDetailList.do")
    @ApiOperation(value = "교육생 차수관리 교육생 상세 하위정보목록", notes = "교육생 차수관리 교육생 상세 하위정보목록 조회.")
    public BaseResponse<List<UserBaselineSubInfo>> getUserBaselineSubDetailList(HttpServletRequest request
    		, @RequestBody UserBaselineSubInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<UserBaselineSubInfo> resultList = userMgrService.getUserBaselineSubDetailList(params);
	        return new BaseResponse<List<UserBaselineSubInfo>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }        
        
	    
}
