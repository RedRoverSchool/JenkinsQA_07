package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject16Test extends BaseTest {

    private void clickNewJobButton() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
    }

    private void clickFreestyleProjectButton() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project')]")).click();
    }

    private void clickOkButtonCreateProject() {
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void setProjectName(String name) {
        getDriver().findElement(By.name("name")).sendKeys(name);
    }

    private void clickEditDescriptionButton() {
        getDriver().findElement(By.xpath("//a[@href = 'editDescription']")).click();
    }

    private void clickSaveDescriptionButton() {
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
    }

    private void setDescription(String name) {
        getDriver().findElement(By.className("jenkins-input")).sendKeys(name);
    }

    private void openDashboard() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void createFreestyleProject(String name) {
        clickNewJobButton();
        setProjectName(name);
        clickFreestyleProjectButton();
        clickOkButtonCreateProject();
    }

    private void addNewDescription(String descriptionText) {
        clickEditDescriptionButton();
        setDescription(descriptionText);
        clickSaveDescriptionButton();
    }

    @Test
    public void testCreateFreestyleProject() {
        createFreestyleProject("create project test");
        openDashboard();
        getDriver().findElement(By.xpath("//span[contains(text(), 'create project test')]")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-index-headline.page-headline")).getText(), "Project create project test");
    }

    @Test
    public void testAddDescriptionToFreestyleProject() {
        createFreestyleProject("add description test");
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
        addNewDescription("description test");

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                "description test");
    }
}
