
package egovframework.com.stu.evaluation;

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
import egovframework.com.file.service.XbtImageService;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.stu.evaluation.service.EvaluationService;
import egovframework.com.stu.learning.service.LearningService;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.LearningProblem;
import egovframework.com.stu.learning.vo.PointStd;
import egovframework.com.stu.theory.vo.StuTheory;
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
@RequestMapping("/stu/evaluation")
@Api(tags = "evaluation Management API")
public class EvaluationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluationController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    

    @Autowired
    private LearningService learningService;    
    
    @Autowired
    private EvaluationService evaluationService;
    
    @Autowired
    private XbtImageService xbtImageService;        
    
    
    /**
     * 사용자평가정보
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/selectEvaluation.do")
    @ApiOperation(value = "사용자평가정보", notes = "사용자평가정보")
    public BaseResponse<Learning> selectEvaluation(HttpServletRequest request, @RequestBody Learning params) {
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
			
			//평가문제가져오기
			List<LearningProblem> problems = learningService.selectLearningProblems(lpParams);	
			if(problems == null) {
				return new BaseResponse<Learning>(BaseResponseCode.EVALUATIONPROBLEM_DATA, BaseResponseCode.LEARNINGPROBLEM_DATA.getMessage());
			}				
			
			//시도횟수
			LearningProblem maxKey = evaluationService.selectEvaluationProblemsMaxkey(lpParams);	
			lpParams.setTrySeq(maxKey.getTrySeq());		
			
			//평가유무확인
			int processYn = evaluationService.selectEvaluationProcessYnCount(lpParams);
			if(processYn == moduleInfoData.getQuestionCnt()) {
				return new BaseResponse<Learning>(BaseResponseCode.ALREADY_STARE, BaseResponseCode.ALREADY_STARE.getMessage());
			}	
			
			//등록된평가문제 체크
			//lpParams.setEndYn("N");
			int problemsCnt = evaluationService.selectEvaluationProblemsCount(lpParams);
			
			//시도횟수
			//LearningProblem maxKey = learningService.selectLearningProblemsMaxkey(lpParams);			
			if(problemsCnt <= 0) {
				//문제등록
				lpParams.setTrySeq(maxKey.getTrySeq());
				moduleInfoData.setTrySeq(maxKey.getTrySeq());			
				evaluationService.insertEvaluationProblems(lpParams);				
			}else {
				moduleInfoData.setTrySeq(maxKey.getTrySeq());
			}			
			
			List<LearningProblem> resultList = evaluationService.selectEvaluationProblemsList(lpParams);
			
			if(resultList == null) {
				return new BaseResponse<Learning>(BaseResponseCode.DATA_IS_NULL_EVALPROBLEMS, BaseResponseCode.DATA_IS_NULL_EVALPROBLEMS.getMessage());
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
     * 평가정답선택
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/updateEvaluationAnswer.do")
    @ApiOperation(value = "평가정답선택", notes = "평가정답선택")
    public BaseResponse<Integer> updateEvaluationAnswer(HttpServletRequest request, @RequestBody Learning params) {
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
			LearningProblem maxKey = evaluationService.selectEvaluationProblemsMaxkey(lpParams);			
			params.setTrySeq(maxKey.getTrySeq());			
			
			Learning answer = evaluationService.selectEvaluationAnswer(params);
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
			
			int result = evaluationService.updateEvaluationAnswer(params); 
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
     * 평가종료
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/endEvaluation.do")
    @ApiOperation(value = "평가종료", notes = "평가종료")
    public BaseResponse<Learning> endEvaluation(HttpServletRequest request, @RequestBody Learning params) {
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
			
			//시도횟수
			LearningProblem lpParams = new LearningProblem();
			lpParams.setProcCd(params.getProcCd());
			lpParams.setProcYear(params.getProcYear());
			lpParams.setProcSeq(params.getProcSeq());
			lpParams.setUserId(params.getUserId());
			LearningProblem maxKey = evaluationService.selectEvaluationProblemsMaxkey(lpParams); 
			params.setTrySeq(maxKey.getTrySeq());				
			
			//평가종료데이터확인
			int baselineCnt = evaluationService.selectEvaluationBaselineResultCount(params);
			
			//평가종료데이터등록
			if(baselineCnt <= 0) {
				evaluationService.insertEvaluationResult(params);				
			}			
			
			Learning gainScore = evaluationService.selectEvaluationSum(params);
			params.setGainScore(gainScore.getGainScore());
			if(gainScore.getGainScore()>=Double.valueOf(baselineData.getPassScore())) {//통과
				params.setPassYn("Y");
			}else {//과락
				params.setPassYn("N");
			}
			
			//학습종료 틀린갯수 맞은갯수 확인 
			Learning resultCnt = evaluationService.selectEvaluationResultCount(params);
			params.setQuestionCnt(resultCnt.getQuestionCnt());
			params.setWrongCnt(resultCnt.getWrongCnt());
			params.setRightCnt(resultCnt.getRightCnt());				
			
			evaluationService.updateEvaluationResult(params);
			
			//평가자평균가져오기
			
			return new BaseResponse<Learning>(params);
			
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }          
    	        
}
