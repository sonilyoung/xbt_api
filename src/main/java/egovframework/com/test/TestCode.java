package egovframework.com.test;

public class TestCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "H1100418X11B-101.jpg";
		
		String[] lastFolderName = fileName.split("-");
		
		System.out.println(lastFolderName[0]);		
	}

}
