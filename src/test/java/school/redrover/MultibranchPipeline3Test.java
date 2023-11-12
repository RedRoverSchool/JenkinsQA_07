package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class MultibranchPipeline3Test extends BaseTest {

    private final static String PROJECT_NAME = "MultibranchPipelineTest";
    private final static String HOME_PAGE = "jenkins-home-link";
    private final List<String> requiredNamesOfTasks = List.of("Status", "Configure", "Scan Multibranch Pipeline Log", "Multibranch Pipeline Events",
            "Delete Multibranch Pipeline", "People", "Build History", "Rename", "Pipeline Syntax", "Credentials");

    private void createProject(String typeOfProject, String nameOfProject, boolean goToHomePage) {
        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//div[@id='side-panel']//a[contains(@href,'newJob')]")))).click();
        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//input[@class='jenkins-input']")))).sendKeys(nameOfProject);
        getDriver().findElement(By.xpath("//span[text()='" + typeOfProject + "']/..")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='ok-button']"))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        if (goToHomePage) {
            getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id(HOME_PAGE)))).click();
        }
    }

    private List<String> getTextOfWebElements(List<WebElement> elements) {
        List<String> textOfWebElements = new ArrayList<>();

        for (WebElement element : elements) {
            textOfWebElements.add(element.getText());
        }
        return textOfWebElements;
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

    @Test
    public void testVisibilityTasksOfSidebarMenu() {
        createProject("Multibranch Pipeline", PROJECT_NAME, true);
        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));

        Assert.assertEquals(namesOfTasks, requiredNamesOfTasks);
    }

    @Test
    public void testVisibilityOfAdditionalTaskOfSidebarMenuIfFolderIsCreated() {
        createProject("Folder", "Nested Folder", true);
        createProject("Multibranch Pipeline", PROJECT_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));
        namesOfTasks.removeAll(requiredNamesOfTasks);

        Assert.assertEquals(namesOfTasks.toString(), "[Move]");
    }

    @Test
    public void testVisibilityOfAdditionalTaskOfSidebarMenuIfProjectInsideFolder() {
        final String folderName = "Wrapper Folder";

        createProject("Folder", folderName, false);
        createProject("Multibranch Pipeline", PROJECT_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']/..")).click();
        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();

        List<String> namesOfTasks = getTextOfWebElements(getDriver().findElements(
                By.xpath("//span[@class='task-link-wrapper ']")));

        Assert.assertTrue(namesOfTasks.contains("Move"), "Move is not the additional task of sidebar menu on the left");
    }
}