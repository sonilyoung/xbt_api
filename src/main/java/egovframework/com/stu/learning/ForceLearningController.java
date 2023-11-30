
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
@Api(tags = "force learning Management API")
public class ForceLearningController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForceLearningController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private LearningService learningService;
    
    @Autowired
    private XbtImageService xbtImageService;        
    
    
    /**
     * 사용자강화학습정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectForceLearning.do")
    @ApiOperation(value = "사용자강화학습정보", notes = "사용자강화학습정보")
    public BaseResponse<Learning> selectForceLearning(HttpServletRequest request, @RequestBody Learning params) {
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
			//lpParams.setQuestionCnt(moduleInfoData.getQuestionCnt());
			
			//강화학습문제가져오기
			List<LearningProblem> problems = learningService.selectForceLearningProblems(lpParams);	
			if(problems == null) {
				return new BaseResponse<Learning>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}				
			//시도횟수
			LearningProblem maxKey = learningService.selectForceLearningProblemsMaxkey(lpParams);	
			lpParams.setTrySeq(maxKey.getTrySeq());
			
			//등록된강화학습문제 체크
			lpParams.setEndYn("N");
			int problemsCnt = learningService.selectForceLearningProblemsCount(lpParams);
			
			
			if(problemsCnt <= 0) {
				//문제등록
				lpParams.setTrySeq(maxKey.getTrySeq()+1);
				moduleInfoData.setTrySeq(maxKey.getTrySeq()+1);
				learningService.insertForceLearningProblems(lpParams);				
			}else {
				moduleInfoData.setTrySeq(maxKey.getTrySeq());
			}
			
			List<LearningProblem> resultList = learningService.selectForceLearningProblemsList(lpParams);
			
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
     * 강화학습정답선택
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/updateForceLearningAnswer.do")
    @ApiOperation(value = "강화학습정답선택", notes = "강화학습정답선택")
    public BaseResponse<Integer> updateForceLearningAnswer(HttpServletRequest request, @RequestBody Learning params) {
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
			LearningProblem maxKey = learningService.selectForceLearningProblemsMaxkey(lpParams);			
			params.setTrySeq(maxKey.getTrySeq());
			
			/*
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
			*/
			/*점수계산*/
			int gainScore = learningService.selectCommonScoreResult(params);
			params.setGainScore(gainScore);
			
			
			/*
			if("1".equals(answer.getAnswer())) {//정답
				double result = 100/(double)moduleInfoData.getQuestionCnt();
				result = Math.round(result*1000)/1000.0; 
				params.setGainScore(result);
			}else {//오답
				params.setGainScore(0);
			}*/
			
			int result = learningService.updateForceLearningAnswer(params); 
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
     * 강화학습종료
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/endForceLearning.do")
    @ApiOperation(value = "강화학습종료", notes = "강화학습종료")
    public BaseResponse<Learning> endForceLearning(HttpServletRequest request, @RequestBody Learning params) {
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
			LearningProblem maxKey = learningService.selectForceLearningProblemsMaxkey(lpParams);
			params.setTrySeq(maxKey.getTrySeq());				
			
			//강화학습종료데이터확인
			int baselineCnt = learningService.selectForceLearningBaselineResultCount(params);
			
			//강화학습종료데이터등록
			if(baselineCnt <= 0) {
				learningService.insertForceLearningResult(params);				
			}

			Learning gainScore = learningService.selectForceLearningSum(params);
			params.setGainScore(gainScore.getGainScore());
			if(gainScore.getGainScore()>=Double.valueOf(baselineData.getPassScore())) {//통과
				params.setPassYn("Y");
			}else {//과락
				params.setPassYn("N");
			}
			
			//강화학습종료 틀린갯수 맞은갯수 확인 
			Learning resultCnt = learningService.selectForceLearningResultCount(params);
			params.setQuestionCnt(resultCnt.getQuestionCnt());
			params.setWrongCnt(resultCnt.getWrongCnt());
			params.setRightCnt(resultCnt.getRightCnt());
			params.setBanCnt(resultCnt.getBanCnt());		
			
			//문제종료처리
			params.setEndYn("Y");
			learningService.updateForceLearningEnd(params);
			
			//결과데이터저장
			learningService.updateForceLearningResult(params);
			
			//강화학습자평균가져오기
			
			return new BaseResponse<Learning>(params);
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }          
    	    
    
    
    
    /**
     * 사용자강화학습완료정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectForceLearningComplete.do")
    @ApiOperation(value = "사용자강화학습완료정보", notes = "사용자강화학습완료정보")
    public BaseResponse<Learning> selectForceLearningComplete(HttpServletRequest request, @RequestBody Learning params) {
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
			LearningProblem maxKey = learningService.selectForceLearningProblemsMaxkey(lpParams);
			lpParams.setTrySeq(maxKey.getTrySeq());
			lpParams.setQuestionCnt(moduleInfoData.getQuestionCnt());
			//List<LearningProblem> problems = learningService.selectLearningProblems(lpParams);	
			//if(problems == null) {
				//return new BaseResponse<Learning>(BaseResponseCode.LEARNINGPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			//}				
			
			lpParams.setEndYn("Y");
			lpParams.setLanguageCode(params.getLanguageCode());
			List<LearningProblem> resultList = learningService.selectForceLearningProblemsResultList(lpParams);
			
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
