package egovframework.com.api.edc.service;

import java.io.File;
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

import egovframework.com.api.edc.dao.EgovXtsEdcApiDAO;
import egovframework.com.api.edc.vo.FaceVO;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.common.GlobalsProperties;
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
	
    /*단품 저장경로*/
    public static final String FACE_ROOT_DIR = GlobalsProperties.getProperty("face.img.path"); 

	@Override
	public JsonNode insertFaceApi(MultipartFile faceImg, FaceVO params) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("=========insertFaceApi start=========");
		
		AttachFile af = new AttachFile();
		af.setTargetName(params.getUserId());
		af.setCommand("regist");
		AttachFile afInfo = fileStorageService.createImageFile(params.getUserId(), af, faceImg);
		//long testTime = System.currentTimeMillpis();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				.connectTimeout(1, TimeUnit.HOURS)
				.writeTimeout(1, TimeUnit.HOURS)
				.readTimeout(1, TimeUnit.HOURS).build();
		
		// JSON 데이터 생성
		JSONObject json = new JSONObject();
		json.put("userId", params.getUserId());

		// JSON 데이터를 문자열로 변환
		String jsonString = json.toString();

		LOGGER.info("face path: " + FACE_ROOT_DIR+File.separator+params.getUserId()+File.separator+afInfo.getSaveFileName());
		
        // 요청 파라미터 설정
        MediaType mediaType = MediaType.parse("multipart/form-data");
		
        // Create a form body with your parameters
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("request_id", params.getUserId())//의뢰번호
                .addFormDataPart("info_key", params.getUserId())//사용자 정보 데이터 키 (유일키)
                .addFormDataPart("info", jsonString) //JSON String (사용자 정보 데이터 : 리턴용) ex) {“name” : “테스트” , “ssn” : “2301013000000” }
                //이미지 데이터 (메타 템플릿
                .addFormDataPart("image", afInfo.getSaveFileName()+"."+afInfo.getFileExt(), RequestBody.create(mediaType, 
                		new File(FACE_ROOT_DIR+File.separator+params.getUserId()+File.separator+afInfo.getSaveFileName())))
                .build();
        

        //Regist ( 얼굴 정보 등록)
		String tgtUrl = url+"/1/regist";
		
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
		LOGGER.info("response: " + response);
		LOGGER.info("Received JSON data: " + jsonData);		
		LOGGER.info("=========insertFaceApi end=========");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode result = mapper.readTree(jsonData);
		return result;
	}    
		
	
	@Override
	public JsonNode livenessFaceApi(MultipartFile faceImg, FaceVO params) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("=========livenessFaceApi start=========");
		
		AttachFile af = new AttachFile();
		af.setTargetName(params.getUserId());
		af.setCommand("liveness");
		AttachFile afInfo = fileStorageService.createImageFile(params.getUserId(), af, faceImg);
		//long testTime = System.currentTimeMillpis();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				.connectTimeout(1, TimeUnit.HOURS)
				.writeTimeout(1, TimeUnit.HOURS)
				.readTimeout(1, TimeUnit.HOURS).build();
		
		LOGGER.info("face path: " + FACE_ROOT_DIR+File.separator+params.getUserId()+File.separator+afInfo.getSaveFileName());
		
        // 요청 파라미터 설정
        MediaType mediaType = MediaType.parse("multipart/form-data");
		
        // Create a form body with your parameters
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("request_id", params.getUserId())//의뢰번호
                //이미지 데이터 (메타 템플릿
                .addFormDataPart("image", afInfo.getSaveFileName()+"."+afInfo.getFileExt(), RequestBody.create(mediaType, 
                		new File(FACE_ROOT_DIR+File.separator+params.getUserId()+File.separator+afInfo.getSaveFileName())))
                .build();
        

        //Regist (획득한 사진에서 얼굴이 진짜인지 여부를 체크)
		String tgtUrl = url+"/1/liveness";
		
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
		LOGGER.info("=========livenessFaceApi end=========");
		LOGGER.info("response: " + response);
		LOGGER.info("Received JSON data: " + jsonData);		
		LOGGER.info("=========livenessFaceApi end=========");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode result = mapper.readTree(jsonData);
		return result;
	}  
	
	@Override
	public JsonNode matchFaceApi(MultipartFile faceImg, FaceVO params) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("=========matchFaceApi start=========");
		
		AttachFile af = new AttachFile();
		af.setTargetName(params.getUserId());
		af.setCommand("match");
		AttachFile afInfo = fileStorageService.createImageFile(params.getUserId(), af, faceImg);
		//long testTime = System.currentTimeMillpis();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				.connectTimeout(1, TimeUnit.HOURS)
				.writeTimeout(1, TimeUnit.HOURS)
				.readTimeout(1, TimeUnit.HOURS).build();
		
		// JSON 데이터 생성
		JSONObject json = new JSONObject();
		json.put("userId", params.getUserId());

		// JSON 데이터를 문자열로 변환
		String jsonString = json.toString();

		LOGGER.info("face path: " + FACE_ROOT_DIR+File.separator+params.getUserId()+File.separator+afInfo.getSaveFileName());
		
        // 요청 파라미터 설정
        MediaType mediaType = MediaType.parse("multipart/form-data");
		
        // Create a form body with your parameters
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("request_id", params.getUserId())//의뢰번호
                .addFormDataPart("info_data_key ", "*")//등록 시 저장된 사용자 정보 데이터 중 특정 데이터 만을 요청 할 때 ex : name
                //이미지 데이터 (메타 템플릿
                .addFormDataPart("image", afInfo.getSaveFileName()+"."+afInfo.getFileExt(), RequestBody.create(mediaType, 
                		new File(FACE_ROOT_DIR+File.separator+params.getUserId()+File.separator+afInfo.getSaveFileName())))
                .build();
        

        //Regist (얼굴일치)
		String tgtUrl = url+"/1/match";
		
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
		LOGGER.info("=========matchFaceApi end=========");
		LOGGER.info("response: " + response);
		LOGGER.info("Received JSON data: " + jsonData);		
		LOGGER.info("=========matchFaceApi end=========");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode result = mapper.readTree(jsonData);
		return result;
	}  	
	
}
