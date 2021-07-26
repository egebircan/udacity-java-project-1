package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    void insert(NoteForm noteForm);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid=#{noteid}")
    void updateNoteById(NoteForm noteForm);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<NoteForm> getNotesByUserId(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteid}")
    void deleteNoteById(NoteForm noteForm);
}
