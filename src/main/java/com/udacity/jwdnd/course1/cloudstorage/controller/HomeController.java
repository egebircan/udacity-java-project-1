package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    NoteService noteService;
    CredentialService credentialService;

    public HomeController(NoteService noteService, CredentialService credentialService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model) {
        model.addAttribute("notes", noteService.getNotes(authentication.getName()));
        model.addAttribute("credentials", credentialService.getCredentials(authentication.getName()));

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
}
