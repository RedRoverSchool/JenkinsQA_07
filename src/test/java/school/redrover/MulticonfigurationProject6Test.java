package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.nio.file.Watchable;

public class MulticonfigurationProject6Test extends BaseTest {

    private static final By NEW_ITEM_LINK = By.linkText("New Item");
    private static final By ITEM_NAME_INPUT = By.id("name");
    private static final By MULTICONFIGURATION_PROJECT_TAB = By.xpath("//li[@class='hudson_matrix_MatrixProject']");
    private static final By OK_BUTTON = By.xpath("//button[@id='ok-button']");
    private static final By SAVE_BUTTON = By.xpath("//button[@name='Submit']");
    private static final By JENKINS_LINK = By.cssSelector("#jenkins-name-icon");
    private static final By PROJECT_NAME = By.xpath("//span[contains(text(),'ProjectMulticonfiguration')]");
    private static final By   ADD_DESCRIPTION_LINK = By.xpath("//a[@id='description-link']");
    private static final By DESCRIPTION_INPUT = By.xpath("//textarea[@name='description']");
    private static final By SAVE_DESCRIPTION_BUTTON = By.xpath("//button[@class='jenkins-button jenkins-button--primary ']");
    private static final By DESCRIPTION = By.xpath("//div[@id='description']/div[1]");


        public void createProjectMulticonfiguration() {
        final String projectName = "ProjectMulticonfiguration";

        WebElement newItemLink = getDriver().findElement(NEW_ITEM_LINK);
        newItemLink.click();

        WebElement newItemNameInput = getDriver().findElement(ITEM_NAME_INPUT);

        newItemNameInput.sendKeys(projectName);

        WebElement newMultiConfigurationProjectTab =   getDriver().findElement(MULTICONFIGURATION_PROJECT_TAB);
        newMultiConfigurationProjectTab.click();

        WebElement newOkButton = getDriver().findElement(OK_BUTTON);
        newOkButton.click();

        WebElement newSaveButton = getDriver().findElement(SAVE_BUTTON);
        newSaveButton.click();

        WebElement newJencinsLink = getDriver().findElement(JENKINS_LINK);
        newJencinsLink.click();

        WebElement newProjectName = getDriver().findElement(PROJECT_NAME);
        newProjectName.click();
    }

    @Test
    public void testCreate() {

        createProjectMulticonfiguration();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project ProjectMulticonfiguration");
    }

    @Test
    public void testAddDescription() {

        createProjectMulticonfiguration();
        final String desriptionText = "Testing";

        WebElement addDescription = getDriver().findElement(ADD_DESCRIPTION_LINK);
        addDescription.click();
        WebElement descriptionInput = getDriver().findElement(DESCRIPTION_INPUT);
        descriptionInput.click();
        descriptionInput.sendKeys(desriptionText);
        WebElement saveDescription = getDriver().findElement(SAVE_DESCRIPTION_BUTTON);
        saveDescription.click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), desriptionText);
    }
}




