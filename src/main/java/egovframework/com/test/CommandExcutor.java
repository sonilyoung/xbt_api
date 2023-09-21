package egovframework.com.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExcutor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandExcutor.class);
	
    public String excutor(String command) {
            // 실행할 쉘 명령어
        	// ProcessBuilder에 넣어줄 커맨드 준비
        	
        try {
            // 실행할 쉘 명령어
            //String command = "ls -l";

            // 프로세스 빌더 생성
            ProcessBuilder processBuilder = new ProcessBuilder();

            LOGGER.info("=============CommandExcutor1=============");
            // 리눅스 쉘 명령어 실행
            processBuilder.command("sh", "-c", command);

            LOGGER.info("=============CommandExcutor2=============");
            
            // 프로세스 시작
            Process process = processBuilder.start();
            
            LOGGER.info("=============CommandExcutor3=============");
            
            // 프로세스 실행 결과 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            LOGGER.info("=============CommandExcutor4============="+reader.readLine());
            // 결과 출력
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 프로세스 종료
            process.waitFor();
            LOGGER.info("=============CommandExcutor5============="+output.toString());
            process.destroy();
            return output.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while executing the shell command";
        }

  
    }
}
