package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    NoteService noteService;
    CredentialService credentialService;
    StorageService storageService;
    FileController fileController;

    public HomeController(NoteService noteService, CredentialService credentialService, StorageService storageService, FileController fileController) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.storageService = storageService;
        this.fileController = fileController;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model) {
        model.addAttribute("files", this.fileController.getListFiles());
        model.addAttribute("notes", this.noteService.getNotes(authentication.getName()));
        model.addAttribute("credentials", this.credentialService.getCredentials(authentication.getName()));
        return "home";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }
}
