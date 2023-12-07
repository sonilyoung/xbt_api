package egovframework.com.score;

import org.springframework.stereotype.Service;

import egovframework.com.adm.system.vo.XbtScore;
import egovframework.com.stu.learning.vo.Learning;


/**
 * 스케줄링 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.04.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.04.16  박지욱          최초 생성
 *  2011.06.30  이기하          패키지 분리(sts -> sts.bst)
 *
 *  </pre>
 */

@Service
public interface XbtScoreService{
	
	public void userScoreCalculate(XbtScore userId) throws Exception;
	
	public void userScoreCalculateAdmin(XbtScore userId) throws Exception;
	
	public int selectCommonScoreResult(Learning params) throws Exception;
}
