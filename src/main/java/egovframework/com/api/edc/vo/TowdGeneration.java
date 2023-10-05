package egovframework.com.api.edc.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TowdGeneration implements Serializable{
	private static final long serialVersionUID = 5493112859864781570L;
	private String category;
	private int categoryCnt;
	private String fileName;
	private String[] kaistCommand;
	private List<byte[]> towdGenList;
	private List<String> fileNameList;
}
