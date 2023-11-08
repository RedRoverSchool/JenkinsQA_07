package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder15Test extends BaseTest {
    private final static String NEW_NAME_FOLDER = "TestFolder";
    private void createNewFolder(String folderName) {
        getDriver().findElement(By.xpath("//a [@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//img[@class = 'icon-folder icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }
    private void createFreestPro(String nameFreestyleProject) {
        getDriver().findElement(By.id("name")).sendKeys(nameFreestyleProject);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
    }
    private void AddDescriptionFolder(String descriptionText){
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(descriptionText);
        getDriver().findElement(By.name("Submit")).click();
    }
    @Test
    public void testCreateNewFolder() {
        createNewFolder("WorkFolder");
        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(text(), 'WorkFolder')]")).getText(), "WorkFolder");
    }
    @Test
    public void testCreateNewJob() {
        createNewFolder("WorkFolder123");

        getDriver().findElement(By.xpath("//span[contains(text(), 'WorkFolder123')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Create a job')]")).click();

        createFreestPro("MyFreestyleProject");

        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//span[contains (text(), 'WorkFolder123')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(text(), 'MyFreestyleProject')]")).getText(),"MyFreestyleProject");
    }
    @Test
    public void testRenameFolder() {
        createNewFolder("FolderJob");

        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[7]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@name = 'newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name = 'newName']")).sendKeys(NEW_NAME_FOLDER);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(text(),'TestFolder')]")).getText(), NEW_NAME_FOLDER);
    }
    @Test
    public void testAddDescriptionFolder() {
        createNewFolder("OfficeFolder");
        getDriver().findElement(By.xpath("//span[contains(text(), 'OfficeFolder')]")).click();

        AddDescriptionFolder("New description");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]")).getText(), "New description");
    }
    @Test
    public void testEditDescriptionFolder() {
        createNewFolder("OfficeFolder");
        getDriver().findElement(By.xpath("//span[contains(text(), 'OfficeFolder')]")).click();

        AddDescriptionFolder("New description");
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("description")).sendKeys("New description edited");
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),"New description edited");
    }
}
