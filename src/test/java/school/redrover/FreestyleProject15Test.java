package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject15Test extends BaseTest {

    private void clickElement(By locator) {
        getDriver().findElement(locator).click();
    }
    private void itemName(String name){
        getDriver().findElement(By.id("name")).sendKeys(name);
    }
    private void addDescription(String descriptionText){
        getDriver().findElement(By.name("description")).sendKeys(descriptionText);
    }
    private void clearDescription(){
        getDriver().findElement(By.name("description")).clear();
    }
    private void createFreestyleProject(String projectName) {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));
        itemName(projectName);
        clickElement(By.className("hudson_model_FreeStyleProject"));
        clickElement(By.id("ok-button"));
    }


    @Test
    public void testRenameProject() {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));
        getDriver().findElement(By.id("name")).sendKeys("AS Test File");
        clickElement(By.className("hudson_model_FreeStyleProject"));
        clickElement(By.id("ok-button"));
        clickElement(By.name("Submit"));

        clickElement(By.cssSelector("a[href='/job/AS%20Test%20File/confirm-rename']"));
        getDriver().findElement(By.cssSelector(".setting-main input.jenkins-input")).clear();
        getDriver().findElement(By.cssSelector(".setting-main input.jenkins-input")).sendKeys("AS Test File new");
        clickElement(By.xpath("//button[contains(@class, 'jenkins-button') and contains(., 'Rename')]"));

        Assert.assertNotNull(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline' and text()='Project AS Test File new']")),
                "Project title element is not present on the page");
    }

    @Test
    public void testAddDescription() {
        createFreestyleProject("Test Project");
        addDescription("Test Description text");
        clickElement(By.name("Submit"));

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), "Test Description text");
    }

    @Test
    public void testEditDescription() {
        final String editedText = "Edited Description text";

        createFreestyleProject("Test Project");
        addDescription("Test Description text");
        clickElement(By.name("Submit"));
        clickElement(By.id("description-link"));
        clearDescription();
        addDescription(editedText);
        clickElement(By.name("Submit"));

        Assert.assertEquals(editedText, getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), "Description  description was not changed");
    }

    @Test
    public void testDeleteDescription() {
        createFreestyleProject("Test Project 2");
        addDescription("Test Description text");
        clickElement(By.name("Submit"));
        clickElement(By.id("description-link"));
        clearDescription();
        clickElement(By.name("Submit"));

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(), "");
    }
}