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

public class GroupTestscriptCollaboratoriumTest extends BaseTest {

    @Test
    public void testVersion() {

        getDriver().findElement(By.xpath("//*[@id = 'jenkins']/footer/div/div[2]/button")).click();
        getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();

        WebElement version = getDriver().findElement(By.xpath("//p[@class = 'app-about-version']"));

        Assert.assertEquals(version.getText(), "Version 2.414.2");
    }

}
