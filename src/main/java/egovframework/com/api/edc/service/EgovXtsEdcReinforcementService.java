package egovframework.com.api.edc.service;

import java.util.List;

import egovframework.com.api.edc.vo.AiForceLearning;
import egovframework.com.api.edc.vo.AiForceLearningResult;
import egovframework.com.api.edc.vo.AiForceUserScore;

public interface EgovXtsEdcReinforcementService {
	
	//public boolean reinforcementLearningAi() throws Exception;
	
	public List<AiForceLearning> selectLearningList(AiForceLearning params);
	
	public List<AiForceLearningResult> selectLearningResultList(AiForceLearningResult params);
	
	public List<AiForceUserScore> selectUserScoreResultList(AiForceUserScore params);
	
}
