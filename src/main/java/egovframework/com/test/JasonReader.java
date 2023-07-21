package egovframework.com.test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

public class JasonReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String jsonFilePath = "D:/files/test.json"; // JSON 파일 경로
        // JSON 파일 읽기
        try {
			String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
	        if (isValidJSON(jsonContent)) {
	            System.out.println("Valid JSON");
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

}
