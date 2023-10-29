package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject3Test extends BaseTest {
    private final static String PROJECT_NAME = "Test Project";

    public void createFreestyleProject(String projectName) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

    }

    @Test
    public void testCreateFreestyleProject() {
        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//tr[@id= 'job_" + PROJECT_NAME + "' ] //td[3]/a")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Project " + PROJECT_NAME);
    }

    @Test
    public void testAddDescription() {

        final String projectDescription = "Test Description";
        final String editedProjectDescription = "Edited Test Description";

        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//tr[@id = 'job_" + PROJECT_NAME + "']//td[3]")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(projectDescription);
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
        getDriver().findElement(By.xpath("//div[contains(text(), '" + projectDescription + "')]/..//a"))
                .click();
        getDriver().findElement(By.name("description")).sendKeys(Keys.chord(Keys.COMMAND, "a"));
        getDriver().findElement(By.name("description")).sendKeys(editedProjectDescription);
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        getDriver().findElement(By.xpath("//div[contains(text(), '" + editedProjectDescription + "')]/..//a"))
                .click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath
                ("//div[@id = 'description']//div[1]")).getText(), "");
    }

    @Test
    public void testRenameFreestyleProject() {
        final String editedProjectName = "Edited Project Name";
        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.xpath("//tr[@id= 'job_" + PROJECT_NAME + "' ] //td[3]/a")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Rename')]/..")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(editedProjectName);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//table[@id]//td[3]//span")).getText(),
                editedProjectName);
    }

}
