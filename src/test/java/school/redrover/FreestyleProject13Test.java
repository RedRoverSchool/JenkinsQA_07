package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject13Test extends BaseTest {

    private final static String PROJECT_NAME = "FreestyleProject";
    private final static String DESCRIPTION = "Add description";
    private final static String NEW_FOLDER = "New folder";

    private void createFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreateFreestyleProject() {
        createFreestyleProject();

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                "Project " + PROJECT_NAME);
    }

    private void addDescription() {
        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testAddDescription() {
        createFreestyleProject();
        addDescription();

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(), DESCRIPTION);
    }

    @Test
    public void testEditExistingDescription() {
        createFreestyleProject();
        addDescription();

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("Edit description ");
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(),
                "Edit description " + DESCRIPTION);
    }

    private void createFolder() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(NEW_FOLDER);
        getDriver().findElement(By.id("j-add-item-type-nested-projects")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreateFolder() {
        createFolder();

        getDriver().findElement(By.xpath("//a[@href='job/New%20folder/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
                NEW_FOLDER);
    }
}
