package egovframework.com.api.edc.service;

import java.io.File;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.HashMap;
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

import egovframework.com.api.edc.dao.EgovXtsEdcApiDAO;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.edc.vo.CommandExcute;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.common.vo.LearningImg;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponseCode;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Service("sudoImgServiceImpl")
public class SudoImgServiceImpl implements SudoImgService {
	
    /*카이스트api*/
    public static final String url = GlobalsProperties.getProperty("kaist.target.api");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SudoImgServiceImpl.class);

    @Resource(name = "EgovXtsEdcApiDAO")
	private EgovXtsEdcApiDAO egovXtsEdcApiDAO;
	
	@Autowired
	private FileStorageService fileStorageService;    
	
    /*kaist xray 저장경로*/
    public static final String KAIST_XRAY_ROOT_DIR = GlobalsProperties.getProperty("kaist.sudo.img.request.path");	
	
	@Override
	public JsonNode sudoImgExcute(LearningImg oj, ApiLogin al, MultipartFile frontImg, MultipartFile sideImg) throws Exception {
		
		AttachFile af1 = fileStorageService.createKaistXrayImageFiles(oj.getBagScanId(), "F", oj, frontImg);
		AttachFile af2 = fileStorageService.createKaistXrayImageFiles(oj.getBagScanId(), "S", oj, sideImg);
		
		//1.정면이미지전송
		InetAddress inetAddress = InetAddress.getLocalHost();
		ApiLog apiLog = new ApiLog();
		apiLog.setSeqId(oj.getBagScanId());
		apiLog.setInsertId(al.getLoginId());		
		apiLog.setApiUrl(inetAddress.toString());
		apiLog.setApiCommand("sudoImgExcute");
		apiLog.setRequestCode("sudoImgExcute1");
		apiLog.setRequestContents("슈도컬러 정면 측면 이미지업로드 시작");
		apiLog.setProgressPer(50);
		insertApiLog(apiLog);

		String resultData = transImages(oj, al, af1, af2);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		JsonNode json = mapper.readTree(resultData);
		result.put("result", json);
		
		if("UPLOAD_SUCCESS".equals(json.get("RET_CODE").asText())) {
			apiLog.setRequestCode("sudoImgExcute2");
			apiLog.setRequestContents("슈도컬러 정면 측면 이미지업로드 완료");
			apiLog.setApiCommand("sudoImgExcute");
			apiLog.setResponseCode(json.get("RET_CODE").asText());
			apiLog.setResponseContents(json.get("RET_DESC").asText());
			apiLog.setProgressPer(100);			
		}else {
			apiLog.setRequestCode("sudoImgExcute2");
			apiLog.setRequestContents("슈도컬러 정면 측면 이미지업로드 실패");
			apiLog.setApiCommand("selectSudoImg");
			apiLog.setResponseCode(json.get("RET_CODE").asText());
			apiLog.setResponseContents(json.get("RET_DESC").asText());	
			apiLog.setProgressPer(50);	
		}
				
		
		insertApiLog(apiLog);		
		
		
		//fileStorageService.createXrayImageFiles(oj.getBagScanId(), "101", oj, frontImg);
		/*
		//1.측면이미지전송
		apiLog.setInsertId(al.getLoginId());		
		apiLog.setApiUrl(url);
		apiLog.setApiCommand("transImages");
		apiLog.setRequestContents("슈도컬러 측면 이미지업로드 시작");
		apiLog.setProgressPer(5);
		insertApiLog(apiLog);

		ApiLog result2 = transImages(oj, al, sideImg);
		
		result2.setApiUrl(url);
		result2.setApiCommand("transImages");
		result2.setRequestContents("슈도컬러 측면 이미지업로드 종료");
		result2.setProgressPer(5);
		insertApiLog(result2);		
		*/	
		
		/*
		//2.카이스트 인공지능 cmd 수행
		insertApiLog(apiLog);
		
		//3.이미지다운로드
		insertApiLog(apiLog);
		*/
		
		return json;
	}

	@Override
	public String transImages(LearningImg oj, ApiLogin al, AttachFile af1, AttachFile af2) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.info("=========sudoImg start=========");
		
		//long testTime = System.currentTimeMillis();
		
		OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(1, TimeUnit.HOURS).build();
		
		// 이미지 파일을 읽고, Base64로 인코딩하여 JSON 데이터에 포함시킴
		File imageFile1 = new File(KAIST_XRAY_ROOT_DIR+File.separator+oj.getBagScanId()+File.separator+af1.getSaveFileName());
		byte[] imageData1 = Files.readAllBytes(imageFile1.toPath());
		
		File imageFile2 = new File(KAIST_XRAY_ROOT_DIR+File.separator+oj.getBagScanId()+File.separator+af2.getSaveFileName());
		byte[] imageData2 = Files.readAllBytes(imageFile2.toPath());		
		//String encodedImageData = Base64.getEncoder().encodeToString(imageData);

		// JSON 데이터 생성
		JSONObject json = new JSONObject();
		json.put("bagScanId", oj.getBagScanId());
		json.put("imgFront", imageData1);
		json.put("imgFrontName", af1.getOriginalFileName());
		
		json.put("imgSide", imageData2);
		
		json.put("imgSideName", af2.getOriginalFileName());		
		//json.put("imageData", encodedImageData);

		// JSON 데이터를 문자열로 변환
		String jsonString = json.toString();

		String tgtUrl = url+File.separator+"api"+File.separator+"transKaistImages.do";
		
		// 요청 생성
		Request request = new Request.Builder()
				.header("Authorization", "Bearer " + al.getAccessToken()) // 토큰을 헤더에 추가
		        .url(tgtUrl)
		        .post(RequestBody.create(jsonString, MediaType.parse("application/json")))
		        .build();

		// 요청 실행
		Response response = client.newCall(request).execute();
		
        // 수신한 JSON 데이터 읽기
        String jsonData = response.body().string();
		LOGGER.info("=========sudoImg end=========");
		System.out.println("response: " + response);
		System.out.println("Received JSON data: " + jsonData);		
		LOGGER.info("=========sudoImg end=========");
		
		return jsonData;
	}


	@Override
	public JsonNode commandExcute(CommandExcute oj, ApiLogin al) throws Exception {
		// TODO Auto-generated method stub
		
		String logStr = "";
		for(String c : oj.getCommand()) {
			logStr += logStr + " " + c; 
		}
		
		
		//1.정면이미지전송
		InetAddress inetAddress = InetAddress.getLocalHost();
		ApiLog apiLog = new ApiLog();
		apiLog.setSeqId("commandExcute");
		apiLog.setInsertId(al.getLoginId());		
		apiLog.setApiUrl(inetAddress.toString());
		apiLog.setRequestCode("commandExcute1");
		apiLog.setApiCommand(logStr);
		apiLog.setRequestContents("KAIST API 커맨드실행");
		apiLog.setProgressPer(30);
		insertApiLog(apiLog);

		LOGGER.info("=========commandExcute start=========");
		
		//long testTime = System.currentTimeMillis();
		
		OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(1, TimeUnit.HOURS).build();
		
		// JSON 데이터 생성
		JSONObject jsonData = new JSONObject();
		jsonData.put("command", oj.getCommand());

		// JSON 데이터를 문자열로 변환
		String jsonString = jsonData.toString();

		String tgtUrl = url+File.separator+"api"+File.separator+"commandKaistExcute.do";
		
		// 요청 생성
		Request request = new Request.Builder()
				.header("Authorization", "Bearer " + al.getAccessToken()) // 토큰을 헤더에 추가
		        .url(tgtUrl)
		        .post(RequestBody.create(jsonString, MediaType.parse("application/json")))
		        .build();

		// 요청 실행
		Response response = client.newCall(request).execute();
		
        // 수신한 JSON 데이터 읽기
        String resultData = response.body().string();
		LOGGER.info("=========commandExcute end=========");
		System.out.println("response: " + response);
		System.out.println("Received JSON data: " + resultData);		
		LOGGER.info("=========commandExcute end=========");
		
		if (response.code()==200) {
			ObjectMapper mapper = new ObjectMapper();
			//Map<String, Object> result = new HashMap<String, Object>();
			JsonNode json = mapper.readTree(resultData);
			//result.put("result", json);
			
			if("0000".equals(json.get("RET_CODE").asText())) {
				//이미지데이터생성
				apiLog.setSeqId("commandExcute");	
				apiLog.setRequestCode("commandExcute2");
				apiLog.setRequestContents("KAIST API 커맨드실행 완료");
				apiLog.setApiCommand(logStr);
				apiLog.setResponseCode(json.get("RET_CODE").asText());
				apiLog.setResponseContents(json.get("RET_DESC").asText());
				apiLog.setProgressPer(60);
			}else {
				apiLog.setSeqId("commandExcute");
				apiLog.setRequestCode("commandExcute2");
				apiLog.setRequestContents("KAIST API 커맨드실행 실패");
				apiLog.setApiCommand(logStr);
				apiLog.setResponseCode(json.get("RET_CODE").asText());
				apiLog.setResponseContents(json.get("RET_DESC").asText());
				apiLog.setProgressPer(60);
			}
			
			this.insertApiLog(apiLog);
			
			//kaist 이미지 데이터생성
			if("0000".equals(json.get("RET_CODE").asText()) && json.get("RET_DATA")!=null) {
				LOGGER.info("commandExcute ========> ", json.get("RET_DATA"));
				apiLog.setRequestCode("selectSudoImg3");
				apiLog.setRequestContents("슈도컬러 이미지 가져와서 생성완료");
				apiLog.setApiCommand(logStr);
				apiLog.setResponseCode(json.get("RET_CODE").asText());
				apiLog.setResponseContents(json.get("RET_DESC").asText());
				apiLog.setProgressPer(100);	
				
				this.insertApiLog(apiLog);			
			}
			return json;
		}else {
			apiLog.setSeqId("commandExcute");
			apiLog.setRequestCode("commandExcute2");
			apiLog.setRequestContents("KAIST API 커맨드실행 실패");
			apiLog.setApiCommand(logStr);
			apiLog.setResponseCode(BaseResponseCode.KAIST_EXEPTION.getCode());
			apiLog.setResponseContents(BaseResponseCode.KAIST_EXEPTION.getMessage());
			apiLog.setProgressPer(60);	
			this.insertApiLog(apiLog);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();

			// 빈 JsonNode 객체 생성
			ObjectNode rootNode = nodeFactory.objectNode();
			
			// 속성과 값 추가
			rootNode.put("RET_CODE", BaseResponseCode.KAIST_EXEPTION.getCode());
			rootNode.put("RET_DESC", BaseResponseCode.KAIST_EXEPTION.getMessage());
			
			return rootNode;
		}
		

		
	}

	@Override
	public JsonNode selectSudoImg(LearningImg oj, ApiLogin al) throws Exception {	
		// TODO Auto-generated method stub
		
		//1.정면이미지전송
		InetAddress inetAddress = InetAddress.getLocalHost();
		ApiLog apiLog = new ApiLog();
		apiLog.setSeqId(oj.getBagScanId());
		apiLog.setInsertId(al.getLoginId());		
		apiLog.setApiUrl(inetAddress.toString());
		apiLog.setRequestCode("selectSudoImg1");
		apiLog.setApiCommand("selectSudoImg");
		apiLog.setRequestContents("슈도컬러 이미지 가져오기 시작");
		apiLog.setProgressPer(30);
		insertApiLog(apiLog);

		LOGGER.info("=========selectSudoImages start=========");
		
		//long testTime = System.currentTimeMillis();
		
		OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(1, TimeUnit.HOURS).build();
		
		// JSON 데이터 생성
		JSONObject jsonData = new JSONObject();
		jsonData.put("bagScanId", oj.getBagScanId());
		//json.put("imageData", encodedImageData);

		// JSON 데이터를 문자열로 변환
		String jsonString = jsonData.toString();

		String tgtUrl = url+File.separator+"api"+File.separator+"selectKaistSudoImg.do";
		
		// 요청 생성
		Request request = new Request.Builder()
				.header("Authorization", "Bearer " + al.getAccessToken()) // 토큰을 헤더에 추가
		        .url(tgtUrl)
		        .post(RequestBody.create(jsonString, MediaType.parse("application/json")))
		        .build();

		// 요청 실행
		Response response = client.newCall(request).execute();
		
        // 수신한 JSON 데이터 읽기
        String resultData = response.body().string();
		LOGGER.info("=========selectSudoImages end=========");
		System.out.println("response: " + response);
		System.out.println("Received JSON data: " + jsonData);		
		LOGGER.info("=========selectSudoImages end=========");
		
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		JsonNode json = mapper.readTree(resultData);
		result.put("result", json);
		
		if("0000".equals(json.get("RET_CODE").asText())) {
			//이미지데이터생성
			//this.makeKaistSudoImages(json.get("RET_DATA"));	
			apiLog.setRequestCode("selectSudoImg2");
			apiLog.setRequestContents("슈도컬러 이미지 가져오기 완료");
			apiLog.setApiCommand("selectSudoImg");
			apiLog.setResponseCode(json.get("RET_CODE").asText());
			apiLog.setResponseContents(json.get("RET_DESC").asText());
			apiLog.setProgressPer(60);
		}else {
			apiLog.setRequestCode("selectSudoImg2");
			apiLog.setRequestContents("슈도컬러 이미지 가져오기 실패");
			apiLog.setApiCommand("selectSudoImg");
			apiLog.setResponseCode(json.get("RET_CODE").asText());
			apiLog.setResponseContents(json.get("RET_DESC").asText());
			apiLog.setProgressPer(60);
		}
		
		this.insertApiLog(apiLog);
		
		
		
		//kaist 이미지 데이터생성
		LOGGER.info("selectSudoImg3 ========> ", json.get("RET_DATA"));
		if("0000".equals(json.get("RET_CODE").asText()) && json.get("RET_DATA")!=null) {
			fileStorageService.makeKaistSudoImages(json.get("RET_DATA"));	
			
			apiLog.setRequestCode("selectSudoImg3");
			apiLog.setRequestContents("슈도컬러 이미지 가져와서 생성완료");
			apiLog.setApiCommand("selectSudoImg");
			apiLog.setResponseCode(json.get("RET_CODE").asText());
			apiLog.setResponseContents(json.get("RET_DESC").asText());
			apiLog.setProgressPer(100);	
			
			this.insertApiLog(apiLog);			
		}
		
		return json;
	}


	
	@Override
	public int insertApiLog(ApiLog oj) throws Exception {
		// TODO Auto-generated method stub
		return egovXtsEdcApiDAO.insertApiLog(oj);
	}

	@Override
	public ApiLog selectProgressPer(ApiLog params) throws Exception {
		// TODO Auto-generated method stub
		return egovXtsEdcApiDAO.selectProgressPer(params);
	}

}
