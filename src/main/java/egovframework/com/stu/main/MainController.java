
package egovframework.com.stu.main;

import java.util.ArrayList;
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
import egovframework.com.adm.system.service.SystemService;
import egovframework.com.adm.system.vo.Notice;
import egovframework.com.global.OfficeMessageSource;
import egovframework.com.global.http.BaseApiMessage;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.http.exception.BaseException;
import egovframework.com.stu.main.service.MainService;
import egovframework.com.stu.main.vo.Schedule;
import egovframework.com.stu.main.vo.Statistics;
import egovframework.com.stu.main.vo.UserStInfo;
import egovframework.com.stu.practice.service.PracticeService;
import egovframework.com.stu.practice.vo.UnitGroup;
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
@RequestMapping("/stu/main")
@Api(tags = "Login Management API")
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private OfficeMessageSource officeMessageSource;

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private SystemService systemService; 
    
    @Autowired
    private MainService mainService; 
    
    @Autowired
    private PracticeService practiceService;      
    
    /**
     * 사용자정보조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectBaselineUserInfo.do")
    @ApiOperation(value = "사용자정보조회", notes = "사용자정보조회.")
    public BaseResponse<UserStInfo> selectBaselineUserInfo(HttpServletRequest request, @RequestBody UserStInfo params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		params.setUserId(login.getUserId());
		try {
			//공지사항조회
	        return new BaseResponse<UserStInfo>(mainService.selectBaselineUserInfo(params));
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }      
    
    
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
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
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }    
    
    
	/**
     * 일정목록조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectScheduleList.do")
    @ApiOperation(value = "일정목록", notes = "일정목록조회.")
    public BaseResponse<Schedule> selectScheduleList(HttpServletRequest request, @RequestBody Schedule params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Schedule>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		try {
			//일정조회
			
			params.setUserId(login.getUserId());
			params.setPMenuCd("1");
			List<Schedule> menu1 = mainService.selectScheduleList(params);
			params.setPMenuCd("2");
			List<Schedule> menu2 = mainService.selectScheduleList(params);
			params.setPMenuCd("3");
			List<Schedule> menu3 = mainService.selectScheduleList(params);
			params.setPMenuCd("4");
			List<Schedule> menu4 = mainService.selectScheduleList(params);
			
			Schedule result = new Schedule();
			result.setMenu1(menu1);
			result.setMenu2(menu2);
			result.setMenu3(menu3);
			result.setMenu4(menu4);
	        return new BaseResponse<Schedule>(result);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }   
	    
    
	/**
     * 통계조회
     * 
     * @param param
     * @return Company
     */
    @PostMapping("/selectStatisticsList.do")
    @ApiOperation(value = "통계조회", notes = "통계조회.")
    public BaseResponse<Statistics> selectStatisticsList(HttpServletRequest request, @RequestBody Statistics params) {
    	Login login = loginService.getLoginInfo(request);
		if (login == null) {
			throw new BaseException(BaseResponseCode.AUTH_FAIL);
		}
		
		if(StringUtils.isEmpty(params.getLanguageCode())){				
			return new BaseResponse<Statistics>(BaseResponseCode.PARAMS_ERROR, "LanguageCode" + BaseApiMessage.REQUIRED.getCode());
		}				
		
		if(StringUtils.isEmpty(params.getType())){				
			return new BaseResponse<Statistics>(BaseResponseCode.PARAMS_ERROR, "Type" + BaseApiMessage.REQUIRED.getCode());
		}		
		
		try {
			//일정조회
			params.setUserId(login.getUserId());
			
			if("1".equals(params.getType())){//학습점수조회
				List<Statistics> titleList = mainService.selectStatisticsTitleList(params);
				List<Statistics> dataList = mainService.selectStatisticsContensList(params);
				
				if(titleList!=null) {
					String [] categorys = new String[titleList.size()];
					int i = 0;
					for(Statistics s : titleList) {
						categorys[i] = s.getProcYear() + " level" + String.valueOf(s.getStudyLvl()) + " "+s.getTrySeq(); 
						i++;
					}
					params.setCategories(categorys);
				}
				
				if(dataList!=null) {
					List<Integer> level1 = new ArrayList<Integer>();
					List<Integer> level2 = new ArrayList<Integer>();
					List<Integer>level3 = new ArrayList<Integer>();
					List<Integer>level4 = new ArrayList<Integer>();
					List<Integer>level5 = new ArrayList<Integer>();
					
					int i = 0;
					for(Statistics s : dataList) {
						if(s.getStudyLvl()==1) {
							level1.add(s.getGainScore()); 
							level2.add(0);
							level3.add(0);
							level4.add(0);
							level5.add(0);
						}else if(s.getStudyLvl()==2){
							level2.add(s.getGainScore()); 
							level1.add(0);
							level3.add(0);
							level4.add(0);
							level5.add(0);
						}else if(s.getStudyLvl()==3){
							level3.add(s.getGainScore()); 
							level1.add(0);
							level2.add(0);
							level4.add(0);
							level5.add(0);
						}else if(s.getStudyLvl()==4){
							level4.add(s.getGainScore()); 
							level1.add(0);
							level2.add(0);
							level3.add(0);
							level5.add(0);
						}else if(s.getStudyLvl()==5){
							level5.add(s.getGainScore()); 
							level1.add(0);
							level2.add(0);
							level3.add(0);
							level4.add(0);
						}
						i++;
					}
					params.setLevel1(level1);				
					params.setLevel2(level2);				
					params.setLevel3(level3);				
					params.setLevel4(level4);				
					params.setLevel5(level5);				
				}				
			}else if("2".equals(params.getType())) {//교육평가조회
				List<Statistics> titleList = mainService.selectStatisticsTitleList(params);
				params.setEduEvaluationList(titleList);
			}else {//오답조회
				
				//총건수
				List<Statistics> dataList1 = mainService.selectStatisticsWrongAnswerList1(params);
				//평균
				List<Statistics> dataList2 = mainService.selectStatisticsWrongAnswerList2(params);
				//오답
				List<Statistics> dataList3 = mainService.selectStatisticsWrongAnswerList3(params);
				
				//총건수
				List<Integer>totalCnt = new ArrayList<Integer>();				
				//평균
				List<Integer> averageCnt = new ArrayList<Integer>();
				//오답
				List<Integer> wrongAnswerCnt = new ArrayList<Integer>();
				
				for(Statistics s : dataList1) {
					totalCnt.add(s.getTotal());
				}
				
				for(Statistics s : dataList2) {
					averageCnt.add(s.getTotal());
				}
				
				for(Statistics s : dataList3) {
					wrongAnswerCnt.add(s.getTotal());
				}
					
				params.setTotalCnt(totalCnt);
				params.setAverageCnt(averageCnt);
				params.setWrongAnswerCnt(wrongAnswerCnt);

				//그룹관리조회
				//UnitGroup ug = new UnitGroup();
				//ug.setLanguageCode(params.getLanguageCode());
				//List<UnitGroup> groupList = practiceService.selectUnitGroupList(ug);
				
				String [] categorys = new String[dataList1.size()];
				int i = 0;
				for(Statistics s : dataList1) {
					categorys[i] = s.getUnitGroupName(); 
					i++;
				}
				params.setCategories(categorys);				
			}
			
	        return new BaseResponse<Statistics>(params);
        } catch (Exception e) {
        	LOGGER.error("error:", e);
            throw new BaseException(BaseResponseCode.UNKONWN_ERROR, BaseResponseCode.UNKONWN_ERROR.getMessage());
        }
    }       
           
}
