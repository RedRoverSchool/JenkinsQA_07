package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject4Test extends BaseTest {

    protected static final String PROJECT_NAME = "FREEStyle";
    protected static final String DESCRIPTION_FOR_FREESTYLE_PROJECT = "It s a description adding test";
    protected static final String ADDITIONAL_DESCRIPTION_FOR_FREESTYLE_PROJECT = "Additional description";

    private void createFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void returnToTheHomePageJenkins() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    private void testFreestyleProjectAddDescription() {

        createFreestyleProject();

        getDriver().findElement(By.id("jenkins-name-icon")).click();//click logo
        getDriver().findElement(By.xpath("//span[contains(text(),'FR')]"));

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_FOR_FREESTYLE_PROJECT);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        getDriver().findElement(By.xpath("//div[contains(text(),'" + DESCRIPTION_FOR_FREESTYLE_PROJECT + "')]"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div"))
                .getText(), DESCRIPTION_FOR_FREESTYLE_PROJECT);
    }

    private void createDescriptionForFreestyleProject() {

        getDriver().findElement(By.xpath("//span[contains(text(),'"+ PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_FOR_FREESTYLE_PROJECT);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

    }

    @Test
    private void testEditExistingDescription() {

        createFreestyleProject();
        returnToTheHomePageJenkins();
        createDescriptionForFreestyleProject();
        getDriver().findElement(By.xpath("//*[@href='editDescription']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(ADDITIONAL_DESCRIPTION_FOR_FREESTYLE_PROJECT);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div"))
                .getText(),ADDITIONAL_DESCRIPTION_FOR_FREESTYLE_PROJECT + DESCRIPTION_FOR_FREESTYLE_PROJECT);

    }
}

