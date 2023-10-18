package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class GroupJavaAutomationTest extends BaseTest {


    @Test
    public void testJenkinsHomePageAndJenkinsVersion()  {

        String title = getDriver().getTitle();
        Assert.assertEquals(title,"Dashboard [Jenkins]");

        WebElement versionJenkinsButton = getDriver().findElement
        (By.xpath("//button[@type='button']"));
        String versionJenkins = versionJenkinsButton.getText();
        Assert.assertEquals(versionJenkins,"Jenkins 2.414.2");
    }
}

