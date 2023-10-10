package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupUnderdogsTest {
    WebDriver driver = new ChromeDriver();

    private final String mainPageUrl = "http://www.99-bottles-of-beer.net/";
    String userName = "academic198405@gmail.com";
    String password = "BikeTrekMarlyn4!";
    String wrongPassword = "Sbbhbhbln2";
    String baseUrl = "https://www.trekbikes.com/us/en_US/";

    public void openMainPage() {
        driver.get(mainPageUrl);
    }

    @AfterMethod
    public void after() {
        driver.quit();
    }

    @Test
    public void MainPageTitleTest() {
        openMainPage();
        String title = driver.getTitle();
        Assert.assertEquals(title, "99 Bottles of Beer | Start");
    }

    @Test
    public void tereshenkov99BottlesTitleTest() {
        driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement title = driver.findElement(By.xpath("//*[@id=\"header\"]/h1"));
        String titleValue = title.getText();

        Assert.assertEquals(titleValue, "99 Bottles of Beer");

    }

    @Test
    public void tereshenkov99BottlesLastMenuLinkTestGetAttribute() {
        driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement lastMenuLink = driver.findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));

        String lastMenuLinkValue = lastMenuLink.getAttribute("textContent");
        Assert.assertEquals(lastMenuLinkValue, "Submit new Language");

    }

    @Test
    public void tereshenkov99BottlesLastMenuLinkTestGetText() {
        driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement lastMenuLink = driver.findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));

        String lastMenuLinkValue = lastMenuLink.getText();
        Assert.assertEquals(lastMenuLinkValue, "SUBMIT NEW LANGUAGE");

    }

    @Test
    public void firstMenuTabTextTest() {
        driver.get("http://www.99-bottles-of-beer.net/abc.html");
        String elementName = driver.findElement(By.xpath("//ul[@id='submenu']/li[1]/a")).getText();
        Assert.assertEquals(elementName, "0-9");
    }

    @Test
    public void authorNamesTest() {
        List<String> expectedAuthorNames = Arrays.asList("Oliver Schade", "Gregor Scheithauer", "Stefan Scheler");
        openMainPage();
        driver.findElement(By.xpath("//a[@href='team.html']")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//h3"));
        List<String> authorNames = new ArrayList<>();
        for (WebElement i : elements) {
            authorNames.add(i.getText());
        }
        Assert.assertEquals(authorNames, expectedAuthorNames);
    }

    //text written in lower case and color red
    @Test
    public void maksinTestInactive() {
        driver = new FirefoxDriver();
        driver.get("http://www.99-bottles-of-beer.net/team.html");
        WebElement text = driver.findElement(By.xpath
                ("/html/body/div/div[3]/p[7]/font/b"));
        Assert.assertTrue(text.getText().toLowerCase().equals(text.getText()));
        Assert.assertEquals(text.getCssValue("color"), "rgb(255, 0, 0)");
    }

    @Test
    public void artuomTrack_correct_CredentialTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement login = driver.findElement(By.xpath("//*[@class='mr-1 material-icons md-24']"));
        js.executeScript("arguments[0].scrollIntoView();", login);
        login.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"j_username\"]"));
        emailField.click();
        emailField.sendKeys(userName);
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"j_password\"]"));
        passwordField.click();
        passwordField.sendKeys(password);
        Thread.sleep(1000);

        WebElement button = driver.findElement(By.xpath("(//*[text()='Log in'])[3]"));
        button.click();
        Thread.sleep(1000);


        WebElement isAllNewArrivals = driver.findElement(By.xpath("(//*[@class=\"pdl-heading pdl-heading--xl \"])[1]"));

        String text = isAllNewArrivals.getText();
        Assert.assertEquals(text, "All-new arrivals");


    }

    @Test
    public void artuomTrack_Incorrect_CredentialTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement login = driver.findElement(By.xpath("//*[@class='mr-1 material-icons md-24']"));
        js.executeScript("arguments[0].scrollIntoView();", login);
        login.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"j_username\"]"));
        emailField.click();
        emailField.sendKeys(userName);
        Thread.sleep(2000);

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"j_password\"]"));
        passwordField.click();
        passwordField.sendKeys(wrongPassword);

        WebElement button = driver.findElement(By.xpath("(//*[text()='Log in'])[3]"));
        button.click();
        Thread.sleep(1000);


        WebElement incorrectUser = driver.findElement(By.xpath("//*[text()='Incorrect username or password']"));

        String text = incorrectUser.getText();
        Assert.assertEquals(text, "Incorrect username or password");


    }

    @Test
    public void artuomMarlin4Test() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        WebElement enterButton = driver.findElement(By.xpath("(//*[@class='pdl-icon pdl-icon--size-24'])[1]"));
        Thread.sleep(2000);
        enterButton.click();
        Thread.sleep(3000);

        WebElement mount = driver.findElement(By.xpath("//*[@id=\"expandMountainBikesMainMenu-compact\"]"));
        mount.click();
        Thread.sleep(1000);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement marlin = driver.findElement(By.xpath("(//*[text()='Marlin'])[1]"));
        js.executeScript("arguments[0].scrollIntoView();", marlin);
        marlin.click();
        Thread.sleep(1000);

        WebElement seeTheBikes = driver.findElement(By.xpath("//*[@title=\"SEE THE BIKES\"]"));
        seeTheBikes.click();
        Thread.sleep(2000);

        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        WebElement marlin4 = driver.findElement(By.xpath("//*[text()='Marlin 4 Gen 2']"));
        js.executeScript("arguments[0].scrollIntoView();", marlin4);
        Thread.sleep(2000);

        String bikeName = marlin4.getText();
        Assert.assertEquals(bikeName, "Marlin 4 Gen 2");
    }

    @Test
    public void kristinaNameAuthorSite() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement button = driver.findElement(By.xpath("//*[@id=\"main\"]/p[4]/a[2]"));
        button.click();

        WebElement nameOliver = driver.findElement(By.xpath("//div[@id=\"main\"]/h3[1]"));
        String name1 = nameOliver.getText();
        Assert.assertEquals(name1, "Oliver Schade");

        WebElement nameGregor = driver.findElement(By.xpath("//div[@id=\"main\"]/h3[2]"));
        String name2 = nameGregor.getText();
        Assert.assertEquals(name2, "Gregor Scheithauer");

        WebElement nameStefan = driver.findElement(By.xpath("//div[@id=\"main\"]/h3[3]"));
        String name3 = nameStefan.getText();
        Assert.assertEquals(name3, "Stefan Scheler");

        driver.quit();
    }

    @Test
    public void kristinaTopLists() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement title = driver.findElement(By.xpath("//*[@id=\"menu\"]/li[4]/a[@href=\"/toplist.html\"]"));
        title.click();

        WebElement language = driver.findElement(By.xpath("//*[@id=\"category\"]/tbody/tr[2]/td[2]/a"));
        String title1 = language.getText();
        Assert.assertEquals(title1, "Malbolge (real loop version)");

        driver.quit();
    }

    @Test
    public void testBrowseLanguagesKotlin() {
        driver = new ChromeDriver();
        driver.get(mainPageUrl);

        WebElement browseLanguagesBtn = driver.findElement(By.xpath("//li/a[text()='Browse Languages']"));
        browseLanguagesBtn.click();

        WebElement letterLink = driver.findElement(By.xpath("//li/a[text()='K']"));
        letterLink.click();

        WebElement languageLink = driver.findElement(By.xpath("//a[contains(@href, 2901)]"));
        languageLink.click();

        WebElement languagePageHeader = driver.findElement(By.xpath("//div[@id='main']/h2"));
        String pageHeader = languagePageHeader.getText();

        Assert.assertEquals(pageHeader, "Language Kotlin");
    }

    @Test
    public void testSearchLanguages() {
        final String partOfWordToSearch = "kot";

        driver = new ChromeDriver();
        driver.get(mainPageUrl);

        WebElement searchLanguagesBtn = driver.findElement(By.xpath("//li/a[text()='Search Languages']"));
        searchLanguagesBtn.click();

        WebElement searchField = driver.findElement(By.xpath("//input[@name='search']"));
        searchField.sendKeys(partOfWordToSearch);

        WebElement goBtn = driver.findElement(By.xpath("//input[@name='submitsearch']"));
        goBtn.click();

        List<WebElement> searchResult = driver.findElements(By.xpath("//td/a[contains(@href,'language')]"));

        for (WebElement element : searchResult) {
            Assert.assertTrue(element.getText().toLowerCase().contains(partOfWordToSearch));
            Assert.assertEquals(element.getTagName(), "a");
        }
    }

    @Test
    public void testRailiaImportantNoticeMarkup() {
        driver = new ChromeDriver();
        openMainPage();
        driver.findElement(By.linkText("SUBMIT NEW LANGUAGE")).click();


        List<WebElement> listItems = driver.findElements(By.xpath("//*[@id=\"main\"]/ul/li/span"));
        Assert.assertFalse(listItems.isEmpty(), "We should have at least one list item with bold text");

        for (WebElement el : listItems) {
            String notificationText = el.getText();
            if (notificationText.equalsIgnoreCase("important:")) {
                String backgroundColor = el.getCssValue("background-color");
                String textColor = el.getCssValue("color");
                Assert.assertEquals(backgroundColor, "rgba(255, 0, 0, 1)");
                Assert.assertEquals(textColor, "rgba(255, 255, 255, 1)");
                Assert.assertEquals(notificationText, notificationText.toUpperCase());
            }

        }
    }

    public void testNamesOfCreatorsOfSite() {
        List<String> teamMembers = Arrays.asList("Oliver Schade", "Gregor Scheithauer", "Stefan Scheler");

        driver = new ChromeDriver();
        driver.get(mainPageUrl);

        WebElement teamLink = driver.findElement(By.xpath("//a[text()='Team']"));
        teamLink.click();

        List<WebElement> creators = driver.findElements(By.xpath("//h3"));
        List<String> namesOfCreators = new ArrayList<>();
        for (WebElement element : creators) {
            namesOfCreators.add(element.getText());
        }

        Assert.assertEquals(namesOfCreators, teamMembers);

    }

    @Test
    public void testSubmitLanguage() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get(mainPageUrl);

        WebElement clickSub = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id=\"menu\"]/li[6]/a")));
        clickSub.click();

        WebElement header = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id=\"submenu\"]/li/a")));
        String actualHeader = header.getText();
        Assert.assertEquals(actualHeader, "Submit New Language");

        driver.quit();
    }

    @Test
    public void testTitle() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get(mainPageUrl);

        WebElement title = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[@id=\"header\"]/h1")));
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle, "99 Bottles of Beer");

        driver.quit();
    }
    @Test
    public void testChromeEnglishBooksFind() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");
        WebElement buttonClick = driver.findElement(By.xpath("//*/ul/li[3]/a"));
        buttonClick.click();
        WebElement searchType = driver.findElement(By.name("search"));
        searchType.sendKeys("English");
        WebElement submitClick = driver.findElement(By.name("submitsearch"));
        submitClick.click();
        WebElement linkName = driver.findElement(By.linkText("English"));
        String toAssert = linkName.getText();
        Assert.assertEquals(toAssert, "English");
        driver.quit();
    }






}
