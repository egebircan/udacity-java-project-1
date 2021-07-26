package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, password, userid) VALUES(#{url}, #{username}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    void insert(CredentialForm credentialForm);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} WHERE credentialid=#{credentialid}")
    void updateCredentialById(CredentialForm credentialForm);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<CredentialForm> getCredentialsByUserId(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    void deleteCredentialById(CredentialForm credentialForm);
}
