package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AbramovTest extends BaseTest {

    private void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createNewJob(String jobName) {
        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(jobName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
    }

    @Test
    public void testNewFirstJenkinsFreestyleProject() {
        final String jobName = "NewTestJob01";

        createNewJob(jobName);

        WebElement newJobLinkName = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]/a"));
        String linkText = newJobLinkName.getText();

        Assert.assertEquals(linkText, "NewTestJob01");
    }

    @Test
    public void testXStreamCoreVersion() {
        WebElement aboutButton = getDriver().findElement(By.xpath("//footer//button"));
        aboutButton.click();

        WebElement aboutJenkinsLink = getDriver().findElement(By.xpath("//a[contains(@href,'/manage/about')]"));
        aboutJenkinsLink.click();

        WebElement xStreamVersion = getDriver().findElement(By.xpath("//td[contains(text(),'com.thoughtworks.xstream:xstream')]"));
        String versionText = xStreamVersion.getText();

        String expectedText = "com.thoughtworks.xstream:xstream:";
        String expectedVersion = "1.4.20";
        Assert.assertEquals(versionText,expectedText+expectedVersion);
    }

    @Test
    public void testDeleteTheRightJob() {
        final String jobNameToDelete = "JobToDelete";
        final String jobNameToKeep = "JobToKeep";

        createNewJob(jobNameToKeep);
        goHome();
        createNewJob(jobNameToDelete);
        goHome();

        getDriver().findElement(By.xpath("//a[contains(@href, 'job/JobToDelete/')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@data-url, 'doDelete')]")).click();
        getDriver().switchTo().alert().accept();

        String jobNameInTheList = getDriver().findElement(By.xpath("//a[contains(@href, 'job/JobToKeep/')]")).getText();
        Assert.assertEquals(jobNameInTheList, jobNameToKeep);
    }
}
