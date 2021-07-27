package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(css="#signUpFirstName")
    private WebElement firstNameField;

    @FindBy(css="#signUpLastName")
    private WebElement lastNameField;

    @FindBy(css="#signUpUsername")
    private WebElement usernameField;

    @FindBy(css="#signUpPassword")
    private WebElement passwordField;

    @FindBy(css="#signUpButton")
    private WebElement submitButton;

    public SignUpPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password) {
        this.firstNameField.sendKeys(firstName);
        this.lastNameField.sendKeys(lastName);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }
}
