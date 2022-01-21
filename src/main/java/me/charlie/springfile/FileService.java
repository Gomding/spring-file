package me.charlie.springfile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final String FILE_PATH = "src/main/resources/file";

    public String fileUpload(MultipartFile file) {
        Path path = Paths.get(FILE_PATH).toAbsolutePath().normalize();
        String filename = file.getOriginalFilename();
        Path targetPath = path.resolve(filename).normalize();
        try {
            file.transferTo(targetPath);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 업로드에 실패했습니다.");
        }
        return filename;
    }
}
