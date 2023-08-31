package egovframework.com.api.edc.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.api.edc.service.EgovXtsEdcThreeDimensionService;
import egovframework.com.global.common.GlobalsProperties;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;


@Service("sudoImgServiceServiceImpl")
public class SudoImgServiceServiceImpl implements SudoImgService {
	
    /*카이스트api*/
    public static final String url = GlobalsProperties.getProperty("kist.target.api");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SudoImgServiceServiceImpl.class);

	@Override
	public String sudoImg(Object value) throws Exception {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		
		LOGGER.info("=========sudoImg start=========");
		
		long testTime = System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		Map<String, String> map = mapper.convertValue(value, Map.class);
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				@SuppressWarnings("unused")
				MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
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
				
		LOGGER.info("sudoImg api: " + response.body().contentType().toString());
		LOGGER.info(response.body().contentLength() + "");
		LOGGER.info("=========sudoImg end=========");
		
		ByteString binary = response.body().byteString();
		response.close();
		result = binary.base64();
		LOGGER.info("End sudoImg Time : " + (System.currentTimeMillis() - testTime ) + "ms");
		return result;
	}

}
