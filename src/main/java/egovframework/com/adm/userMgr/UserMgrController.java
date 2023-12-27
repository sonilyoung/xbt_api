
package egovframework.com.adm.userMgr;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.eduMgr.service.EduMgrService;
import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.system.vo.XbtScore;
import egovframework.com.adm.userMgr.service.UserMgrService;
import egovframework.com.adm.userMgr.vo.CertificationInfo;
import egovframework.com.adm.userMgr.vo.MenuPin;
import egovframework.com.adm.userMgr.vo.TeacherInfo;
import egovframework.com.adm.userMgr.vo.UserBaseline;
import egovframework.com.adm.userMgr.vo.UserBaselineDetail;
import egovframework.com.adm.userMgr.vo.UserBaselinePop;
import egovframework.com.adm.userMgr.vo.UserBaselineScore;
import egovframework.com.adm.userMgr.vo.UserBaselineSub;
import egovframework.com.adm.userMgr.vo.UserBaselineSubInfo;
import egovframework.com.adm.userMgr.vo.UserCertificate;
import egovframework.com.adm.userMgr.vo.UserCertificateDetail;
import egovframework.com.adm.userMgr.vo.UserInfo;
import egovframework.com.common.service.CommonService;
import egovframework.com.common.vo.Common;
import egovframework.com.excel.ExcelRead;
import egovframework.com.excel.ExcelReadOption;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.global.util.AES256Util;
import egovframework.com.score.XbtScoreService;
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
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private EduMgrService eduMgrService;    
    
    @Autowired
    private XbtScoreService xbtScoreService;      
    
    /*파일업로드 저장경로*/
    public static final String FILE_UPLOAD_PATH = GlobalsProperties.getProperty("file.upload.path");    
    
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    /**
     * 교육생 정보조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectUserListPop.do")
    @ApiOperation(value = "교육생정보관리", notes = "교육생정보를 관리한다.")
    public BaseResponse<List<UserInfo>> selectUserListPop(HttpServletRequest request, @RequestBody UserInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<UserInfo> resultList = userMgrService.selectUserListPop(params);
			
			for(UserInfo u : resultList) {
	        	AES256Util aesUtil = new AES256Util();
	            String pwEnc = aesUtil.decrypt(u.getUserPw());
	            u.setUserPw(pwEnc);					
			}
			
	        return new BaseResponse<List<UserInfo>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    /**
     * 교육생 합격불합격 정보조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectBaselineUserList.do")
    @ApiOperation(value = "교육생 합격불합격 정보조회", notes = "교육생 합격불합격 정보조회")
    public BaseResponse<List<UserBaseline>> selectBaselineUserList(HttpServletRequest request, @RequestBody UserBaseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<UserBaseline> resultList = userMgrService.selectBaselineUserList(params);
	        return new BaseResponse<List<UserBaseline>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  
    
    
    /**
     * 차수 교육생 비중평가팝업조호
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectBaselineUser.do")
    @ApiOperation(value = "차수 교육생 비중평가팝업조호", notes = "차수 교육생 비중평가팝업조호")
    public BaseResponse<UserBaselinePop> selectBaselineUser(HttpServletRequest request, @RequestBody UserBaseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<UserBaselinePop>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<UserBaselinePop>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			UserBaseline evaluation = userMgrService.selectBaselineEvaluation(params);
			UserBaseline theory = userMgrService.selectBaselineTherory(params);
			UserBaseline practice = userMgrService.selectBaselinePractice(params);
			UserBaseline basicInfo = userMgrService.selectBaselineBasicTotalScore(params);
			
			if(evaluation==null) {
				evaluation = new UserBaseline();
				evaluation.setEvaluationTotalScore(basicInfo.getEvaluationTotalScore());  		
			}
			
			if(theory==null) {
				theory = new UserBaseline();
				theory.setTheoryTotalScore(basicInfo.getTheoryTotalScore());
			}
			
			if(practice==null) {
				practice = new UserBaseline();
				practice.setPracticeTotalScore(basicInfo.getPracticeTotalScore());
				practice.setPracticeCarTotalScore(basicInfo.getPracticeCarTotalScore());
				practice.setPracticeHumanTotalScore(basicInfo.getPracticeHumanTotalScore());
			}
			
			UserBaselinePop result = new UserBaselinePop();
			result.setEvaluationInfo(evaluation);
			result.setTheoryInfo(theory);
			result.setPracticeInfo(practice);
			
	        return new BaseResponse<UserBaselinePop>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }         
    
    /**
     * 교육생 실기점수 업데이트
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateBaselineUser.do")
    @ApiOperation(value = "교육생 실기점수 업데이트", notes = "교육생 실기점수 업데이트")
    public BaseResponse<UserBaseline> updateBaselineUser(HttpServletRequest request, @RequestBody UserBaseline params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<UserBaseline>(BaseResponseCode.PARAMS_ERROR, "procCd" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<UserBaseline>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getPracticeScore())){				
			return new BaseResponse<UserBaseline>(BaseResponseCode.PARAMS_ERROR, "PracticeScore" + BaseApiMessage.REQUIRED.getCode());
		}
		
		
		try {
			//차수조회
			Baseline bp = new Baseline();
			bp.setProcCd(params.getProcCd());
			Baseline baseline = eduMgrService.selectBaseline(bp);			
			UserBaseline eduCodeInfo = userMgrService.selectBaselinePractice(params);
			int result = 0;
			int gainScore = 0;
			if(baseline!=null) {
				int practiceScore = (params.getPracticeScore() * baseline.getPracticeTotalScore())/100;
				params.setPracticeBeforeScore(params.getPracticeScore());
				params.setPracticeScore(practiceScore);
				
				/*
				int practiceHumanScore = (params.getPracticeHumanScore() * baseline.getPracticeHumanTotalScore())/100;
				params.setPracticeHumanBeforeScore(params.getPracticeHumanScore());
				params.setPracticeHumanScore(practiceHumanScore);
				
				int practiceCarScore = (params.getPracticeCarScore() * baseline.getPracticeCarTotalScore())/100;
				params.setPracticeCarBeforeScore(params.getPracticeCarScore());
				params.setPracticeCarScore(practiceCarScore);				
				*/

				if("4".equals(eduCodeInfo.getEduCode())){
					gainScore = eduCodeInfo.getTheoryScore() + practiceScore;
					//gainScore = eduCodeInfo.getTheoryScore() + practiceHumanScore;
				}else if("6".equals(eduCodeInfo.getEduCode())){
					gainScore = eduCodeInfo.getTheoryScore() + practiceScore;
					//gainScore = eduCodeInfo.getTheoryScore() + practiceHumanScore + practiceCarScore;
				}else {
					gainScore = eduCodeInfo.getEvaluationScore() + eduCodeInfo.getTheoryScore() + practiceScore;
				}	
				//강사등록
				params.setGainScore(gainScore);
				result = userMgrService.updateBaselineUser(params);
				
				params.setPracticeScore(params.getPracticeBeforeScore());
				//params.setPracticeHumanScore(params.getPracticeHumanBeforeScore());
				//params.setPracticeCarScore(params.getPracticeCarBeforeScore());		
			}
			
			//합격불합격처리
			XbtScore xs = new XbtScore();
			xs.setProcCd(params.getProcCd());
			xs.setUserId(params.getUserId());
			xbtScoreService.userScoreCalculate(xs);
			
			if(result>0) {
				return new BaseResponse<UserBaseline>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage(), params);
			}else {
				return new BaseResponse<UserBaseline>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage(), params);
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 교육생 아이디 중복체크 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectUserCheck.do")
    @ApiOperation(value = "교육생 아이디 중복체크", notes = "교육생 아이디 중복체크")
    public BaseResponse<UserInfo> selectUserCheck(HttpServletRequest request, @RequestBody UserInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			UserInfo result = userMgrService.selectUser(params);
			if(result == null) {
				return new BaseResponse<UserInfo>(BaseResponseCode.NO_ID, BaseResponseCode.NO_ID.getMessage());
			}else {
				return new BaseResponse<UserInfo>(BaseResponseCode.EXGIST_USERS, BaseResponseCode.EXGIST_USERS.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 교육생 아이디 중복체크 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStuUserCheck.do")
    @ApiOperation(value = "교육생 아이디 중복체크", notes = "교육생 아이디 중복체크")
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
    public BaseResponse<UserInfo> selectStuUserCheck(HttpServletRequest request, @RequestBody UserInfo params) {
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			UserInfo result = userMgrService.selectUser(params);
			if(result == null) {
				return new BaseResponse<UserInfo>(BaseResponseCode.NO_ID, BaseResponseCode.NO_ID.getMessage());
			}else {
				return new BaseResponse<UserInfo>(BaseResponseCode.EXGIST_USERS, BaseResponseCode.EXGIST_USERS.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }        
    
	/**
	 * 교육생등록 (교육생이 직접등록) 
	 * 
	 * @param param
	 * @return Company
	 */
	@PostMapping("/insertStuUser.do")
	@ApiOperation(value = "교육생", notes = "교육생등록.")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertStuUser(HttpServletRequest request, @RequestBody UserInfo params) {
		
		if(StringUtils.isEmpty(params.getEduName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduName" + BaseApiMessage.REQUIRED.getCode());
		}
		
		//if(StringUtils.isEmpty(params.getUserId())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userId" + BaseApiMessage.REQUIRED.getCode());
		//}
		
		if(StringUtils.isEmpty(params.getUserNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		//if(StringUtils.isEmpty(params.getUserNmCh())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNmCh" + BaseApiMessage.REQUIRED.getCode());
		//}

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

		//패스워드 0000 디폴트
		//if(StringUtils.isEmpty(params.getUserPw())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userPw" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getDept())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "dept" + BaseApiMessage.REQUIRED.getCode());
		//}		
		
		//직책(지위)
		//if(StringUtils.isEmpty(params.getPosition())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "position" + BaseApiMessage.REQUIRED.getCode());
		//}

		//담당업무
		//if(StringUtils.isEmpty(params.getWork())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "work" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getRegistNumber())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "registNumber" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getEmployStatusYn())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "employStatusYn" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getLastEdu())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEdu" + BaseApiMessage.REQUIRED.getCode());
		//}

		if(StringUtils.isEmpty(params.getWriteDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "writeDate" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCompany())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "company" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getHpNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "hpNo" + BaseApiMessage.REQUIRED.getCode());
		}else {
			params.setUserId(params.getHpNo()); 
		}


		//if(StringUtils.isEmpty(params.getEmail())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "email" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getCareerYn())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerYn" + BaseApiMessage.REQUIRED.getCode());
		//}

		/*
		if("Y".equals(params.getCareerYn())) {
			if(StringUtils.isEmpty(params.getCareer1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career1" + BaseApiMessage.REQUIRED.getCode());
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
			
			
		}*/		
		
		//if(StringUtils.isEmpty(params.getLastEduName())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduName" + BaseApiMessage.REQUIRED.getCode());
		//}	
		//if(StringUtils.isEmpty(params.getLastEduDept())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduDept" + BaseApiMessage.REQUIRED.getCode());
		//}			
		
		//if(StringUtils.isEmpty(params.getLastEduYear())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduYear" + BaseApiMessage.REQUIRED.getCode());
		//}	
		
		//if(StringUtils.isEmpty(params.getLastEduEnd())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduEnd" + BaseApiMessage.REQUIRED.getCode());
		//}	
		
		
		UserInfo userInfo = userMgrService.selectUser(params);
		if(userInfo!=null) {
			return new BaseResponse<Integer>(BaseResponseCode.EXGIST_USERS, BaseResponseCode.EXGIST_USERS.getMessage());
		}
		
		try {
			
			/*
			 * 보안검색 초기 : 5일 / 40시간
			             정기 : 5일 / 40시간
			             인증평가 : 1일 / 4시간
			
			   항공경비 초기 : 4일 / 30시간
			             정기 : 5일 / 40시간
			             인증평가 : 1일 / 4시간
			 * */			
			params.setEduCode(params.getEduName());
			
			Common cp = new Common();
			cp.setLanguageCode("kr");
			cp.setGroupId("eduName");
			List<Common> clist = commonService.selectCommonList(cp);
			if(clist!=null) {
				for(Common c : clist) {
					if(params.getEduName().equals(c.getCodeValue())){
						params.setEduName(c.getCodeName());
						params.setEduDay(c.getMemo1());
						params.setEduTime(c.getMemo2());						
					} 
				}
			}	
			
			//교육생등록
			params.setInsertId(params.getUserId());
	    	AES256Util aesUtil = new AES256Util();
	        String pwEnc = aesUtil.encrypt("0000");
	        params.setUserPw(pwEnc);
			int result = userMgrService.insertUser(params);
			
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
		
		//if(StringUtils.isEmpty(params.getUserId())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userId" + BaseApiMessage.REQUIRED.getCode());
		//}
		
		if(StringUtils.isEmpty(params.getUserNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		//if(StringUtils.isEmpty(params.getUserNmCh())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNmCh" + BaseApiMessage.REQUIRED.getCode());
		//}

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

		//패스워드 0000 디폴트
		//if(StringUtils.isEmpty(params.getUserPw())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userPw" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getDept())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "dept" + BaseApiMessage.REQUIRED.getCode());
		//}		
		
		//직책(지위)
		//if(StringUtils.isEmpty(params.getPosition())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "position" + BaseApiMessage.REQUIRED.getCode());
		//}

		//담당업무
		//if(StringUtils.isEmpty(params.getWork())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "work" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getRegistNumber())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "registNumber" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getEmployStatusYn())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "employStatusYn" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getLastEdu())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEdu" + BaseApiMessage.REQUIRED.getCode());
		//}

		if(StringUtils.isEmpty(params.getWriteDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "writeDate" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCompany())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "company" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getHpNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "hpNo" + BaseApiMessage.REQUIRED.getCode());
		}else {
			params.setUserId(params.getHpNo()); 
		}


		//if(StringUtils.isEmpty(params.getEmail())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "email" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getCareerYn())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerYn" + BaseApiMessage.REQUIRED.getCode());
		//}

		/*
		if("Y".equals(params.getCareerYn())) {
			if(StringUtils.isEmpty(params.getCareer1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career1" + BaseApiMessage.REQUIRED.getCode());
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
			
			
		}*/		
		
		//if(StringUtils.isEmpty(params.getLastEduName())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduName" + BaseApiMessage.REQUIRED.getCode());
		//}	
		//if(StringUtils.isEmpty(params.getLastEduDept())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduDept" + BaseApiMessage.REQUIRED.getCode());
		//}			
		
		//if(StringUtils.isEmpty(params.getLastEduYear())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduYear" + BaseApiMessage.REQUIRED.getCode());
		//}	
		
		//if(StringUtils.isEmpty(params.getLastEduEnd())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduEnd" + BaseApiMessage.REQUIRED.getCode());
		//}	
		
		
		UserInfo userInfo = userMgrService.selectUser(params);
		if(userInfo!=null) {
			return new BaseResponse<Integer>(BaseResponseCode.EXGIST_USERS, BaseResponseCode.EXGIST_USERS.getMessage());
		}
		
		try {
			
			/*
			 * 보안검색 초기 : 5일 / 40시간
			             정기 : 5일 / 40시간
			             인증평가 : 1일 / 4시간
			
			   항공경비 초기 : 4일 / 30시간
			             정기 : 5일 / 40시간
			             인증평가 : 1일 / 4시간
			 * */			
			params.setEduCode(params.getEduName());
			
			Common cp = new Common();
			cp.setLanguageCode("kr");
			cp.setGroupId("eduName");
			List<Common> clist = commonService.selectCommonList(cp);
			if(clist!=null) {
				for(Common c : clist) {
					if(params.getEduName().equals(c.getCodeValue())){
						params.setEduName(c.getCodeName());
						params.setEduDay(c.getMemo1());
						params.setEduTime(c.getMemo2());
					} 
				}
			}	
			
			//교육생등록
			params.setInsertId(login.getUserId());
        	AES256Util aesUtil = new AES256Util();
        	
            //String pwEnc = aesUtil.encrypt(params.getUserPw());
        	String pwEnc = aesUtil.encrypt("0000");
            params.setUserPw(pwEnc);
			int result = userMgrService.insertUser(params);
			
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
		
		//if(StringUtils.isEmpty(params.getUserId())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userId" + BaseApiMessage.REQUIRED.getCode());
		//}
		
		if(StringUtils.isEmpty(params.getUserNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		//if(StringUtils.isEmpty(params.getUserNmCh())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userNmCh" + BaseApiMessage.REQUIRED.getCode());
		//}

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

		//패스워드 0000 디폴트
		//if(StringUtils.isEmpty(params.getUserPw())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "userPw" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getDept())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "dept" + BaseApiMessage.REQUIRED.getCode());
		//}		
		
		//직책(지위)
		//if(StringUtils.isEmpty(params.getPosition())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "position" + BaseApiMessage.REQUIRED.getCode());
		//}

		//담당업무
		//if(StringUtils.isEmpty(params.getWork())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "work" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getRegistNumber())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "registNumber" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getEmployStatusYn())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "employStatusYn" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getLastEdu())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEdu" + BaseApiMessage.REQUIRED.getCode());
		//}

		if(StringUtils.isEmpty(params.getWriteDate())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "writeDate" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getCompany())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "company" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getHpNo())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "hpNo" + BaseApiMessage.REQUIRED.getCode());
		}else {
			params.setUserId(params.getHpNo()); 
		}


		//if(StringUtils.isEmpty(params.getEmail())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "email" + BaseApiMessage.REQUIRED.getCode());
		//}

		//if(StringUtils.isEmpty(params.getCareerYn())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "careerYn" + BaseApiMessage.REQUIRED.getCode());
		//}

		/*
		if("Y".equals(params.getCareerYn())) {
			if(StringUtils.isEmpty(params.getCareer1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career1" + BaseApiMessage.REQUIRED.getCode());
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
			
			
		}*/		
		
		//if(StringUtils.isEmpty(params.getLastEduName())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduName" + BaseApiMessage.REQUIRED.getCode());
		//}	
		//if(StringUtils.isEmpty(params.getLastEduDept())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduDept" + BaseApiMessage.REQUIRED.getCode());
		//}			
		
		//if(StringUtils.isEmpty(params.getLastEduYear())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduYear" + BaseApiMessage.REQUIRED.getCode());
		//}	
		
		//if(StringUtils.isEmpty(params.getLastEduEnd())){				
			//return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "lastEduEnd" + BaseApiMessage.REQUIRED.getCode());
		//}	
		
		
		try {
			/*
			 * 보안검색 초기 : 5일 / 40시간
			             정기 : 5일 / 40시간
			             인증평가 : 1일 / 4시간
			
			   항공경비 초기 : 4일 / 30시간
			             정기 : 5일 / 40시간
			             인증평가 : 1일 / 4시간
			 * */			
			params.setEduCode(params.getEduName());
			
			Common cp = new Common();
			cp.setLanguageCode("kr");
			cp.setGroupId("eduName");
			List<Common> clist = commonService.selectCommonList(cp);
			if(clist!=null) {
				for(Common c : clist) {
					if(params.getEduName().equals(c.getCodeValue())){
						params.setEduName(c.getCodeName());
						params.setEduDay(c.getMemo1());
						params.setEduTime(c.getMemo2());						
					} 
				}
			}			
			
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
	//교육생 엑셀 추가
	@ResponseBody
	@PostMapping(value="/insertStudentExcel.do" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)	
	public BaseResponse<List<UserInfo>> insertStudentExcel(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
			,@RequestPart(value = "params", required = false) UserInfo params
	) throws Exception{
		LOGGER.debug("========= insertStudentExcel 교육생 엑셀등록 ========="+ excelFile);

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
			String[] coloumNm = {"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
			
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			
			//LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params = new UserInfo();
			
			int result = 0;
			int duplicationCnt = 0;
			
			
			userMgrService.deleteDuplicationUser(params);
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new UserInfo();
				params.setUserId(excelData.get("L").replaceAll("-", ""));//아이디
				params.setClassType(excelData.get("B"));//등록차수 
				params.setEduCode(excelData.get("C")); //교육과정코드
				params.setUserNm(excelData.get("D"));//국문성명
	            params.setUserNmEn(excelData.get("E"));//영문성명
	            params.setSex(excelData.get("F"));//성별
	            
	            // 엑셀 날짜 값을 Java Date 객체로 변환
	            if(null != excelData.get("G") && !"".equals(excelData.get("G"))){	
		            //Date date1 = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.parseDouble(excelData.get("G")));
		            // 날짜를 원하는 형식으로 문자열로 변환
		            //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		            //String dateString1 = dateFormat1.format(date1);
		            params.setBirthDay(excelData.get("G"));//생년월일
	            }

	            
	            params.setAge(excelData.get("H"));//나이
	            params.setAddress(excelData.get("I"));//주소
	            params.setAddressEn(excelData.get("J"));//영문주소
	            params.setCompany(excelData.get("K"));//소속
	            params.setHpNo(excelData.get("L").replaceAll("-", ""));//휴대폰
	            //여기까지 필수값
	            
	            //필수가 아닌데이터 처리
	    		if(!StringUtils.isEmpty(excelData.get("M"))){				
	    			params.setEmail(excelData.get("M"));//이메일
	            }
	    		
	    		if(!StringUtils.isEmpty(excelData.get("N"))){				
	    			params.setCareerYn(excelData.get("N"));//항공보안경력유무
	            }	            
	            
	    		if(null != excelData.get("O") && !"".equals(excelData.get("O"))){
		            // 엑셀 날짜 값을 Java Date 객체로 변환
		            //Date date2 = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.parseDouble(excelData.get("O")));
		            // 날짜를 원하는 형식으로 문자열로 변환
		            //SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		            //String dateString2 = dateFormat2.format(date2);	            
		            params.setCareerStartDate1(excelData.get("O"));//보안경력시작일	    			
	    		}
	    		
	    		if(null != excelData.get("P") && !"".equals(excelData.get("P"))){
		            // 엑셀 날짜 값을 Java Date 객체로 변환
		            //Date date3 = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.parseDouble(excelData.get("P")));
		            // 날짜를 원하는 형식으로 문자열로 변환
		            //SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		            //String dateString3 = dateFormat3.format(date3);	  	            
		            params.setCareerEndDate1(excelData.get("P"));//보안경력종료일
	    		}
	    		
	    		if(!StringUtils.isEmpty(excelData.get("Q"))){
	    			params.setCareerCompany1(excelData.get("Q"));//소속
	    		}
	    		
	    		if(!StringUtils.isEmpty(excelData.get("R"))){
	    			params.setCareerPosition1(excelData.get("R"));//직책(직위)
	    		}
	    		
	    		if(!StringUtils.isEmpty(excelData.get("S"))){
	    			params.setCareer1(excelData.get("S"));//담당업무				
	    		}
	    		
	    		//if(!StringUtils.isEmpty(excelData.get("S"))){
	    			//params.setClassType(excelData.get("S"));//수업만 A반 B반				
	    		//}	
	    		
	    		if(!StringUtils.isEmpty(excelData.get("T"))){
	    			params.setFaceType(excelData.get("T"));//안면인식				
	    		}	  	
	    		
	    			
	            // 엑셀 날짜 값을 Java Date 객체로 변환
	            if(null != excelData.get("U") && !"".equals(excelData.get("U"))){	
		            // 엑셀 날짜 값을 Java Date 객체로 변환
		            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.parseDouble(excelData.get("U")));
		            // 날짜를 원하는 형식으로 문자열로 변환
		            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		            String dateString = dateFormat.format(date);		            	
		            params.setWriteDate(dateString);//입교신청일
	            }	    			
	            	    		
	            
				Common cp = new Common();
				cp.setLanguageCode("kr");
				cp.setGroupId("eduName");
				List<Common> clist = commonService.selectCommonList(cp);
				if(clist!=null) {
					for(Common c : clist) {
						if(params.getEduCode().equals(c.getCodeValue())){
							params.setEduName(c.getCodeName());
							params.setEduDay(c.getMemo1());
							params.setEduTime(c.getMemo2());		
						} 
					}
				}		     
				
	        	AES256Util aesUtil = new AES256Util();
	            String pwEnc = aesUtil.encrypt("0000");
	            params.setUserPw(pwEnc);				
	            params.setInsertId(login.getUserId());
	            
				UserInfo ui = userMgrService.selectUser(params);
				if(ui != null) {
					duplicationCnt = userMgrService.insertDuplicationUser(params);
				}else {
					result = userMgrService.insertUser(params);	
				}				
			}
			
			
			List<UserInfo> duplicaitonUserList = userMgrService.selectDuplicationUserList(params);

            if(duplicationCnt>0) {
	            return new BaseResponse<List<UserInfo>>(BaseResponseCode.EXGIST_USERS, BaseResponseCode.EXGIST_USERS.getMessage(), duplicaitonUserList);
            }			
			
            if(result>0) {
	            return new BaseResponse<List<UserInfo>>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage(), duplicaitonUserList);
            }else {
            	return new BaseResponse<List<UserInfo>>(BaseResponseCode.DATA_IS_NULL, BaseResponseCode.DATA_IS_NULL.getMessage(), duplicaitonUserList);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<List<UserInfo>>(BaseResponseCode.SAVE_ERROR);
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
		
		if(StringUtils.isEmpty(params.getUserIdList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserIdList" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//교육생삭제
			int result = 0;
			for(String id :params.getUserIdList()) {
				params.setUserId(id);
				result = userMgrService.deleteUser(params);
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }        
        

    /**
     * 강사 정보조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTeacherList.do")
    @ApiOperation(value = "강사정보관리", notes = "강사정보를 관리한다.")
    public BaseResponse<List<TeacherInfo>> getTeacherList(HttpServletRequest request, @RequestBody TeacherInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			List<TeacherInfo> resultList = userMgrService.selectTeacherList(params);
			
			for(TeacherInfo u : resultList) {
	        	AES256Util aesUtil = new AES256Util();
	            String pwEnc = aesUtil.decrypt(u.getUserPw());
	            u.setUserPw(pwEnc);					
			}
			
	        return new BaseResponse<List<TeacherInfo>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    /**
     * 강사 상세정보조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTeacher.do")
    @ApiOperation(value = "강사 상세정보조회", notes = "강사 상세정보조회 관리한다.")
    public BaseResponse<TeacherInfo> selectTeacher(HttpServletRequest request, @RequestBody TeacherInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<TeacherInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			TeacherInfo resultList = userMgrService.selectTeacher(params);
        	AES256Util aesUtil = new AES256Util();
            String pwEnc = aesUtil.decrypt(resultList.getUserPw());
            resultList.setUserPw(pwEnc);			
	        return new BaseResponse<TeacherInfo>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    
    /**
     * 강사 아이디 중복체크 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectTeacherCheck.do")
    @ApiOperation(value = "강사 아이디 중복체크", notes = "강사 아이디 중복체크")
    public BaseResponse<TeacherInfo> selectTeacherCheck(HttpServletRequest request, @RequestBody TeacherInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<TeacherInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}			
		
		try {
			TeacherInfo result = userMgrService.selectTeacher(params);
			if(result == null) {
				return new BaseResponse<TeacherInfo>(BaseResponseCode.NO_ID, BaseResponseCode.NO_ID.getMessage());
			}else {
				return new BaseResponse<TeacherInfo>(BaseResponseCode.EXGIST_USERS, BaseResponseCode.EXGIST_USERS.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
            
        
    /**
     * 강사등록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertTeacher.do")
    @ApiOperation(value = "강사", notes = "강사등록.")
    public BaseResponse<Integer> insertTeacher(HttpServletRequest request, @RequestBody TeacherInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getEduName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduName" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserNmCh())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserNmCh" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNmEn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserNmEn" + BaseApiMessage.REQUIRED.getCode());
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
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "TeacherPw" + BaseApiMessage.REQUIRED.getCode());
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

		if("Y".equals(params.getCareerYn())) {
			if(StringUtils.isEmpty(params.getCareer1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career1" + BaseApiMessage.REQUIRED.getCode());
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
		
		
		TeacherInfo TeacherInfo = userMgrService.selectTeacher(params);
		if(TeacherInfo!=null) {
			return new BaseResponse<Integer>(BaseResponseCode.EXGIST_USERS, BaseResponseCode.EXGIST_USERS.getMessage());
		}
		
		/*
		 * 보안검색 초기 : 5일 / 40시간
		             정기 : 5일 / 40시간
		             인증평가 : 1일 / 4시간
		
		   항공경비 초기 : 4일 / 30시간
		             정기 : 5일 / 40시간
		             인증평가 : 1일 / 4시간
		 * */			
		params.setEduCode(params.getEduName());
		
		Common cp = new Common();
		cp.setLanguageCode("kr");
		cp.setGroupId("eduName");
		List<Common> clist = commonService.selectCommonList(cp);
		if(clist!=null) {
			for(Common c : clist) {
				if(params.getEduName().equals(c.getCodeValue())){
					params.setEduName(c.getCodeName());
				} 
			}
		}			
		
		try {
			//강사등록
			params.setInsertId(login.getUserId());
        	AES256Util aesUtil = new AES256Util();
            String pwEnc = aesUtil.encrypt(params.getUserPw());
            params.setUserPw(pwEnc);
			int result = userMgrService.insertTeacher(params);
			userMgrService.insertUserMaster(params);
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
     * 강사수정
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateTeacher.do")
    @ApiOperation(value = "강사", notes = "강사수정.")
    public BaseResponse<Integer> updateTeacher(HttpServletRequest request, @RequestBody TeacherInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getEduName())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "eduName" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNm())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserNm" + BaseApiMessage.REQUIRED.getCode());
		}
		
		if(StringUtils.isEmpty(params.getUserNmCh())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserNmCh" + BaseApiMessage.REQUIRED.getCode());
		}

		if(StringUtils.isEmpty(params.getUserNmEn())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserNmEn" + BaseApiMessage.REQUIRED.getCode());
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
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "TeacherPw" + BaseApiMessage.REQUIRED.getCode());
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

		if("Y".equals(params.getCareerYn())) {
			if(StringUtils.isEmpty(params.getCareer1())){				
				return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "career1" + BaseApiMessage.REQUIRED.getCode());
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
			//강사등록
			params.setUpdateId(login.getUserId());
        	AES256Util aesUtil = new AES256Util();
            String pwEnc = aesUtil.encrypt(params.getUserPw());
            params.setUserPw(pwEnc);			
			int result = userMgrService.updateTeacher(params);
			userMgrService.updateUserMaster(params);
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
     * 강사삭제
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteTeacher.do")
    @ApiOperation(value = "강사", notes = "강사삭제.")
    public BaseResponse<Integer> deleteTeacher(HttpServletRequest request, @RequestBody TeacherInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserIdList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserIdList" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//강사삭제
			int result = 0;
			for(String id :params.getUserIdList()) {
				params.setUserId(id);
				result = userMgrService.deleteTeacher(params);
				userMgrService.deleteUserMaster(params);
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
     * 이수증명서학생목록
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectCertificationUserList.do")
    @ApiOperation(value = "이수증명서학생목록", notes = "이수증명서학생목록")
    public BaseResponse<List<UserCertificate>> selectCertificationUserList(HttpServletRequest request, @RequestBody UserCertificate params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<List<UserCertificate>>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			List<UserCertificate> resultList = userMgrService.selectCertificationUserList(params);
			
	        return new BaseResponse<List<UserCertificate>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    
    /**
     * 이수증명서학생
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectCertificationUser.do")
    @ApiOperation(value = "이수증명서학생", notes = "이수증명서학생")
    public BaseResponse<List<UserCertificateDetail>> selectCertificationUser(HttpServletRequest request, @RequestBody List<UserCertificateDetail> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		List<UserCertificateDetail> resultList = new ArrayList<UserCertificateDetail>();
		try {
			
			for(UserCertificateDetail ps : params) {
				
				if(StringUtils.isEmpty(ps.getUserId())){				
					return new BaseResponse<List<UserCertificateDetail>>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
				}		
				
				if(StringUtils.isEmpty(ps.getProcCd())){				
					return new BaseResponse<List<UserCertificateDetail>>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
				}		
				
				if(StringUtils.isEmpty(ps.getProcSeq())){				
					return new BaseResponse<List<UserCertificateDetail>>(BaseResponseCode.PARAMS_ERROR, "ProcSeq" + BaseApiMessage.REQUIRED.getCode());
				}				
				
				if(StringUtils.isEmpty(ps.getEduCode())){				
					return new BaseResponse<List<UserCertificateDetail>>(BaseResponseCode.PARAMS_ERROR, "EduCode" + BaseApiMessage.REQUIRED.getCode());
				}		
				
				//보안
				if("1".equals(ps.getEduCode()) || "2".equals(ps.getEduCode()) || "3".equals(ps.getEduCode())) {
					ps.setEduType("1");
				//경비
				}else if("4".equals(ps.getEduCode()) || "5".equals(ps.getEduCode()) || "6".equals(ps.getEduCode())) {
					ps.setEduType("2");
				}
				
				UserCertificateDetail certNumber = userMgrService.selectCertNumber(ps);
				UserCertificateDetail result = userMgrService.selectCertificationUser(ps);
				if(result!=null) {
					result.setCertificationId(certNumber.getCertificationId());
					resultList.add(result);					
				}
			}			
			
	        return new BaseResponse<List<UserCertificateDetail>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    /**
     * 이수증명서 자격증번호 가져오기
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectCertNumber.do")
    @ApiOperation(value = "이수증명서 자격증번호 가져오기", notes = "이수증명서 자격증번호 가져오기")
    public BaseResponse<UserCertificateDetail> selectCertNumber(HttpServletRequest request, @RequestBody UserCertificateDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getEduCode())){				
			return new BaseResponse<UserCertificateDetail>(BaseResponseCode.PARAMS_ERROR, "EduCode" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			//보안
			if("1".equals(params.getEduCode()) || "2".equals(params.getEduCode()) || "3".equals(params.getEduCode())) {
				
				params.setEduType("1");
			//경비
			}else if("4".equals(params.getEduCode()) || "5".equals(params.getEduCode()) || "6".equals(params.getEduCode())) {
				params.setEduType("2");
			}
			
			UserCertificateDetail result = userMgrService.selectCertNumber(params);
			
	        return new BaseResponse<UserCertificateDetail>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }       
    
    
    
    /**
     * 이수증명서 자격증번호 저장
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/insertCertNumber.do")
    @ApiOperation(value = "이수증명서 자격증번호 저장", notes = "이수증명서 자격증번호 저장")
    public BaseResponse<List<CertificationInfo>> insertCertNumber(HttpServletRequest request, @RequestBody List<CertificationInfo> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			int result = 0;
			for(CertificationInfo ps : params) {
				if(StringUtils.isEmpty(ps.getCertificationId())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "certificationId" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getProcCd())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "procCd" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getProcYear())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "procYear" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getProcSeq())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "procSeq" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getEduCode())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "eduCode" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getEduName())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "eduName" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getUserId())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "userId" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getUserNm())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "userNm" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getPracticeScore())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "practiceScore" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getTheoryScore())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "theoryScore" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getEvaluationScore())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "evaluationScore" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getPassYn())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "passYn" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getEduStartDate())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "eduStartDate" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getEduEndDate())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "eduEndDate" + BaseApiMessage.REQUIRED.getCode());
				}
				if(StringUtils.isEmpty(ps.getEndingYn())){				
					return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.PARAMS_ERROR, "endingYn" + BaseApiMessage.REQUIRED.getCode());
				}			
				ps.setInsertId(login.getUserId());
				result = userMgrService.insertCertNumber(ps);
			}
			
			if(result>0) {
				return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
			}else {
				return new BaseResponse<List<CertificationInfo>>(BaseResponseCode.SAVE_ERROR, BaseResponseCode.SAVE_ERROR.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }         
            
    
    
    
    /**
     * 평가데이터 초기화
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteEvaluationData.do")
    @ApiOperation(value = "평가데이터 초기화", notes = "평가데이터 초기화")
    public BaseResponse<UserInfo> deleteEvaluationData(HttpServletRequest request, @RequestBody List<UserInfo> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			int result = 0;
			for(UserInfo ui : params) {
				if(StringUtils.isEmpty(ui.getProcCd())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
				}		
				
				if(StringUtils.isEmpty(ui.getUserId())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
				}	
				
				result = userMgrService.deleteEvaluationData(ui);
			}
			
			if(result>0) {
				return new BaseResponse<UserInfo>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
			}else {
				return new BaseResponse<UserInfo>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    /**
     * 이론데이터 초기화
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteTheoryData.do")
    @ApiOperation(value = "이론데이터 초기화", notes = "이론데이터 초기화")
    public BaseResponse<UserInfo> deleteTheoryData(HttpServletRequest request, @RequestBody List<UserInfo> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			int result = 0;
			for(UserInfo ui : params) {
				if(StringUtils.isEmpty(ui.getProcCd())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
				}		
				
				if(StringUtils.isEmpty(ui.getUserId())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
				}	
				
				result = userMgrService.deleteTheoryData(ui);
			}
			
			if(result>0) {
				return new BaseResponse<UserInfo>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
			}else {
				return new BaseResponse<UserInfo>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   
    
    /**
     * 위험물데이터 초기화
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/deleteDangerData.do")
    @ApiOperation(value = "위험물데이터 초기화", notes = "위험물데이터 초기화")
    public BaseResponse<UserInfo> deleteDangerData(HttpServletRequest request, @RequestBody List<UserInfo> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			int result = 0;
			for(UserInfo ui : params) {
				if(StringUtils.isEmpty(ui.getProcCd())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
				}		
				
				if(StringUtils.isEmpty(ui.getUserId())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getCode());
				}	
				
				result = userMgrService.deleteDangerData(ui);
			}
			
			if(result>0) {
				return new BaseResponse<UserInfo>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
			}else {
				return new BaseResponse<UserInfo>(BaseResponseCode.FAIL, BaseResponseCode.FAIL.getMessage());
			}
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }       
    
    
    /**
     * 점수집계 수동실행
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/updateUserScore.do")
    @ApiOperation(value = "점수집계 수동실행", notes = "점수집계 수동실행")
    public BaseResponse<UserInfo> updateUserScore(HttpServletRequest request, @RequestBody List<UserInfo> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			for(UserInfo ui : params) {
				if(StringUtils.isEmpty(ui.getProcCd())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getCode());
				}		
				
				if(StringUtils.isEmpty(ui.getUserId())){				
					return new BaseResponse<UserInfo>(BaseResponseCode.PARAMS_ERROR, "userId" + BaseApiMessage.REQUIRED.getCode());
				}	
				
				XbtScore xs = new XbtScore();
				xs.setProcCd(ui.getProcCd());
				xs.setUserId(ui.getUserId());
				xbtScoreService.userScoreCalculate(xs);
			}
			
			return new BaseResponse<UserInfo>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }       
            
        
    
    /**
     * 차수 교육생 점수조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectBaselineStudentScore.do")
    @ApiOperation(value = "차수 교육생 점수조회", notes = "차수 교육생 점수조회")
    public BaseResponse<List<UserBaselineScore>> selectBaselineStudentScore(HttpServletRequest request, @RequestBody UserBaselineScore params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getCommand())){	
			List<UserBaselineScore> result = new ArrayList<UserBaselineScore>();
			return new BaseResponse<List<UserBaselineScore>>(result);
		}	
		
		
		try {
			List<UserBaselineScore> resultList = userMgrService.selectBaselineStudentScore(params);
	        return new BaseResponse<List<UserBaselineScore>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }      
    
    
    /**
     * 메뉴별 핀번호 조회
     * 
     * @param param
     * @return 
     */
    @PostMapping("/selectMenuPinList.do")
    @ApiOperation(value = "메뉴별 핀번호 조회", notes = "메뉴별 핀번호 조회")
    public BaseResponse<List<MenuPin>> selectMenuPinList(HttpServletRequest request, @RequestBody MenuPin params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {

			List<MenuPin> resultMenuPinList = userMgrService.selectMenuPinList(params);
			if(resultMenuPinList != null) {
				List<MenuPin> resultList = new ArrayList<>();
				for(MenuPin m : resultList) {
					String[] tmpStr = m.getPinNumber().split("");
					m.setPinData(tmpStr);
				}
			}
			
	        return new BaseResponse<List<MenuPin>>(resultMenuPinList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }     
    
    
    /**
     * 메뉴별 핀번호 등록
     * 
     * @param param
     * @return 
     */
    @PostMapping("/insertMenuPin.do")
    @ApiOperation(value = "메뉴별 핀번호 등록", notes = "메뉴별 핀번호 등록")
    public BaseResponse<Integer> insertMenuPin(HttpServletRequest request, @RequestBody List<MenuPin> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			int result = 0;
			for(MenuPin mp : params) {
				if(StringUtils.isEmpty(mp.getPinTitle())){				
					return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PinTitle" + BaseApiMessage.REQUIRED.getCode());
				}
				
				
				if(StringUtils.isEmpty(mp.getPinkey())){				
					return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Pinkey" + BaseApiMessage.REQUIRED.getCode());
				}
				
				
				if(StringUtils.isEmpty(mp.getPinData())){				
					return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PinData" + BaseApiMessage.REQUIRED.getCode());
				}else {
					String pinNumber = "";
					for(String s : mp.getPinData()) {
						pinNumber = pinNumber + s + "";
					}
					mp.setPinNumber(pinNumber); 
				}
				
				//공지사항등록
				mp.setInsertId(login.getUserId());
				result = userMgrService.insertMenuPin(mp);
			}
			
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
     * 메뉴별 핀번호 등록
     * 
     * @param param
     * @return 
     */
    @PostMapping("/updateMenuPin.do")
    @ApiOperation(value = "메뉴별 핀번호 수정", notes = "메뉴별 핀번호 수정")
    public BaseResponse<Integer> updateMenuPin(HttpServletRequest request, @RequestBody List<MenuPin> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		try {
			int result = 0;
			for(MenuPin mp : params) {
				if(StringUtils.isEmpty(mp.getPinTitle())){				
					return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PinTitle" + BaseApiMessage.REQUIRED.getCode());
				}
				
				
				if(StringUtils.isEmpty(mp.getPinkey())){				
					return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "Pinkey" + BaseApiMessage.REQUIRED.getCode());
				}
				
				
				if(StringUtils.isEmpty(mp.getPinData())){				
					return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "PinData" + BaseApiMessage.REQUIRED.getCode());
				}else {
					String pinNumber = "";
					for(String s : mp.getPinData()) {
						pinNumber = pinNumber + s + "";
					}
					mp.setPinNumber(pinNumber); 
				}
				
				
				//공지사항등록
				mp.setUpdateId(login.getUserId());
				result = userMgrService.updateMenuPin(mp);
				
			}
			
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
            
        
}
