
package egovframework.com.stu.learning;

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

import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.common.vo.LearningMainImg;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.stu.learning.service.LearningService;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.learning.vo.PointStd;
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
@RequestMapping("/stu/learning")
@Api(tags = "learning Management API")
public class LearningController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LearningController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private LearningService learningService;
    
    @Autowired
    private XbtImageService xbtImageService;        
    
    
    /**
     * 사용자학습정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectLearning.do")
    @ApiOperation(value = "사용자학습정보", notes = "사용자학습정보")
    public BaseResponse<Learning> selectLearningInfo(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			moduleInfoData.setUserId(login.getUserId());
			moduleInfoData.setUserName(login.getUserNm());
			LearningProblem lpParams = new LearningProblem();
			lpParams.setUserId(login.getUserId());
			lpParams.setModuleId(moduleInfoData.getModuleId());
			lpParams.setProcCd(baselineData.getProcCd());
			lpParams.setProcYear(baselineData.getProcYear());
			lpParams.setProcSeq(baselineData.getProcSeq());		
			lpParams.setQuestionCnt(moduleInfoData.getQuestionCnt());
			
			//학습문제가져오기
			List<LearningProblem> problems = learningService.selectLearningProblems(lpParams);	
			if(problems == null) {
				return new BaseResponse<Learning>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}				
			//시도횟수
			LearningProblem maxKey = learningService.selectLearningProblemsMaxkey(lpParams);	
			lpParams.setTrySeq(maxKey.getTrySeq());
			
			//등록된학습문제 체크
			lpParams.setEndYn("N");
			int problemsCnt = learningService.selectLearningProblemsCount(lpParams);
			
			
			if(problemsCnt <= 0) {
				//문제등록
				lpParams.setTrySeq(maxKey.getTrySeq()+1);
				moduleInfoData.setTrySeq(maxKey.getTrySeq()+1);
				learningService.insertLearningProblems(lpParams);				
			}else {
				moduleInfoData.setTrySeq(maxKey.getTrySeq());
			}
			
			List<LearningProblem> resultList = learningService.selectLearnProblemsList(lpParams);
			
			if(resultList == null) {
				return new BaseResponse<Learning>(BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS, BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS.getMessage());
			}			
			

			List<LearningProblem> result = xbtImageService.selectLeaningImgList(resultList); 
			moduleInfoData.setLearningProblemList(result);
			return new BaseResponse<Learning>(moduleInfoData);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
       
    
    
    
    /**
     * 학습시작
     * 
     * @param param
     * @return Company
    
    @PostMapping("/startLearning.do")
    @ApiOperation(value = "학습시작", notes = "학습시작")
    public BaseResponse<List<LearningProblem>> startLearning(HttpServletRequest request, @RequestBody LearningProblem params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getModuleId())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "ModuleId" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		if(StringUtils.isEmpty(params.getEduType())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "EduType" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			
			//차수정보조회
			Learning learnData = new Learning();
			learnData.setUserId(login.getUserId());
			Learning baselineData = learningService.selectBaseline(learnData);
			if(baselineData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}			
			
			//문제조회
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());
			List<LearningProblem> problems = learningService.selectLearningProblems(params);	
			if(problems == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}			
			
			//등록된학습문제 체크
			int problemsCnt = learningService.selectLearningProblemsCount(params);
			
			if(problemsCnt > 0) {
				learningService.updateLearningProblems(params);
			}else {
				//문제등록
				learningService.insertLearningProblems(params);				
			}
			
			
			List<LearningProblem> resultList = learningService.selectLearnProblemsList(params); 
			return new BaseResponse<List<LearningProblem>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  */    
           
    
    /**
     * 학습이미지목록가져오기
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectImgList.do")
    @ApiOperation(value = "학습이미지목록가져오기", notes = "학습이미지목록가져오기")
    public BaseResponse<List<LearningProblem>> selectImgList(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			moduleInfoData.setUserId(login.getUserId());
			moduleInfoData.setUserName(login.getUserNm());
			LearningProblem lpParams = new LearningProblem();
			lpParams.setUserId(login.getUserId());
			lpParams.setModuleId(moduleInfoData.getModuleId());
			lpParams.setProcCd(baselineData.getProcCd());
			lpParams.setProcYear(baselineData.getProcYear());
			lpParams.setProcSeq(baselineData.getProcSeq());		
			lpParams.setQuestionCnt(moduleInfoData.getQuestionCnt());
			
			//학습문제가져오기
			List<LearningProblem> problems = learningService.selectLearningProblems(lpParams);	
			if(problems == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}	


			List<LearningProblem> result = xbtImageService.selectLeaningImgList(problems); 
			return new BaseResponse<List<LearningProblem>>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }      
    
    /**
     * 단일이미지가져오기 (정면, 측면, 실사)
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectCommonLearningImg.do")
    @ApiOperation(value = "학습이미지목록가져오기", notes = "학습이미지목록가져오기")
    public BaseResponse<LearningMainImg> selectResultImg(HttpServletRequest request, @RequestBody LearningImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<LearningMainImg>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		try {
			LearningMainImg result = xbtImageService.selectCommonLearningImg(params); 
			return new BaseResponse<LearningMainImg>(result);
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
     * 물품연습이미지가져오기 단일이미지가져오기 (정면, 측면, 실사)
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectCommonPracticeImg.do")
    @ApiOperation(value = "물품연습이미지가져오기", notes = "물품연습이미지가져오기")
    public BaseResponse<LearningMainImg> selectCommonPracticeImg(HttpServletRequest request, @RequestBody LearningImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<LearningMainImg>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		try {
			LearningMainImg result = xbtImageService.selectCommonPracticeImg(params); 
			return new BaseResponse<LearningMainImg>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }       
    

    
    /**
     * 단품의사색체이미지
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectUnitImg.do")
    @ApiOperation(value = "단품의사색체이미지", notes = "단품의사색체이미지")
    public BaseResponse<LearningImg> selectUnitImg(HttpServletRequest request, @RequestBody LearningImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<LearningImg>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		if(StringUtils.isEmpty(params.getCommand())){				
			return new BaseResponse<LearningImg>(BaseResponseCode.PARAMS_ERROR, "Command" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			LearningImg result = xbtImageService.selectPracticeImg(params); 
			return new BaseResponse<LearningImg>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }      
    
    
    
    /**
     * 3d각도조절
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectThreedAngle.do")
    @ApiOperation(value = "3d각도조절", notes = "의사색체이미지")
    public BaseResponse<LearningImg> selectThreedAngle(HttpServletRequest request, @RequestBody LearningImg params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getUnitId())){				
			return new BaseResponse<LearningImg>(BaseResponseCode.PARAMS_ERROR, "UnitId" + BaseApiMessage.REQUIRED.getMessage());
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
     * 학습정답선택
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/updateLearningAnswer.do")
    @ApiOperation(value = "학습정답선택", notes = "학습정답선택")
    public BaseResponse<Integer> updateLearningAnswer(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getUserActionDiv())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserActionDiv" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}			
			
			params.setModuleId(moduleInfoData.getModuleId());
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());	
			
			//시도횟수
			LearningProblem lpParams = new LearningProblem();
			lpParams.setProcCd(params.getProcCd());
			lpParams.setProcYear(params.getProcYear());
			lpParams.setProcSeq(params.getProcSeq());
			lpParams.setUserId(params.getUserId());
			LearningProblem maxKey = learningService.selectLearningProblemsMaxkey(lpParams);			
			params.setTrySeq(maxKey.getTrySeq());
			
			Learning answer = learningService.selectLearnAnswer(params);
			params.setAnswerDiv(answer.getAnswerDiv());
			PointStd score = learningService.selectPointStdScore(params);
			
			int gainScore = 0;
			if("0".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getBanUnitScore();	
			}else if("1".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getBanUnitScore();
			}else if("2".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getLimitUnitScore();	
			}else if("3".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getQuestionUnitScore();	
			}else if("4".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getPassUnitScore();				
			}else {
				gainScore = 0;
			}
			params.setGainScore(gainScore);
			/*
			if("1".equals(answer.getAnswer())) {//정답
				double result = 100/(double)moduleInfoData.getQuestionCnt();
				result = Math.round(result*1000)/1000.0; 
				params.setGainScore(result);
			}else {//오답
				params.setGainScore(0);
			}*/
			
			int result = learningService.updateLearningAnswer(params); 
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
     * 학습종료
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/endLearning.do")
    @ApiOperation(value = "학습종료", notes = "학습종료")
    public BaseResponse<Learning> endLearning(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}			
			
			params.setModuleId(moduleInfoData.getModuleId());
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());
			params.setStudyLvl(moduleInfoData.getStudyLvl());
			params.setPassScore(baselineData.getPassScore());
			params.setQuestionCnt(moduleInfoData.getQuestionCnt());
			
			//시도횟수
			LearningProblem lpParams = new LearningProblem();
			lpParams.setProcCd(params.getProcCd());
			lpParams.setProcYear(params.getProcYear());
			lpParams.setProcSeq(params.getProcSeq());
			lpParams.setUserId(params.getUserId());
			LearningProblem maxKey = learningService.selectLearningProblemsMaxkey(lpParams);
			params.setTrySeq(maxKey.getTrySeq());				
			
			//학습종료데이터확인
			int baselineCnt = learningService.selectLearningBaselineResultCount(params);
			
			//학습종료데이터등록
			if(baselineCnt <= 0) {
				learningService.insertLearningResult(params);				
			}

			Learning gainScore = learningService.selectLeaningSum(params);
			params.setGainScore(gainScore.getGainScore());
			if(gainScore.getGainScore()>=Double.valueOf(baselineData.getPassScore())) {//통과
				params.setPassYn("Y");
			}else {//과락
				params.setPassYn("N");
			}
			
			//학습종료 틀린갯수 맞은갯수 확인 
			Learning resultCnt = learningService.selectLearningResultCount(params);
			params.setQuestionCnt(resultCnt.getQuestionCnt());
			params.setWrongCnt(resultCnt.getWrongCnt());
			params.setRightCnt(resultCnt.getRightCnt());
			params.setBanCnt(resultCnt.getBanCnt());		
			
			//문제종료처리
			params.setEndYn("Y");
			learningService.updateLearningEnd(params);
			
			//결과데이터저장
			learningService.updateLearningResult(params);
			
			//학습자평균가져오기
			
			return new BaseResponse<Learning>(params);
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }          
    	    
    
    
    
    /**
     * 사용자학습완료정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectLearningComplete.do")
    @ApiOperation(value = "사용자학습완료정보", notes = "사용자학습완료정보")
    public BaseResponse<Learning> selectLearningComplete(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}				
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			moduleInfoData.setUserId(login.getUserId());
			moduleInfoData.setUserName(login.getUserNm());
			LearningProblem lpParams = new LearningProblem();
			lpParams.setUserId(login.getUserId());
			lpParams.setModuleId(moduleInfoData.getModuleId());
			lpParams.setProcCd(baselineData.getProcCd());
			lpParams.setProcYear(baselineData.getProcYear());
			lpParams.setProcSeq(baselineData.getProcSeq());		
			LearningProblem maxKey = learningService.selectLearningProblemsMaxkey(lpParams);
			lpParams.setTrySeq(maxKey.getTrySeq());
			lpParams.setQuestionCnt(moduleInfoData.getQuestionCnt());
			//List<LearningProblem> problems = learningService.selectLearningProblems(lpParams);	
			//if(problems == null) {
				//return new BaseResponse<Learning>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			//}				
			
			lpParams.setEndYn("Y");
			lpParams.setLanguageCode(params.getLanguageCode());
			List<LearningProblem> resultList = learningService.selectLearnProblemsResultList(lpParams);
			
			if(resultList == null) {
				return new BaseResponse<Learning>(BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS, BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS.getMessage());
			}			
			
			moduleInfoData.setLearningProblemList(resultList);
			
			return new BaseResponse<Learning>(moduleInfoData);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
    
    
    /**
     * 사용자학습완료 물품상세정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectLearnProblemsResult.do")
    @ApiOperation(value = "사용자학습완료 물품상세정보", notes = "사용자학습완료 물품상세정보")
    public BaseResponse<List<LearningProblem>> selectLearnProblemsResult(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}				
		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<List<LearningProblem>>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<List<LearningProblem>>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			
			LearningProblem lpParams = new LearningProblem();
			lpParams.setLanguageCode(params.getLanguageCode());
			lpParams.setBagScanId(params.getBagScanId());
			List<LearningProblem> resultList = learningService.selectLearnProblemsResult(lpParams);
			return new BaseResponse<List<LearningProblem>>(resultList);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }        
    
    
    
    
    /**
     * 사용자오답문제정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectWrongAnswer.do")
    @ApiOperation(value = "사용자오답문제정보", notes = "사용자오답문제정보")
    public BaseResponse<Learning> selectWrongAnswer(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			moduleInfoData.setUserId(login.getUserId());
			moduleInfoData.setUserName(login.getUserNm());
			LearningProblem lpParams = new LearningProblem();
			lpParams.setUserId(login.getUserId());
			lpParams.setModuleId(moduleInfoData.getModuleId());
			lpParams.setProcCd(baselineData.getProcCd());
			lpParams.setProcYear(baselineData.getProcYear());
			lpParams.setProcSeq(baselineData.getProcSeq());		
			lpParams.setQuestionCnt(moduleInfoData.getQuestionCnt());
			
			//오답문제가져오기
			List<LearningProblem> problems = learningService.selectWrongAnswerProblems(lpParams);	
			if(problems == null) {
				return new BaseResponse<Learning>(BaseResponseCode.WRONGANSWERPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}				
			//시도횟수
			LearningProblem maxKey = learningService.selectWrongAnswerProblemsMaxkey(lpParams);	
			lpParams.setTrySeq(maxKey.getTrySeq());
			
			//등록된학습문제 체크
			lpParams.setEndYn("N");
			int problemsCnt = learningService.selectWrongAnswerProblemsCount(lpParams);
			
			if(problemsCnt <= 0) {
				//문제등록
				lpParams.setTrySeq(maxKey.getTrySeq()+1);
				moduleInfoData.setTrySeq(maxKey.getTrySeq()+1);
				learningService.insertWrongAnswerProblems(lpParams);				
			}else {
				moduleInfoData.setTrySeq(maxKey.getTrySeq());
			}
			
			List<LearningProblem> resultList = learningService.selectWrongAnswerProblemsList(lpParams);
			
			if(resultList == null) {
				return new BaseResponse<Learning>(BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS, BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS.getMessage());
			}			
			

			List<LearningProblem> result = xbtImageService.selectLeaningImgList(resultList); 
			moduleInfoData.setLearningProblemList(result);
			return new BaseResponse<Learning>(moduleInfoData);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
           
    
    /**
     * 오답문제정답선택
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/updateWrongAnswer.do")
    @ApiOperation(value = "오답문제정답선택", notes = "오답문제정답선택")
    public BaseResponse<Integer> updateWrongAnswer(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}		
		if(StringUtils.isEmpty(params.getBagScanId())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "BagScanId" + BaseApiMessage.REQUIRED.getMessage());
		}
		if(StringUtils.isEmpty(params.getUserActionDiv())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "UserActionDiv" + BaseApiMessage.REQUIRED.getMessage());
		}		
		
		try {
			
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}			
			
			params.setModuleId(moduleInfoData.getModuleId());
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());	
			
			//시도횟수
			LearningProblem lpParams = new LearningProblem();
			lpParams.setProcCd(params.getProcCd());
			lpParams.setProcYear(params.getProcYear());
			lpParams.setProcSeq(params.getProcSeq());
			lpParams.setUserId(params.getUserId());
			LearningProblem maxKey = learningService.selectWrongAnswerProblemsMaxkey(lpParams);			
			params.setTrySeq(maxKey.getTrySeq());
			
			Learning answer = learningService.selectWrongAnswer(params);
			params.setAnswerDiv(answer.getAnswerDiv());
			PointStd score = learningService.selectPointStdScore(params);
			
			int gainScore = 0;
			if("0".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getBanUnitScore();	
			}else if("1".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getBanUnitScore();
			}else if("2".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getLimitUnitScore();	
			}else if("3".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getQuestionUnitScore();	
			}else if("4".contentEquals(params.getUserActionDiv())) {
				gainScore = score.getPassUnitScore();				
			}else {
				gainScore = 0;
			}
			params.setGainScore(gainScore);
			
			int result = learningService.updateWrongAnswer(params); 
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
     * 오답문제풀이종료
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/endWrongAnswer.do")
    @ApiOperation(value = "오답문제풀이종료", notes = "오답문제풀이종료")
    public BaseResponse<Learning> endWrongAnswer(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}			
			
			params.setModuleId(moduleInfoData.getModuleId());
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());
			params.setStudyLvl(moduleInfoData.getStudyLvl());
			params.setPassScore(baselineData.getPassScore());
			params.setQuestionCnt(moduleInfoData.getQuestionCnt());
			
			//시도횟수
			LearningProblem lpParams = new LearningProblem();
			lpParams.setProcCd(params.getProcCd());
			lpParams.setProcYear(params.getProcYear());
			lpParams.setProcSeq(params.getProcSeq());
			lpParams.setUserId(params.getUserId());
			LearningProblem maxKey = learningService.selectWrongAnswerProblemsMaxkey(lpParams);
			params.setTrySeq(maxKey.getTrySeq());				
			
			//오답문제종료데이터확인
			int baselineCnt = learningService.selectWrongAnswerBaselineResultCount(params);
			
			//오답문제종료데이터등록
			if(baselineCnt <= 0) {
				learningService.insertWrongAnswerResult(params);				
			}			

			Learning gainScore = learningService.selectWrongAnswerSum(params);
			params.setGainScore(gainScore.getGainScore());
			if(gainScore.getGainScore()>=Double.valueOf(baselineData.getPassScore())) {//통과
				params.setPassYn("Y");
			}else {//과락
				params.setPassYn("N");
			}
			
			//학습종료 틀린갯수 맞은갯수 확인 
			Learning resultCnt = learningService.selectWrongAnswerResultCount(params);
			params.setQuestionCnt(resultCnt.getQuestionCnt());
			params.setWrongCnt(resultCnt.getWrongCnt());
			params.setRightCnt(resultCnt.getRightCnt());
			params.setBanCnt(resultCnt.getBanCnt());		
			
			//문제종료처리
			params.setEndYn("Y");
			learningService.updateWrongAnswerEnd(params);
			
			//결과데이터저장
			learningService.updateWrongAnswerResult(params);
			
			//학습자평균가져오기
			
			return new BaseResponse<Learning>(params);
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }              
           
    
    /**
     * 사용자오답문제완료정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectWrongAnswerComplete.do")
    @ApiOperation(value = "사용자오답문제완료정보", notes = "사용자오답문제완료정보")
    public BaseResponse<Learning> selectWrongAnswerComplete(HttpServletRequest request, @RequestBody Learning params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Learning>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getMessage());
		}				
		
		//다국어처리조회
		if("ko".equals(params.getLanguageCode())) {
			params.setLanguageCode("kr");
		}		
		
		try {
			Learning baselineData = learningService.selectBaseline(params);
			if(baselineData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}
			
			params.setProcCd(baselineData.getProcCd()); 
			List<Learning> learningData = learningService.selectLearning(params);
			if(learningData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			Learning moduleInfoData = learningService.selectModuleInfo(params);
			if(moduleInfoData == null) {
				return new BaseResponse<Learning>(BaseResponseCode.MODULE_DATA, BaseResponseCode.MODULE_DATA.getMessage());
			}
			moduleInfoData.setUserId(login.getUserId());
			moduleInfoData.setUserName(login.getUserNm());
			LearningProblem lpParams = new LearningProblem();
			lpParams.setUserId(login.getUserId());
			lpParams.setModuleId(moduleInfoData.getModuleId());
			lpParams.setProcCd(baselineData.getProcCd());
			lpParams.setProcYear(baselineData.getProcYear());
			lpParams.setProcSeq(baselineData.getProcSeq());			
			LearningProblem maxKey = learningService.selectWrongAnswerProblemsMaxkey(lpParams);
			lpParams.setTrySeq(maxKey.getTrySeq());
			lpParams.setQuestionCnt(moduleInfoData.getQuestionCnt());
			
			lpParams.setEndYn("Y");
			lpParams.setLanguageCode(params.getLanguageCode());
			List<LearningProblem> resultList = learningService.selectWrongAnswerProblemsResultList(lpParams);
			
			if(resultList == null) {
				return new BaseResponse<Learning>(BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS, BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS.getMessage());
			}			
			
			moduleInfoData.setLearningProblemList(resultList);
			
			return new BaseResponse<Learning>(moduleInfoData);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }        
}
