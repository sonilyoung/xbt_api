package egovframework.com.api.edc.service;

import java.io.File;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import egovframework.com.adm.contents.vo.XrayContents;
import egovframework.com.api.edc.dao.EgovXtsEdcApiDAO;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.edc.vo.FaceVO;
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.TowdGeneration;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponseCode;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Service("faceServiceImpl")
public class XbtFaceApiServiceImpl implements XbtFaceApiService {
    
	/*외부 api*/
    public static final String url = GlobalsProperties.getProperty("face.target.api");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XbtFaceApiService.class);

    @Resource(name = "EgovXtsEdcApiDAO")
	private EgovXtsEdcApiDAO egovXtsEdcApiDAO;
	
	@Autowired
	private FileStorageService fileStorageService;

	@Override
	public FaceVO insertFaceApi(FaceVO params) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("=========insertFaceApi start=========");
		
		//long testTime = System.currentTimeMillpis();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				.connectTimeout(1, TimeUnit.HOURS)
				.writeTimeout(1, TimeUnit.HOURS)
				.readTimeout(1, TimeUnit.HOURS).build();
		
		// 이미지 파일을 읽고, Base64로 인코딩하여 JSON 데이터에 포함시킴
		//File imageFile1 = new File(KAIST_XRAY_ROOT_DIR+File.separator+oj.getBagScanId()+File.separator+af1.getSaveFileName());
		//byte[] imageData1 = Files.readAllBytes(imageFile1.toPath());
		
		//File imageFile2 = new File(KAIST_XRAY_ROOT_DIR+File.separator+oj.getBagScanId()+File.separator+af2.getSaveFileName());
		//byte[] imageData2 = Files.readAllBytes(imageFile2.toPath());		
		//String encodedImageData = Base64.getEncoder().encodeToString(imageData);

		
        // 요청 파라미터 설정
        MediaType mediaType = MediaType.parse("multipart/form-data");
		
        // Create a form body with your parameters
        
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("request_id", "value1")//의뢰번호
                .addFormDataPart("image", "value2")//이미지 데이터 (메타 템플릿
                .addFormDataPart("info_key", "value2")//사용자 정보 데이터 키 (유일키)
                .addFormDataPart("info", "value2") //JSON String (사용자 정보 데이터 : 리턴용) ex) {“name” : “테스트” , “ssn” : “2301013000000” }
                .addFormDataPart("file", "file_name.txt",
                        RequestBody.create(MediaType.parse("text/plain"), new File("file_path.txt")))
                .build();
        

        //Regist ( 얼굴 정보 등록)
		String tgtUrl = url+File.separator+"/1/regis";
		
		// 요청 생성
		Request request = new Request.Builder()
				//.header("Authorization", "Bearer " + al.getAccessToken()) // 토큰을 헤더에 추가
		        .url(tgtUrl)
		        .post(requestBody)
		        .build();

		// 요청 실행
		Response response = client.newCall(request).execute();
		
        // 수신한 JSON 데이터 읽기
        String jsonData = response.body().string();
		LOGGER.info("=========insertFaceApi end=========");
		System.out.println("response: " + response);
		System.out.println("Received JSON data: " + jsonData);		
		LOGGER.info("=========insertFaceApi end=========");
		
		return null;
	}    
		
	
}
