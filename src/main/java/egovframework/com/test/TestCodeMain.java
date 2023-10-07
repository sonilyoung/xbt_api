package egovframework.com.test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class TestCodeMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        JSONObject json = new JSONObject();
        json.put("bagScanId", 1);
        json.put("imgFront", 2);
        json.put("imgFrontName", "234fsodjpsjfdijgspfdjgijspfdjgpiosjfdg");

        try (FileWriter file = new FileWriter("D:/files/output.json")) {
            file.write(json.toString());
            System.out.println("JSON 객체가 파일에 성공적으로 저장되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
	
		
	    try {
	        // Read the JSON file as a string
	        String jsonString = new String(Files.readAllBytes(Paths.get("D:/files/output.json")));
	
	        // Convert the string to a JSON object
	        JSONObject jsonObject = new JSONObject(jsonString);
	
	        // Use the JSON object as needed
	        System.out.println("jsonObject:" + jsonObject);
	
	        System.out.println("JSON 파일을 성공적으로 읽고 JSON 객체로 변환했습니다.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	}

}
