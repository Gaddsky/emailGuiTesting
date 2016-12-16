import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.ComposeNewPage;
import pageObjects.InboxPage;
import pageObjects.LoginPage;
import pageObjects.SendMsgOkPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * This tests check Email-service: login, creating new letter, deleting first letter
 */
@RunWith(Parameterized.class)
public class EmailGuiTest {
    /** Instance of the using webdriver */
    private WebDriver driver;
    /** Reference for the webdriver's constructor */
    private DriverBuilder driverB;
    private String domain = "mail.ru";
    /** Username, used in email service. It mast be set as evironment variable 'MAILTESTLOGIN' in your system */
    private String username = System.getenv("MAILTESTLOGIN");
    /** Password, used in email service. It mast be set as evironment variable 'MAILTESTPASSWORD' in your system */
    private String password = System.getenv("MAILTESTPASSWORD");

    /** Functional interface for creation WebDriver's instances with reference to constructor */
    public interface DriverBuilder {
        WebDriver create();
    }

    public EmailGuiTest(DriverBuilder driverB) {
        this.driverB = driverB;
    }

    /**
     *
     * @return List of the references for Webdriver's constructors, which will be used for testing
     */
    @Parameterized.Parameters
    public static List<DriverBuilder> getTestDrivers() {
        return new ArrayList<>(Arrays.asList(
                ChromeDriver::new,
                FirefoxDriver::new
        ));
    }

    /** This method is calling before each test. It creates needed WebDriver instance */
    @Before
    public void setUpWebDriver() {
        driver = driverB.create();
    }

    /** Testing of the login to the web-site */
    @Test
    public void loginTest() {
        InboxPage inboxPage = loginProcedure();

        Assert.assertTrue(inboxPage.getTitle().endsWith("Почта Mail.Ru"));
        Assert.assertTrue(inboxPage.getUrl().endsWith("e.mail.ru/messages/inbox/?back=1"));
        Assert.assertEquals(inboxPage.getLoggedAccountName(), username + "@" + domain);
        Assert.assertTrue(inboxPage.getActiveFolderName().contains("Входящие"));
    }

    /** Testing the creation of the new message */
    @Test
    public void newLetter() {
        InboxPage inboxPage = loginProcedure();

        ComposeNewPage composeNew = inboxPage.clickNewLetter();
        composeNew.typeTo(username +"@" + domain);
        composeNew.typeSubject(UUID.randomUUID().toString());
        composeNew.typeText("This is test text\nThat is");
        SendMsgOkPage sendMsgOkPage = composeNew.sendButtonClick();

        Assert.assertTrue(sendMsgOkPage.getTitle().endsWith(" - Почта Mail.Ru"));
        Assert.assertTrue(sendMsgOkPage.getUrl().contains("e.mail.ru/sendmsgok?id="));
        Assert.assertEquals(sendMsgOkPage.getLoggedAccountName(), username + "@" + domain);
        Assert.assertEquals(sendMsgOkPage.getSentTitle(), "Ваше письмо отправлено. Перейти во Входящие");
    }


    /** Testing the deletion of the first letter in inbox folder */
    @Test
    public void deleteFirstLetter() {
        InboxPage inboxPage = loginProcedure();

        if (!inboxPage.isInboxLetters()) {
            ComposeNewPage composeNew = inboxPage.clickNewLetter();
            composeNew.typeTo(username +"@" + domain);
            composeNew.typeSubject(UUID.randomUUID().toString());
            composeNew.typeText("This letter will be delete\nThat is");
            inboxPage = composeNew.sendButtonClick().returnToInbox().waitForUnread();
        }
        String letterToDeleteHeader = inboxPage.getFirstLetterHeader();
        inboxPage.chooseFirstLetter().deleteSelectedLetter().waitForDelete();

//        Assert.assertFalse(inboxPage.getAllLettersHeaders().contains(letterToDeleteHeader)); // another variant of assertion check
        Assert.assertNotEquals(letterToDeleteHeader, inboxPage.getFirstLetterHeader());

    }

    /** This method provides login procedure to the web-site */
    private InboxPage loginProcedure() {
        driver.get("https://" + domain);
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.typeUsername(username)
                .typePassword(password)
                .uncheckRememberCheckbox()
                .submitLogin();
    }

    /** This method calling after each test, it quits used WebDriver instance*/
    @After
    public void quit() {
        driver.quit();
    }
}
