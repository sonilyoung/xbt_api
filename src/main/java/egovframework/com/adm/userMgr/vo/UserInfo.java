package egovframework.com.adm.userMgr.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.Data;

@Data
public class UserInfo {

	private Long userNo; /*사용자NO*/
	private String userId;                     /*사용자id*/        
	private String userNm;                     /*사용자명*/        
	private String userPw;                     /*패스워드*/
	private String userPhoto;								 /*교육생사진(조회 속도를 위해 조회하지 않음 입력을 위해 빈칸 처리)*/
	private String iauthCd;                     /*권한코드*/        
	private String authNm;	                 /*권한코드이름*/
	private String company;	                 /*회사*/
	private String dept;                        /*부서*/    
	private String position;	                 /*직급*/
	private String trainingDiv;                /*교육구분*/
	private String telNo;                      /*전화번호*/        
	private String hpNo;                       /*핸드폰번호*/        
	private String email;                       /*이메일*/        
	private String loginStart;                 /*최초로그인*/        
	private String loginLast;                  /*최종로그인*/        
	private String loginError;                 /*로그인에러*/        
	private String pwPrior;                    /*이전비밀번호*/        
	private String pwChange;                   /*비밀번호변경일*/        
	private String pwUpdate;                  /*비밀번호변경주기*/        
	private String pwPeriod;                   /*비밀번호변경기간*/        
	private String useYn;                     /*사용여부*/        
	private String insertId;                      /*등록자id*/        
	private String insertDate;                      /*등록일자*/        
	private String updateId;                      /*수정자id*/        
	private String updateDate;                      /*수정일자*/   
	
}
