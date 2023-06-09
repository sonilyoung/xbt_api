
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
import egovframework.com.stu.learning.vo.LearningProblem;
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
			
			moduleInfoData.setQuestionList(resultList);
			return new BaseResponse<StuTheory>(moduleInfoData);
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
			xbtImageService.selectTheoryImg(tr);
			
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
     */ 
    @PostMapping("/submitTheory.do")
    @ApiOperation(value = "이론제출", notes = "이론제출.")
    public BaseResponse<StuTheory> submitTheory(HttpServletRequest request, @RequestBody StuTheory params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getQuestionList())){				
			return new BaseResponse<StuTheory>(BaseResponseCode.PARAMS_ERROR, "QuestionList" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			//이론조회
			//StuTheory result = theoryService.submitTheory(params);
			//Theory tr = new Theory();
			//xbtImageService.selectTheoryImg(tr);
			
	        return new BaseResponse<StuTheory>(params);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    } 
}
