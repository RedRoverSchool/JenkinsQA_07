package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.ArrayList;
import java.util.List;

public class FreestyleProject7Test extends BaseTest {

    private final String PROJECT_NAME = "Freestyle Project";

    private void createFreeStyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test

    public void testCreateFreestyleProjectFromNewItem() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input#name.jenkins-input")).sendKeys("FreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        WebElement freestyleProject = getDriver().findElement(By.xpath("//a[@href='job/FreestyleProject/']"));

        Assert.assertTrue(freestyleProject.isDisplayed());
    }

    @Test
    public void testPermalinksListOnStatusPage() {

        final String[] buildSuccessfulPermalinks = {"Last build", "Last stable build", "Last successful build",
                "Last completed build"};

        createFreeStyleProject(PROJECT_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Build Now"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@class='build-row-cell']")));

        getDriver().navigate().refresh();

        List<WebElement> permalinks = getDriver().findElements(
                By.xpath("//ul[@class='permalinks-list']/li"));

        ArrayList<String> permalinksTexts = new ArrayList<>();

        Assert.assertEquals(permalinks.size(), 4);

        for (int i = 0; i < permalinks.size(); i++) {
            permalinksTexts.add(permalinks.get(i).getText());
            Assert.assertTrue((permalinksTexts.get(i)).contains(buildSuccessfulPermalinks[i]));
        }

    }
}
