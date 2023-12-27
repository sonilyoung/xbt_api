package egovframework.com.api.edc.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommandExcute implements Serializable{
	private static final long serialVersionUID = -7403289726465589312L;
	private String[] command;
}
