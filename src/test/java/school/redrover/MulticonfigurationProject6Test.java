package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject6Test extends BaseTest {

    private static final By NEW_ITEM_LINK = By.linkText("New Item");
    private static final By ITEM_NAME_INPUT = By.id("name");
    private static final By MULTICONFIGURATION_PROJECT_TAB = By.xpath("//li[@class='hudson_matrix_MatrixProject']");
    private static final By OK_BUTTON = By.xpath("//button[@id='ok-button']");
    private static final By SAVE_BUTTON = By.xpath("//button[@name='Submit']");
    private static final By JENKINS_LINK = By.cssSelector("#jenkins-name-icon");
    private static final By PROJECT_NAME = By.xpath("//span[contains(text(),'ProjectMulticonfiguration')]");

    @Test
    public void testCreate() {

        getDriver().findElement(NEW_ITEM_LINK).click();
        getDriver().findElement(ITEM_NAME_INPUT).sendKeys("ProjectMulticonfiguration");
        getDriver().findElement(MULTICONFIGURATION_PROJECT_TAB).click();
        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(JENKINS_LINK).click();

        getDriver().findElement(PROJECT_NAME).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project ProjectMulticonfiguration");
    }
}
