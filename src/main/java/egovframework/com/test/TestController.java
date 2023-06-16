
package egovframework.com.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.adm.contents.service.ContentsService;
import egovframework.com.adm.contents.vo.XbtSeq;
import egovframework.com.common.service.CommonService;
import egovframework.com.common.vo.Common;
import egovframework.com.common.vo.SeqGroupCode;
import egovframework.com.excel.ExcelRead;
import egovframework.com.excel.ExcelReadOption;
import egovframework.com.global.annotation.SkipAuth;
import egovframework.com.global.authorization.SkipAuthLevel;
import egovframework.com.global.common.GlobalsProperties;
import egovframework.com.global.http.BaseResponse;
import egovframework.com.global.http.BaseResponseCode;
import egovframework.com.global.util.FileReader;
import egovframework.com.test.service.TestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;

/**
 * 테스트
 */
@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
    /*파일업로드 저장경로*/
    public static final String FILE_UPLOAD_PATH = GlobalsProperties.getProperty("file.upload.path");
    
    public static final int XBT_TARGET_NAME = 5500;

    @Autowired
    private TestService testService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private ContentsService contentsService;
    
    public static final String targetUnit = "";
    
	@GetMapping("/index.do")
	@ApiOperation(value = "test", notes = "test")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> index(HttpServletRequest request, @RequestParam(required = false) String params) {

		return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
	}

	@RequestMapping(value = "/selectPdf.do", method = RequestMethod.GET)
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public byte[] selectPdf(
			HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		FileInputStream fis = null;
		BufferedOutputStream bos = null;

		 String pdfFileName = "D:/files/pdffile.pdf";
		 File pdfFile = new File(pdfFileName);
		 byte[] fileByte = Files.readAllBytes(pdfFile.toPath());
		 
		 //클라이언트 브라우져에서 바로 보는 방법(헤더 변경)
		 response.setContentType("application/pdf");

		 //★ 이 구문이 있으면 [다운로드], 이 구문이 없다면 바로 target 지정된 곳에 view 해줍니다.
		 response.addHeader("Content-Disposition", "attachment; filename="+pdfFile.getName()+".pdf");

		 return fileByte;
	}	
	
	
	@RequestMapping(value = "/selectFile.do", method = RequestMethod.GET)
    @SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public void selectFile(
			HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		FileInputStream fis = null;
		BufferedOutputStream bos = null;

		try {
		 String pdfFileName = "D:/files/pdffile.pdf";
		 File pdfFile = new File(pdfFileName);
		 
		 //클라이언트 브라우져에서 바로 보는 방법(헤더 변경)
		 response.setContentType("application/pdf");

		 //★ 이 구문이 있으면 [다운로드], 이 구문이 없다면 바로 target 지정된 곳에 view 해줍니다.
		 response.addHeader("Content-Disposition", "attachment; filename="+pdfFile.getName()+".pdf");

		 //파일 읽고 쓰는 건 일반적인 Write방식이랑 동일합니다. 다만 reponse 출력 스트림 객체에 write.
		 fis = new FileInputStream(pdfFile);
		 int size = fis.available(); //지정 파일에서 읽을 수 있는 바이트 수를 반환
		 byte[] buf = new byte[size]; //버퍼설정
		 int readCount = fis.read(buf);

		 response.flushBuffer();
		 bos = new BufferedOutputStream(response.getOutputStream());
		 bos.write(buf, 0, readCount);
		 bos.flush();
		} catch(Exception e) {
		 e.printStackTrace();
		} finally {
		 try {
		  if (fis != null) fis.close(); //close는 꼭! 반드시!
		  if (bos != null) bos.close();
		 } catch (IOException e) {
		  e.printStackTrace();
		 }
		}
	}

	@GetMapping("/fileList.do")
	@ApiOperation(value = "test", notes = "test")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> fileList(HttpServletRequest request, @RequestParam(required = false) String params)
			throws IOException {
		String xrayPath = GlobalsProperties.getProperty("xray.img.path");

		String scanId = "X00241";
		String strDirPath = xrayPath + scanId;
		File[] fileList = null;
		fileList = FileReader.ListFile(strDirPath);

		byte[] fileByte;/* 이미지 */

		// 결과유기물
		System.out.println("result count : " + fileList.length);

		// byte변환
		for (int i = 0; i < fileList.length; i++) {
			System.out.println("result : " + fileList[i]);
			fileByte = Files.readAllBytes(fileList[i].toPath());
			System.out.println("fileByte : " + fileByte);
		}

		return new BaseResponse<Integer>(BaseResponseCode.SUCCESS, BaseResponseCode.SUCCESS.getMessage());
	}

	@GetMapping("/arrange_level.do")
	@ApiOperation(value = "test api", notes = "test api.")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<TestResultApi> test2(HttpServletRequest request,
			@RequestParam(required = false) String params) {

		List<TestApi> unitInfoList = new ArrayList<TestApi>();
		TestApi unitInfo = new TestApi();
		unitInfo.setLangSet("kr");
		unitInfo.setUnitDes("총");
		unitInfoList.add(unitInfo);
		unitInfo.setLangSet("en");
		unitInfo.setUnitDes("건");
		unitInfoList.add(unitInfo);

		List<TestApi> cbtUnitImgList = new ArrayList<TestApi>();
		TestApi cbtUnitImg = new TestApi();
		cbtUnitImg.setImgType("COLOR");
		cbtUnitImg.setUnitImg("Base64");
		cbtUnitImgList.add(unitInfo);
		cbtUnitImg.setImgType("COLOR");
		cbtUnitImg.setUnitImg("Base64");
		cbtUnitImgList.add(unitInfo);

		TestResultApi result = new TestResultApi();
		result.setUnitGroupCd("0001");
		result.setStudyLvl("1");
		result.setDecipMachineCd("SCP100-1");
		result.setUnitInfo(unitInfoList);
		result.setCbtUnitImg(cbtUnitImgList);

		return new BaseResponse<TestResultApi>(result);
	}

	@GetMapping("/selectEmpUnitImage.do")
	@ApiOperation(value = "test api", notes = "test api.")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public JSONObject selectEmpUnitImage(HttpServletRequest request, @RequestParam(required = false) String params)
			throws ParseException {
		String response = "{\"unitImages\": [ {\"cbtUnitImg\": [ {\"imgType\": \"COLOR\", \"imgRotate\": \"90º\", \"unitImg\": \"string\"}],\"decipMachineCd\": \"0000\", \"studyLvl\": \"1\", \"unitGroupCd\": \"1001\", \"unitId\": \"101\", \"unitInfo\": [{\"langSet\": \"kr\", \"unitDes\": \"gun\"}]}]}";
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(response);
		return jsonObject;
	}
	
	
	//가방안에 담긴 데이터정렬 데이터추가
	@PostMapping(value="/selctBagInfoList")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public void selctBagInfoList(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= excelUpload ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
           
	        XSSFWorkbook wb = new XSSFWorkbook();
	        XSSFSheet sheet = wb.createSheet("첫번째 시트");        
	        
	        Row row = null;
	        Cell cell = null;
	        int rowNum = 0;	
	        
	        
			//스타일 설정
			CellStyle xssfWb = wb.createCellStyle();
			 
			//정렬
			xssfWb.setAlignment(CellStyle.ALIGN_CENTER);
			xssfWb.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			//테두리 라인
			xssfWb.setBorderRight(HSSFCellStyle.BORDER_THIN);
			xssfWb.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			xssfWb.setBorderTop(HSSFCellStyle.BORDER_THIN);
			xssfWb.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			
			//배경색
			//xssfWb.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
			xssfWb.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			xssfWb.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);   
			
			//폰트
			XSSFFont font = wb.createFont();
			font.setFontName("맑은고딕");
			font.setBold(true);
			xssfWb.setFont(font);
			
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
	        int i = 0;
	        String targetValue = "";
			for(LinkedHashMap<String, String> excelData: excelContent){    
	            row = sheet.createRow(rowNum++);
	            //row.setHeight((short)1200);
	            
	            cell = row.createCell(0);
	            cell.setCellValue(excelData.get("A")); //가방촬영id
	            cell = row.createCell(1);
	            
	            if(!excelData.get("A").equals(targetValue)) {
	            	i=0;
	            }
	            
	            cell.setCellValue(i+1);
	            cell = row.createCell(2);
	            cell.setCellValue(excelData.get("C"));//물품코드
	            cell = row.createCell(3);
	            cell.setCellValue(excelData.get("D"));//물푸몀ㅇ
	            cell = row.createCell(4);
	            cell.setCellValue(excelData.get("E"));//물품분류코드 그룹코드
	            cell = row.createCell(5);
	            cell.setCellValue(excelData.get("F"));//개봉여부 OPEN CLOSE
	            cell = row.createCell(6);
	            cell.setCellValue(excelData.get("G"));//제한여부 Restricted, PASS , Prohibited
	            cell = row.createCell(7);//actionDiv
	            
	            Common cp = new Common();
	            cp.setLanguageCode("kr");
	            cp.setGroupId("actionDiv");
	            cp.setMemo1(excelData.get("F")); //개봉여부
	            cp.setMemo2(excelData.get("G")); //제한여부
	            Common cr = commonService.selectCommonDetail(cp);
	            cell.setCellValue(cr.getCodeValue());//actionDiv
	            
	            i++;
	            targetValue = excelData.get("A");
			}
			
	        // 컨텐츠 타입과 파일명 지정
			//xls확장자로 다운로드   
			String tempName = "xray가방데이터";
			response.setContentType("ms-vnd/excel");
			//response.setContentType("application/x-msdownload;charset=utf-8");
			String fileName = URLEncoder.encode(tempName, ("UTF-8"));
			response.setHeader("Set-Cookie", "fileDownloadToken=Y; path=/");
			response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+".xlsx\"");         
	        // Excel File Output
	        wb.write(response.getOutputStream());
	        wb.close();
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	        destFile.delete(); // 업로드된 엑셀파일 삭제
	    }catch(Exception e) {
	    	e.printStackTrace();
	    } 
	    
	}
	
	
	@PostMapping(value="/insertXbtBagConstUnitTemp")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertXbtBagConstUnitTemp(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= excelUpload ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
           
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
	            //row.setHeight((short)1200);
	            params.put("bagScanId", excelData.get("A"));
	            params.put("seq", excelData.get("B"));
	            params.put("unitId", excelData.get("C")+targetUnit);
	            params.put("unitGroupCd", excelData.get("E"));
	            
	            testService.insertXbtBagConstUnitTemp(params);
			}
			
			int result = 1;
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}	
	
	@PostMapping(value="/insertXbtBagInfoTemp")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertXbtBagInfoTemp(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= excelUpload ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
			
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int result = 0;
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
	            //row.setHeight((short)1200);
				
				if("1".equals(excelData.get("B"))) {
		            params.put("bagScanId", excelData.get("A"));
		            params.put("unitId", excelData.get("C")+targetUnit);
		            params.put("unitGroupCd", excelData.get("E"));
		            params.put("openYn", excelData.get("F"));
		            params.put("closeYn", excelData.get("G"));
		            
		            Common cp = new Common();
		            cp.setLanguageCode("kr");
		            cp.setGroupId("actionDiv");
		            cp.setCodeValue(excelData.get("H"));
		            cp.setCommand("codeValue");
		            Common cr = commonService.selectCommon(cp);
		            params.put("actionDivName", cr.getCodeName());
		            params.put("actionDiv", excelData.get("H"));
		            
		            result = testService.insertXbtBagInfoTemp(params);					
				}

			}
			
			
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}	
	
	
	@PostMapping(value="/insertUnitTemp")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertUnitTemp(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= excelUpload ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
           
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int result = 0;
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
	            //row.setHeight((short)1200);
				params.put("unitScanId", "U"+excelData.get("C")+targetUnit);
	            params.put("unitId", excelData.get("C")+targetUnit);
	            params.put("unitGroupCd", excelData.get("E"));
	            params.put("unitName", excelData.get("D"));
	            params.put("unitDesc", excelData.get("I"));
	            
	            result = testService.insertUnitTemp(params);
			}
			
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}	
	

	
	
	//가방안에 담긴 데이터정렬 데이터추가
	@PostMapping(value="/selctBagInfoRenameList")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public void selctBagInfoRenameList(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= excelUpload ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
           
	        XSSFWorkbook wb = new XSSFWorkbook();
	        XSSFSheet sheet = wb.createSheet("첫번째 시트");        
	        
	        Row row = null;
	        Cell cell = null;
	        int rowNum = 0;	
	        
	        
			//스타일 설정
			CellStyle xssfWb = wb.createCellStyle();
			 
			//정렬
			xssfWb.setAlignment(CellStyle.ALIGN_CENTER);
			xssfWb.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			//테두리 라인
			xssfWb.setBorderRight(HSSFCellStyle.BORDER_THIN);
			xssfWb.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			xssfWb.setBorderTop(HSSFCellStyle.BORDER_THIN);
			xssfWb.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			
			//배경색
			//xssfWb.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
			xssfWb.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			xssfWb.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);   
			
			//폰트
			XSSFFont font = wb.createFont();
			font.setFontName("맑은고딕");
			font.setBold(true);
			xssfWb.setFont(font);
			
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
	        int s = 0;
	        int i = 0;
	        String targetValue = "";
	        String newName = "";
	        String seqId = "";
			for(LinkedHashMap<String, String> excelData: excelContent){    
	            row = sheet.createRow(rowNum++);
	            //row.setHeight((short)1200);
	            cell = row.createCell(0);
	            if(!excelData.get("A").equals(targetValue)) {
					seqId = String.format("X%05d", (XBT_TARGET_NAME+i));
					LOGGER.info("name:"+ excelData.get("A") +"   rename:" + seqId);
					cell.setCellValue(seqId);		
					i++;	            	
					s=0;
	            }else {
	            	cell.setCellValue(newName);
	            }
	            
	            cell = row.createCell(1);
	            cell.setCellValue(s+1);
	            cell = row.createCell(2);
	            cell.setCellValue(excelData.get("C"));
	            cell = row.createCell(3);
	            cell.setCellValue(excelData.get("D"));
	            cell = row.createCell(4);
	            cell.setCellValue(excelData.get("E"));
	            cell = row.createCell(5);
	            cell.setCellValue(excelData.get("F"));
	            cell = row.createCell(6);
	            cell.setCellValue(excelData.get("G"));
	            cell = row.createCell(7);//actionDiv
	            
	            Common cp = new Common();
	            cp.setLanguageCode("kr");
	            cp.setGroupId("actionDiv");
	            cp.setMemo1(excelData.get("F")); //개봉여부
	            cp.setMemo2(excelData.get("G")); //제한여부
	            Common cr = commonService.selectCommonDetail(cp);
	            cell.setCellValue(cr.getCodeValue());//actionDiv
	            
	            targetValue = excelData.get("A");
	            newName = seqId;
	            s++;
			}
			
	        // 컨텐츠 타입과 파일명 지정
			//xls확장자로 다운로드   
			String tempName = "xray가방데이터";
			response.setContentType("ms-vnd/excel");
			//response.setContentType("application/x-msdownload;charset=utf-8");
			String fileName = URLEncoder.encode(tempName, ("UTF-8"));
			response.setHeader("Set-Cookie", "fileDownloadToken=Y; path=/");
			response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+".xlsx\"");         
	        // Excel File Output
	        wb.write(response.getOutputStream());
	        wb.close();
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	        destFile.delete(); // 업로드된 엑셀파일 삭제
	    }catch(Exception e) {
	    	e.printStackTrace();
	    } 
	    
	}	
	
	
	
	@PostMapping(value="/insertUnitRename")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertUnitRename(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= excelUpload ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
           
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int result = 0;
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
	            //row.setHeight((short)1200);
				params.put("unitScanId", "U"+excelData.get("C"));
	            params.put("unitId", excelData.get("C"));
	            params.put("unitGroupCd", excelData.get("E"));
	            params.put("unitName", excelData.get("D"));
	            params.put("unitDesc", excelData.get("D"));
	            
	            result = testService.insertUnitRename(params);
			}
			
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}		
	
	
	@PostMapping(value="/insertXbtBagConstUnitRename")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertXbtBagConstUnitRename(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= excelUpload ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
           
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int i = 0;
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
	            //row.setHeight((short)1200);
	            params.put("bagScanId", excelData.get("A"));
	            params.put("seq", excelData.get("B"));
	            params.put("unitGroupCd", excelData.get("C"));
	            params.put("unitId", excelData.get("D"));
	            
	            testService.insertXbtBagConstUnitRename(params);
	            i++;
			}
			
			int result = 1;
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}		
	
	@PostMapping(value="/insertXbtBagInfoRename")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertXbtBagInfoRename(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= insertXbtBagInfoRenameTemp ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
			
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int result = 0;
			
			int i = 0;
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
	            //row.setHeight((short)1200);
				
				if("1".equals(excelData.get("B"))) {
		            params.put("bagScanId", excelData.get("A"));
		            params.put("unitId", excelData.get("C"));
		            params.put("unitGroupCd", excelData.get("E"));
		            params.put("openYn", excelData.get("F"));
		            params.put("closeYn", excelData.get("G"));
		            
		            Common cp = new Common();
		            cp.setLanguageCode("kr");
		            cp.setGroupId("actionDiv");
		            cp.setCodeValue(excelData.get("H"));
		            cp.setCommand("codeValue");
		            Common cr = commonService.selectCommon(cp);
		            params.put("actionDivName", cr.getCodeName());
		            params.put("actionDiv", excelData.get("H"));
		            
		            result = testService.insertXbtBagInfoRename(params);
		            i++;
				}

			}
			
			
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}			
	
	
	
	//이론문제 등록
	@PostMapping(value="/insertTheoryExcel")
	@SkipAuth(skipAuthLevel = SkipAuthLevel.SKIP_ALL)
	public BaseResponse<Integer> insertTheoryExcel(
			HttpServletRequest request
			,HttpServletResponse response
			,@RequestPart(value = "excelFile", required = true) MultipartFile excelFile
	) throws Exception{
		LOGGER.debug("========= insertTheoryExcel 이론문제등록 ========="+ excelFile);

	    try {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmsss"); 
			Date time = new Date(); 
			String fmtDate=format.format(time);

			//String stordFilePath = GlobalsProperties.getProperty("Globals.fileStorePath");
			File fileDir = new File(FILE_UPLOAD_PATH);
			// root directory 없으면 생성
			if (!fileDir.exists()) {
				fileDir.mkdirs(); //폴더 생성합니다.
			}             
			File destFile = new File(FILE_UPLOAD_PATH + File.separator + fmtDate+"_"+excelFile.getOriginalFilename()); // 파일위치 지정
			
			excelFile.transferTo(destFile); // 엑셀파일 생성
			String[] coloumNm = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
			    
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(destFile.getAbsolutePath()); //파일경로 추가
			excelReadOption.setOutputColumns(coloumNm); //추출할 컬럼명 추가
			excelReadOption.setStartRow(2); //시작행(헤더부분 제외)
			List<LinkedHashMap<String, String>>excelContent  = ExcelRead.read(excelReadOption);
			
	        //String[] coloumNm = {"A", "C", "D", "E", "F", "H"};
			
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int result = 0;
			
			for(LinkedHashMap<String, String> excelData: excelContent){
				params = new LinkedHashMap<String, Object>();
				
				XbtSeq seq = new XbtSeq();
				seq.setSeqInfo(SeqGroupCode.XBT_THEORY_ID.getCode());
				XbtSeq unitId = contentsService.selectXbtSeq(seq);
				
	            params.put("questionId", unitId.getSeqId());//문제아이디
	            params.put("questionType", excelData.get("B"));//B문제타입
	            params.put("studyLvl", excelData.get("C"));//C학습레벨
	            params.put("useYn", excelData.get("D"));//D사용여부
	            
	            Common cp = new Common();
	            cp.setLanguageCode("kr");
	            cp.setGroupId("eduName");
	            cp.setCodeName(excelData.get("E"));
	            cp.setCommand("codeName");
	            Common cr = commonService.selectCommon(cp);
	            params.put("lageGroupCd", cr.getCodeValue());//대그룹
	            
	            params.put("middleGroupCd", excelData.get("F"));//F중그룹
	            params.put("smallGroupCd", excelData.get("G"));//G소그룹
	            params.put("question", excelData.get("H"));//H문제
	            params.put("actionDiv", excelData.get("I"));//I정답
	            params.put("choice1", excelData.get("J"));//J지문1
	            params.put("choice2", excelData.get("K"));//K지문2
	            params.put("choice3", excelData.get("L"));//L지문3
	            params.put("choice4", excelData.get("M"));//M지문4
	            
	            result = testService.insertTheoryExcel(params);

			}
			
			
            if(result>0) {
	            return new BaseResponse<Integer>(BaseResponseCode.SAVE_SUCCESS, BaseResponseCode.SAVE_SUCCESS.getMessage());
            }else {
            	return new BaseResponse<Integer>(BaseResponseCode.DATA_IS_NULL);
            }
	    }catch(Exception e) {
	    	return new BaseResponse<Integer>(BaseResponseCode.SAVE_ERROR);
	    } 
	    
	}			
}
