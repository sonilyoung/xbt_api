package egovframework.com.global.util;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ImageConvertUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageConvertUtil.class);
	
	public static int convertPngToJpg(Map<String, Object> params) throws Exception{
		
		String filePath = (String)params.get("filePath");
		LOGGER.info("filePath : " + filePath);		
		
		int result = 0;
		File[] fileList = FileReader.ListFile( filePath );
        if(fileList==null) {
        	return result;
        }   
        
        LOGGER.info("변환시작 - 폴더 총 갯수 : " + fileList.length);
        for( int i = 0; i < fileList.length; i++ ) {
        	LOGGER.info("----------------------- START Convert PNG to JPG  --------------------------- " +i+ "번째");
        	try {
        		//byte[] fileByte = Files.readAllBytes(fileList[i].toPath());
        		
        		File[] fileDetailList = FileReader.ListFile(fileList[i].toPath().toString());
        		for( int j = 0; j < fileDetailList.length; j++ ) {
        			String fileNameWithoutExtension = fileDetailList[j].getName().substring(0, fileDetailList[j].getName().lastIndexOf('.'));
        			File beforeFile = new File(fileDetailList[j].toPath().toString());
        			File afterFile  = new File(fileDetailList[j].getParent() + File.separator + fileNameWithoutExtension + ".jpg");
        			
        			BufferedImage beforeImg = ImageIO.read(beforeFile);
        			BufferedImage afterImg  = new BufferedImage(beforeImg.getWidth(), beforeImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        			
        			afterImg.createGraphics().drawImage(beforeImg, 0, 0, Color.white, null);
        			ImageIO.write(afterImg, "jpg", afterFile);
        		}
                result = 1;
        		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
        	LOGGER.info("-------------------------END Convert Success ----------------------------- " +i+ "번째");
        }
        LOGGER.info("변환폴더 종료");
		
		return result;
	}
	
	
	public static int removeFile(Map<String, Object> params) throws Exception{
		int result = 0;
		String filePath = (String)params.get("filePath");
		String target = (String)params.get("target");
		LOGGER.info("filePath : " + filePath);
		LOGGER.info("target : " + target);
		LOGGER.info("-----------------------START 이미지 삭제 시작 ---------------------------");
		
		File[] fileList = FileReader.ListFile( filePath );

	    if (fileList != null) {
	    	for( int i = 0; i < fileList.length; i++ ) {
	    		File[] fileDetailList = FileReader.ListFile(fileList[i].toPath().toString());
	    		for( int j = 0; j < fileDetailList.length; j++ ) {
	    			
    	            if (fileDetailList[j].isFile()) {
    	            	if(fileDetailList[j].getName().contains(target)) {
    	            		fileDetailList[j].delete();  // 파일 삭제
    	            	}	            		 
    	            }
	    		}
	    	}			
	    	result = 1;
	    }
	    LOGGER.info("-----------------------END 이미지 삭제 종료 -----------------------");
	    return result;
	}
}
