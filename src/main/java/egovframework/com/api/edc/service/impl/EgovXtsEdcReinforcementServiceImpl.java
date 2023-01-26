package egovframework.com.api.edc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.api.edc.service.EgovXtsEdcReinforcementService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service("egovCbtEdcReinforcementServiceImpl")
public class EgovXtsEdcReinforcementServiceImpl implements EgovXtsEdcReinforcementService {
	
	private String url = "http://127.0.0.1:8080/test/arrange_level.do";
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsEdcReinforcementServiceImpl.class);
	
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
	}

}
