package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MultibranchPipeline3Test extends BaseTest {

    private final static String PROJECT_NAME = "MultibranchPipelineTest";
    private final static String HOME_PAGE = "jenkins-home-link";

    private void createProject(String typeOfProject, String nameOfProject, boolean goToHomePage) {
        getDriver().findElement(By.xpath("//div[@id='side-panel']//a[contains(@href,'newJob')]")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(nameOfProject);
        getDriver().findElement(By.xpath("//span[text()='" + typeOfProject + "']/..")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        if (goToHomePage) {
            getDriver().findElement(By.id(HOME_PAGE)).click();
        }
    }

    @Test
    public void testMultibranchPipelineCreation() {

        final var expectedPipelineName = "AutoPipelineName";

        WebElement newJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        newJobButton.click();
        WebElement nameFieldInput = getDriver().findElement(By.xpath("//input[@id='name']"));
        nameFieldInput.sendKeys(expectedPipelineName);
        WebElement multiBranchPipelineButton = getDriver().findElement(By.xpath("//li[contains(@class,'multibranch')]"));
        multiBranchPipelineButton.click();
        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        WebElement submitPipelineButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        submitPipelineButton.click();
        WebElement createdPipeLineName = getDriver().findElement(By.xpath("//h1"));
        String actualPipelineName = createdPipeLineName.getText();

        Assert.assertEquals(actualPipelineName, expectedPipelineName);

    }

    @Test
    public void testSidebarMenuConsistingOfTenTasks() {
        final int quantityOfTasks = 10;

        createProject("Multibranch Pipeline", PROJECT_NAME, true);
        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();

        Assert.assertEquals(
                getDriver().findElements(By.xpath("//span[@class='task-link-wrapper ']")).size(),
                quantityOfTasks);
    }
}