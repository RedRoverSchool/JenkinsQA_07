package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline2Test extends BaseTest {

    private static final String JOB_NAME = "New_Pipeline";

    private void createAPipeline (String jobName) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreate() {
        createAPipeline(JOB_NAME);

        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(), JOB_NAME);
    }

    @Test
    public void testDelete() {

        createAPipeline(JOB_NAME);

        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href='/']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + JOB_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete')]")).click();

        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }
}
