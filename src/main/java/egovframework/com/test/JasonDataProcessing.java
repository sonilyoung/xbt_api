package egovframework.com.test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import egovframework.com.api.edc.vo.AiForceLearning;
import egovframework.com.api.edc.vo.AiForceLearningResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JasonDataProcessing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String jsonFilePath = "D:/files/test.json"; // JSON 파일 경로
        // JSON 파일 읽기
        try {
			String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
	        if (isValidJSON(jsonContent)) {
	            System.out.println("Valid JSON");
	            
	            JSONObject jsonObject = new JSONObject(jsonContent);
	            ArrayList<AiForceLearningResult> result = getAiForceResult(jsonObject);
	            log.debug("json result:", result);
	            
	        } else {
	            System.out.println("Invalid JSON");
	        }			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static boolean isValidJSON(String jsonString) {
        try {
            new JSONObject(jsonString);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
    
    public static ArrayList<AiForceLearningResult> getAiForceResult(JSONObject responseJson) {
        ArrayList<AiForceLearningResult> locationList = new ArrayList<>();
     
        try {
            // locations 배열이 가지고 있는 value 값을 가져와보자.
            JSONArray locations = responseJson.getJSONArray("RET_DATA");
     
            for (int i = 0; i < locations.length(); i++) {
                JSONObject data = locations.getJSONObject(i);
     
                AiForceLearningResult al = new AiForceLearningResult();
     
                al.setProgressNo(data.getLong("progressNo"));
                al.setBagScanId(data.getString("bagScanId"));
                locationList.add(al);
            }
     
        } catch (JSONException e) {
            e.printStackTrace();
        }
     
        return locationList;
    }
    

}
