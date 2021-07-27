package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    NoteService noteService;
    CredentialService credentialService;
    StorageService storageService;
    Boolean uploadError = false;

    public HomeController(NoteService noteService, CredentialService credentialService, StorageService storageService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.storageService = storageService;
    }

    @PostConstruct
    public void postConstruct() {
        storageService.deleteAll();
        storageService.init();
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model) {
        model.addAttribute("files", this.getListFiles());
        model.addAttribute("notes", noteService.getNotes(authentication.getName()));
        model.addAttribute("credentials", credentialService.getCredentials(authentication.getName()));

        if (this.uploadError) {
            model.addAttribute("uploadError", true);
            this.uploadError = false;
        }

        return "home";
    }

    @PostMapping("/createNote")
    public String createNote(Authentication authentication, NoteForm noteForm, Model model) {
        noteForm.setUserName(authentication.getName());

        if (noteForm.getNoteid() == null) {
            noteService.createNote(noteForm);
        } else {
            noteService.updateNote(noteForm);
        }

        return "redirect:" + "/home";
    }

    @PostMapping("/deleteNote")
    public String deleteNote(NoteForm noteForm, Model model) {
        noteService.deleteNote(noteForm);
        return "redirect:" + "/home";
    }


    @PostMapping("/createCredential")
    public String createCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        credentialForm.setUserName(authentication.getName());

        if (credentialForm.getCredentialid() == null) {
            credentialService.createCredential(credentialForm);
        } else {
            credentialService.updateCredential(credentialForm);
        }

        return "redirect:" + "/home";
    }

    @PostMapping("/deleteCredential")
    public String deleteCredential(CredentialForm credentialForm, Model model) {
        credentialService.deleteCredential(credentialForm);
        return "redirect:" + "/home";
    }


    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute FileForm fileForm, Model model) {
        if (storageService.load(fileForm.getFile().getOriginalFilename()) != null) {
            this.uploadError = true;
            return "redirect:" + "/home";
        }

        try {
            storageService.save(fileForm.getFile());
        } catch (Exception ignored) { }

        return "redirect:" + "/home";
    }

    @GetMapping("/files")
    public List<FileInfo> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(HomeController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return fileInfos;
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/deleteFile")
    public String deleteFile(FileInfo fileInfo, Model model) {
        storageService.deleteFile(fileInfo);
        return "redirect:" + "/home";
    }
}
