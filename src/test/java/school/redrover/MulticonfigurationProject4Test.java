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
    private static final By ADD_DESCRIPTION_LINK = By.xpath("//a[@id='description-link']");
    private static final By DESCRIPTION_INPUT = By.xpath("//textarea[@name='description']");
    private static final By SAVE_DESCRIPTION_BUTTON = By.xpath("//button[@class='jenkins-button jenkins-button--primary ']");
    private static final By DESCRIPTION = By.xpath("//div[@id='description']/div[1]");
    private static final By EDIT_DESCRIPTION_BUTTON = By.id("description-link");
    private static final By DESCRIPTION_TEXT = By.tagName("textarea");

    private void createMulticonfigurationProject() {
        final String projectName = "MulticonfigurationProject";

        WebElement newItemLink = getDriver().findElement(NEW_ITEM_LINK);
        newItemLink.click();
        WebElement itemNameInput = getDriver().findElement(ITEM_NAME_INPUT);
        itemNameInput.click();
        itemNameInput.sendKeys(projectName);
        WebElement multiconfigurationProject = getDriver().findElement(MULTICONFIGURATION_PROJECT_TAB);
        multiconfigurationProject.click();
        WebElement okButton = getDriver().findElement(OK_BUTTON);
        okButton.click();
        WebElement saveButton = getDriver().findElement(SAVE_BUTTON);
        saveButton.click();
        WebElement jenkinsHomeLink = getDriver().findElement(JENKINS_HOME_LINK);
        jenkinsHomeLink.click();
    }

    @Test
    public void testCreateMulticonfigurationProject() {
        createMulticonfigurationProject();
        final String projectName = "MulticonfigurationProject";

        WebElement nameProject = getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']"));
        nameProject.click();

        Assert.assertEquals(getDriver().findElement(PROJECT_TITTLE).getText(), String.format("Project %s", projectName));
    }

    @Test
    public void testCreateMulticonfigurationProjectWithDesription() {
        createMulticonfigurationProject();
        final String desriptionText = "test";

        WebElement addDescription = getDriver().findElement(ADD_DESCRIPTION_LINK);
        addDescription.click();
        WebElement descriptionInput = getDriver().findElement(DESCRIPTION_INPUT);
        descriptionInput.click();
        descriptionInput.sendKeys(desriptionText);
        WebElement saveDescription = getDriver().findElement(SAVE_DESCRIPTION_BUTTON);
        saveDescription.click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), desriptionText);
    }

    @Test
    public void testEditDesriptionMulticonfigurationProject() {
        createMulticonfigurationProject();
        final String desriptionText = "test";
        final String editDescriptionText = "edit description test";

        WebElement addDescription = getDriver().findElement(ADD_DESCRIPTION_LINK);
        addDescription.click();
        WebElement descriptionInput = getDriver().findElement(DESCRIPTION_INPUT);
        descriptionInput.click();
        descriptionInput.sendKeys(desriptionText);
        WebElement saveDescription = getDriver().findElement(SAVE_DESCRIPTION_BUTTON);
        saveDescription.click();
        WebElement editDescription = getDriver().findElement(EDIT_DESCRIPTION_BUTTON);
        editDescription.click();
        WebElement descriptionText = getDriver().findElement(DESCRIPTION_TEXT);
        descriptionText.clear();
        descriptionText.sendKeys(editDescriptionText);
        getDriver().findElement(SAVE_DESCRIPTION_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), editDescriptionText);
    }
}