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


@Service("egovCbtEdcThreeDimensionServiceImpl")
public class EgovXtsEdcThreeDimensionServiceImpl implements EgovXtsEdcThreeDimensionService {
	
	private String url = "http://192.168.132.220:8000/recon3d";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXtsEdcThreeDimensionServiceImpl.class);

	@Override
	public String threeDimension(Object value) throws Exception {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		LOGGER.info("3D Image Generator");
		long testTime = System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		Map<String, String> map = mapper.convertValue(value, Map.class);
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				@SuppressWarnings("unused")
				MediaType mediaType = MediaType.parse("multipart/form-data");
				/*.addFormDataPart("IMG_RAW_H", map.get("imgF"))
				.addFormDataPart("IMG_RAW_L", map.get("imgS"))*/
				RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("ANGLE", map.get("param"))
					.addFormDataPart("ID", map.get("name"))
					.addFormDataPart("IMG_FRONT_RAW", map.get("imgF"))
					.addFormDataPart("IMG_SIDE_RAW", map.get("imgS"))
					.build();
				Request request = new Request.Builder()
					.url(url)
					.method("POST", body)
					.build();
				Response response = client.newCall(request).execute();
		LOGGER.info("3D 영상 생성 API 데이터 형식 : " + response.body().contentType().toString());
		LOGGER.info(response.body().contentLength() + "");
		ByteString binary = response.body().byteString();
		response.close();
		result = binary.base64();
		LOGGER.info("End Current Time : " + (System.currentTimeMillis() - testTime ) + "ms");
		return result;
	}

}
