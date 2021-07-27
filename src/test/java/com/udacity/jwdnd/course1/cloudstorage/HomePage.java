package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
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

    @FindBy(css="#noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(css="#deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(css="#nav-credentials-tab")
    private WebElement credentialsTabAnchor;


    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void navigateToNotes() {
        this.notesTabAnchor.click();
    }

    public void navigateToCredentials() {
        this.credentialsTabAnchor.click();
    }

    public void createNote(String title, String description) {
        this.navigateToNotes();

        this.openNoteModalButton.click();

        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);
    }
}
