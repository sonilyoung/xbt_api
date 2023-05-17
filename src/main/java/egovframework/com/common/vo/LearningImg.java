package egovframework.com.common.vo;

import lombok.Data;

@Data
public class LearningImg{
	private Long bagScanNo;
	private String bagScanId;
	private String unitScanId;
	private String unitId;
	private String command;
	private int studyLvl;
	//private byte[] imgReal;
	
	private byte[] imgReal;//실사
	private byte[] imgFront;//정면
	private byte[] imgSide;//측면
	private byte[] imgThreed;//3d이미지
	private byte[] imgThreedAngle;//각조조절된3d이미지
	
	private byte[] imgFrontColor; //정면컬러 101
	private byte[] imgFrontColorMineral;//정면무기물 102
	private byte[] imgFrontColorOrganism;//정면유기물 103
	private byte[] imgFrontColorReversal;//정면반전 104
	private byte[] imgFrontColorBwRate1;//정면채도 105
	private byte[] imgFrontColorBwRate2;//정면채도 106
	private byte[] imgFrontColorBwRate3;//정면채도 107
	private byte[] imgFrontColorBwRate4;//정면채도 108
	private byte[] imgFrontColorBwRate5;//정면채도 109
	private byte[] imgFrontColorBwRate6;//정면채도 110
	
	private byte[] imgFrontBw;//정면흑백 111
	private byte[] imgFrontBwMineral;//정면흑백무기물 112
	private byte[] imgFrontBwOrganism;//정면흑백유기물 113
	private byte[] imgFrontBwReversal;//정면흑백반전 114
	private byte[] imgFrontBwBwRate1;//정면흑백채도 115
	private byte[] imgFrontBwBwRate2;//정면흑백채도 116
	private byte[] imgFrontBwBwRate3;//정면흑백채도 117
	private byte[] imgFrontBwBwRate4;// 정면흑백채도118
	private byte[] imgFrontBwBwRate5;//정면흑백채도 119
	private byte[] imgFrontBwBwRate6;//정면흑백채도 120
	
	private byte[] imgSideColor;//측면컬러 201
	private byte[] imgSideColorMineral;//측면무기물 202
	private byte[] imgSideColorOrganism;//측면유기물 203
	private byte[] imgSideColorReversal;//측면반전 204
	private byte[] imgSideColorBwRate1;//측면채도 205
	private byte[] imgSideColorBwRate2;//측면채도206
	private byte[] imgSideColorBwRate3;//측면채도207
	private byte[] imgSideColorBwRate4;//측면채도208
	private byte[] imgSideColorBwRate5;//측면채도209
	private byte[] imgSideColorBwRate6;//측면채도210
	
	private byte[] imgSideBw;//측면흑백211
	private byte[] imgSideBwMinerals;//측면흑백무기물212
	private byte[] imgSideBwOrganism;//측면흑백유기물213
	private byte[] imgSideBwReversal;//측면흑백반전214
	private byte[] imgSideBwBwRate1;//측면흑백채도215
	private byte[] imgSideBwBwRate2;//측면흑백채도216
	private byte[] imgSideBwBwRate3;//측면흑백채도217
	private byte[] imgSideBwBwRate4;//측면흑백채도218
	private byte[] imgSideBwBwRate5;//측면흑백채도219
	private byte[] imgSideBwBwRate6;//측면흑백채도220
	
	private String insertId;
	private String insertDate;
	private String updateId;	
	private String updateDate;	
}
