package me.charlie.springfile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {

    @GetMapping("/files")
    public String fileUploadForm(Model model) {
        return "files/index";
    }
}
