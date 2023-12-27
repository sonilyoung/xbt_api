package egovframework.com.api.edc.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;

@Service("egovCbtEdcPseudoFilterServiceImpl")
public class EgovXtsEdcPseudoFilterServiceImpl implements EgovXtsEdcPseudoFilterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsEdcPseudoFilterServiceImpl.class);
	
	private String url = "http://192.168.132.220:8000/filter";

	@Override
	public String pseudoFilter(Object value) throws Exception {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		LOGGER.info("Pseudo Image Generator");
		@SuppressWarnings("unchecked")
		Map<String, String> map = mapper.convertValue(value, Map.class);
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				@SuppressWarnings("unused")
				MediaType mediaType = MediaType.parse("multipart/form-data");
				RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("Param", map.get("param"))
					.addFormDataPart("NAME", map.get("name"))
					.addFormDataPart("IMG_RAW_H", map.get("imgF"))
					.addFormDataPart("IMG_RAW_L", map.get("imgS"))
					.build();
				Request request = new Request.Builder()
					.url(url)
					.method("POST", body)
					.build();
				Response response = client.newCall(request).execute();
		LOGGER.info("의사색채 이미지 생성 API 데이터 형식 : " + response.body().contentType().toString());
		LOGGER.info(response.body().contentLength() + "");
		ByteString binary = response.body().byteString();
		response.close();
		result = binary.base64();
		return result;
	}
	
}
