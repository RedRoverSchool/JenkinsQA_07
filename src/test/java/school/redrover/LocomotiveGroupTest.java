package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Ignore
public class LocomotiveGroupTest {
    @Test
    public void demoqaTextBoxTest() {
        String fullName = "Tom Jonson";
        String email = "mail@mail.com";

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");

        String pageTitleText = driver.findElement(By.className("main-header")).getText();
        Assert.assertEquals(pageTitleText, "Text Box");

        WebElement fullNameTextBox = driver.findElement(By.cssSelector("#userName"));
        fullNameTextBox.sendKeys(fullName);

        WebElement emailTextBox = driver.findElement(By.id("userEmail"));
        emailTextBox.sendKeys(email);

        WebElement submitButton = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        String actualFullName = driver
                .findElement(By.id("name"))
                .getText();
        Assert.assertEquals(actualFullName, "Name:" + fullName);

        String actualEmail = driver
                .findElement(By.xpath("//*[@id=\"email\"]"))
                .getText();

        Assert.assertEquals(actualEmail, "Email:" + email);

        driver.quit();
    }

    @Test
    public void testLink() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            String linkExpected = "https://demoqa.com/";

            driver.get("https://demoqa.com/links");
            String originalWindow = driver.getWindowHandle();
            WebElement link = driver.findElement(By.xpath("//*[@id=\"simpleLink\"]"));
            String linkActual = link.getAttribute("href");

            Assert.assertEquals(linkActual, linkExpected);

            link.click();

            Thread.sleep(1000);

            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class=\"banner-image\"]")).isDisplayed();

        } finally {
            driver.quit();
        }
    }

    @Test
    public void checkRadioButton() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://demoqa.com/radio-button");


        selectRadioButton(driver, "Yes");
        WebElement textRadioButton = driver.findElement(By.xpath("//p[@class='mt-3']"));
        Assert.assertEquals(textRadioButton.getText(), "You have selected Yes");

        Thread.sleep(3000);

        selectRadioButton(driver, "Impressive");
        Assert.assertEquals(textRadioButton.getText(), "You have selected Impressive");

        Thread.sleep(3000);

        driver.close();
    }

    public static void selectRadioButton(WebDriver driver, String value) {
        WebElement RadioButton = driver.findElement(By.xpath("//label[normalize-space()='" + value + "']"));
        RadioButton.click();
    }

    @Test

    public void DashboardButtonTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://refero.design/");
            WebElement bentoBox = driver.findElement(By.xpath("//div[@class='xK9VF'][contains(text(),'Dashboard')]"));
            Actions actions = new Actions(driver);
            actions.moveToElement(bentoBox);
            bentoBox.click();

            Assert.assertEquals(driver.getCurrentUrl(), "https://refero.design/search?page_types[id][]=28&order=popular");
        } finally {
            driver.quit();
        }
    }

    @Test

    public void simpleSearchTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();

            driver.get("https://999.md/");

            WebElement categoryButton = driver.findElement(By.xpath("//button[@id='js-categories-toggle']"));
            categoryButton.click();

            WebElement autoButton = driver.findElement(By.xpath("//a[contains(text(),'Autoturisme')]"));
            autoButton.click();

            Thread.sleep(1000);

            WebElement masini = driver.findElement(By.xpath("//h1[contains(text(),'Mașini')]"));
            String value = masini.getText();
            Assert.assertEquals(value, "Mașini");

            WebElement searchInput = driver.findElement(By.xpath("//input[@id='js-search-input']"));
            searchInput.click();

            WebElement input = driver.findElement(By.xpath("//input[@id='js-search-input']"));
            input.sendKeys("audi a6");

            WebElement searchButton = driver.findElement(By.xpath("//span[contains(.,'Caută')]"));
            searchButton.click();

            WebElement title = driver.findElement(By.xpath("//span[contains(text(),'Rezultatele căutării')]"));
            String value1 = title.getText();
            Assert.assertEquals(value1,"Rezultatele căutării \"audi a6\" (2 607 anunţuri)");

            WebElement searchInput1 = driver.findElement(By.xpath("//input[@id='js-search-input']"));
            searchInput1.click();

            Thread.sleep(3000);

            WebElement dropDown= driver.findElement(By.xpath("//html[1]/body[1]/div[3]/header[1]/div[2]/div[1]/form[1]/fieldset[1]/span[1]/div[1]/div[1]/div[1]"));
            dropDown.click();

            WebElement title1 = driver.findElement(By.xpath("//span[contains(text(),'Autoturisme')]"));
            String value2 = title1.getText();
            Assert.assertEquals(value2,"Autoturisme (600)");

        driver.quit();
        //test
    }
}