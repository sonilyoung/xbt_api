package egovframework.com.file.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일 첨부 상세
 * 
 * @fileName : AttachFile.java
 * @author : YeongJun Lee
 * @date : 2022.07.12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachFile {
    private Long atchFileId;
    private int fileSn;
    private String command;
    private String targetName;
    private String filePath;
    private String saveFileName; 
    private String originalFileName; 
    private String fileExt;
    private int fileSize;
    private boolean saved;
    private boolean deleted;
}
