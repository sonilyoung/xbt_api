package egovframework.com.api.edc.service;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import egovframework.com.api.edc.vo.FaceVO;

public interface XbtFaceApiService {
	
	public JsonNode insertFaceApi(MultipartFile faceImg, FaceVO params) throws Exception;

	public JsonNode livenessFaceApi(MultipartFile faceImg, FaceVO params) throws Exception;
	
	public JsonNode matchFaceApi(MultipartFile faceImg, FaceVO params) throws Exception;

	
}
