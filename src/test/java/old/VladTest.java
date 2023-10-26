package old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class VladTest extends BaseTest {

    private void deleteText(WebElement deleteText){

        deleteText.sendKeys(Keys.CONTROL + "a");
        deleteText.sendKeys(Keys.DELETE);
    }
    private void CreateNewJob(String newJobXpath) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("test123");
        getDriver().findElement(By.xpath(newJobXpath)).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test
    public void testJenkinsVersion() {

        getDriver().findElement(By.xpath("//button[contains(@class,'jenkins_ver')]")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/about']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p[.='Version 2.414.2']")).getText(),
                "Version 2.414.2");
    }
    @Test
    public void testJenkinsManage() {

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link']")).click();
        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();

        WebElement setText = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        deleteText(setText);
        setText.sendKeys("test123");

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@formnovalidate]"));
        saveButton.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div")).getText(),
                "test123");
    }
    @Test
    public void testCreateNewFreestyleProject() {

        CreateNewJob("//ul[@class='j-item-options']/li");

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                "Project test123");
    }
    @Test
    public void testCreateNewPipeline() {

        CreateNewJob("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']");

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                "Pipeline test123");
    }
    @Test
    public void testCreateNewMultiConfigurationProject() {

        CreateNewJob("//li[@class='hudson_matrix_MatrixProject']");

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='matrix-project-headline page-headline']")).getText(),
                "Project test123");
    }
    @Test
    public void testCreateNewFolder() {

        CreateNewJob("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']");

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),
                "test123");
    }
}
