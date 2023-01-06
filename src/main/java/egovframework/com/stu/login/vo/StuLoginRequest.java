package egovframework.com.stu.login.vo;

import lombok.Data;

@Data
public class StuLoginRequest {
    private String loginId;
    private String loginPw;
    private String language;

}
