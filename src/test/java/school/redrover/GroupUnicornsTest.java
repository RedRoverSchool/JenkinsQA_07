package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.time.Duration;
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
}