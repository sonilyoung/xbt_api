package egovframework.com.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import egovframework.com.global.util.FileReader;

public class TestCode4Main {

	public static void main(String[] args) {

		//듀얼이미지
		//String filePath = "D:\\HAN_SEO_UNIVERSITY\\dual_images_complete\\";
		//String targetPath = "D:\\HAN_SEO_UNIVERSITY\\dual_images_complete\\";
		
		//싱글이미지
		String filePath = "D:\\HAN_SEO_UNIVERSITY\\test_images\\\\";
		File[] fileList = FileReader.ListFile( filePath );
		
		
        for( int i = 0; i < fileList.length; i++ ) {
        	try {
        		//System.out.println("path : " + fileList[i].toPath());
        		
        		
        		File[] fileDetailList = FileReader.ListFile(fileList[i].toPath().toString());
        		String newFileName = "";
                String fileTargetName = "";         		
        		for( int j = 0; j < fileDetailList.length; j++ ) {
        			//System.out.println("fileDetailList : " + fileDetailList[j].toPath());
        			
    				byte[] fileByte = Files.readAllBytes(fileDetailList[j].toPath());
            		Path file = Paths.get(fileDetailList[j].toPath().toString());        		        	

            		if (
            			fileDetailList[j].getName().contains("B-101") || 
            			fileDetailList[j].getName().contains("B-102") ||
            			fileDetailList[j].getName().contains("B-103") ||
            			fileDetailList[j].getName().contains("B-104") ||
            			fileDetailList[j].getName().contains("B-105") ||
            			fileDetailList[j].getName().contains("B-106") ||
            			fileDetailList[j].getName().contains("B-107") ||
            			fileDetailList[j].getName().contains("B-108") ||
            			fileDetailList[j].getName().contains("B-109") ||
            			fileDetailList[j].getName().contains("B-110") ||
            			fileDetailList[j].getName().contains("B-111") ||
            			fileDetailList[j].getName().contains("B-112") ||
            			fileDetailList[j].getName().contains("B-113") ||
            			fileDetailList[j].getName().contains("B-114") ||
            			fileDetailList[j].getName().contains("B-115") ||
            			fileDetailList[j].getName().contains("B-116") ||
            			fileDetailList[j].getName().contains("B-117") ||
            			fileDetailList[j].getName().contains("B-118") ||
            			fileDetailList[j].getName().contains("B-119") ||
            			fileDetailList[j].getName().contains("B-120")
            		) {
                		if(fileDetailList[j].getName().contains("B-101")) {//측면
                			fileTargetName = "201";
                		}else if(fileDetailList[j].getName().contains("B-102")) {
                			fileTargetName = "202";
                		}else if(fileDetailList[j].getName().contains("B-103")) {
                			fileTargetName = "203";
                		}else if(fileDetailList[j].getName().contains("B-104")) {
                			fileTargetName = "204";
                		}else if(fileDetailList[j].getName().contains("B-105")) {
                			fileTargetName = "205";
                		}else if(fileDetailList[j].getName().contains("B-106")) {
                			fileTargetName = "206";
                		}else if(fileDetailList[j].getName().contains("B-107")) {
                			fileTargetName = "207";
                		}else if(fileDetailList[j].getName().contains("B-108")) {
                			fileTargetName = "208";
                		}else if(fileDetailList[j].getName().contains("B-109")) {
                			fileTargetName = "209";
                		}else if(fileDetailList[j].getName().contains("B-110")) {
                			fileTargetName = "210";
                		}else if(fileDetailList[j].getName().contains("B-111")) {
                			fileTargetName = "211";
                		}else if(fileDetailList[j].getName().contains("B-112")) {
                			fileTargetName = "212";
                		}else if(fileDetailList[j].getName().contains("B-113")) {
                			fileTargetName = "213";
                		}else if(fileDetailList[j].getName().contains("B-114")) {
                			fileTargetName = "214";
                		}else if(fileDetailList[j].getName().contains("B-115")) {
                			fileTargetName = "215";
                		}else if(fileDetailList[j].getName().contains("B-116")) {
                			fileTargetName = "216";
                		}else if(fileDetailList[j].getName().contains("B-117")) {
                			fileTargetName = "217";
                		}else if(fileDetailList[j].getName().contains("B-118")) {
                			fileTargetName = "218";
                		}else if(fileDetailList[j].getName().contains("B-119")) {
                			fileTargetName = "219";
                		}else if(fileDetailList[j].getName().contains("B-120")) {
                			fileTargetName = "220";
                		}
                		
                        // 파일명 변경
                		String[] targetFileName = fileDetailList[j].getName().split("-");
                		
            			newFileName = targetFileName[0] + "-" + fileTargetName + ".jpg";
            			
            			
            			System.out.println(newFileName);
            			
                        Path renamedFile = file.resolveSibling(newFileName);
                        // 변경된 파일 저장
                        Files.write(renamedFile, fileByte);    
                        fileDetailList[j].delete();
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
