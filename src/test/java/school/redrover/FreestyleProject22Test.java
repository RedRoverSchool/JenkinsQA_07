package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject22Test extends BaseTest {
    private void CreateNewFreestyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }
    private final String FREESTYLE_PROJECT_NAME = "FreestyleProject1";
    private final String FREESTYLE_PROJECT_INVALID_NAME = "FreestyleProject2";

    @Test
    public void testCreateFreestyleProjectWithValidName() {
        CreateNewFreestyleProject(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject1/']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),
                "Project " + FREESTYLE_PROJECT_NAME);
    }
    @DataProvider(name = "wrong-characters")
    public Object[][] providerWrongCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Test(dataProvider = "wrong-characters")
    public void testWrongCharactersBeforeNameProject(String wrongCharacters) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(wrongCharacters);

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-invalid"))
                .getText(), "» ‘" + wrongCharacters + "’ is an unsafe character");

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());

        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
    }

    @Test(dataProvider = "wrong-characters")
    public void testWrongCharactersAfterNameProject(String wrongCharacters) {
        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name"))
                .sendKeys(FREESTYLE_PROJECT_INVALID_NAME + wrongCharacters);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText(),
                "‘" + wrongCharacters + "’ " + "is an unsafe character");
    }
}
