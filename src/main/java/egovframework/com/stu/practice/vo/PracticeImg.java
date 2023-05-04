package egovframework.com.stu.practice.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PracticeImg{
	private Long bagScanNo;
	private String bagScanId;
	private int studyLvl;
	private byte[] imgReal;
	private byte[] imgSide;
	private byte[] imgFront;
	private byte[] imgFrontCollar;
	private byte[] imgFrontOrganism;
	private byte[] imgFrontCollarOutline;
	private byte[] imgFrontCollarReversal;
	private byte[] imgFrontCollarBwRate1;
	private byte[] imgFrontCollarBwRate2;
	private byte[] imgFrontCollarBwRate3;
	private byte[] imgFrontCollarBwRate4;
	private byte[] imgFrontCollarBwRate5;
	private byte[] imgFrontCollarBwRate6;
	private byte[] imgFrontBw;
	private byte[] imgFrontMinerals;
	private byte[] imgFrontBwOutline;
	private byte[] imgFrontBwReversal;
	private byte[] imgFrontBwBwRate1;
	private byte[] imgFrontBwBwRate2;
	private byte[] imgFrontBwBwRate3;
	private byte[] imgFrontBwBwRate4;
	private byte[] imgFrontBwBwRate5;
	private byte[] imgFrontBwBwRate6;
	
	private byte[] imgSideCollar;
	private byte[] imgSideOrganism;
	private byte[] imgSideCollarOutline;
	private byte[] imgSideCollarReversal;
	private byte[] imgSideCollarBwRate1;
	private byte[] imgSideCollarBwRate2;
	private byte[] imgSideCollarBwRate3;
	private byte[] imgSideCollarBwRate4;
	private byte[] imgSideCollarBwRate5;
	private byte[] imgSideCollarBwRate6;
	private byte[] imgSideBw;
	private byte[] imgSideMinerals;
	private byte[] imgSideBwOutline;
	private byte[] imgSideBwReversal;
	private byte[] imgSideBwBwRate1;
	private byte[] imgSideBwBwRate2;
	private byte[] imgSideBwBwRate3;
	private byte[] imgSideBwBwRate4;
	private byte[] imgSideBwBwRate5;
	private byte[] imgSideBwBwRate6;
	
	private String insertId;
	private String insertDate;
	private String updateId;	
	private String updateDate;	
}
