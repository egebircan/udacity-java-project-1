package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class FileController {
    StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostConstruct
    public void postConstruct() {
        this.storageService.deleteAll();
        this.storageService.init();
    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute FileForm fileForm, Model model, RedirectAttributes redirAttrs) {
        if (fileForm.getFile().getOriginalFilename().isEmpty()) {
            return "redirect:" + "/home";
        }

        if (this.storageService.load(fileForm.getFile().getOriginalFilename()) != null) {
            redirAttrs.addFlashAttribute("uploadError", "A file with the same name already exists");
            return "redirect:" + "/home";
        }

        try {
            this.storageService.save(fileForm.getFile());
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("uploadError", "File exceeds maximum size limit of 5MB.");
            return "redirect:" + "/home";
        }

        return "redirect:" + "/home/success";
    }

    @GetMapping("/files")
    public List<FileInfo> getListFiles() {
        return this.storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = this.storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/deleteFile")
    public String deleteFile(FileInfo fileInfo, Model model) {
        this.storageService.deleteFile(fileInfo);
        return "redirect:" + "/home/success";
    }
}
