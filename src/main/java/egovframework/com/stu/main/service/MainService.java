package egovframework.com.stu.main.service;

import java.util.List;

import egovframework.com.stu.main.vo.Schedule;
import egovframework.com.stu.main.vo.Statistics;
import egovframework.com.stu.main.vo.UserStInfo;

/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @see
 * @version 1.0
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *
 *      </pre>
 */
public interface MainService {
	
	public UserStInfo selectBaselineUserInfo(UserStInfo params);
	
	public List<Schedule> selectScheduleList(Schedule params);
	
	public List<Statistics> selectStatisticsTitleList(Statistics params);
	
	public List<Statistics> selectStatisticsContensList(Statistics params);
	
	public List<Statistics> selectStatisticsWrongAnswerList1(Statistics params);
	
	public List<Statistics> selectStatisticsWrongAnswerList2(Statistics params);
	
	public List<Statistics> selectStatisticsWrongAnswerList3(Statistics params);

}
