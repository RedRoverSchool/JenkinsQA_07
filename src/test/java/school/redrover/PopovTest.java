package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


@Ignore
public class PopovTest extends BaseTest {
    public String SearchAndPost(String ElementName, String Text){
        WebElement textBox = getDriver().findElement(By.name(ElementName));
        textBox.sendKeys(Text);
        WebElement submitButton = getDriver().findElement(By.cssSelector("button"));
        submitButton.click();
        WebElement message = getDriver().findElement(By.id("message"));
        return message.getText();
    }

    @BeforeMethod
    public void WebsiteOpen() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
    }

    @Test
    public void TestTitle() {
        String title = getDriver().getTitle();
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

