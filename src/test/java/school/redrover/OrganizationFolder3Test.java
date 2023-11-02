package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class OrganizationFolder3Test extends BaseTest {

    private static final String PROJECT_NAME = "Organization Folder";
    private static final String NEW_PROJECT_NAME = "Organization Folder Renamed";

    private void createProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateProject() {
        createProject();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),"Organization Folder");
    }

    @DataProvider(name = "wrong-character")
    public Object[][] provideWrongCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Test(dataProvider = "wrong-character")
    public void testCreateProjectWithInvalidChar(String invalidData) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(invalidData);

        String errorMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(errorMessage, "» ‘" + invalidData + "’ is an unsafe character");
    }

    @Test
    public void testCreateProjectWithSpaceInsteadOfName() {
        final String space = " ";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(space);
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Error");
    }

    @Test
    public void testCreateProjectWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testRenameProjectFromProjectPage() {
        createProject();

        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectFromDashboardDropdownMenu() {
        createProject();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//*[@id='job_" + PROJECT_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectWithSameName() {
        createProject();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//*[@id='job_" + PROJECT_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div//p")).getText(), "The new name is the same as the current name.");
    }

    @Test
    public void testDisableProject() {
        createProject();

        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.id("enable-project")).getText().substring(0, 46), "This Organization Folder is currently disabled");
    }
}
