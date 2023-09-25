package egovframework.com.api.edc.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ThreedGeneration implements Serializable{
	
	private static final long serialVersionUID = 5094756872612852233L;
	private String unitId;
	private String[] kaistCommand;
	private List<byte[]> threedGenList;
	private List<String> fileNameList;
	
	private byte[] outputh; //3D View Synthesis 결과 GIF 이미지 (high).
	private byte[] outputl; //3D View Synthesis 결과 GIF 이미지 (low).	
}
