package egovframework.com.common.vo;

import lombok.Data;

@Data
public class LearningMainImg{
	private Long bagScanNo;
	private String bagScanId;
	private String command;
	private String unitName;
	private int studyLvl;
	private byte[] imgReal;//실사
	private byte[] imgFront;//정면
	private byte[] imgSide;//측면
	private byte[] imgThreed;//측면
	
}
