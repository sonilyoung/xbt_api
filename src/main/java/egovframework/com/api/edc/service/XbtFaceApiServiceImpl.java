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
import egovframework.com.api.edc.vo.ThreedGeneration;
import egovframework.com.api.edc.vo.TowdGeneration;
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


@Service("faceServiceImpl")
public class XbtFaceApiServiceImpl implements XbtFaceApiService {}
