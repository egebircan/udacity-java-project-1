package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CredentialService {
    EncryptionService encryptionService;
    CredentialMapper credentialMapper;
    UserMapper userMapper;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public void createCredential(CredentialForm credentialForm) {
        String encodedKey = encryptionService.generateKey();
        credentialForm.setKey(encodedKey);

        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        credentialForm.setPassword(encryptedPassword);

        User user = userMapper.getUser(credentialForm.getUserName());
        credentialForm.setUserid(user.getUserId());
        credentialMapper.insert(credentialForm);
    }

    public void updateCredential(CredentialForm credentialForm) {
        String encodedKey = encryptionService.generateKey();
        credentialForm.setKey(encodedKey);

        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        credentialForm.setPassword(encryptedPassword);

        credentialMapper.updateCredentialById(credentialForm);
    }

    public void deleteCredential(CredentialForm credentialForm) {
        credentialMapper.deleteCredentialById(credentialForm);
    }

    public List<CredentialForm> getCredentials(String userName) {
        User user = userMapper.getUser(userName);
        List<CredentialForm> credentials = credentialMapper.getCredentialsByUserId(user.getUserId());
        credentials.forEach(c -> c.setPassword(encryptionService.decryptValue(c.getPassword(), c.getKey())));
        return credentials;
    }
}
