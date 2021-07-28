package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class NoteController {
    NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/createNote")
    public String createNote(Authentication authentication, NoteForm noteForm, Model model) {
        noteForm.setUserName(authentication.getName());

        if (noteForm.getNoteid() == null) {
            noteService.createNote(noteForm);
        } else {
            noteService.updateNote(noteForm);
        }

        return "redirect:" + "/home/success";
    }

    @PostMapping("/deleteNote")
    public String deleteNote(NoteForm noteForm, Model model) {
        noteService.deleteNote(noteForm);
        return "redirect:" + "/home/success";
    }
}
