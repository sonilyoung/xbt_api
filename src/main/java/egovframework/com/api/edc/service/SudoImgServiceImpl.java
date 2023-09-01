package egovframework.com.api.edc.service;

import java.io.File;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.adm.contents.vo.XrayImgContents;
import egovframework.com.api.edc.dao.EgovXtsEdcApiDAO;
import egovframework.com.api.edc.vo.ApiLog;
import egovframework.com.api.login.service.ApiLoginService;
import egovframework.com.api.login.vo.ApiLogin;
import egovframework.com.file.service.FileStorageService;
import egovframework.com.file.vo.AttachFile;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Service("sudoImgServiceImpl")
public class SudoImgServiceImpl implements SudoImgService {
	
    /*카이스트api*/
    public static final String url = GlobalsProperties.getProperty("kist.target.api");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SudoImgServiceImpl.class);

    @Resource(name = "EgovXtsEdcApiDAO")
	private EgovXtsEdcApiDAO egovXtsEdcApiDAO;
	
	@Autowired
	private FileStorageService fileStorageService;    
	
    /*kist xray 저장경로*/
    public static final String KIST_XRAY_ROOT_DIR = GlobalsProperties.getProperty("kist.xray.img.path");	
	
	@Override
	public BaseResponse<Integer> sudoImgExcute(XrayImgContents oj, ApiLogin al, MultipartFile frontImg, MultipartFile sideImg) throws Exception {
		
		AttachFile af = fileStorageService.createKistXrayImageFiles(oj.getBagScanId(), "101", oj, frontImg);
		
		//1.정면이미지전송
		InetAddress inetAddress = InetAddress.getLocalHost();
		ApiLog apiLog = new ApiLog();
		apiLog.setSeqId(oj.getBagScanId());
		apiLog.setInsertId(al.getLoginId());		
		apiLog.setApiUrl(inetAddress.toString());
		apiLog.setApiCommand("transImages");
		apiLog.setRequestContents("슈도컬러 정면 이미지업로드 시작");
		apiLog.setProgressPer(5);
		insertApiLog(apiLog);

		ApiLog result1 = transImages(oj, al, af);
		
		result1.setSeqId(oj.getBagScanId());
		result1.setInsertId(al.getLoginId());		
		result1.setApiUrl(inetAddress.toString());
		result1.setApiCommand("transImages");
		result1.setRequestContents("슈도컬러 정면 이미지업로드 종료");
		result1.setProgressPer(5);
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
		
		return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
	}

	@Override
	public ApiLog transImages(XrayImgContents oj, ApiLogin al, AttachFile af) throws Exception {
		// TODO Auto-generated method stub
		ApiLog aipLog = new ApiLog();
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		
		LOGGER.info("=========sudoImg start=========");
		
		//long testTime = System.currentTimeMillis();
		
		OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(1, TimeUnit.HOURS).build();
		
		// 이미지 파일을 읽고, Base64로 인코딩하여 JSON 데이터에 포함시킴
		File imageFile = new File(KIST_XRAY_ROOT_DIR+File.separator+oj.getBagScanId()+File.separator+af.getSaveFileName());
		byte[] imageData = Files.readAllBytes(imageFile.toPath());
		//String encodedImageData = Base64.getEncoder().encodeToString(imageData);

		// JSON 데이터 생성
		JSONObject json = new JSONObject();
		XrayImgContents params = new XrayImgContents();
		json.put("imgFront", imageData);
		//json.put("imageData", encodedImageData);

		// JSON 데이터를 문자열로 변환
		String jsonString = json.toString();

		String tgtUrl = url+File.separator+"api"+File.separator+"transImages.do";
		
		// 요청 생성
		Request request = new Request.Builder()
				.header("Authorization", "Bearer " + al.getAccessToken()) // 토큰을 헤더에 추가
		        .url(tgtUrl)
		        .post(RequestBody.create(jsonString, MediaType.parse("application/json")))
		        .build();

		// 요청 실행
		Response response = client.newCall(request).execute();
		LOGGER.info("=========sudoImg end=========");
		LOGGER.info("response:" + response);
		
		return aipLog;
	}



	@Override
	public ApiLog sudoImgCmd(XrayImgContents oj, ApiLogin al) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiLog getImages(XrayImgContents oj, ApiLogin al) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertApiLog(ApiLog oj) throws Exception {
		// TODO Auto-generated method stub
		return egovXtsEdcApiDAO.insertApiLog(oj);
	}
}
