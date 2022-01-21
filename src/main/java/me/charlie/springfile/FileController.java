package me.charlie.springfile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
