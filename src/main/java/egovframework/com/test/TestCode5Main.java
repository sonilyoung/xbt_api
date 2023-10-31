package egovframework.com.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import egovframework.com.global.util.FileReader;

public class TestCode5Main {

	public static void main(String[] args) {

		//듀얼이미지
		//String filePath = "D:/HAN_SEO_UNIVERSITY/original_dual_images/";
		//String targetPath = "D:/HAN_SEO_UNIVERSITY/REAL_IMAGES/dual_images/";
		
		//싱글이미지
		String filePath = "D:/HAN_SEO_UNIVERSITY/original_single_images/";
		String targetPath = "D:/HAN_SEO_UNIVERSITY/REAL_IMAGES/single_images/";		
		File[] fileList = FileReader.ListFile( filePath );
		
		
		
        for( int i = 0; i < fileList.length; i++ ) {
        	try {
        		//System.out.println("path : " + fileList[i].toPath());
        		
        		String lastFolderName = fileList[i].toPath().toString().substring(fileList[i].toPath().toString().length()-8, fileList[i].toPath().toString().length());
        		System.out.println("===== lastFolderName ===== " + lastFolderName);
        		
        		File[] fileDetailList = FileReader.ListFile(fileList[i].toPath().toString());
        		for( int j = 0; j < fileDetailList.length; j++ ) {
        			//System.out.println("fileDetailList : " + fileDetailList[j].toPath());
        			
        			if(fileDetailList[j].getName().contains(".jpg") || fileDetailList[j].getName().contains(".JPG")) {
        				System.out.println("realImages : " + fileDetailList[j].getName());
        				byte[] fileByte = Files.readAllBytes(fileDetailList[j].toPath());
        				
        		        File fileDir = new File(targetPath + lastFolderName);
        		        // root directory 없으면 생성
        		    	if (!fileDir.exists()) {
        		    		
        		    		
        		    		fileDir.mkdirs(); //폴더 생성합니다.
        		    	}        		
        		    	
        		    	FileOutputStream fos = new FileOutputStream(targetPath + lastFolderName + "/" + lastFolderName + "-401" + ".jpg");
        	            fos.write(fileByte);        	
        	            fos.close();          		    	
        			}
        			
        		}
        		//byte[] fileByte = Files.readAllBytes(fileList[i].toPath());
        		//System.out.println("fileName : " + fileList[i].getName());
        		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
        }
	
	}

}
