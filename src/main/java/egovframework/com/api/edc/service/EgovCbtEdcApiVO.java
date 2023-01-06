package egovframework.com.api.edc.service;

import java.util.HashMap;
import java.util.Objects;

public class EgovCbtEdcApiVO {
	
	private HashMap<Flag, PseudoFilter> pseudo;
	private HashMap<Flag, ThreeDimension> dimension;
	
	public static class Flag {
		
		private final String code;
		private int hashCode;
		
		public Flag(String key) {
			this.code = key;
			this.setHashCode(Objects.hash(key));
		}

		public String getCode() {
			return code;
		}

		public int getHashCode() {
			return hashCode;
		}

		public void setHashCode(int hashCode) {
			this.hashCode = hashCode;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o)
	            return true;
	        if (o == null || getClass() != o.getClass())
	            return false;
	        Flag that = (Flag) o;
	        return code == that.code;
		}
		
		@Override
		public int hashCode() {
			return this.hashCode;
		}
	}
	
	public class PseudoFilter {
		
		//Pseudo Color Filter
		private int flag;//flag of request
		private String IMG_RAW_H;//High level raw image
		private String IMG_RAW_L;//Low level raw image
		private String Param;//Pseudo Color Parameter
		private String NAME;//0_F, 0_S(Unit File Name)
		
		public String getIMG_RAW_H() {
			return IMG_RAW_H;
		}
		public void setIMG_RAW_H(String iMG_RAW_H) {
			IMG_RAW_H = iMG_RAW_H;
		}
		
		public String getIMG_RAW_L() {
			return IMG_RAW_L;
		}
		public void setIMG_RAW_L(String iMG_RAW_L) {
			IMG_RAW_L = iMG_RAW_L;
		}
		
		public String getParam() {
			return Param;
		}
		public void setParam(String param) {
			Param = param;
		}

		public String getNAME() {
			return NAME;
		}
		public void setNAME(String nAME) {
			NAME = nAME;
		}
		public int getFlag() {
			return flag;
		}
		public void setFlag(int flag) {
			this.flag = flag;
		}
	}
	
	public class ThreeDimension {
		
		//3D Image Generator
		private int flag;//flag of request
		private String IMG_FRONT_RAW;//Front Raw Image
		private String IMG_SIDE_RAW;//Side Raw Image
		private String ID;//Unit Id
		private String ANGLE;//Angle of 3D Image(INT 0 ~ 29)
		
		public String getIMG_FRONT_RAW() {
			return IMG_FRONT_RAW;
		}
		public void setIMG_FRONT_RAW(String iMG_FRONT_RAW) {
			IMG_FRONT_RAW = iMG_FRONT_RAW;
		}
		
		public String getIMG_SIDE_RAW() {
			return IMG_SIDE_RAW;
		}
		public void setIMG_SIDE_RAW(String iMG_SIDE_RAW) {
			IMG_SIDE_RAW = iMG_SIDE_RAW;
		}

		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}

		public String getANGLE() {
			return ANGLE;
		}
		public void setANGLE(String aNGLE) {
			ANGLE = aNGLE;
		}
		public int getFlag() {
			return flag;
		}
		public void setFlag(int flag) {
			this.flag = flag;
		}
	}

	public HashMap<Flag, PseudoFilter> getPseudo() {
		return pseudo;
	}

	public void setPseudo(HashMap<Flag, PseudoFilter> pseudo) {
		this.pseudo = pseudo;
	}

	public HashMap<Flag, ThreeDimension> getDimension() {
		return dimension;
	}

	public void setDimension(HashMap<Flag, ThreeDimension> dimension) {
		this.dimension = dimension;
	}
	
}
