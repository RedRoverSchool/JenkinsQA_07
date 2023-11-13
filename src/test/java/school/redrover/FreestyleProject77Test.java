package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject77Test extends BaseTest {

    private static final String NAME_OF_FOLDER = "MyFolder";

    private void createProject() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(NAME_OF_FOLDER);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

    }
    @Test
    public void testCreateProject() {
        createProject();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//a[@href = 'job/" + NAME_OF_FOLDER + "/']")).getText(), NAME_OF_FOLDER);

    }

    @Test(dependsOnMethods = "testCreateProject")
    public void testAddDescription() {

        String description = "New Description";

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit'][contains(text(), 'Save')]")).click();
        String descriptionName = getDriver().
                findElement(By.xpath("//div[@id='description']/div[contains(text(),'" + description + "')]"))
                .getText();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("description")).sendKeys(description.toLowerCase());
        getDriver().findElement(By.xpath("//button[@name='Submit'][contains(text(), 'Save')]")).click();
        String renameDescription = getDriver().
                findElement(By.xpath("//div[@id='description']/div[contains(text(),'" + description.toLowerCase() + "')]"))
                .getText();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.xpath("//button[@name='Submit'][contains(text(), 'Save')]")).click();

        String wholeDescription = getDriver().
                findElement(By.xpath("//div[@id='description']/div[1]"))
                .getText();

        Assert.assertEquals(descriptionName, description);
        Assert.assertEquals(renameDescription, description.toLowerCase());
        Assert.assertEquals(wholeDescription.length(), 0);

    }
}
