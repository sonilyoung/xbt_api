package egovframework.com.scheduler.service;

import egovframework.com.adm.eduMgr.service.EduMgrService;
import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.learningMgr.LearningMgrController;
import egovframework.com.adm.system.service.SystemService;
import egovframework.com.adm.system.vo.XbtScore;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsScheduling.class);
	
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
				
				if(baseline!=null) {
					//이론점수
					XbtScore theory = systemService.selectTheoryScore(xs);
					
					//비중반영전
					int tgtTheoryScore = 0;
					int tgtEvaluationScore = 0;
					int tgtPracticeScore = 0;//일반실기점수
					int tgtpracticeCarScore = 0;//차량실기점수
					int tgtpracticeHumanScore = 0;//대인실기점수					
					
					//비중반영후
					int theoryScore = 0;
					int evaluationScore = 0;
					int practiceScore = 0;//일반실기점수 비중반영
					int practiceCarScore = 0;//차량실기점수 비중반영
					int practiceHumanScore = 0;//대인실기점수 비중반영
					
					
					if(theory!=null) {
						tgtTheoryScore = theory.getGainScore();
						theoryScore = Math.round((theory.getGainScore()*baseline.getTheoryTotalScore())/100);
						LOGGER.info("==============이론==============");
						LOGGER.info("교육생 theoryScore:" + theory.getUserId() + " : " + theory.getGainScore());
						LOGGER.info("설정 theoryScore:" + baseline.getTheoryTotalScore());
						LOGGER.info("theoryScore:" + theoryScore);
						xs.setTheoryScore(theoryScore);
						xs.setCommand("theory");
						systemService.updateXbtScore(xs);					
					}
					
					//평가점수
					XbtScore evaluation = systemService.selectEvaluationScore(xs);
					if(evaluation!=null) {
						tgtEvaluationScore = evaluation.getGainScore();
						evaluationScore =  Math.round((evaluation.getGainScore()*baseline.getEvaluationTotalScore())/100);
						LOGGER.info("==============평가==============");
						LOGGER.info("교육생 evaluationScore:" + evaluation.getUserId() + " : " + evaluation.getGainScore());
						LOGGER.info("설정 evaluationScore:"+ baseline.getEvaluationTotalScore());
						LOGGER.info("evaluationScore:"+ evaluationScore);
						xs.setEvaluationScore(evaluationScore);
						xs.setCommand("evaluation");
						systemService.updateXbtScore(xs);
					}
					

					//항공경비 초기교육 (5일 / 30시간) (4) , 항공경비 인증평가교육 (1일 / 4시간) (6)
					if("4".equals(xs.getEduCode()) || "6".equals(xs.getEduCode())){ 
						//실습점수
						XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
						
						if("4".equals(xs.getEduCode())){
							if(practiceTotalScore!=null) {
								tgtpracticeHumanScore = practiceTotalScore.getPracticeHumanScore();
								
								practiceHumanScore =  Math.round((practiceTotalScore.getPracticeHumanScore()*baseline.getPracticeHumanTotalScore())/100);
								LOGGER.info("==============항공경비 초기교육 (5일 / 30시간) (4) 실습==============");
								LOGGER.info("practiceHumanScore:"+ practiceHumanScore);					
							}								
						}
						
						
						if("6".equals(xs.getEduCode())){
							if(practiceTotalScore!=null) {
								tgtpracticeCarScore = practiceTotalScore.getPracticeCarScore();
								tgtpracticeHumanScore = practiceTotalScore.getPracticeHumanScore();
								
								practiceCarScore =  Math.round((practiceTotalScore.getPracticeCarScore()*baseline.getPracticeCarTotalScore())/100);
								practiceHumanScore =  Math.round((practiceTotalScore.getPracticeHumanScore()*baseline.getPracticeHumanTotalScore())/100);
								LOGGER.info("==============항공경비 인증평가교육 (1일 / 4시간) (6) 실습==============");
								LOGGER.info("practiceCarScore:"+ practiceCarScore);
								LOGGER.info("practiceHumanScore:"+ practiceHumanScore);					
							}								
						}
											
						
						
						XbtScore processTheoryScore = systemService.selectTheoryProcessScore(xs);
						if(processTheoryScore!=null) {
							if("Y".equals(processTheoryScore.getTheoryYn()) && "Y".equals(processTheoryScore.getPracticeYn())) {
								
								/*
								 *  항공경비 초기교육 (5일 / 30시간) (4)
									이론 90점            - 커트라인 80점
									대인 10점 (실기평가)    - 커트라인 80점
								 */
								/*
									항공경비 인증평가교육 (1일 / 4시간) (6)
									이론 90점            - 커트라인 80점
									대인 10점 (실기평가)    - 커트라인 80점
									차량 10점 (실기평가)    - 커트라인 80점
								 */								
								
								if("4".equals(xs.getEduCode())){
									 //커트라인비교 추가
									if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
										xs.setPassYn("N");
									}else if(practiceHumanScore < baseline.getPracticeHumanScore()) {//대인실기커트라인비교
										xs.setPassYn("N");	
									}									
								}
								
								if("6".equals(xs.getEduCode())){
									 //커트라인비교 추가
									if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
										xs.setPassYn("N");
									}else if(practiceCarScore < baseline.getPracticeCarScore()) {//차량실기커트라인비교
										xs.setPassYn("N");
									}else if(practiceHumanScore < baseline.getPracticeHumanScore()) {//대인실기커트라인비교
										xs.setPassYn("N");	
									}									
								}
								
								if(tgtTheoryScore < baseline.getPassTheoryScore()) {
									systemService.updateXbtEndScore(xs); //과락처리
								}
								
								
								if(tgtTheoryScore >= baseline.getPassTheoryScore()) {
									int totalScore = 0;
									
									if("4".equals(xs.getEduCode())){
										totalScore = theoryScore + practiceHumanScore;
									}
									
									if("6".equals(xs.getEduCode())){
										totalScore = theoryScore + practiceHumanScore + practiceCarScore;
									}
									
									xs.setGainScore(totalScore);
									 
									if(totalScore >= baseline.getEndingStdScore()) {
										xs.setPassYn("Y");
									}else {
										xs.setPassYn("N");
									}
									systemService.updateXbtEndScore(xs);								
								}

							}					
						}							
					}else { //일반교육생
						//실습점수
						XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
						if(practiceTotalScore!=null) {
							tgtPracticeScore = practiceTotalScore.getPracticeScore();
							practiceScore =  Math.round((practiceTotalScore.getPracticeScore()*baseline.getPracticeTotalScore())/100);
							LOGGER.info("==============실습==============");
							LOGGER.info("교육생 evaluationScore:" + practiceTotalScore.getUserId() + " : " + practiceTotalScore.getPracticeScore());
							LOGGER.info("설정 evaluationScore:"+ baseline.getPracticeTotalScore());
							LOGGER.info("evaluationScore:"+ practiceTotalScore.getPracticeScore());					
						}						
						
						
						XbtScore processScore = systemService.selectProcessScore(xs);
						if(processScore!=null) {
							if("Y".equals(processScore.getTheoryYn()) && "Y".equals(processScore.getPracticeYn()) && "Y".equals(processScore.getEvaluationYn())) {
								
								 //커트라인비교 추가
								if(tgtEvaluationScore < baseline.getPassScore()) {//평가커트라인비교
									xs.setPassYn("N");
								}else if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
									xs.setPassYn("N");
								}else if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
									xs.setPassYn("N");	
								}
								
								if(tgtEvaluationScore < baseline.getPassScore() || tgtTheoryScore < baseline.getPassTheoryScore() || tgtPracticeScore < baseline.getPassPracticeScore()) {
									systemService.updateXbtEndScore(xs); //과락처리
								}
								
								
								if(tgtEvaluationScore >= baseline.getPassScore() && tgtTheoryScore >= baseline.getPassTheoryScore() && tgtPracticeScore >= baseline.getPassPracticeScore()) {
									int totalScore = theoryScore + evaluationScore + practiceScore;
									xs.setGainScore(totalScore);
									 
									if(totalScore >= baseline.getEndingStdScore()) {
										xs.setPassYn("Y");
									}else {
										xs.setPassYn("N");
									}
									systemService.updateXbtEndScore(xs);								
								}

							}					
						}							
					}
					
					//기간이 지난 차수 ENDING_YN = 'Y'
					systemService.updateBaselineStatus(xs);
				}
				
			}
		}
		
		
			
		
		
	}
}
