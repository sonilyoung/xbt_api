package egovframework.com.common.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommonSystemMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codeId;
	private String codeValue;
	private String remarks;
	private String url;
	private String useYn;
	private String languageCode;
	private String messageType;
	
}
