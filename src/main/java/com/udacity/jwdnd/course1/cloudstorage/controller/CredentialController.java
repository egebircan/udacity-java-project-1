package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class CredentialController {
    CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/createCredential")
    public String createCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        credentialForm.setUserName(authentication.getName());

        if (credentialForm.getCredentialid() == null) {
            credentialService.createCredential(credentialForm);
        } else {
            credentialService.updateCredential(credentialForm);
        }

        return "redirect:" + "/home/success";
    }

    @PostMapping("/deleteCredential")
    public String deleteCredential(CredentialForm credentialForm, Model model) {
        credentialService.deleteCredential(credentialForm);
        return "redirect:" + "/home/success";
    }
}
