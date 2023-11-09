package egovframework.com.api.edc.vo;

import lombok.Data;

@Data
public class FaceVO {
	private int request_id;
	private int face_id;
	private byte[] image;
	private String info_key;
	private String info;
}
