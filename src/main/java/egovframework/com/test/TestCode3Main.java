package egovframework.com.test;

import java.io.File;

import egovframework.com.global.util.FileReader;

//파일확인
public class TestCode3Main {

	public static void main(String[] args) {

		//듀얼이미지
		String filePath = "D:/HAN_SEO_UNIVERSITY/original_dual_images/";
		
		//싱글이미지
		//String filePath = "D:/HAN_SEO_UNIVERSITY/original_single_images/";
		File[] fileList = FileReader.ListFile( filePath );
		
		
		
        for( int i = 0; i < fileList.length; i++ ) {
        	try {
        		//System.out.println("path : " + fileList[i].toPath());
        		
        		String lastFolderName = fileList[i].toPath().toString().substring(fileList[i].toPath().toString().length()-8, fileList[i].toPath().toString().length());
        		System.out.println(lastFolderName);
        		
      			//System.out.println("realImages : " + fileDetailList[j].getName());
        		//byte[] fileByte = Files.readAllBytes(fileList[i].toPath());
        		//System.out.println("fileName : " + fileList[i].getName());
        		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
        }
	
	}

}
