package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class AikaTest extends BaseTest {

    @Test
    public void testAdminUserDisplayed() {
        getDriver().findElement(By.xpath("//span[text() = 'People']/parent::a")).click();

        Assert.assertTrue(getDriver().findElement(By.linkText("admin")).isDisplayed());
    }

    @Test
    public void testVerifyJenkinsVersion() {
        String  expectedJenkinsVersion = "2.414.2";
        String jenkinsVersion = getDriver().findElement(By.xpath("//button[contains(text(), 'Jenkins')]")).getText();

        Assert.assertEquals(jenkinsVersion.split(" ")[1], expectedJenkinsVersion);
    }

    @Test
    public void testDashboardOpened() {

        WebElement newItem = getDriver().findElement(By.xpath("//div[@id ='tasks']//a[1]"));
        newItem.click();

        goToDashboard();

        Assert.assertTrue(getDriver().getTitle().contains("Dashboard"));
    }

    @Test
    public void testCreateNewFreestyleProject() {

        createFreestyleProject("Test v1");
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1"))
                .getText().contains("Test v1"));
    }

    @Test
    public void testRenameProject() throws InterruptedException {

        final String projectName = "Test v2";

        createFreestyleProject(projectName);
        goToDashboard();

        WebElement projectInList = getDriver().findElement(
                By.xpath("//table[@id = 'projectstatus']//span[text() = '"+projectName+"']"));
        WebElement button = getDriver().findElement(By.xpath
                ("//table[@id = 'projectstatus']//span[text() = '"+projectName+"']//following-sibling::button"));
        //hover over first to make dropdown element to be visible
        Actions actions = new Actions(getDriver());
        actions.moveToElement(projectInList).perform();
        button.click();

        selectFromJenkinsMenuDropdown("Rename");

        WebElement newNameInput = getDriver().findElement(By.xpath("//input[@name = 'newName']"));
        newNameInput.clear();
        newNameInput.sendKeys("Test renamed");
        getDriver().findElement(By.name("Submit")).click();

        goToDashboard();

        List<WebElement> projectsList = getDriver().findElements(
                By.xpath("//table[@id = 'projectstatus']//tbody//tr"));
        for(WebElement project : projectsList) {
            if(project.findElement(By.xpath("//a/span")).getAttribute("innerText")
                    .equalsIgnoreCase("Test renamed") ) {
                Assert.assertTrue(project.isDisplayed());
            }
            else if(project.findElement(By.xpath("//a/span")).getAttribute("innerText")
                    .equalsIgnoreCase(projectName)) {
                Assert.assertFalse(project.isDisplayed());
            }
        }

    }

    private void createFreestyleProject(String projectName) {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text() = 'Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys("Freestyle project description");
        getDriver().findElement(By.name("Submit")).click();

    }

    private void goToDashboard () {
        getDriver().findElement(By.xpath("//a[@href='/']")).click();
    }

    private void selectFromJenkinsMenuDropdown (String dropdownOption) {

        WebElement parentElement = getDriver().findElement(By.className("jenkins-dropdown"));
        List<WebElement> allChildElements = parentElement.findElements(By.xpath("*"));
        for(WebElement el : allChildElements) {
            if(el.getText().equalsIgnoreCase(dropdownOption)){
                el.click();
            }
        }
    }

}
