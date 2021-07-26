package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    NoteMapper noteMapper;
    UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public void createNote(NoteForm noteForm) {
        User user = userMapper.getUser(noteForm.getUserName());
        noteForm.setUserId(user.getUserId());
        noteMapper.insert(noteForm);
    }

    public void updateNote(NoteForm noteForm) {
        noteMapper.updateNoteById(noteForm);
    }

    public void deleteNote(NoteForm noteForm) {
        noteMapper.deleteNoteById(noteForm);
    }

    public List<NoteForm> getNotes(String userName) {
        User user = userMapper.getUser(userName);
        return noteMapper.getNotesByUserId(user.getUserId());
    }
}
