package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;


public class GroupAqaLearnTest extends BaseTest {
    @Test
    public void testFirstJenkins() {

        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.xpath("//*[@id='tasks']//span//span")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='h3']")).getText(),
                "Enter an item name");

    }
    @Ignore
    @Test
    public void testNameOfTitle() {

        String url = "https://openweathermap.org/";
        String expectedResult = "OpenWeather";

        WebDriver driver = new ChromeDriver();

        driver.get(url);

        WebElement searchTitle = driver.findElement
                (
                        By.xpath("//div/h1")
                );
        String actualResult = searchTitle.getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }
}
