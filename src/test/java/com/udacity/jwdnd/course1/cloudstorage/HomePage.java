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

    @FindBy(css="#deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(css="#nav-credentials-tab")
    private WebElement credentialsTabAnchor;


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

    public void navigateToCredentials() {
        this.credentialsTabAnchor.click();
    }

    public void createNote(String title, String description) {
        this.navigateToNotes();

        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#openNoteModalButton")));
        this.openNoteModalButton.click();

        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#noteSubmitButton")));
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);
        this.noteSubmitButton.click();
    }

    public void deleteNote() {
        this.deleteNoteButton.click();
    }
}
