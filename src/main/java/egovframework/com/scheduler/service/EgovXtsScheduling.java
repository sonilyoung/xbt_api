package egovframework.com.scheduler.service;

import egovframework.com.adm.eduMgr.service.EduMgrService;
import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.system.service.SystemService;
import egovframework.com.adm.system.vo.XbtScore;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    @Autowired
    private SystemService systemService; 

    @Autowired
    private EduMgrService eduMgrService;    
    
	/**
	 * 하루단위로 작업하는 배치 프로그램
	 * @exception Exception
	 */
	public void xtsStats() throws Exception {
		System.out.println("스케줄링 시작");
		
		//교육생정보가져오기
		XbtScore params = new XbtScore();
		List<XbtScore> userList = systemService.selectXbtEndingUserList(params);
		
		if(userList!=null) {
			
			for(XbtScore  xs : userList) {
				//차수조회
				Baseline bp = new Baseline();
				bp.setProcCd(xs.getProcCd());
				Baseline baseline = eduMgrService.selectBaseline(bp);
				//THEORY_TOTAL_SCORE //이론비중
				//EVALUATION_TOTAL_SCORE //평가비중
				//PRACTICE_TOTAL_SCORE //실습비중
				//baseline.getTheoryTotalScore()
				//baseline.getEvaluationTotalScore()
				//baseline.getPracticeTotalScore()
				
				//이론점수
				XbtScore theory = systemService.selectTheoryScore(xs);
				if(theory!=null) {
					int theoryScore = (theory.getGainScore()*baseline.getTheoryTotalScore())/100;
					xs.setTheoryScore(theoryScore);
					xs.setCommand("theory");
					systemService.updateXbtScore(xs);					
				}
				
				//평가점수
				XbtScore evaluation = systemService.selectEvaluationScore(xs);
				if(evaluation!=null) {
					int evaluationScore = (evaluation.getGainScore()*baseline.getEvaluationTotalScore())/100;
					xs.setEvaluationScore(evaluationScore);
					xs.setCommand("evaluation");
					systemService.updateXbtScore(xs);
				}

				//XbtScore evaluationTotalScore = systemService.selectPracticeScore(xs);
				//systemService.updateXbtScore(xs);

				
			}
		}
		
		
			
		
		
	}
}
