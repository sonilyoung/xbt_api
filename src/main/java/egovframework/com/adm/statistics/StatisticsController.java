
package egovframework.com.adm.statistics;

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

import egovframework.com.adm.contents.vo.Language;
import egovframework.com.adm.login.service.LoginService;
import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.statistics.service.StatisticsService;
import egovframework.com.adm.statistics.vo.StatisticsGroup;
import egovframework.com.adm.statistics.vo.StatisticsPerformance;
import egovframework.com.adm.statistics.vo.StatisticsPerformanceDetail;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
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
@RequestMapping("/adm/statistics")
@Api(tags = "Statistics Management API")
public class StatisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private StatisticsService statisticsService;

    
  
    /**
     * 통계학습실적조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsLearningList.do")
    @ApiOperation(value = "통계학습실적조회", notes = "통계학습실적조회.")
    public BaseResponse<List<StatisticsPerformance>> selectStatisticsLearningList(HttpServletRequest request, @RequestBody StatisticsPerformance params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			//통계학습실적조회
	        return new BaseResponse<List<StatisticsPerformance>>(statisticsService.selectStatisticsLearningList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   
    
    
    
    /**
     *  통계 - 사용자별 학습실적조회 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsLearning.do")
    @ApiOperation(value = " 통계 - 사용자별 학습실적조회 ", notes = " 통계 - 사용자별 학습실적조회 .")
    public BaseResponse<List<StatisticsPerformanceDetail>> selectStatisticsLearning(HttpServletRequest request, @RequestBody StatisticsPerformanceDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<List<StatisticsPerformanceDetail>>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			//통계학습실적조회
	        return new BaseResponse<List<StatisticsPerformanceDetail>>(statisticsService.selectStatisticsLearning(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   
    
    
    /**
     *  통계 - 사용자별 학습실적조회상세 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsLearningDetail.do")
    @ApiOperation(value = " 통계 - 사용자별 학습실적조회상세 ", notes = " 통계 - 사용자별 학습실적조회상세 .")
    public BaseResponse<List<StatisticsPerformanceDetail>> selectStatisticsLearningDetail(HttpServletRequest request, @RequestBody StatisticsPerformanceDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<List<StatisticsPerformanceDetail>>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		if(StringUtils.isEmpty(params.getUserId())){				
			return new BaseResponse<List<StatisticsPerformanceDetail>>(BaseResponseCode.PARAMS_ERROR, "UserId" + BaseApiMessage.REQUIRED.getMessage());
		}			
		
		try {
			//통계학습실적조회
	        return new BaseResponse<List<StatisticsPerformanceDetail>>(statisticsService.selectStatisticsLearningDetail(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }        
        
    
    
    /**
     * 통계학습그룹조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsLearningGroupList.do")
    @ApiOperation(value = "통계학습그룹조회", notes = "통계학습그룹조회.")
    public BaseResponse<List<StatisticsGroup>> selectStatisticsLearningGroupList(HttpServletRequest request, @RequestBody StatisticsGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			//통계학습그룹조회
	        return new BaseResponse<List<StatisticsGroup>>(statisticsService.selectStatisticsLearningGroupList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }    
        
    
    
    
    
    /**
     * 통계평가실적조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsEvaluationList.do")
    @ApiOperation(value = "통계평가실적조회", notes = "통계평가실적조회.")
    public BaseResponse<List<StatisticsPerformance>> selectStatisticsEvaluationList(HttpServletRequest request, @RequestBody StatisticsPerformance params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			//통계학습실적조회
	        return new BaseResponse<List<StatisticsPerformance>>(statisticsService.selectStatisticsEvaluationList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   
    
    /**
     *  통계 - 사용자별 평가실적조회 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsEvaluation.do")
    @ApiOperation(value = " 통계 - 사용자별 평가실적조회 ", notes = " 통계 - 사용자별 평가실적조회 .")
    public BaseResponse<List<StatisticsPerformanceDetail>> selectStatisticsEvaluation(HttpServletRequest request, @RequestBody StatisticsPerformanceDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<List<StatisticsPerformanceDetail>>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			//통계학습실적조회
	        return new BaseResponse<List<StatisticsPerformanceDetail>>(statisticsService.selectStatisticsEvaluation(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }      
    
    /**
     * 통계평가그룹조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsEvaluationGroupList.do")
    @ApiOperation(value = "통계평가그룹조회", notes = "통계평가그룹조회.")
    public BaseResponse<List<StatisticsGroup>> selectStatisticsEvaluationGroupList(HttpServletRequest request, @RequestBody StatisticsGroup params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			//통계학습그룹조회
	        return new BaseResponse<List<StatisticsGroup>>(statisticsService.selectStatisticsEvaluationGroupList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }        
    
    
    /**
     * 통계이론실적조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsTheoryList.do")
    @ApiOperation(value = "통계이론실적조회", notes = "통계이론실적조회.")
    public BaseResponse<List<StatisticsPerformance>> selectStatisticsTheoryList(HttpServletRequest request, @RequestBody StatisticsPerformance params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		
		try {
			//통계이론실적조회
	        return new BaseResponse<List<StatisticsPerformance>>(statisticsService.selectStatisticsTheoryList(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }   
        
    
    
    /**
     *  통계 - 사용자별 이론평가실적조회 
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsTheory.do")
    @ApiOperation(value = " 통계 - 사용자별 이론평가실적조회 ", notes = " 통계 - 사용자별 이론평가실적조회 .")
    public BaseResponse<List<StatisticsPerformanceDetail>> selectStatisticsTheory(HttpServletRequest request, @RequestBody StatisticsPerformanceDetail params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getProcCd())){				
			return new BaseResponse<List<StatisticsPerformanceDetail>>(BaseResponseCode.PARAMS_ERROR, "ProcCd" + BaseApiMessage.REQUIRED.getMessage());
		}	
		
		try {
			//통계학습실적조회
	        return new BaseResponse<List<StatisticsPerformanceDetail>>(statisticsService.selectStatisticsTheory(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, e.getMessage());
        }
    }         
	    
}
