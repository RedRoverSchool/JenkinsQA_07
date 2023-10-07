package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupSevenTest {
    @Test
    public void kylieTitleTest() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://kyliecosmetics.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Kylie Cosmetics by Kylie Jenner | Kylie Skin | Kylie Baby");

        driver.quit();
    }

    @Test
    public void searchTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("https://kyliecosmetics.com/collections/kylie-cosmetics");

            Thread.sleep(1000);

            WebElement searchButton = driver.findElement(By.xpath("//a[@title='Search']"));
            searchButton.click();

            WebElement searchInput = driver.findElement(By.xpath("//input[@id='SearchForm-Header-Query']"));
            searchInput.sendKeys("lip kit");

            WebElement searchButtonNext = driver.findElement(By.xpath("//button[@id='SearchForm-Header-Submit']"));
            searchButtonNext.click();

            WebElement title = driver.findElement(By.xpath("//h1[normalize-space()='Search']"));
            String value = title.getText();
            Assert.assertEquals(value, "SEARCH");

        } finally {
            driver.quit();
        }
    }

    @Test
    public void TestBddSearch() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            driver.get("https://duckduckgo.com/");
            WebElement searchBox = driver.findElement(By.xpath("//input[@class = 'searchbox_input__bEGm3']"));
            searchBox.sendKeys("bdd");
            WebElement searchButton = driver.findElement(By.xpath("//button[@class = 'searchbox_searchButton__F5Bwq iconButton_button__6x_9C']"));
            searchButton.click();
            WebElement searchResult = driver.findElement(By.xpath("//h2[@class = 'Ee2e63EzQ9F3xq9wsGDY']"));
            String resultText = searchResult.getText();
            Assert.assertTrue(resultText.contains("Behavior-driven development"));
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testSearch() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://elitetransit.com/");

            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
            WebElement buttonContact = driver.findElement(By.xpath("//ul[@id='top-menu']//a[normalize-space()='Contact']"));
            buttonContact.click();
            String title = driver.getTitle();

            Assert.assertEquals(title, "Contact | ELITE Transit Solutions");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testTextInput() {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(800));

            WebElement input = driver.findElement(By.id("my-text-id"));
            input.click();
            input.sendKeys("Selenium");

            WebElement submit = driver.findElement(By.tagName("button")); ////button[@type='submit']
            submit.submit();
            WebElement message = driver.findElement(By.id("message"));
            Assert.assertEquals(message.getText(), "Received!");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testPage() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://megagroup.by/");
        driver.manage().window().maximize();
        Thread.sleep(1500);

        WebElement price = driver.findElement(
                By.xpath("//nav[@class='mp-header__nav']/a[@href='/price']"));
        price.click();

        Thread.sleep(1500);
        WebElement header = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(header.getText(), "Стоимость сайтов");
        driver.quit();
    }
}
