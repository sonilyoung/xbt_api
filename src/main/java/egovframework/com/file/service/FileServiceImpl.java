package egovframework.com.file.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.file.dao.FileDAO;
import egovframework.com.file.vo.AttachFile;

/**
 * FileStorageService 구현체로 서블릿 설치 경로에 파일 저장 (※파일 Storage 결정 시까지 임시로 사용)
 * 
 * @fileName : ServletFileStorageService.java
 * @author : YeongJun Lee
 * @date : 2022.07.12
 */
@Service
public class FileServiceImpl implements FileService {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
    @Resource(name = "FileDAO")
	private FileDAO fileDAO;	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AttachFile> selectFileList(AttachFile param) {
		
		return (List<AttachFile>) fileDAO.selectFileList(param);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AttachFile> selectFileAll(AttachFile param) {
		
		return (List<AttachFile>) fileDAO.selectFileAll(param);
	}
	

	@Override
	public AttachFile selectFile(AttachFile param) {
		
		return fileDAO.selectFile(param);
	}

	@Override
	public int insertFile(AttachFile param) {
		
		return fileDAO.insertFile(param);
	}

	@Override
	public int deleteFile(AttachFile param) {
		
		return fileDAO.deleteFile(param);
	}

	@Override
	public int deleteFileAll(AttachFile param) {
		
		return fileDAO.deleteFileAll(param);
	}



}
