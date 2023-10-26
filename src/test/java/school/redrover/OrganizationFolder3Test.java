package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder3Test extends BaseTest {

    private static final String PROJECT_NAME = "Organization Folder";

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
}
