package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Ignore
public class GroupUnderdogsTest extends BaseTest {

    // the next line will be deleted as soon as all methods within the class are refactored and driver is changed to getDriver()
    WebDriver driver;

    private static final String MAIN_PAGE_URL_99BOTTLES = "http://www.99-bottles-of-beer.net/";
    private static final String ABC_PAGE_URL_99BOTTLES = "http://www.99-bottles-of-beer.net/abc.html";
    private static final String SUBMIT_PAGE_URL_99BOTTLES = "http://www.99-bottles-of-beer.net/submitnewlanguage.html";


    @Test
    public void test99BottlesTitleTest_tereshenkov29() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement title = getDriver().findElement(By.xpath("//*[@id=\"header\"]/h1"));
        String titleValue = title.getText();
        assertEquals(titleValue, "99 Bottles of Beer");
    }

    @Test
    public void test99BottlesLastMenuLinkGetAttribute_tereshenkov29() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement lastMenuLink = getDriver().findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));
        String lastMenuLinkValue = lastMenuLink.getAttribute("textContent");
        assertEquals(lastMenuLinkValue, "Submit new Language");
    }

    @Test
    public void test99BottlesLastMenuLinkGetText_tereshenkov29() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement lastMenuLink = getDriver().findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));
        String lastMenuLinkValue = lastMenuLink.getText();
        assertEquals(lastMenuLinkValue, "SUBMIT NEW LANGUAGE");
    }

    @Test
    public void testJenkinsVersionInFooter_tereshenkov29() {

        WebElement JenkinsVersionInFooter = getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"));

        String JenkinsVersionInFooterValue = JenkinsVersionInFooter.getText();
        assertEquals(JenkinsVersionInFooterValue, "Jenkins 2.414.2");
    }

    @Test
    public void test99BottlesMainPageTitle_Olgla() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        String title = getDriver().getTitle();
        assertEquals(title, "99 Bottles of Beer | Start");
    }

    @Test
    public void testFirstMenuTabText_Olgla() {
        getDriver().get("http://www.99-bottles-of-beer.net/abc.html");
        String elementName = getDriver().findElement(By.xpath("//ul[@id='submenu']/li[1]")).getText();
        assertEquals(elementName, "0-9");
    }

    @Test
    public void testAuthorNames_Olgla() {
        List<String> expectedAuthorNames = Arrays.asList("Oliver Schade", "Gregor Scheithauer", "Stefan Scheler");
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        getDriver().findElement(By.xpath("//a[@href='team.html']")).click();
        List<WebElement> elements = getDriver().findElements(By.xpath("//h3"));
        List<String> authorNames = new ArrayList<>();
        for (WebElement i : elements) {
            authorNames.add(i.getText());
        }
        assertEquals(authorNames, expectedAuthorNames);
    }


    @Test
    public void testJenkinsLogOut_maksin() {

        getDriver().findElement(By.xpath("//*[@id='page-header']/div[3]/a[2]")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath
                        ("//*[@id='main-panel']/div/h1")).getText(),
                "Sign in to Jenkins");
    }

    @Test
    public void testVerifyIconSize() {

        final String projectName = "test";
        final String background = "rgba(175, 175, 207, 0.176)";

        getDriver().findElement(By.xpath("(//a[@href='/view/all/newJob'])[1]")).click();
        getDriver().findElement(By.xpath("(//input[@id='name'])[1]")).sendKeys(projectName);
        getDriver().findElement(By.xpath("(//span[normalize-space()='Folder'])[1]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();
        getDriver().findElement(By.xpath("(//a[normalize-space()='Dashboard'])[1]")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span")).getText(), projectName);

        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div[2]/div/div[1]/ol/li[2]")).click(); // M

        getDriver().findElement(By.linkText("People")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[3]/div[1]/ol[1]/li[2]"))
                .getCssValue("background-color"), background);

        getDriver().findElement(By.linkText("Build History")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[6]/div[1]/ol[1]/li[2]"))
                .getCssValue("background-color"), background);

        getDriver().findElement(By.linkText("My Views")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[2]/div[2]/div[1]/div[1]/ol[1]/li[2]"))
                .getCssValue("background-color"), background);

    }

    public void closePromoDrawerIfVisible() {
        WebElement promoDrawer = getDriver().findElement(By.className("promo-drawer__drawer"));
        String drawerStatus = promoDrawer.getAttribute("data-drawer-status");

        if (drawerStatus != null && drawerStatus.equals("open")) {
            List<WebElement> promoDrawerHeaders = promoDrawer.findElements(By.className("promo-drawer__header"));
            if (!promoDrawerHeaders.isEmpty()) {
                promoDrawerHeaders.get(0).click();
            }
        }
    }

    @Test
    public void testIdAdminArtuom() throws InterruptedException {
        getDriver().findElement(By.xpath("//a[@href='/user/admin']")).click();
        getDriver().findElement(By.xpath("(//span[@class='task-link-wrapper '])[4]")).click();

        WebElement button = getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();

        WebElement UserIdAdm = getDriver().findElement(By.xpath("//div[@id='description']/following-sibling::div"));
        String actNameOfUser = UserIdAdm.getText();
        String expRes = "Jenkins User ID: admin";
        assertEquals(actNameOfUser, expRes);

    }

    @Test
    public void testKristinaNameAuthorSite() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement button = getDriver().findElement(By.xpath("//*[@id='main']/p[4]/a[2]"));
        button.click();

        WebElement nameOliver = getDriver().findElement(By.xpath("//div[@id='main']/h3[1]"));
        String name1 = nameOliver.getText();
        assertEquals(name1, "Oliver Schade");

        WebElement nameGregor = getDriver().findElement(By.xpath("//div[@id='main']/h3[2]"));
        String name2 = nameGregor.getText();
        assertEquals(name2, "Gregor Scheithauer");

        WebElement nameStefan = getDriver().findElement(By.xpath("//div[@id='main']/h3[3]"));
        String name3 = nameStefan.getText();
        assertEquals(name3, "Stefan Scheler");
    }

    @Test
    public void testKristinaNameMenu() {
        getDriver().get(ABC_PAGE_URL_99BOTTLES);
        WebElement title = getDriver().findElement(By.xpath("//*[@id='submenu']/li[1]/a"));
        String value = title.getText();
        Assert.assertEquals(value, "0-9");
    }

    @Test
    public void testKristinaTopLists() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        WebElement title = getDriver().findElement(By.xpath("//*[@id='menu']/li[4]/a[@href='/toplist.html']"));
        title.click();
        WebElement language = getDriver().findElement(By.xpath("//*[@id='category']/tbody/tr[2]/td[2]/a"));
        String title1 = language.getText();
        assertEquals(title1, "Malbolge (real loop version)");
    }

    @Test
    public void testKristinaSubmitLanguage() {
        getDriver().get(SUBMIT_PAGE_URL_99BOTTLES);

        WebElement button = getDriver().findElement(By.xpath("//*[@id='addlanguage']/p/input[8]"));
        button.click();

        WebElement alert = getDriver().findElement(By.xpath("//*[@id='main']/p"));
        String title = alert.getText();
        Assert.assertEquals(title, "Error: Precondition failed - Incomplete Input.");
    }

    @Test
    public void testImportantNoticeMarkupRailia() {

        getDriver().get(MAIN_PAGE_URL_99BOTTLES);
        getDriver().findElement(By.linkText("SUBMIT NEW LANGUAGE")).click();

        List<WebElement> listItems = getDriver().findElements(By.xpath("//*[@id='main']/ul/li/span"));
        Assert.assertFalse(listItems.isEmpty(), "We should have at least one list item with bold text");

        for (WebElement el : listItems) {
            String notificationText = el.getText();
            if (notificationText.equalsIgnoreCase("important:")) {
                String backgroundColor = el.getCssValue("background-color");
                String textColor = el.getCssValue("color");
                assertEquals(backgroundColor, "rgba(255, 0, 0, 1)");
                assertEquals(textColor, "rgba(255, 255, 255, 1)");
                assertEquals(notificationText, notificationText.toUpperCase());
            }
        }
    }

    @Test
    public void testSubmitLanguage() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);

        WebElement clickSub = getDriver().findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));
        clickSub.click();

        WebElement header = getDriver().findElement(By.xpath("//*[@id=\"submenu\"]/li/a"));
        String actualHeader = header.getText();

        assertEquals(actualHeader, "Submit New Language");
    }

    @Test
    public void testTitle() {
        getDriver().get(MAIN_PAGE_URL_99BOTTLES);

        WebElement title = getDriver().findElement(By.xpath("//*[@id=\"header\"]/h1"));
        String actualTitle = title.getText();

        assertEquals(actualTitle, "99 Bottles of Beer");
    }

    @Test
    public void testYuliafaReddit() {

        getDriver().get("https://www.reddit.com/?feed=home");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Reddit - Dive into anything");

        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("login-username")).sendKeys("test@mail.ru");
        getDriver().findElement(By.id("login-password")).sendKeys("12Qwerty");

    }

    @Test
    public void testTask4Kateryna1979() throws InterruptedException {

        getDriver().get(ABC_PAGE_URL_99BOTTLES);

        String menu = getDriver().findElement(By.xpath
                ("//body/div[@id='wrap']/div[@id='navigation']/ul[@id='submenu']/li/a[@href='0.html']")).getText();

        String expectedResult = "0-9";

        Assert.assertEquals(menu, expectedResult);

    }

    @Ignore
    @Test
    public void testCreateNewJob() throws InterruptedException {

        WebElement createJobButton = getDriver().findElement(By.xpath("//a [@href='newJob']"));
        createJobButton.click();

        WebElement inputField = getDriver().findElement(By.xpath("//input[@name = 'name']"));
        inputField.click();
        inputField.sendKeys("My Job");

        WebElement projectType = getDriver().findElement(By.xpath("//div[contains(text(),'This is the central feature of Jenkins. Jenkins wi')]"));
        projectType.click();

        WebElement okButton = getDriver().findElement(By.xpath("//button[@id='ok-button']"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[contains(text(),'Save')]"));
        saveButton.click();

        WebElement title = getDriver().findElement(By.xpath("//*[@class = 'job-index-headline page-headline']"));
        String value = title.getText();
        Assert.assertEquals(value, "Project My Job");

    }

    @Ignore
    @Test
    public void testDescription() {

        WebElement addDescriptionButton = getDriver().findElement(By.xpath("//a[@id='description-link']"));
        addDescriptionButton.click();

        WebElement descriptionTextField = getDriver().findElement(By.xpath("//*[@name = 'description']"));
        descriptionTextField.click();
        descriptionTextField.sendKeys("Test Description");

        WebElement saveButton = getDriver().findElement(By.xpath("//*[@name = 'Submit']"));
        saveButton.click();

        WebElement title = getDriver().findElement(By.xpath("//div[contains(text(),'Test Description')]"));
        String value = title.getText();
        Assert.assertEquals(value, "Test Description");

    }

    @Test
    public void testSearchSeleniumOB() {
        getDriver().get("https://www.selenium.dev");
        String title = getDriver().getTitle();

        Assert.assertEquals(title, "Selenium");
    }
}
