package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GroupUnicornsTest {

    @Test
    public void usPsPageOpenTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.usps.com/");
        String title = driver.getTitle();
        assertEquals("Welcome | USPS", title);
        driver.quit();
    }

    @Test
    public void usPsSendMailPackageTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.usps.com/");
        WebElement send = driver.findElement(By.xpath("//a[@id='mail-ship-width']"));
        send.click();
        String sendTitle = driver.getTitle();
        assertEquals("Send Mail & Packages | USPS", sendTitle);
        driver.quit();
    }

    @Test
    public void testSuccessfulLogin() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        String username = "tomsmith";
        String password = "SuperSecretPassword!";
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("radius")).click();
        String actual = driver.findElement(By.id("flash")).getText();
        assertTrue(actual.contains("You logged into a secure area!"));
        driver.quit();
    }

    @Test
    public void testLoginAttemptWithInvalidUsername() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        String username = "tomsmith123";
        String password = "SuperSecretPassword!";
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("radius")).click();
        String actual = driver.findElement(By.id("flash-messages")).getText();
        assertTrue(actual.contains("Your username is invalid!"));
        driver.quit();
    }

    @Test
    public void w3SchoolTest() {
        WebDriver wd = new ChromeDriver();
        try {
            wd.get("https://www.w3schools.com/");

            //title
            String title = wd.getTitle();
            Assert.assertEquals(title, "W3Schools Online Web Tutorials");

            //H1 heading
            WebElement h1Heading = wd.findElement(By.className("learntocodeh1"));
            Assert.assertEquals(h1Heading.getText(), "Learn to Code");

            //H3 heading
            WebElement h3Heading = wd.findElement(By.className("learntocodeh3"));
            Assert.assertEquals(h3Heading.getText(), "With the world's largest web developer site.");

            //H4 heading
            WebElement h4Heading = wd.findElement(By.className("learntocodeh4"));
            Assert.assertEquals(h4Heading.getText(), "Not Sure Where To Begin?");

            //text box
            WebElement textBox = wd.findElement(By.id("search2"));

            //search button
            WebElement searchButton = wd.findElement(By.id("learntocode_searchbtn"));
            textBox.sendKeys("java tutorial");
            searchButton.click();

            //title
            title = wd.getTitle();
            Assert.assertEquals(title, "Java Tutorial");
        } finally {
            wd.quit();
        }
    }

    @Test
    public void testGeico() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.geico.com/");

            WebElement title = driver.findElement(By.xpath("//div/h1[@id ='section1heading']"));
            title.isDisplayed();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));

            WebElement zipCode = driver.findElement(By.xpath("//div/input[@id ='ssp-service-zip']"));
            zipCode.sendKeys("11111");

            WebElement submit = driver.findElement(By.xpath("//input[@class ='btn btn--secondary']"));
            submit.click();

            WebElement message = driver.findElement(By.xpath("//div/p[@class = 'text-message']"));
            message.isDisplayed();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testSearch() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.w3schools.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "W3Schools Online Web Tutorials");
        WebElement textBox = driver.findElement(By.id("search2"));
        WebElement submitButton = driver.findElement(By.id("learntocode_searchbtn"));
        textBox.sendKeys("HTML Tutorial");
        submitButton.click();
        WebElement message = driver.findElement(By.className("color_h1"));
        String value = message.getText();
        Assert.assertEquals(value, "Tutorial");

        driver.quit();
    }

    @Test
    public void demoWebShopTest() {
        WebDriver driver = new ChromeDriver();

        String pageTitlePath = "//div[@class='page-title' ]//h1";

        String basePath = "//ul[@class='top-menu']//a[@href='/";

        HashMap<String, String> pages = new HashMap<>();
        pages.put("Books", basePath + "books']");
        pages.put("Computers", basePath + "computers']");
        pages.put("Electronics", basePath + "electronics']");
        pages.put("Apparel & Shoes", basePath + "apparel-shoes']");
        pages.put("Digital downloads", basePath + "digital-downloads']");
        pages.put("Jewelry", basePath + "jewelry']");
        pages.put("Gift Cards", basePath + "gift-cards']");

        String pageTitle;

        try {
            driver.get("https://demowebshop.tricentis.com/");

            for (String key : pages.keySet()) {
                driver.findElement(By.xpath(pages.get(key))).click();
                pageTitle = driver.findElement(By.xpath(pageTitlePath)).getText();
                Assert.assertEquals(pageTitle, key);
            }

        } finally {
            driver.quit();
        }
    }

    @Test
    public void searchVerificationGitHub() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().window().maximize();
            driver.get("https://github.com");
            WebElement searchBox = driver.findElement(By.xpath("//span[@class=\"flex-1\"]"));
            searchBox.click();
            WebElement inputButton = driver.findElement(By.xpath("//*[@class='QueryBuilder-InputWrapper']/input"));
            inputButton.sendKeys("selenium" + Keys.ENTER);
            List<WebElement> listOfResults = driver.findElements(By.xpath("//span[starts-with(@class, 'Text-sc-17v1xeu-0 qaOIC search-match')]"));
            int expectedSize = 10;
            int actualSize = listOfResults.size();
            Assert.assertEquals(actualSize, expectedSize);
        } finally {
            driver.quit();
        }
    }
    @Test
    public void testTradingView() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        String url = "https://www.tradingview.com/chart/";
        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
            WebElement tickerNameActual = driver.findElement(By.xpath("(//div[@class = 'js-button-text text-GwQQdU8S text-cq__ntSC'])[3]"));
            Assert.assertEquals(tickerNameActual.getText(), "AAPL");

            driver.findElement(By.xpath("//button[@id = 'header-toolbar-symbol-search']")).click();
            WebElement searchTable = driver.findElement(By.xpath("//input[@class = 'search-ZXzPWcCf upperCase-ZXzPWcCf input-qm7Rg5MB']"));
            searchTable.clear();
            searchTable.sendKeys("SPX");
            searchTable.sendKeys(Keys.ENTER);
            Thread.sleep(500);
            WebElement newTickerNameActual = driver.findElement(By.xpath("(//div[@class = 'js-button-text text-GwQQdU8S text-cq__ntSC'])[3]"));
            Assert.assertEquals(newTickerNameActual.getText(), "SPX");
        } finally {
            driver.quit();
        }
    }
    @Test
    public void verificationSocialIconsGitHub() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().window().maximize();
            driver.get("https://github.com");
            JavascriptExecutor jsExec=(JavascriptExecutor) driver;
            WebElement twitterIcon= driver.findElement(By.xpath("((//footer[@role='contentinfo']//ul)[5]//a)[1]"));
            jsExec.executeScript("arguments[0].scrollIntoView();", twitterIcon);
            String mainWindow= driver.getWindowHandle();
            twitterIcon.click();
            String url = driver.getCurrentUrl();
            WebElement closeButton=driver.findElement(By.xpath("//*[@aria-label='Close']"));
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            closeButton.click();
            Assert.assertTrue(url.contains("twitter"));
        } finally {
            driver.quit();
        }
    }
}