package egovframework.com.stu.learning.vo;

import java.util.List;

import lombok.Data;

@Data
public class LearningMainImg{
	private Long bagScanNo;
	private String bagScanId;
	private String command;
	private int studyLvl;
	private byte[] imgReal;//실사
	private byte[] imgFront;//정면
	private byte[] imgSide;//측면
	
}
