package egovframework.com.test;

import java.util.List;

import lombok.Data;

@Data
public class TestResultApi {

	private String unitGroupCd;
	private String studyLvl;
	private String decipMachineCd;
	private List<TestApi> unitInfo;
	private List<TestApi> cbtUnitImg;

}
