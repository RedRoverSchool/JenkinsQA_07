package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject4Test extends BaseTest {
    private static final By NEW_ITEM_LINK = By.linkText("New Item");
    private static final By ITEM_NAME_INPUT = By.id("name");
    private static final By MULTICONFIGURATION_PROJECT_TAB = By.xpath("//span[text()='Multi-configuration project']");
    private static final By OK_BUTTON = By.cssSelector("#ok-button");
    private static final By SAVE_BUTTON = By.name("Submit");
    private static final By JENKINS_HOME_LINK = By.xpath("//a[@id='jenkins-home-link']");
    private static final By PROJECT_TITTLE = By.cssSelector(".matrix-project-headline.page-headline");
    @Test
    public void testCreateMulticonfigurationProject() {
        final String projectName = "MulticonfigurationProject";

        WebElement newItemLink = getDriver().findElement(NEW_ITEM_LINK);
        newItemLink.click();
        WebElement itemNameInput = getDriver().findElement(ITEM_NAME_INPUT);
        itemNameInput.click();
        itemNameInput.sendKeys("MulticonfigurationProject");
        WebElement multiconfigurationProject = getDriver().findElement(MULTICONFIGURATION_PROJECT_TAB);
        multiconfigurationProject.click();
        WebElement okButton = getDriver().findElement(OK_BUTTON);
        okButton.click();
        WebElement saveButton = getDriver().findElement(SAVE_BUTTON);
        saveButton.click();
        WebElement jenkinsHomeLink = getDriver().findElement(JENKINS_HOME_LINK);
        jenkinsHomeLink.click();
        WebElement nameProject = getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']"));
        nameProject.click();

        Assert.assertEquals(getDriver().findElement(PROJECT_TITTLE).getText(), "Project " + projectName);
    }
}
