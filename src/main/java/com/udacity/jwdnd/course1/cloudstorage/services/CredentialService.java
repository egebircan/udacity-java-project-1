package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    UserMapper userMapper;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
    }

    public void createCredential(CredentialForm credentialForm) {
        User user = userMapper.getUser(credentialForm.getUserName());
        credentialForm.setUserid(user.getUserId());
        credentialMapper.insert(credentialForm);
    }

    public void updateCredential(CredentialForm credentialForm) {
        credentialMapper.updateCredentialById(credentialForm);
    }

    public void deleteCredential(CredentialForm credentialForm) {
        credentialMapper.deleteCredentialById(credentialForm);
    }

    public List<CredentialForm> getCredentials(String userName) {
        User user = userMapper.getUser(userName);
        return credentialMapper.getCredentialsByUserId(user.getUserId());
    }
}
