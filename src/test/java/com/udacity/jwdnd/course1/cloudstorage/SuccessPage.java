package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessPage {
    WebDriver driver;

    @FindBy(css="#goHome")
    private WebElement goToHomeAnchor;

    public SuccessPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void goHome() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#goHome")));
        this.goToHomeAnchor.click();
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#logoutButton")));
    }
}
