package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject22Test extends BaseTest {
    private final String FREESTYLE_PROJECT_NAME = "FreestyleProject1";
    private final String FREESTYLE_PROJECT_INVALID_NAME = "FreestyleProject2";

    @Test
    public  void testCreateFreestyleProject() {
        getDriver().findElement(By.linkText("New Item")).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.className("hudson_model_FreeStyleProject"))).click();
        getDriver().findElement(By.id("name")).sendKeys(FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@name = 'Submit']"))).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-name-icon"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/FreestyleProject1/']"))).click();

        Assert.assertEquals(getWait2().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='main-panel']/h1"))).getText(),
                "Project " + FREESTYLE_PROJECT_NAME);
    }

    @DataProvider(name = "wrong-characters")
    public Object[][] providerWrongCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Test(dataProvider = "wrong-characters")
    public void testWrongCharactersBeforeNameProject(String wrongCharacters) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.id("name"))).sendKeys(wrongCharacters);

        Assert.assertEquals( getWait2().until(ExpectedConditions.elementToBeClickable(
                By.id("itemname-invalid"))).getText(), "» ‘" + wrongCharacters + "’ is an unsafe character");

        Assert.assertFalse(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ok-button"))).isEnabled());
    }

    @Test(dataProvider = "wrong-characters")
    public void testWrongCharactersAfterNameProject(String wrongCharacters) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("name")))
                .sendKeys(FREESTYLE_PROJECT_INVALID_NAME + wrongCharacters);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='main-panel']/p"))).getText(),
                "‘" + wrongCharacters + "’ " + "is an unsafe character");
    }
}
