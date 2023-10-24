package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AbramovTest extends BaseTest {

    @Test
    public void testNewFirstJenkinsFreestyleProject() {
        WebElement newJob = getDriver().findElement(By.cssSelector("a[href='newJob']"));
        newJob.click();

        WebElement inputJobName = getDriver().findElement(By.cssSelector(".jenkins-input"));
        inputJobName.sendKeys("NewTestJob01");

        WebElement freestyleJobOption = getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject"));
        freestyleJobOption.click();

        WebElement okNewJobButton = getDriver().findElement(By.cssSelector("#ok-button"));
        okNewJobButton.click();

        WebElement newJobLinkName = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]/a"));
        String linkText = newJobLinkName.getText();

        Assert.assertEquals(linkText, "NewTestJob01");
    }

    @Test
    public void testXStreamCoreVersion() {
        WebElement aboutButton = getDriver().findElement(By.xpath("//footer//button"));
        aboutButton.click();

        WebElement aboutJen = getDriver().findElement(By.xpath("//a[contains(@href,'/manage/about')]"));
        aboutJen.click();

        WebElement xStreamVersion = getDriver().findElement(By.xpath("//td[contains(text(),'com.thoughtworks.xstream:xstream')]"));
        String versionText = xStreamVersion.getText();

        String expectedText = "com.thoughtworks.xstream:xstream:";
        String expectedVersion = "1.4.20";
        Assert.assertEquals(versionText,expectedText+expectedVersion);
    }
}
