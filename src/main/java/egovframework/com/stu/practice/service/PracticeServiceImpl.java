package egovframework.com.stu.practice.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.stu.practice.vo.UnitGroup;
import egovframework.com.stu.practice.dao.PracticeDAO;
import egovframework.com.stu.practice.vo.Practice;
//import lombok.extern.log4j.Log4j2;


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
//@Log4j2
@Service("PracticeService")
public class PracticeServiceImpl implements PracticeService {

    @Resource(name = "PracticeDAO")
	private PracticeDAO PracticeDAO;


	@Override
	@SuppressWarnings("unchecked")
	public List<UnitGroup> selectUnitGroupList(UnitGroup params) {
		
		return (List<UnitGroup>) PracticeDAO.selectUnitGroupList(params);
	}
	
	@Override
	public UnitGroup selectUnitGroupAnswer(UnitGroup params) {
		
		return PracticeDAO.selectUnitGroupAnswer(params);
	}
	

    @Override
	@SuppressWarnings("unchecked")
	public List<Practice> selectUnitList(Practice params) {
		
		return (List<Practice>)PracticeDAO.selectUnitList(params);
	}

	@Override
	public Practice selectUnit(Practice params) {
		
		return PracticeDAO.selectUnit(params);
	}

}
