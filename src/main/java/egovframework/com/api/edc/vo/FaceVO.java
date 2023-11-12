package egovframework.com.api.edc.vo;

import lombok.Data;

@Data
public class FaceVO {
	private String userId;
	private String imageType;
	private int requestId;
	private int faceId;
	private byte[] faceImage;
	private String infoKey;
	private String info;
}
