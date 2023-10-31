package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject4Test extends BaseTest {

    private void createFreestyleProject(String projectName) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void returnToTheHomePageJenkins() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    private void testFreestyleProjectAddDescription() {
        final String descriptionForFreestyleProject = "It s a description adding test";

        createFreestyleProject("FREE");

        getDriver().findElement(By.id("jenkins-name-icon")).click();//click logo
        getDriver().findElement(By.xpath("//span[contains(text(),'FR')]"));

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("It s a description adding test");
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        getDriver().findElement(By.xpath("//div[contains(text(),'" + descriptionForFreestyleProject + "')]"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div"))
                .getText(), descriptionForFreestyleProject);
    }


    private void addFreestyleProjectAddDescription(String descriptionForFreestyleProject) {
        getDriver().findElement(By.xpath("//span[contains(text(),'FRE')]")).click();
        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(descriptionForFreestyleProject);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

    }

    @Test
    private void testEditExistingDescription() {
        final String descriptionForFreestyleProject = "It s a description adding test";
        final String additionalDescription = "2";

        createFreestyleProject("FREEE");
        returnToTheHomePageJenkins();

        addFreestyleProjectAddDescription("It s a description adding test");
        getDriver().findElement(By.xpath("//*[@href='editDescription']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(additionalDescription);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div"))
                .getText(),additionalDescription + descriptionForFreestyleProject);

    }
}

