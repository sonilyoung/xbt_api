package egovframework.com.api.login.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ApiLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String loginId;                     //식별아이디        
    private String regDt;                      //등록일        
}
