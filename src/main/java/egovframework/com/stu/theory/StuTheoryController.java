
package egovframework.com.stu.theory;

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
import egovframework.com.adm.theory.vo.Theory;
import egovframework.com.file.service.XbtImageService;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.exception.BaseResponse;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.stu.learning.service.LearningService;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.theory.service.StuTheoryService;
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
@RequestMapping("/stu/theory")
@Api(tags = "theory Management API")
public class StuTheoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StuTheoryController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private StuTheoryService theoryService;
    
    @Autowired
    private LearningService learningService;    
    
    @Autowired
    private XbtImageService xbtImageService;    
    
    
    /**
     * 이론정보
     * 
     * @param param
     * @return Company
   */
    @PostMapping("/selectTheoryList.do")
    @ApiOperation(value = "이론정보", notes = "이론정보")
    public BaseResponse<StuTheory> selectTheoryList(HttpServletRequest request, @RequestBody StuTheory params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		params.setUserId(login.getUserId());
		params.setUserName(login.getUserNm());
		
		try {
			StuTheory moduleInfoData = new StuTheory();
			
			Learning lp = new Learning();
			lp.setUserId(login.getUserId());
			Learning baselineData = learningService.selectBaseline(lp);
			if(baselineData == null) {
				return new BaseResponse<StuTheory>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}			
			
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());
			params.setStudyLvl(baselineData.getStudyLvl());
			
			params.setQuestionCnt(baselineData.getTheoryQuestionCnt()); 						
			//이론문제가져오기
			List<StuTheory> problems = theoryService.selectTheoryList(params);
			if(problems == null) {
				return new BaseResponse<StuTheory>(BaseResponseCode.THEORY_DATA, BaseResponseCode.THEORY_DATA.getMessage());
			}				
			
			//시도횟수
			StuTheory maxKey = theoryService.selectTheoryProblemsMaxkey(params);	
			params.setTrySeq(maxKey.getTrySeq());			
			
			//등록된학습문제 체크
			params.setEndYn("N");
			int problemsCnt = theoryService.selectTheoryProblemsCount(params);
			
			
			if(problemsCnt <= 0) {
				//문제등록
				params.setTrySeq(maxKey.getTrySeq()+1);
				moduleInfoData.setTrySeq(maxKey.getTrySeq()+1);
				theoryService.insertTheoryProblems(params);				
			}else {
				moduleInfoData.setTrySeq(maxKey.getTrySeq());
			}
			
			List<StuTheory> resultList = theoryService.selectTheoryProblemsList(params);
			
			if(resultList == null) {
				return new BaseResponse<StuTheory>(BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS, BaseResponseCode.DATA_IS_NULL_LAERNPROBLEMS.getMessage());
			}			
			
			params.setQuestionList(resultList);
			return new BaseResponse<StuTheory>(params);
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
    public BaseResponse<StuTheory> selectTheory(HttpServletRequest request, @RequestBody StuTheory params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getQuestionId())){				
			return new BaseResponse<StuTheory>(BaseResponseCode.PARAMS_ERROR, "QuestionId" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			//이론조회
			StuTheory result = theoryService.selectTheory(params);
			Theory tr = new Theory();
			tr.setQuestionId(params.getQuestionId());
			xbtImageService.selectTheoryImg(tr);
			result.setChoiceImg1(tr.getChoiceImg1());
			result.setChoiceImg2(tr.getChoiceImg2());
			result.setChoiceImg3(tr.getChoiceImg3());
			result.setChoiceImg4(tr.getChoiceImg4());
			result.setMultiPlusImg(tr.getMultiPlusImg());
			
	        return new BaseResponse<StuTheory>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }  
    
    
    
    
    
    /**
     * 이론제출
     * 
     * @param param
     * @return Company
     
    @PostMapping("/updateTheory.do")
    @ApiOperation(value = "이론제출", notes = "이론제출.")
    public BaseResponse<Integer> updateTheory(HttpServletRequest request, @RequestBody List<StuTheory> params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params)){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "params" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			StuTheory st = new StuTheory();
			int result = 0;
			for(StuTheory s : params) {
				Learning lp = new Learning();
				lp.setUserId(login.getUserId());
				Learning baselineData = learningService.selectBaseline(lp);
				if(baselineData == null) {
					return new BaseResponse<Integer>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
				}	
				
				StuTheory answer = theoryService.selectTheoryAnswer(s);
				s.setAnswerDiv(answer.getAnswerDiv());
				
				if("1".equals(answer.getAnswer())) {//정답
					answer.setGainScore(Math.round(baselineData.getQuestionCnt()/100));
				}else {//오답
					answer.setGainScore(0);
				}
				
				result = theoryService.updateTheoryAnswer(answer);
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
    } */ 
    
    
    /**
     * 이론종료
     * 
     * @param param
     * @return Company
    */  
    @PostMapping("/endTheory.do")
    @ApiOperation(value = "이론종료", notes = "이론종료")
    public BaseResponse<Integer> endTheory(HttpServletRequest request, @RequestBody StuTheory params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		params.setUserId(login.getUserId());
		
		if(StringUtils.isEmpty(params.getMenuCd())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "MenuCd" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		if(StringUtils.isEmpty(params.getTheoryList())){				
			return new BaseResponse<Integer>(BaseResponseCode.PARAMS_ERROR, "TheoryList" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			Learning lp = new Learning();
			lp.setUserId(login.getUserId());			
			Learning baselineData = learningService.selectBaseline(lp);
			if(baselineData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.BASELINE_DATA, BaseResponseCode.BASELINE_DATA.getMessage());
			}			
			
			params.setProcCd(baselineData.getProcCd());
			params.setProcYear(baselineData.getProcYear());
			params.setProcSeq(baselineData.getProcSeq());
			
			params.setQuestionCnt(baselineData.getTheoryQuestionCnt()); 
			//이론문제가져오기
			List<StuTheory> problems = theoryService.selectTheoryList(params);
			int problemsCnt = 0;
			if(problems!=null) {
				problemsCnt = problems.size();	
			}
			 
			
			//시도횟수
			StuTheory maxKey = theoryService.selectTheoryProblemsMaxkey(params);
			params.setTrySeq(maxKey.getTrySeq());			
			
			for(StuTheory s : params.getTheoryList()) {
				s.setUserId(login.getUserId());
				s.setProcCd(baselineData.getProcCd());
				s.setProcYear(baselineData.getProcYear());
				s.setProcSeq(baselineData.getProcSeq());
				s.setTrySeq(maxKey.getTrySeq());
				StuTheory answer = theoryService.selectTheoryAnswer(s);
				s.setAnswerDiv(answer.getAnswerDiv());
				answer.setQuestionId(s.getQuestionId());
				params.setQuestionId(s.getQuestionId());
				if("1".equals(answer.getAnswer())) {//정답
					//double result = 100/(double)baselineData.getTheoryQuestionCnt();
					double result = 100/(double)problemsCnt;
					result = Math.round(result*1000)/1000.0; 
					answer.setGainScore(result);					
				}else {//오답
					answer.setGainScore(0);
				}
				
				answer.setUserId(login.getUserId());
				answer.setProcCd(baselineData.getProcCd());
				answer.setProcYear(baselineData.getProcYear());
				answer.setProcSeq(baselineData.getProcSeq());
				answer.setTrySeq(maxKey.getTrySeq());
				answer.setUserActionDiv(s.getUserActionDiv());
				theoryService.updateTheoryAnswer(answer);
			}			
			
			lp.setProcCd(baselineData.getProcCd()); 
			lp.setUserId(login.getUserId());
			lp.setProcCd(baselineData.getProcCd());
			lp.setProcYear(baselineData.getProcYear());
			lp.setTrySeq(maxKey.getTrySeq());			
			List<Learning> learningData = learningService.selectLearning(lp); /*menuCd*/
			if(learningData == null) {
				return new BaseResponse<Integer>(BaseResponseCode.EDU_DATA, BaseResponseCode.EDU_DATA.getMessage());
			}
			
			//학습종료데이터확인
			int baselineCnt = theoryService.selectTheoryBaselineResultCount(params);
			
			//학습종료데이터등록
			if(baselineCnt <= 0) {
				StuTheory studyLvl = theoryService.selectStudyLvlTheory(params);
				params.setStudyLvl(studyLvl.getTrySeq()); 
				theoryService.insertTheoryResult(params);				
			}

			StuTheory gainScore = theoryService.selectTheorySum(params);
			params.setGainScore(gainScore.getGainScore());
			if(gainScore.getGainScore()>=Double.valueOf(baselineData.getPassScore())) {//통과
				params.setPassYn("Y");
			}else {//과락
				params.setPassYn("N");
			}
			
			//학습종료 틀린갯수 맞은갯수 확인 
			StuTheory resultCnt = theoryService.selectTheoryResultCount(params);
			params.setQuestionCnt(resultCnt.getQuestionCnt());
			params.setWrongCnt(resultCnt.getWrongCnt());
			params.setRightCnt(resultCnt.getRightCnt());		
			
			//문제종료처리
			params.setEndYn("Y");
			theoryService.updateTheoryEnd(params);
			
			//결과데이터저장
			int result = theoryService.updateTheoryResult(params);
			
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
