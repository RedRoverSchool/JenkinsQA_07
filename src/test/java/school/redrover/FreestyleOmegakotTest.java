package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleOmegakotTest extends BaseTest {

    private static final String NAMEFOLDER = "MyFolder";

    private void createProject(String name) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(name);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

    }

    @Test
    public void testaddDescription() {

        String description = "New Description";

        createProject(NAMEFOLDER);
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
