package egovframework.com.api.edc.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.api.edc.dao.EgovXtsEdcApiDAO;
import egovframework.com.api.edc.vo.AiForceLearning;
import egovframework.com.api.edc.vo.AiForceLearningResult;
import egovframework.com.api.edc.vo.AiForceUserScore;

@Service("egovCbtEdcReinforcementService")
public class EgovXtsEdcReinforcementServiceImpl implements EgovXtsEdcReinforcementService {
	
	//private String url = "http://127.0.0.1:8080/test/arrange_level.do";
	//private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsEdcReinforcementServiceImpl.class);
	
    @Resource(name = "EgovXtsEdcApiDAO")
	private EgovXtsEdcApiDAO egovXtsEdcApiDAO;	
	
	/*
	@Override
	public boolean reinforcementLearningAi() throws Exception {
		
		boolean result = false;
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url(url)//url
				  .get()//request method
				  .build();
				Response response = client.newCall(request).execute();
				
		result = response.isSuccessful();
		response.close();
		
		return result;
	}*/

	@Override
	@SuppressWarnings("unchecked")
	public List<AiForceLearning> selectLearningList(AiForceLearning params) {
		
		return (List<AiForceLearning>)egovXtsEdcApiDAO.selectLearningList(params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AiForceLearningResult> selectLearningResultList(AiForceLearningResult params) {
		
		return (List<AiForceLearningResult>)egovXtsEdcApiDAO.selectLearningResultList(params);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<AiForceUserScore> selectUserScoreResultList(AiForceUserScore params) {
		
		return (List<AiForceUserScore>)egovXtsEdcApiDAO.selectUserScoreResultList(params);
	}
	
}
