package egovframework.com.score;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.adm.eduMgr.service.EduMgrService;
import egovframework.com.adm.eduMgr.vo.Baseline;
import egovframework.com.adm.system.service.SystemService;
import egovframework.com.adm.system.vo.XbtScore;
import egovframework.com.stu.learning.dao.LearningDAO;
import egovframework.com.stu.learning.vo.Learning;
import egovframework.com.stu.learning.vo.PointStd;


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

@Service
public class XbtScoreServiceImpl implements XbtScoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(XbtScoreServiceImpl.class);
	
    @Autowired
    private SystemService systemService; 

    @Autowired
    private EduMgrService eduMgrService;    

    @Resource(name = "LearningDAO")
	private LearningDAO learningDAO;
    
	/**
	 * 사용자 점수 계산
	 * @exception Exception
	 */
	public void userScoreCalculateAdmin(String userId) throws Exception {
		LOGGER.info("=======================사용자 점수 계산 시작=============================");
	
		//교육생정보 가져오기
		XbtScore params = new XbtScore();
		params.setUserId(userId);
		XbtScore xs = systemService.selectXbtEndingUser(params);
		
		if(xs!=null) {
			LOGGER.info("eduCode : " + xs.getEduCode());
			LOGGER.info("eduName : " + xs.getEduName());
			LOGGER.info("id : " + xs.getUserId());
			LOGGER.info("이름 : " + xs.getUserNm());
			
			//차수조회
			Baseline bp = new Baseline();
			bp.setProcCd(xs.getProcCd());
			Baseline baseline = eduMgrService.selectBaseline(bp);
			
			if(baseline!=null) {
				
				//비중반영전
				int tgtTheoryScore = 0;
				int tgtDangerScore = 0;//위험물
				int tgtEvaluationScore = 0;
				int tgtPracticeScore = 0;//일반실기점수
				//int tgtpracticeCarScore = 0;//차량실기점수
				//int tgtpracticeHumanScore = 0;//대인실기점수					
				
				//비중반영후
				int theoryScore = 0;
				int dangerScore = 0;//위험물
				int evaluationScore = 0;
				int practiceScore = 0;//일반실기점수 비중반영
				//int practiceCarScore = 0;//차량실기점수 비중반영
				//int practiceHumanScore = 0;//대인실기점수 비중반영
				
				
				//이론
				XbtScore theory = systemService.selectTheoryScore(xs);					
				if(theory != null) {
					//이론점수
					tgtTheoryScore = theory.getGainScore();
					
					theoryScore = Math.round((theory.getGainScore()*baseline.getTheoryTotalScore())/100);
					LOGGER.info("==============이론==============");
					LOGGER.info("교육생 theoryScore:" + theory.getUserId() + " : " + theory.getGainScore());
					LOGGER.info("설정 theoryScore:" + baseline.getTheoryTotalScore());
					LOGGER.info("theoryScore:" + theoryScore);
					xs.setTheoryScore(theoryScore);
					xs.setCommand("theory");
					systemService.updateXbtScore(xs);	
					
					tgtDangerScore = theory.getDangerScore();
					
					theoryScore = Math.round(theory.getDangerScore());
					LOGGER.info("==============이론 위험물==============");
					LOGGER.info("교육생 theoryScore:" + theory.getUserId() + " : " + theory.getDangerScore());
					LOGGER.info("dangerScore:" + dangerScore);
					xs.setDangerScore(dangerScore);
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
				
				
				XbtScore processScore = systemService.selectProcessScore(xs);
				
				//1 -> 보안검색요원 초기교육 (5일 / 40시간) - 실기(비중30) , 이론(비중30) , CBT(비중40) -50문제 
				//항공위험물이론 별도 커트라인 80점 
				if("1".equals(xs.getEduCode())){
					if(processScore!=null) {
						//실습점수
						XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
						if(practiceTotalScore!=null) {
							tgtPracticeScore = practiceTotalScore.getPracticeScore();
							practiceScore =  Math.round((practiceTotalScore.getPracticeScore()));
							LOGGER.info("==============실습점수==============");
							LOGGER.info("교육생 evaluationScore:" + practiceTotalScore.getUserId() + " : " + practiceTotalScore.getPracticeScore());
							LOGGER.info("설정 evaluationScore:"+ baseline.getPracticeTotalScore());
							LOGGER.info("evaluationScore:"+ practiceTotalScore.getPracticeScore());					
						}							
						
						//이론
						if("Y".equals(processScore.getTheoryYn())){
							if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
								xs.setTheoryPassYn("N");
							}else {
								xs.setTheoryPassYn("Y");
							}
						}
							
						//실습
						if("Y".equals(processScore.getPracticeYn())){
							if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
								xs.setPracticePassYn("N");	
							}else {
								xs.setPracticePassYn("Y");	
							}						 
						}
						
						//평가
						if("Y".equals(processScore.getEvaluationYn())) {
							if(tgtEvaluationScore < baseline.getPassScore()) {//평가커트라인비교
								xs.setEvaluationPassYn("N");
							}else {
								xs.setEvaluationPassYn("Y");
							}
						}
						
						//위험물
						if("Y".equals(processScore.getDangerYn())) {
							if(tgtDangerScore < baseline.getPassDangerScore()) {//평가커트라인비교
								xs.setDangerPassYn("N");
							}else {
								xs.setDangerPassYn("Y");
							}						
						}						
						
						if(tgtEvaluationScore >= baseline.getPassScore() 
							&& tgtTheoryScore >= baseline.getPassTheoryScore() 
							&& tgtPracticeScore >= baseline.getPassPracticeScore()
							&& tgtDangerScore >= baseline.getPassDangerScore()
						) {
							int totalScore = theoryScore + evaluationScore + practiceScore;
							xs.setGainScore(totalScore);
							 
							if(totalScore >= baseline.getEndingStdScore()) {
								xs.setEndingYn("Y");
								xs.setPassYn("Y");
							}else {
								xs.setEndingYn("ING");
								xs.setPassYn("N");
							}
							systemService.updateXbtEndScore(xs);								
						}else {
							xs.setEndingYn("ING");
							xs.setPassYn("N");
							systemService.updateXbtEndScore(xs);
						}

					}					
				}
				
				//2 -> 보안검색요원 정기교육 (1일 / 8시간) - 평가만
				if("2".equals(xs.getEduCode())){
					if(processScore!=null) {
						if("Y".equals(processScore.getEvaluationYn())) {
							
							 //커트라인비교 추가
							if(tgtEvaluationScore < baseline.getPassScore()) {//평가커트라인비교
								xs.setPassYn("N");
							}
							
							if(tgtEvaluationScore < baseline.getPassScore()) {
								systemService.updateXbtEndScore(xs); //과락처리
							}
							
							
							if(tgtEvaluationScore >= baseline.getPassScore()) {
								int totalScore = evaluationScore;
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
				
				//항공경비 초기교육 (5일 / 30시간) (4) , 항공경비 인증평가교육 (1일 / 4시간) (6)
				if("4".equals(xs.getEduCode()) || "6".equals(xs.getEduCode())){ 
					
				}
				
				
				
				
				
				
				
				
				

				

				//항공경비 초기교육 (5일 / 30시간) (4) , 항공경비 인증평가교육 (1일 / 4시간) (6)
				if("4".equals(xs.getEduCode()) || "6".equals(xs.getEduCode())){ 
					//실습점수
					XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
					
					if("4".equals(xs.getEduCode())){
						if(practiceTotalScore!=null) {
							//practiceHumanScore =  Math.round((practiceTotalScore.getPracticeHumanScore()*baseline.getPracticeHumanTotalScore())/100);
							practiceScore =  Math.round(practiceTotalScore.getPracticeScore());
							LOGGER.info("==============항공경비 초기교육 (5일 / 30시간) (4) 실습==============");
							LOGGER.info("practiceHumanScore:"+ practiceScore);					
						}								
					}
					
					
					if("6".equals(xs.getEduCode())){
						if(practiceTotalScore!=null) {
							//tgtpracticeCarScore = practiceTotalScore.getPracticeCarScore();
							//tgtpracticeHumanScore = practiceTotalScore.getPracticeHumanScore();
							
							//practiceCarScore =  Math.round((practiceTotalScore.getPracticeCarScore()*baseline.getPracticeCarTotalScore())/100);
							//practiceHumanScore =  Math.round((practiceTotalScore.getPracticeHumanScore()*baseline.getPracticeHumanTotalScore())/100);
							
							//practiceCarScore =  Math.round(practiceTotalScore.getPracticeCarScore());
							//practiceHumanScore =  Math.round(practiceTotalScore.getPracticeHumanScore());	
							practiceScore =  Math.round(practiceTotalScore.getPracticeScore());
							LOGGER.info("==============항공경비 인증평가교육 (1일 / 4시간) (6) 실습==============");
							LOGGER.info("practiceHumanScore:"+ practiceScore);								
						}								
					}
										
					
					
					
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
								//}else if(practiceHumanScore < baseline.getPracticeHumanScore()) {//대인실기커트라인비교
									//xs.setPassYn("N");	
								//}	
								}else if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
									xs.setPassYn("N");	
								}
																
							}
							
							if("6".equals(xs.getEduCode())){
								 //커트라인비교 추가
								if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
									xs.setPassYn("N");
								//}else if(practiceCarScore < baseline.getPracticeCarScore()) {//차량실기커트라인비교
									//xs.setPassYn("N");
								//}else if(practiceHumanScore < baseline.getPracticeHumanScore()) {//대인실기커트라인비교
									//xs.setPassYn("N");	
								//}
								}else if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
									xs.setPassYn("N");	
								}									
							}
							
							if(tgtTheoryScore < baseline.getPassTheoryScore()) {
								systemService.updateXbtEndScore(xs); //과락처리
							}
							
							
							if(tgtTheoryScore >= baseline.getPassTheoryScore()) {
								int totalScore = 0;
								
								if("4".equals(xs.getEduCode())){
									//totalScore = theoryScore + practiceHumanScore;
									totalScore = theoryScore + practiceScore;
								}
								
								if("6".equals(xs.getEduCode())){
									//totalScore = theoryScore + practiceHumanScore + practiceCarScore;
									totalScore = theoryScore + practiceScore;
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
				//1 -> 보안검색요원 초기교육 (5일 / 40시간) - 실기(비중30) , 이론(비중30) , CBT(비중40) -50문제 
				//항공위험물이론 별도 커트라인 80점 
				}else if("1".equals(xs.getEduCode())){
					 //일반교육생
					//실습점수
					XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
					if(practiceTotalScore!=null) {
						tgtPracticeScore = practiceTotalScore.getPracticeScore();
						//practiceScore =  Math.round((practiceTotalScore.getPracticeScore()*baseline.getPracticeTotalScore())/100);
						practiceScore =  Math.round((practiceTotalScore.getPracticeScore()));
						LOGGER.info("==============실습점수==============");
						LOGGER.info("교육생 evaluationScore:" + practiceTotalScore.getUserId() + " : " + practiceTotalScore.getPracticeScore());
						LOGGER.info("설정 evaluationScore:"+ baseline.getPracticeTotalScore());
						LOGGER.info("evaluationScore:"+ practiceTotalScore.getPracticeScore());					
					}						
					
					
					XbtScore processScore = systemService.selectProcessScore(xs);
					if(processScore!=null) {
						
						//이론
						if("Y".equals(processScore.getTheoryYn())){
							if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
								xs.setPassYn("N");
							}
						}
							
						//실습
						if("Y".equals(processScore.getPracticeYn())){
							if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
								xs.setPassYn("N");	
							}							 
						}
						
						//평가
						if("Y".equals(processScore.getEvaluationYn())) {
							if(tgtEvaluationScore < baseline.getPassScore()) {//평가커트라인비교
								xs.setPassYn("N");
							}
						}
						
						//위험물
						if("Y".equals(processScore.getDangerYn())) {
							if(tgtDangerScore < baseline.getPassDangerScore()) {//평가커트라인비교
								xs.setPassYn("N");
							}							
						}						
						
						if(tgtEvaluationScore < baseline.getPassScore() 
							|| tgtTheoryScore < baseline.getPassTheoryScore() 
							|| tgtPracticeScore < baseline.getPassPracticeScore()) {
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
				
					
				//2 -> 보안검색요원 정기교육 (1일 / 8시간) - 평가만
				}else if("2".equals(xs.getEduCode())){
					
					XbtScore processScore = systemService.selectProcessScore(xs);
					if(processScore!=null) {
						if("Y".equals(processScore.getEvaluationYn())) {
							
							 //커트라인비교 추가
							if(tgtEvaluationScore < baseline.getPassScore()) {//평가커트라인비교
								xs.setPassYn("N");
							}
							
							if(tgtEvaluationScore < baseline.getPassScore()) {
								systemService.updateXbtEndScore(xs); //과락처리
							}
							
							
							if(tgtEvaluationScore >= baseline.getPassScore()) {
								int totalScore = evaluationScore;
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
					//5 -> 항공경비 정기교육 (1일 / 8시간)
					//10 -> 항공보안경비 감독자 정기 (1일/8시간)
				}else if("5".equals(xs.getEduCode()) || "10".equals(xs.getEduCode())){
					LOGGER.info("==============처리사항 없음...==============");
				}else { //일반교육생
					//실습점수
					XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
					if(practiceTotalScore!=null) {
						tgtPracticeScore = practiceTotalScore.getPracticeScore();
						//practiceScore =  Math.round((practiceTotalScore.getPracticeScore()*baseline.getPracticeTotalScore())/100);
						practiceScore =  Math.round((practiceTotalScore.getPracticeScore()));
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
				//systemService.updateBaselineStatus(xs);
			}			
		}
		
		LOGGER.info("=======================사용자 점수 계산 끝=============================");
	}
	
	/**
	 * 사용자 점수 계산
	 * @exception Exception
	 */
	public void userScoreCalculate(String userId) throws Exception {
		LOGGER.info("=======================사용자 점수 계산 시작=============================");
	
		//교육생정보 가져오기
		XbtScore params = new XbtScore();
		params.setUserId(userId);
		XbtScore xs = systemService.selectXbtEndingUser(params);
		
		if(xs!=null) {
			LOGGER.info("eduCode : " + xs.getEduCode());
			LOGGER.info("eduName : " + xs.getEduName());
			LOGGER.info("id : " + xs.getUserId());
			LOGGER.info("이름 : " + xs.getUserNm());
			
			//차수조회
			Baseline bp = new Baseline();
			bp.setProcCd(xs.getProcCd());
			Baseline baseline = eduMgrService.selectBaseline(bp);
			
			if(baseline!=null) {
				//이론점수
				XbtScore theory = systemService.selectTheoryScore(xs);
				
				//비중반영전
				int tgtTheoryScore = 0;
				int tgtDangerScore = 0;//위험물
				int tgtEvaluationScore = 0;
				int tgtPracticeScore = 0;//일반실기점수
				//int tgtpracticeCarScore = 0;//차량실기점수
				//int tgtpracticeHumanScore = 0;//대인실기점수					
				
				//비중반영후
				int theoryScore = 0;
				int dangerScore = 0;//위험물
				int evaluationScore = 0;
				int practiceScore = 0;//일반실기점수 비중반영
				//int practiceCarScore = 0;//차량실기점수 비중반영
				//int practiceHumanScore = 0;//대인실기점수 비중반영
				
				
				//이론
				if(theory != null) {
					
					tgtTheoryScore = theory.getGainScore();
					
					theoryScore = Math.round((theory.getGainScore()*baseline.getTheoryTotalScore())/100);
					LOGGER.info("==============이론==============");
					LOGGER.info("교육생 theoryScore:" + theory.getUserId() + " : " + theory.getGainScore());
					LOGGER.info("설정 theoryScore:" + baseline.getTheoryTotalScore());
					LOGGER.info("theoryScore:" + theoryScore);
					xs.setTheoryScore(theoryScore);
					xs.setCommand("theory");
					systemService.updateXbtScore(xs);	
					
					tgtDangerScore = theory.getDangerScore();
					
					theoryScore = Math.round(theory.getDangerScore());
					LOGGER.info("==============이론 위험물==============");
					LOGGER.info("교육생 theoryScore:" + theory.getUserId() + " : " + theory.getDangerScore());
					LOGGER.info("dangerScore:" + dangerScore);
					xs.setDangerScore(dangerScore);
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
							//practiceHumanScore =  Math.round((practiceTotalScore.getPracticeHumanScore()*baseline.getPracticeHumanTotalScore())/100);
							practiceScore =  Math.round(practiceTotalScore.getPracticeScore());
							LOGGER.info("==============항공경비 초기교육 (5일 / 30시간) (4) 실습==============");
							LOGGER.info("practiceHumanScore:"+ practiceScore);					
						}								
					}
					
					
					if("6".equals(xs.getEduCode())){
						if(practiceTotalScore!=null) {
							//tgtpracticeCarScore = practiceTotalScore.getPracticeCarScore();
							//tgtpracticeHumanScore = practiceTotalScore.getPracticeHumanScore();
							
							//practiceCarScore =  Math.round((practiceTotalScore.getPracticeCarScore()*baseline.getPracticeCarTotalScore())/100);
							//practiceHumanScore =  Math.round((practiceTotalScore.getPracticeHumanScore()*baseline.getPracticeHumanTotalScore())/100);
							
							//practiceCarScore =  Math.round(practiceTotalScore.getPracticeCarScore());
							//practiceHumanScore =  Math.round(practiceTotalScore.getPracticeHumanScore());	
							practiceScore =  Math.round(practiceTotalScore.getPracticeScore());
							LOGGER.info("==============항공경비 인증평가교육 (1일 / 4시간) (6) 실습==============");
							LOGGER.info("practiceHumanScore:"+ practiceScore);								
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
								//}else if(practiceHumanScore < baseline.getPracticeHumanScore()) {//대인실기커트라인비교
									//xs.setPassYn("N");	
								//}	
								}else if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
									xs.setPassYn("N");	
								}
																
							}
							
							if("6".equals(xs.getEduCode())){
								 //커트라인비교 추가
								if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
									xs.setPassYn("N");
								//}else if(practiceCarScore < baseline.getPracticeCarScore()) {//차량실기커트라인비교
									//xs.setPassYn("N");
								//}else if(practiceHumanScore < baseline.getPracticeHumanScore()) {//대인실기커트라인비교
									//xs.setPassYn("N");	
								//}
								}else if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
									xs.setPassYn("N");	
								}									
							}
							
							if(tgtTheoryScore < baseline.getPassTheoryScore()) {
								systemService.updateXbtEndScore(xs); //과락처리
							}
							
							
							if(tgtTheoryScore >= baseline.getPassTheoryScore()) {
								int totalScore = 0;
								
								if("4".equals(xs.getEduCode())){
									//totalScore = theoryScore + practiceHumanScore;
									totalScore = theoryScore + practiceScore;
								}
								
								if("6".equals(xs.getEduCode())){
									//totalScore = theoryScore + practiceHumanScore + practiceCarScore;
									totalScore = theoryScore + practiceScore;
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
				//1 -> 보안검색요원 초기교육 (5일 / 40시간) - 실기(비중30) , 이론(비중30) , CBT(비중40) -50문제 
				//항공위험물이론 별도 커트라인 80점 
				}else if("1".equals(xs.getEduCode())){
					 //일반교육생
					//실습점수
					XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
					if(practiceTotalScore!=null) {
						tgtPracticeScore = practiceTotalScore.getPracticeScore();
						//practiceScore =  Math.round((practiceTotalScore.getPracticeScore()*baseline.getPracticeTotalScore())/100);
						practiceScore =  Math.round((practiceTotalScore.getPracticeScore()));
						LOGGER.info("==============실습점수==============");
						LOGGER.info("교육생 evaluationScore:" + practiceTotalScore.getUserId() + " : " + practiceTotalScore.getPracticeScore());
						LOGGER.info("설정 evaluationScore:"+ baseline.getPracticeTotalScore());
						LOGGER.info("evaluationScore:"+ practiceTotalScore.getPracticeScore());					
					}						
					
					
					XbtScore processScore = systemService.selectProcessScore(xs);
					if(processScore!=null) {
						
						//이론
						if("Y".equals(processScore.getTheoryYn())){
							if(tgtTheoryScore < baseline.getPassTheoryScore()) {//이론평가커트라인비교
								xs.setPassYn("N");
							}
						}
							
						//실습
						if("Y".equals(processScore.getPracticeYn())){
							if(tgtPracticeScore < baseline.getPassPracticeScore()) {//실기커트라인비교
								xs.setPassYn("N");	
							}							 
						}
						
						//평가
						if("Y".equals(processScore.getEvaluationYn())) {
							if(tgtEvaluationScore < baseline.getPassScore()) {//평가커트라인비교
								xs.setPassYn("N");
							}
						}
						
						//위험물
						if("Y".equals(processScore.getDangerYn())) {
							if(tgtDangerScore < baseline.getPassDangerScore()) {//평가커트라인비교
								xs.setPassYn("N");
							}							
						}						
						
						if(tgtEvaluationScore < baseline.getPassScore() 
							|| tgtTheoryScore < baseline.getPassTheoryScore() 
							|| tgtPracticeScore < baseline.getPassPracticeScore()) {
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
				
					
				//2 -> 보안검색요원 정기교육 (1일 / 8시간) - 평가만
				}else if("2".equals(xs.getEduCode())){
					
					XbtScore processScore = systemService.selectProcessScore(xs);
					if(processScore!=null) {
						if("Y".equals(processScore.getEvaluationYn())) {
							
							 //커트라인비교 추가
							if(tgtEvaluationScore < baseline.getPassScore()) {//평가커트라인비교
								xs.setPassYn("N");
							}
							
							if(tgtEvaluationScore < baseline.getPassScore()) {
								systemService.updateXbtEndScore(xs); //과락처리
							}
							
							
							if(tgtEvaluationScore >= baseline.getPassScore()) {
								int totalScore = evaluationScore;
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
					//5 -> 항공경비 정기교육 (1일 / 8시간)
					//10 -> 항공보안경비 감독자 정기 (1일/8시간)
				}else if("5".equals(xs.getEduCode()) || "10".equals(xs.getEduCode())){
					LOGGER.info("==============처리사항 없음...==============");
				}else { //일반교육생
					//실습점수
					XbtScore practiceTotalScore = systemService.selectPracticeScore(xs);
					if(practiceTotalScore!=null) {
						tgtPracticeScore = practiceTotalScore.getPracticeScore();
						//practiceScore =  Math.round((practiceTotalScore.getPracticeScore()*baseline.getPracticeTotalScore())/100);
						practiceScore =  Math.round((practiceTotalScore.getPracticeScore()));
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
				//systemService.updateBaselineStatus(xs);
			}			
		}
		
		LOGGER.info("=======================사용자 점수 계산 끝=============================");
	}	
	
	
	@Override
	public int selectCommonScoreResult(Learning params) throws Exception {
		// TODO Auto-generated method stub
		PointStd score = learningDAO.selectPointStdScore(params);
		
		/*
		int gainScore = 0;
		if("0".equals(params.getAnswerDiv())) {
			if("0".equals(params.getUserActionDiv())) { //반입금지
				gainScore = score.getQuestionUnitScore();
			}else if("1".equals(params.getUserActionDiv())) { //의심물품
				gainScore = score.getBanUnitScore();
			}			
		}else if("1".equals(params.getAnswerDiv())) {
			if("0".equals(params.getUserActionDiv())) { //반입금지
				gainScore = score.getQuestionUnitScore();	
			}else if("1".equals(params.getUserActionDiv())) { //의심물품
				gainScore = score.getBanUnitScore();
			}
		}else if("2".equals(params.getAnswerDiv())) {
			if(params.getAnswerDiv().equals(params.getUserActionDiv())) {
				gainScore = score.getLimitUnitScore();
			}
		}else if("3".equals(params.getAnswerDiv())) {
			if("3".equals(params.getUserActionDiv())) { //통과
				gainScore = score.getQuestionUnitScore();
			}else if("4".equals(params.getUserActionDiv())) { //의심
				gainScore = score.getPassUnitScore();	
			}	
		}else if("4".equals(params.getAnswerDiv())) {
			if("3".equals(params.getUserActionDiv())) { //통과
				gainScore = score.getQuestionUnitScore();
			}else if("4".equals(params.getUserActionDiv())) { //의심
				gainScore = score.getPassUnitScore();	
			}			
		}else {
			gainScore = 0;
		}*/
		
		/* 점수체계 3개로반영*/
		int gainScore = 0;
		if("0".contentEquals(params.getUserActionDiv()) || "1".contentEquals(params.getUserActionDiv())) {
			gainScore = score.getBanUnitScore();
		}else if("2".contentEquals(params.getUserActionDiv())) {
			gainScore = score.getLimitUnitScore();	
		}else if("3".contentEquals(params.getUserActionDiv()) || "4".contentEquals(params.getUserActionDiv())) {
			gainScore = score.getPassUnitScore();				
		}else {
			gainScore = 0;
		}		
		
		
		return gainScore;
	}
	
}
