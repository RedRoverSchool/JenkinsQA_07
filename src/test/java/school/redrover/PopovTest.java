package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PopovTest {
    WebDriver driver = new ChromeDriver();
    public String SearchAndPost(String ElementName, String Text){
        WebElement textBox = driver.findElement(By.name(ElementName));
        textBox.sendKeys(Text);
        WebElement submitButton = driver.findElement(By.cssSelector("button"));
        submitButton.click();
        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        driver.navigate().back();
        driver.navigate().refresh();
        return value;

    }
    @BeforeTest
    public void SiteOpen(){
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
    }

    @AfterTest
    public void SiteClose(){
        driver.quit();
    }

    @Test
    public void TestTitle() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Web form");
    }

    @Test
    public void TestInputBox() {
        Assert.assertEquals(SearchAndPost("my-text","Selenium"),"Received!");
    }

    @Test
    public void TestTextArea() {
        Assert.assertEquals(SearchAndPost("my-textarea","Maven"),"Received!");
    }
}

