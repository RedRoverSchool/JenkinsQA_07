package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;


import java.util.concurrent.TimeUnit;


public class LocomotiveGroupTest extends BaseTest {
    @Test
    public void testDemoqaTextBox() {
        String fullName = "Tom Jonson";
        String email = "mail@mail.com";

        getDriver().get("https://demoqa.com/text-box");

        String pageTitleText = getDriver().findElement(By.className("main-header")).getText();
        Assert.assertEquals(pageTitleText, "Text Box");

        WebElement fullNameTextBox = getDriver().findElement(By.cssSelector("#userName"));
        fullNameTextBox.sendKeys(fullName);

        WebElement emailTextBox = getDriver().findElement(By.id("userEmail"));
        emailTextBox.sendKeys(email);

        WebElement submitButton = getDriver().findElement(By.id("submit"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        String actualFullName = getDriver()
                .findElement(By.id("name"))
                .getText();
        Assert.assertEquals(actualFullName, "Name:" + fullName);

        String actualEmail = getDriver()
                .findElement(By.xpath("//*[@id=\"email\"]"))
                .getText();

        Assert.assertEquals(actualEmail, "Email:" + email);
    }

    @Test
    public void testLink() throws InterruptedException {
        String linkExpected = "https://demoqa.com/";

        getDriver().get("https://demoqa.com/links");
        String originalWindow = getDriver().getWindowHandle();
        WebElement link = getDriver().findElement(By.xpath("//*[@id=\"simpleLink\"]"));
        String linkActual = link.getAttribute("href");

        Assert.assertEquals(linkActual, linkExpected);

        link.click();

        Thread.sleep(1000);

        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }

        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//*[@class=\"banner-image\"]")).isDisplayed();
    }

    @Ignore
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

    @Ignore
    public static void selectRadioButton(WebDriver driver, String value) {
        WebElement RadioButton = driver.findElement(By.xpath("//label[normalize-space()='" + value + "']"));
        RadioButton.click();
    }

    @Ignore
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

      @Ignore
      @Test
    public void yandexSearchBarTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        String url = "https://ya.ru/";
        try{
            driver.get(url);
            WebElement searchBar = driver.findElement(By.xpath("//div[@class='search3__input-wrapper']/input"));
            WebElement searchButton = driver.findElement(By.xpath("//button[@class='search3__button mini-suggest__button']"));
            searchBar.click();
            searchBar.sendKeys("Ответ на главный вопрос жизни");
            searchButton.click();
            WebElement searchText = driver.findElement(By.xpath("//div[text()='Ответ на главный вопрос жизни, вселенной и всего такого']"));
            Assert.assertTrue(searchText.isDisplayed());
        }finally {
            driver.quit();
        }
      }

    @Ignore
    @Test
    public void skateSiteHeaderTest() throws InterruptedException {
        // этот  комметарий  добавлен  для  пуша    гита,   ибо   не пушится и не ньюреквестится
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.skate.net/");

        WebElement title = driver.findElement(By.className("header_text"));
        String value = title.getText();
        Assert.assertEquals(value, "skates, skate gear, ice skates and more");

        Thread.sleep(2000);
        driver.quit();
    }

}
