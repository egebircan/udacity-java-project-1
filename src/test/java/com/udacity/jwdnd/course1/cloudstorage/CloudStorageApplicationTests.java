package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
		driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignUpPage() {
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void shouldRedirectToLoginWhenUnauthorized() {
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void shouldSignUpThenLoginThenLogout() {
		signUpAndLogin("signloginlogout", "signloginlogout");

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void shouldCreateNote() {
		signUpAndLogin("createnote", "createnote");
		createNote("demoNoteTitle", "description");
		Assertions.assertTrue(driver.getPageSource().contains("demoNoteTitle"));
	}

	@Test
	public void shouldDeleteNote()  {
		signUpAndLogin("deletenote", "deletenote");
		createNote("demoNoteTitle", "description");

		HomePage homePage = new HomePage(driver);
		homePage.deleteNote();

		this.goToHomeFromSuccess();

		homePage.navigateToNotes();

		Assertions.assertFalse(driver.getPageSource().contains("demoNoteTitle"));
	}

	void createNote(String title, String description) {
		new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav-notes-tab")));
		HomePage homePage = new HomePage(driver);
		homePage.createNote(title, description);

		this.goToHomeFromSuccess();

		homePage.navigateToNotes();
	}

	void goToHomeFromSuccess() {
		new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#goHome")));
		SuccessPage successPage = new SuccessPage(driver);
		successPage.goHome();

		new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#logoutButton")));
	}

	void pressKey(Keys key) {
		Actions builder = new Actions(driver);
		builder.sendKeys(key).build().perform();
		builder.release().perform();
	}
	void signUpAndLogin(String username, String password) {
		String firstName = "firstName";
		String lastName = "lastName";

		driver.get(baseURL + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signUp(firstName, lastName, username, password);

		new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#loginButton")));

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}
}
