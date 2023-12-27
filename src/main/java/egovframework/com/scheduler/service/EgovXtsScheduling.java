package egovframework.com.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.adm.system.service.SystemService;
import egovframework.com.adm.system.vo.XbtScore;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


/**
 * 스케줄링 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.04.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.04.16  박지욱          최초 생성
 *  2011.06.30  이기하          패키지 분리(sts -> sts.bst)
 *
 *  </pre>
 */

@Service("egovXtsScheduling")
public class EgovXtsScheduling extends EgovAbstractServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsScheduling.class);
	
    @Autowired
    private SystemService systemService; 

	/**
	 * 하루단위로 작업하는 배치 프로그램
	 * @exception Exception
	 */
	public void xtsStats() throws Exception {
		LOGGER.info("========스케줄링 시작=======");
		XbtScore params = new XbtScore();
		
		//학습데이터 일정종료처리
		systemService.updateLearningStatus(params);
		
		//기간이 지난 차수 ENDING_YN = 'Y'
		systemService.updateBaselineStatus(params);
		LOGGER.info("========스케줄링 끝=======");
	}
}
