package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;


@Ignore
public class MariaPracticeTest extends BaseTest {

    public static final String PAGESELENIUM = "https://www.selenium.dev/selenium/web/web-form.html";

    @Test
    public void SearchTest() {

        getDriver().get(PAGESELENIUM);
        String title = getDriver().getTitle();

        WebElement textBox = getDriver().findElement(By.name("my-text"));
        textBox.sendKeys("Selenium");

        WebElement submitButton = getDriver().findElement(By.cssSelector("button"));
        submitButton.click();

        WebElement message = getDriver().findElement(By.id("message"));

        Assert.assertEquals("Web form", title);
        Assert.assertEquals("Received!", message.getText());
    }

    @Test
    public void createUserTest() {

        getDriver().get(PAGESELENIUM);

        WebElement textInput = getDriver().findElement(By.xpath("//input[@name='my-text']"));
        textInput.sendKeys("Maria Dymsha");

        WebElement password = getDriver().findElement(By.xpath("//input[@name='my-password']"));
        password.sendKeys("12345");

        WebElement textarea = getDriver().findElement(By.xpath("//textarea[@name='my-textarea']"));
        textarea.sendKeys("Good morning");

        WebElement clickSubmit = getDriver().findElement(By.xpath("//button[@class='btn btn-outline-primary mt-3']"));
        clickSubmit.click();

        WebElement textFormSubmitted = getDriver().findElement(By.xpath("//h1[@class='display-6']"));

        Assert.assertEquals(textFormSubmitted.getText(), "Form submitted");
    }
    @Ignore
    @Test
    public void linkReturnToIndexTest() {

        getDriver().get(PAGESELENIUM);

        WebElement clickSubmit = getDriver().findElement(By.xpath("//a[@href='./index.html']"));
        clickSubmit.click();

        WebElement clickDisabledElement = getDriver().findElement(By.xpath("//a[@href='click_tests/disabled_element.html']"));
        clickDisabledElement.click();

        WebElement textSeeBelow = getDriver().findElement(By.xpath("//*[text()='See below']"));

        Assert.assertEquals(textSeeBelow.getText(), "See below");
    }

    @Test
    public void WelcomeToJenkinsTest() {

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[text()='Dashboard']")).getText(),
                "Dashboard");
    }
}