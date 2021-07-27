package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
		signUpAndLogin();

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	// page load issue
	@Test
	public void shouldCreateNote() {
		signUpAndLogin();
		createNote();
		Assertions.assertTrue(driver.getPageSource().contains("demoNoteTitle"));
	}

	// page load issue
	@Test
	public void shouldDeleteNote()  {
		signUpAndLogin();
		createNote();

		HomePage homePage = new HomePage(driver);
		pressKey(Keys.TAB);
		pressKey(Keys.TAB);
		pressKey(Keys.TAB);
		pressKey(Keys.TAB);
		pressKey(Keys.ENTER);

		homePage.navigateToNotes();

		Assertions.assertFalse(driver.getPageSource().contains("demoNoteTitle"));
	}

	void createNote() {
		HomePage homePage = new HomePage(driver);
		homePage.createNote("demoNoteTitle", "description");

		pressKey(Keys.TAB);
		pressKey(Keys.TAB);
		pressKey(Keys.ENTER);

		homePage.navigateToNotes();
	}

	void pressKey(Keys key) {
		Actions builder = new Actions(driver);
		builder.sendKeys(key).build().perform();
		builder.release().perform();
	}
	void signUpAndLogin() {
		String firstName = "firstName";
		String lastName = "lastName";
		String username = "username";
		String password = "password";

		driver.get(baseURL + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signUp(firstName, lastName, username, password);

		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}
}
