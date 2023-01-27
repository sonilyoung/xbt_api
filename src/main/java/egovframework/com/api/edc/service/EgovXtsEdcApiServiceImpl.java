package egovframework.com.api.edc.service;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import egovframework.com.api.edc.dao.EgovXtsEdcApiDAO;
import egovframework.com.api.edc.vo.UnitImages;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2014.12.08	 이기하			암호화방식 변경(OfficeFileScrty.encryptPassword)
 *
 *      </pre>
 */
@Log4j2
@Service("EgovXtsEdcApiService")
public class EgovXtsEdcApiServiceImpl implements EgovXtsEdcApiService {

	private String empUrl = "http://127.0.0.1:8080/test/selectEmpUnitImage.do";
	
    @Resource(name = "EgovXtsEdcApiDAO")
	private EgovXtsEdcApiDAO egovXtsEdcApiDAO;

	@Override
	public int saveEmpUnitImage(UnitImages params) throws IOException {
		// TODO Auto-generated method stub
		return egovXtsEdcApiDAO.saveEmpUnitImage(params);
	}

	
	@Override
	public Map<String, Object> selectEmpUnitImage(UnitImages params) throws Exception {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url(empUrl)//url
				  .get()//request method
				  .build();
		Response response = client.newCall(request).execute();
		
		log.info("=================엠폴통신결과==================");
		String result = response.body().string();
		log.info(result);
		Gson gson = new Gson();
		//UnitImages ui = gson.fromJson(result, UnitImages.class);
		Map<String, Object> map = gson.fromJson(result, Map.class);
		//ObjectMapper objectMapper = new ObjectMapper();
		//UnitImages ui = objectMapper.convertValue(result, UnitImages.class);
		log.info(map);
		response.close();
		return map;
	}	


}
