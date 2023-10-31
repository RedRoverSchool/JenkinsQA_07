package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject2Test extends BaseTest {

    @Test
    public void testCreate() {
        final String projectName = "FreeStyleProjectName";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + projectName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project " + projectName);
    }

    @Test
    public void testCreate2() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys("Online store");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//span[text()='Online store']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText(), "Project Online store");
    }

    @Test
    public void testGiveNewNameProject() {
        String jobName = "freestyleJob";
        String newJobName = "freestyleJob-2";

        CreateNewItem(jobName);
        getDriver().navigate().back();
        getDriver().navigate().back();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + jobName + "')]")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]/span")).click();
        getDriver().findElement(By.xpath("//input[@value='" + jobName + "']")).clear();
        getDriver().findElement(By.xpath("//input[@value='" + jobName + "']")).sendKeys(newJobName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='breadcrumbs']/li[3]/a")).getText(), newJobName);
    }

    private void CreateNewItem(String itemName) {
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(
                By.xpath("//div/input[@class='jenkins-input']")).sendKeys(itemName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']/label[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testDeleteFreestyleProject() {
        final String itemName = "Freestyle Project1";

        CreateNewItem(itemName);
        getDriver().navigate().back();
        getDriver().navigate().back();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + itemName + "')]")).click();
        getDriver().findElement(By.xpath(" //*[@id='tasks']/div[6]/span/a")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElements(
                By.xpath("//*[@id='breadcrumbs']/li[3]/a")).size(), 0);
    }
}
