package egovframework.com.global.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class ComUtils {
	public static Boolean imgExtentionCheck(MultipartFile m) {
        String fileName = m.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(fileName);
        if(!fileExtension.toUpperCase().equals("JPG") 
       		&& !fileExtension.toUpperCase().equals("GIF")
       		&& !fileExtension.toUpperCase().equals("JPEG")
       		&& !fileExtension.toUpperCase().equals("PNG")
        ) {
        	return false;
        }else {
        	return true;
        }	
	}
	
	public static List<LocalDate> getDatesBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
		int numOfDaysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1)
        	.limit(numOfDaysBetween+1)
        	.mapToObj(i -> startDate.plusDays(i))
		.collect(Collectors.toList());
	}
}