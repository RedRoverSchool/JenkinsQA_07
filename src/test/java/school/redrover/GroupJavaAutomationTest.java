package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.io.File;
import java.net.URI;
import java.time.Duration;
import org.testng.asserts.SoftAssert;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import static org.testng.Assert.assertEquals;


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

