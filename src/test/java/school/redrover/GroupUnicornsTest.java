package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.util.HashMap;
import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class GroupUnicornsTest extends BaseTest {

    @Test
    public void testUsPsPageOpen() {
        getDriver().get("https://www.usps.com/");

        Assert.assertEquals(getDriver().getTitle(), "Welcome | USPS");
    }

    @Test
    public void testUsPsSendMailPackageOpen() {
        getDriver().get("https://www.usps.com/");

        WebElement send = getDriver().findElement(By.xpath("//a[@id='mail-ship-width']"));
        send.click();

        Assert.assertEquals(getDriver().getTitle(), "Send Mail & Packages | USPS");
    }

    @Test
    public void testSuccessfulLogin() {
        WebDriver driver = getDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        String username = "tomsmith";
        String password = "SuperSecretPassword!";
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("radius")).click();
        String actual = driver.findElement(By.id("flash")).getText();
        assertTrue(actual.contains("You logged into a secure area!"));
    }

    @Test
    public void testLoginAttemptWithInvalidUsername() {
        WebDriver driver = getDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        String username = "tomsmith123";
        String password = "SuperSecretPassword!";
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("radius")).click();
        String actual = driver.findElement(By.id("flash-messages")).getText();
        assertTrue(actual.contains("Your username is invalid!"));
    }

    @Test
    public void testW3School()
    {
        getDriver().get("https://www.w3schools.com/");

        //title
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "W3Schools Online Web Tutorials");

        //H1 heading
        WebElement h1Heading = getDriver().findElement(By.className("learntocodeh1"));
        Assert.assertEquals(h1Heading.getText(), "Learn to Code");

        //H3 heading
        WebElement h3Heading = getDriver().findElement(By.className("learntocodeh3"));
        Assert.assertEquals(h3Heading.getText(), "With the world's largest web developer site.");

        //H4 heading
        WebElement h4Heading = getDriver().findElement(By.className("learntocodeh4"));
        Assert.assertEquals(h4Heading.getText(), "Not Sure Where To Begin?");

        //text box
        WebElement textBox = getDriver().findElement(By.id("search2"));

        //search button
        WebElement searchButton = getDriver().findElement(By.id("learntocode_searchbtn"));
        textBox.sendKeys("java tutorial");
        searchButton.click();

        //title
        title = getDriver().getTitle();
        Assert.assertEquals(title, "Java Tutorial");
    }


    @Test
    public void W3school1test() {
        getDriver().get("https://www.w3schools.com/");

        Assert.assertEquals(getDriver().getTitle(), "W3Schools Online Web Tutorials");

        getDriver().findElement(By.id("search2")).sendKeys("HTML Tutorial");

        getDriver().findElement(By.id("learntocode_searchbtn")).click();

        Assert.assertEquals(getDriver().getTitle(), "HTML Tutorial");
    }

    @Test
    public void testDemoWebShop() {

        String pageTitlePath = "//div[@class='page-title']//h1";
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
        getDriver().get("https://demowebshop.tricentis.com/");

        for (String key : pages.keySet()) {
            getDriver().findElement(By.xpath(pages.get(key))).click();
            pageTitle = getDriver().findElement(By.xpath(pageTitlePath)).getText();
            Assert.assertEquals(pageTitle, key);
        }
    }

    @Ignore
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

    @Ignore
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

    @Ignore
    @Test
    public void verificationSocialIconsGitHub() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().window().maximize();
            driver.get("https://github.com");
            JavascriptExecutor jsExec = (JavascriptExecutor) driver;
            WebElement twitterIcon = driver.findElement(By.xpath("((//footer[@role='contentinfo']//ul)[5]//a)[1]"));
            jsExec.executeScript("arguments[0].scrollIntoView();", twitterIcon);
            twitterIcon.click();
            String url = driver.getCurrentUrl();
            WebElement closeButton = driver.findElement(By.xpath("//*[@aria-label='Close']"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            closeButton.click();
            Assert.assertTrue(url.contains("twitter"));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testComputersMenu() {

        String[] computers = new String[]{"Desktops", "Notebooks", "Accessories"};

        getDriver().get("https://demowebshop.tricentis.com/");
        getDriver().findElement(By.xpath("//ul[@class='top-menu']//a[@href='/computers']")).click();

        List<WebElement> elements = getDriver().findElements(By.className("sub-category-item"));
        boolean actual = true;
        for (int i = 0; i < elements.size(); i++) {
            if (!computers[i].equals(elements.get(i).getText())) {
                actual = false;
                break;
            }
        }
        assertTrue(actual);
    }

    @Ignore
    @Test
    public void verificationSocialIconsGitHub2(){
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().window().maximize();
            driver.get("https://github.com");
            JavascriptExecutor jsExec = (JavascriptExecutor) driver;
            WebElement twitterIcon = driver.findElement(By.xpath("((//footer[@role='contentinfo']//ul)[5]//a)[1]"));
            jsExec.executeScript("arguments[0].scrollIntoView();", twitterIcon);
            List<WebElement> listOfIcons = driver.findElements(By.xpath("(//footer[@role='contentinfo']//ul)[5]//a"));
            listOfIcons.get(1).click();
            String url = driver.getCurrentUrl();
            driver.findElement(By.xpath("//div[@aria-label='Close']")).click();
            Assert.assertTrue(url.contains("face"));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void unsuccessfulLoginDigitalBankTest() {
        WebDriver driver = getDriver();
        driver.get("http://18.118.14.155:8080/bank/login");
        driver.manage().window().maximize();
        WebElement icon = driver.findElement(By.xpath("//div//img[@class = 'align-content']"));
        icon.isDisplayed();

        WebElement loginBtn = driver.findElement(By.id("username"));
        loginBtn.sendKeys("tester1@gmail.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("1234Test");
        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();
        WebElement errorMsg = driver.findElement(By.xpath("//div[contains(@class, 'sufee-alert')]"));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message is displayed");
    }

    @Test
    public void successfulLoginDigitalBankTest() {
        WebDriver driver = getDriver();
        driver.get("http://18.118.14.155:8080/bank/login");
        driver.manage().window().maximize();
        WebElement icon = driver.findElement(By.xpath("//div//img[@class = 'align-content']"));
        icon.isDisplayed();

        WebElement loginBtn = driver.findElement(By.id("username"));
        loginBtn.sendKeys("tester@gmail.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("Test1234");
        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();
        WebElement avatar = driver.findElement(By.xpath("//img[contains(@class, 'user-avatar')]"));
        Assert.assertTrue(avatar.isDisplayed(), "Avatar is displayed");
    }
    @Test
    public void testJenkinsVersion() {
        JenkinsUtils.login(getDriver());

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).getText(),
                "Jenkins 2.414.2");
    }
}