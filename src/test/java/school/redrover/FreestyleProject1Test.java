package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject1Test extends BaseTest {
    private final static String PROJECT_NAME = "FreestyleProject";
    private final static String HOME_PAGE = "jenkins-home-link";
    private final static String NAME_SEARCH = "//span[text()='FreestyleProject']";

    private void createProject(String typeOfProject, String nameOfProject, boolean goToHomePage) {
        getDriver().findElement(By.xpath("//div[@id='side-panel']//a[contains(@href,'newJob')]")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(nameOfProject);
        getDriver().findElement(By.xpath("//span[text()='" + typeOfProject + "']/..")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        if(goToHomePage) {
            getDriver().findElement(By.id(HOME_PAGE)).click();
        }
    }

    @Test
    public void testCreateFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("(//span[@class='label'])[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
        String actualName = getDriver()
                .findElement(By.xpath(NAME_SEARCH)).getText();
        Assert.assertEquals(actualName, PROJECT_NAME);
    }

    @Test
    public void testDeleteFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("(//span[@class='label'])[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
        getDriver().findElement(By.xpath(NAME_SEARCH)).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Project']")).click();
        getDriver().switchTo().alert().accept();
    }

    @Test
    public void testConfigureBuildEnvironmentSettingsAddTimestamp() throws InterruptedException {
        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();
        getDriver().findElement(By.xpath("//span[text()='Configure']/..")).click();

        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();
        Thread.sleep(600);
        getDriver().findElement(By.xpath("//label[text()='Add timestamps to the Console Output']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//span[text()='Build Now']/..")).click();
        Thread.sleep(5000);
        getDriver().navigate().refresh();
        getDriver().findElement(By.xpath("//span[@class='build-status-icon__outer']")).click();

        List<WebElement> timestamps = getDriver().findElements(
                By.xpath("//pre[@class='console-output']//span[@class='timestamp']"));

        Assert.assertNotEquals(timestamps.size(), 0);
        for (WebElement timestamp : timestamps) {
            Assert.assertTrue(timestamp.getText().trim().matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"));
        }
    }

    @Test
    public void testMoveJobToFolder() {
        final String folderName = "FolderWrapper";
        final String destinationOption = "Jenkins » " + folderName;

        createProject("Freestyle project", PROJECT_NAME, true);
        createProject("Folder", folderName, true);

        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).click();
        getDriver().findElement(By.xpath("//span[text()='Move']/..")).click();

        Select destinationDropdown = new Select(getDriver().findElement(By.xpath("//select[@name='destination']")));
        destinationDropdown.selectByVisibleText(destinationOption);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id(HOME_PAGE)).click();

        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']/..")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']/..")).isDisplayed());
    }
    @Test
    public void testCreatingFreestyleProject() {
        String projectName = "Artusom";
        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id='add-item-panel']//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//div[@id='items']//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a[@href='/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']/span")).getText(),projectName);
    }

    @Test
    public void testThisProjectIsParameterizedCheckboxAddBooleanParameter() {

        final String name = "Ņame";
        final String description = "Description";

        createProject("Freestyle project", PROJECT_NAME, true);

        getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]")).click();

        getDriver().findElement(By.
                        xpath("//div[@nameref='rowSetStart28']//span[@class='jenkins-checkbox']"))
                .click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Add Parameter')]")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), 'Boolean Parameter')]")).click();
        getDriver().findElement(By.xpath("//input[@name = 'parameter.name']"))
                .sendKeys(name);
        getDriver().findElement(By.xpath("//textarea[@name = 'parameter.description']"))
                .sendKeys(description);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        getDriver().findElement(By.xpath("//span[contains(text(), 'Configure')]/..")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//input[@name = 'parameter.name']"))
                .getAttribute("value"),
                name);
        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//textarea[@name = 'parameter.description']"))
                .getAttribute("value"),
                description);
    }
}
