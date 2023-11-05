package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder2Test extends BaseTest {
    private void createNewOrganizationFolder(String organizationFolderName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(organizationFolderName);
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateNewOrganizationFolder() {
        final String organizationFolderName = "Folder1";
        createNewOrganizationFolder(organizationFolderName);

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//span[text() = 'Folder1']"))
                .getText(), organizationFolderName);
    }

    @Test
    public void testCreatingFreestyleProject() {
        String projectName = "Artusom";
        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id='add-item-panel']//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//div[@id='items']//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a[@href='/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']/span")).getText(),projectName);
    }

    @Test
    public void testAddDescription () {
        final String description = "Тест123";

        createNewOrganizationFolder("Test");

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).click();
        getDriver().findElement(By.name("_.description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(),description);
    }
}
