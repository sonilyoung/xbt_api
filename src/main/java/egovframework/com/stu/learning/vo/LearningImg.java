package egovframework.com.stu.learning.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class LearningImg{
	private Long bagScanNo;
	private String bagScanId;
	private int studyLvl;
	//private byte[] imgReal;
	
	private byte[] imgFront;//정면
	private byte[] imgSide;//측면
	
	private byte[] imgFrontColor; //정면컬러 101
	private byte[] imgFrontColorMineral;//정면무기물 102
	private byte[] imgFrontColorOrganism;//정면유기물 103
	private byte[] imgFrontColorReversal;//정면반전 104
	private byte[] imgFrontColorBwRate1;//정면채도 105
	private byte[] imgFrontColorBwRate2;//106
	private byte[] imgFrontColorBwRate3;//107
	private byte[] imgFrontColorBwRate4;//108
	private byte[] imgFrontColorBwRate5;//109
	private byte[] imgFrontColorBwRate6;//110
	
	private byte[] imgFrontBw;//정면흑백 111
	private byte[] imgFrontBwMineral;//정면흑백무기물 112
	private byte[] imgFrontBwOrganism;//정면흑백유기물 113
	private byte[] imgFrontBwReversal;//정면흑백반전 114
	private byte[] imgFrontBwBwRate1;//정면흑백채도 115
	private byte[] imgFrontBwBwRate2;//116
	private byte[] imgFrontBwBwRate3;//117
	private byte[] imgFrontBwBwRate4;//118
	private byte[] imgFrontBwBwRate5;//119
	private byte[] imgFrontBwBwRate6;//120
	
	private byte[] imgSideColor;//201
	private byte[] imgSideColorMineral;//202
	private byte[] imgSideColorOrganism;//203
	private byte[] imgSideColorReversal;//204
	private byte[] imgSideColorBwRate1;//205
	private byte[] imgSideColorBwRate2;//206
	private byte[] imgSideColorBwRate3;//207
	private byte[] imgSideColorBwRate4;//208
	private byte[] imgSideColorBwRate5;//209
	private byte[] imgSideColorBwRate6;//210
	
	private byte[] imgSideBw;//211
	private byte[] imgSideBwMinerals;//212
	private byte[] imgSideBwOrganism;//213
	private byte[] imgSideBwReversal;//214
	private byte[] imgSideBwBwRate1;//215
	private byte[] imgSideBwBwRate2;//216
	private byte[] imgSideBwBwRate3;//217
	private byte[] imgSideBwBwRate4;//218
	private byte[] imgSideBwBwRate5;//219
	private byte[] imgSideBwBwRate6;//220
	
	private String insertId;
	private String insertDate;
	private String updateId;	
	private String updateDate;	
}
