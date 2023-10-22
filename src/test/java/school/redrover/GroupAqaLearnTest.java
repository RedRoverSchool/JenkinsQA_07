package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupAqaLearnTest extends BaseTest {
    private WebDriverWait wait2;
    private WebDriverWait wait5;
    private WebDriverWait wait10;
    private static final String NAME_PROJECT = "Hello world";

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        }
        return wait5;
    }

    protected WebDriverWait getWait2() {
        if (wait2 == null) {
            wait2 = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        }
        return wait2;
    }

    protected WebDriverWait getWait10() {
        if (wait10 == null) {
            wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        }
        return wait10;
    }


    public void scroll(int deltaY) {
        new Actions(getDriver())
                .scrollFromOrigin(WheelInput.ScrollOrigin.fromViewport(), 0, deltaY)
                .perform();
    }

    public void clickPerform(By by) {
        new Actions(getDriver())
                .click(getDriver().findElement(by))
                .perform();
    }

    public void clickPerform(WebElement webElement) {
        new Actions(getDriver())
                .click(webElement)
                .perform();
    }

    public void CreateFreestyleProjectJob(String nameProject) {
        getDriver().findElement(
                By.xpath("//*[@id='tasks']/div[1]/span/a")
        ).click();
        getDriver().findElement(
                By.name("name")
        ).sendKeys(NAME_PROJECT);
        getDriver().findElement(
                By.cssSelector(".icon-freestyle-project")
        ).click();

        clickPerform(By.xpath("//*[@id='ok-button']"));
        getDriver().findElement(
                By.xpath("//*[@name='description']")
        ).sendKeys(NAME_PROJECT.concat(" java test program"));
        scroll(600);

        clickPerform(getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@for='radio-block-1']"))));
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@checkdependson='credentialsId']")
        )).sendKeys("https://github.com/kriru/firstJava.git");
        scroll(2000);

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.id("yui-gen9-button")
        )).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.id("yui-gen16")
        )).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@name='description']")
        )).sendKeys("javac ".concat(NAME_PROJECT.concat(".java\njava ".concat(NAME_PROJECT))));

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.name("Apply")
        )).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.name("Submit")
        )).click();
        clickPerform(getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='tasks']/div[4]/span/a"))));
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='model-link inside build-link display-name']")
        )).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='icon-terminal icon-xlg']")
        )).click();
    }

    @Test
    public void testFirstJenkins() {

        getDriver().findElement(By.xpath("//*[@id='tasks']//span//span")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//*[@class='h3']")).getText(),
                "Enter an item name");

    }

    @Test
    public void testSecondJenkins() {

        List<String> expectedResult = Arrays.asList(
                "New Item",
                "People",
                "Build History",
                "Manage Jenkins",
                "My Views"
        );

        List<WebElement> sidePanel = getDriver().findElements(
                By.xpath("//div/div[@class='task ']")
        );

        List<String> actualResult = new ArrayList<>();

        for (WebElement task : sidePanel) {
            actualResult.add(task.getText());
        }

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void CreateFreestyleProject() {
        CreateFreestyleProjectJob("RedRover");

        Assert.assertTrue(getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='console-output']")
        )).getText().contains("Finished: SUCCESS"));
    }
}
