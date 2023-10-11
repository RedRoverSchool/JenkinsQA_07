package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
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
}
