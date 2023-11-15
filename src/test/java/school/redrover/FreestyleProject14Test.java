package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject14Test extends BaseTest {

    private final String PROJECT_NAME = "New Freestyle Project";

    private void createFreeStyleProject(String projectName) throws InterruptedException {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        Thread.sleep(500);
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class= 'jenkins-input']")).sendKeys("FreestyleProject1");
        getDriver().findElement(By.xpath("//li[@class= 'hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id= 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name= 'Submit']")).click();

        getDriver().findElement(By.xpath("//li/a[@class= 'model-link']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject1/']")).click();

        Assert.assertEquals
                (getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                        "Project FreestyleProject1");
    }

    @Test
    public void testCreateFreestyleProject() {
        final String projectName = "Test Project";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//span[text()='Test Project']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + projectName);
    }

    @Test
    public void testDisableProjectFromStatus() throws InterruptedException {
        createFreeStyleProject(PROJECT_NAME);

        goToJenkinsHomePage();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.cssSelector("#disable-project .jenkins-button")).click();

        goToJenkinsHomePage();

        String fromStatus = getDriver().findElement(By.id("job_" + PROJECT_NAME)).findElement(By.className("svg-icon"))
                .getAttribute("title");
        Assert.assertEquals(fromStatus, "Disabled");
    }

}
