package me.charlie.springfile;

import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files")
    public String fileUploadForm(Model model) {
        return "files/index";
    }

    @PostMapping("/files")
    public String fileUpload(@RequestBody MultipartFile file, RedirectAttributes attributes) {
        attributes.addFlashAttribute("filename", fileService.fileUpload(file));
        return "redirect:/files";
    }

    @GetMapping("/file")
    public String fileDownloadForm() {
        return "files/download";
    }

    @GetMapping("/file/download")
    public ResponseEntity<Resource> fileDownload(@RequestParam String filename) throws IOException {
        Resource resource = fileService.fileDownload(filename);
        File file = resource.getFile();

        Tika tika = new Tika();
        String mediaType = tika.detect(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename:\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, mediaType)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                .body(resource);
    }
}
