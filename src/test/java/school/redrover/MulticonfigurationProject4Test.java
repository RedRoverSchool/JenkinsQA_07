package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject4Test extends BaseTest {
    private static final By ADD_DESCRIPTION_LINK = By.xpath("//a[@id='description-link']");
    private static final By DESCRIPTION_INPUT = By.xpath("//textarea[@name='description']");
    private static final By SAVE_DESCRIPTION_BUTTON = By.xpath(
            "//button[@class='jenkins-button jenkins-button--primary ']");

    private void createMulticonfigurationProject() {
        final String projectName = "MulticonfigurationProject";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Multi-configuration project']")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
    }

    @Test
    public void testCreateMulticonfigurationProject() {
        createMulticonfigurationProject();
        final String projectName = "MulticonfigurationProject";

        WebElement nameProject = getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']"));
        nameProject.click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".matrix-project-headline.page-headline")).getText(),
                String.format("Project %s", projectName));
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

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//div[@id='description']/div[1]")).getText(), desriptionText);
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
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.tagName("textarea")).clear();
        getDriver().findElement(By.tagName("textarea")).sendKeys(editDescriptionText);
        getDriver().findElement(SAVE_DESCRIPTION_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//div[@id='description']/div[1]")).getText(), editDescriptionText);
    }
}