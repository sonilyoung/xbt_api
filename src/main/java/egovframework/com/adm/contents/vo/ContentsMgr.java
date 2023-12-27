package egovframework.com.adm.contents.vo;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.Data;

@Data
public class ContentsMgr {

	private List<String> headerInfo;
	
	private List<EgovMap> resultList;
	
	
	
}
