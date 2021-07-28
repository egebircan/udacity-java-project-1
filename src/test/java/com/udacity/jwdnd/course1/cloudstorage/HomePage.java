package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;

    @FindBy(css="#logoutButton")
    private WebElement logoutButton;

    @FindBy(css="#nav-notes-tab")
    private WebElement notesTabAnchor;

    @FindBy(css="#openNoteModalButton")
    private WebElement openNoteModalButton;

    @FindBy(css="#note-title")
    private WebElement noteTitleField;

    @FindBy(css="#note-description")
    private WebElement noteDescriptionField;

    @FindBy(css="#noteSubmitButton")
    private WebElement noteSubmitButton;

    @FindBy(css="#editNoteButton")
    private WebElement editNoteButton;

    @FindBy(css="#deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(css="#nav-credentials-tab")
    private WebElement credentialsTabAnchor;

    @FindBy(css="#openCredentialModalButton")
    private WebElement openCredentialModalButton;

    @FindBy(css="#credential-url")
    private WebElement credentialUrlField;

    @FindBy(css="#credential-username")
    private WebElement credentialUsernameField;

    @FindBy(css="#credential-password")
    private WebElement credentialPasswordField;

    @FindBy(css="#credentialSubmitButton")
    private WebElement credentialSubmitButton;

    @FindBy(css="#editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(css="#deleteCredentialButton")
    private WebElement deleteCredentialButton;

    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void navigateToNotes() {
        this.notesTabAnchor.click();
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#openNoteModalButton")));
    }

    public void createNote(String title, String description) {
        this.navigateToNotes();

        this.openNoteModalButton.click();

        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#noteSubmitButton")));
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);
        this.noteSubmitButton.click();
    }

    public void editNote(String title, String description) {
        this.editNoteButton.click();

        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#noteSubmitButton")));

        this.noteTitleField.clear();
        this.noteTitleField.sendKeys(title);

        this.noteDescriptionField.clear();
        this.noteDescriptionField.sendKeys(description);

        this.noteSubmitButton.click();
    }

    public void deleteNote() {
        this.deleteNoteButton.click();
    }

    public void navigateToCredentials() {
        this.credentialsTabAnchor.click();
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#openCredentialModalButton")));
    }

    public void createCredential(String url, String username, String password) {
        this.navigateToCredentials();

        this.openCredentialModalButton.click();

        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#credentialSubmitButton")));
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);
        this.credentialSubmitButton.click();
    }

    public void editCredential(String url, String username, String password) {
        this.editCredentialButton.click();

        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#credentialSubmitButton")));

        this.credentialUrlField.clear();
        this.credentialUrlField.sendKeys(url);

        this.credentialUsernameField.clear();
        this.credentialUsernameField.sendKeys(username);

        this.credentialPasswordField.clear();
        this.credentialPasswordField.sendKeys(password);

        this.credentialSubmitButton.click();
    }

    public void deleteCredential() {
        this.deleteCredentialButton.click();
    }
}
