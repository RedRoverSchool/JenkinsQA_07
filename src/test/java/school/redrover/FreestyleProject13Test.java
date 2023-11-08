package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject13Test extends BaseTest {

    private final static String PROJECT_NAME = "FreestyleProject";
    private final static String DESCRIPTION = "Add description";
    private final static String NEW_FOLDER = "New folder";

    private void createProject(String itemType, String itemName) {
        getDriver().findElement(By.xpath("//div[@id='side-panel']//a[contains(@href,'newJob')]")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(itemName);
        getDriver().findElement(By.xpath("//span[text()='" + itemType + "']/..")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
    }

    private void addDescription() {
        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreateFreestyleProject() {
        createProject("Freestyle project", PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test
    public void testAddDescription() {
        createProject("Freestyle project", PROJECT_NAME);
        addDescription();

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(), DESCRIPTION);
    }

    @Test
    public void testEditExistingDescription() {
        createProject("Freestyle project", PROJECT_NAME);
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

    @Test
    public void testCreateFolder() {
        createProject("Folder", NEW_FOLDER);

        getDriver().findElement(By.xpath("//a[@href='job/New%20folder/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), NEW_FOLDER);
    }

    @Test
    public void testMoveProject() {
        createProject("Freestyle project", PROJECT_NAME);
        createProject("Folder", NEW_FOLDER);

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + PROJECT_NAME + "/move']")).click();

        Select select = new Select(getDriver().findElement(By.xpath("//select[@name = 'destination']")));
        select.selectByValue("/" + NEW_FOLDER);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='job/New%20folder/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']")).getText(), PROJECT_NAME);
    }
}
