package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject16Test extends BaseTest {

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testCreateFreestyleProjectWithValidName() {
        createFreestyleProject("Freestyle project test");
        openDashboard();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project test')]")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-index-headline.page-headline")).getText(),
                "Project Freestyle project test");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithValidName")
    public void testAddProjectDescription() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project test')]")).click();
        getDriver().findElement(By.xpath("//a[@href = 'editDescription']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("description test");
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                "description test");
    }

    @Test(dependsOnMethods = "testAddProjectDescription")
    public void testEditProjectDescription() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project test')]")).click();
        getDriver().findElement(By.xpath("//a[@href = 'editDescription']")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("Edited description");
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                "Edited description");
    }

    @Test(dependsOnMethods = "testEditProjectDescription")
    public void testDeleteProjectDescription() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project test')]")).click();
        getDriver().findElement(By.xpath("//a[@href = 'editDescription']")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                "");
        Assert.assertTrue(getDriver().findElement(By.xpath("//a[text()='Add description']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testDeleteProjectDescription")
    public void testRenameProjectUsingValidName() {
        String newProjectName = "Renamed freestyle project";

        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project test')]")).click();
        getDriver().findElement(By.xpath("//*[@id = 'tasks']/div[7]")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(newProjectName);
        getDriver().findElement(By.xpath("//button[contains(text(), 'Rename')]")).click();

        openDashboard();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class = 'jenkins-table__link model-link inside']/span")).getText(),
                newProjectName);
    }
}
