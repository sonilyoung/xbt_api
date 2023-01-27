package egovframework.com.api.edc.vo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UnitImages {

	@SerializedName("cbtUnitImg")
	private List <CbtUnitImg> cbtUnitImg;
	
	@SerializedName("decipMachineCd")	
	private String decipMachineCd;

	@SerializedName("studyLvl")
	private String studyLvl;

	@SerializedName("unitGroupCd")
	private String unitGroupCd;	

	@SerializedName("unitId")
	private String unitId;	

	@SerializedName("unitInfo")
	private List <UnitInfo> unitInfo;	
			
}
