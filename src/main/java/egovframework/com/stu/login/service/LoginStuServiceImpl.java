package egovframework.com.stu.login.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.adm.login.vo.Login;
import egovframework.com.adm.login.vo.LoginRequest;
import egovframework.com.global.util.AES256Util;
import egovframework.com.stu.login.dao.UserStuManageDAO;
import egovframework.com.stu.login.vo.StuLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * 사용자관리에 관한 비지니스 클래스를 정의한다.
 * 
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2014.12.08	 이기하			암호화방식 변경(OfficeFileScrty.encryptPassword)
 *
 *      </pre>
 */
@Service("loginStuService")
public class LoginStuServiceImpl implements LoginStuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginStuServiceImpl.class);
    private static final String secretKey = "secretsecretsecretsecretsecret";
    private static final long validityInMilliseconds = 3600000 * 24;
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
	private UserStuManageDAO repository;	

    public String createToken(HttpServletRequest request, LoginRequest loginRequest) {
    	StuLogin login = null;
        String tokenStr = null;

        LOGGER.debug("loginRequest.getLoginId()    ]" + loginRequest.getLoginId() + "[");
        login = repository.getStuByLoginId(loginRequest.getLoginId());
        if (login != null) {
        	
        	//if(login.getIsEnable()==0) {
        		//throw new CustomBaseException(BaseResponseCode.STOP_ID, BaseResponseCode.STOP_ID.getMessage());
        	//}
        	
            try {
            	AES256Util aesUtil = new AES256Util();
	            String pwEnc = aesUtil.encrypt(loginRequest.getLoginPw());
	
	            LOGGER.debug("login.getUserNm()    ]" + login.getUserNm() + "[");
	            LOGGER.debug("login.getUserPw() ]" + login.getUserPw() + "[");
	            LOGGER.debug("loginRequest.getLoginPw() ]" + loginRequest.getLoginPw() + "[");
	            LOGGER.debug("pwEnc                     ]" + pwEnc + "[");
	
	            if (StringUtils.equals(pwEnc, login.getUserPw())) {
	                //login.setLoginIp(OfficeClntInfo.getClntIP((HttpServletRequest) request));
	                login.setLoginLast(tokenStr);
	                tokenStr = toString(login);
	            } else {
	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }       	
        } else {
            return null;
        }
        


        LOGGER.debug("tokenStr                  ]" + tokenStr + "[");

        return createToken(tokenStr);
    }

    public StuLogin getLoginInfo(HttpServletRequest request) {
    	StuLogin login = new StuLogin();
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                LOGGER.error("Authorizaton을 찾을 수 없습니다.");
                return null;
            }

            String token = header.replace("Bearer ", "");
            if (!validateToken(token)) {
                LOGGER.error("Bearer을 찾을 수 없습니다.");
                return null;
            }

            String str = getSubject(token);
            login = toVo(str);
            LOGGER.debug("getLoginInfo==>{}", login);
        } catch (Exception e) {
            LOGGER.error("Token을 구할 수 없습니다. " + e.getMessage());
            return null;
        }
        return login;
    }

    private String toString(StuLogin login) throws Exception {
        String str = login.getUserNo() + "|" 
        		+ login.getUserId() + "|" 
        		+ login.getUserNm() + "|" 
        		+ login.getUserPw() + "|"
                + login.getAuthCd() + "|"
                + (StringUtils.isEmpty(login.getCompany()) ? "null" : login.getCompany()) + "|"
                + (StringUtils.isEmpty(login.getDept()) ? "null" : login.getDept()) + "|"
                + (StringUtils.isEmpty(login.getTelNo()) ? "null" : login.getTelNo()) + "|"
                + (StringUtils.isEmpty(login.getEmail()) ? "null" : login.getEmail()) + "|"
                + login.getLoginStart() + "|"
                + login.getLoginLast() + "|"
                + login.getPwPrior() + "|"
                + login.getPwChange() + "|"
                + login.getPwUpdate() + "|"
                + login.getPwPeriod() + "|"
                + (StringUtils.isEmpty(login.getUseYn()) ? "null" : login.getUseYn()) + "|"
                + login.getInsertId() + "|"
                + login.getInsertDate() + "|"
                + login.getUpdateId() + "|"
                + login.getUpdateDate() + "|"
                + (StringUtils.isEmpty(login.getPosition()) ? "null" : login.getPosition());
        return str;
    }

    private StuLogin toVo(String str) throws Exception {
    	StuLogin login = new StuLogin();
        String strSplit[] = StringUtils.split(str, '|');
        login.setUserNo(Long.parseLong(strSplit[0]));
		login.setUserId(strSplit[1]); 
		login.setUserNm(strSplit[2]); 
		login.setUserPw(strSplit[3]);
        login.setAuthCd(strSplit[4]);
        login.setCompany(strSplit[5]);
        login.setDept(strSplit[6]);
        login.setTelNo(strSplit[7]);
        login.setEmail(strSplit[8]);
        login.setLoginStart(strSplit[9]);
        login.setLoginLast(strSplit[10]);
        login.setPwPrior(strSplit[11]);
        login.setPwChange(strSplit[12]);
        login.setPwUpdate(strSplit[13]);
        login.setPwPeriod(strSplit[14]);
        login.setUseYn(strSplit[15]);
        login.setInsertId(strSplit[16]);
        login.setInsertDate(strSplit[17]);
        login.setUpdateId(strSplit[18]);
        login.setUpdateDate(strSplit[19]);
        login.setPosition(strSplit[20]);     
        return login;
    }

    private String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    // 토큰에서 값 추출
    private String getSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 유효한 토큰인지 확인
    private boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰이 정상 생성시 해당날짜로 마지막 로그인 시간 업데이트함
	@Override
	public void updateLoginTime(String loginId) {
		repository.updateLoginTime(loginId);
	}
	
	@Override
	public void updateLoginCnt(String loginId) {
		repository.updateLoginCnt(loginId);
	}	
	
	
    public LoginRequest getPwdInfo(LoginRequest params) {
    	return repository.getPwdInfo(params);
    }

	@Override
	public StuLogin selectUserId(StuLogin params) {
		// TODO Auto-generated method stub
		return repository.selectUserId(params);
	}

	@Override
	public int updateUserPwd(StuLogin params) {
		// TODO Auto-generated method stub
		return repository.updateUserPwd(params);
	}	    
	
	@Override
	public StuLogin selectXbtFaceType(StuLogin params) {
		// TODO Auto-generated method stub
		return repository.selectXbtFaceType(params);
	}
	
}
