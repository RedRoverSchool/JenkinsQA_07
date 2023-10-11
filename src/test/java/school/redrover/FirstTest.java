package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;


public class  FirstTest extends BaseTest {

    @Test
    public void testSearch() throws InterruptedException {

        JenkinsUtils.login(getDriver());

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }
}

