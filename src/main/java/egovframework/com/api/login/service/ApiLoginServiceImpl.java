package egovframework.com.api.login.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.api.login.vo.ApiLogin;
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
@Service("apiLoginService")
public class ApiLoginServiceImpl implements ApiLoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLoginServiceImpl.class);
    private static final String secretKey = "secretsecretsecretsecretsecret";
    private static final long validityInMilliseconds = 3600000 * 24;
    private static final String CLIENT_ID = "kist_xbt_api1";

    public String createToken(HttpServletRequest request, ApiLogin loginRequest) {
        ApiLogin login = new ApiLogin();
        String tokenStr = null;

        LOGGER.debug("loginRequest.getLoginId()    ]" + loginRequest.getLoginId() + "[");
        login.setLoginId(CLIENT_ID);

        try {
            tokenStr = toString(login);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }       	


        LOGGER.debug("tokenStr                  ]" + tokenStr + "[");

        return createToken(tokenStr);
    }

    public ApiLogin getLoginInfo(HttpServletRequest request) {
        ApiLogin login = new ApiLogin();
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

    private String toString(ApiLogin login) throws Exception {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = login.getLoginId() + "|" + df; 
        return str;
    }

    private ApiLogin toVo(String str) throws Exception {
        ApiLogin login = new ApiLogin();
        String strSplit[] = StringUtils.split(str, '|');
        login.setLoginId(strSplit[0]);
        login.setRegDt(strSplit[1]);
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

}
