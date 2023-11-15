package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject23Test extends BaseTest {

    private final String PROJECT_NAME = "Freestyle_Project";

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void configureParameterizedBuild(String projectName, String choiceName, String choiceOptions) {
        getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/configure']")).click();

        WebElement parameterizedOption = getDriver().findElement(By.xpath("//div[@nameref='rowSetStart28']//span[@class='jenkins-checkbox']"));
        parameterizedOption.click();
        getDriver().findElement(By.xpath("//button[@suffix='parameterDefinitions']")).click();
        getDriver().findElement(By.linkText("Choice Parameter")).click();
        getDriver().findElement(By.name("parameter.name")).sendKeys(choiceName);
        getDriver().findElement(By.name("parameter.choices"))
                .sendKeys(choiceOptions.replace(" ", Keys.chord(Keys.SHIFT, Keys.ENTER)));
        getDriver().findElement(By.name("Submit")).click();
    }

    private void addTimeStampToConsoleOutput(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/configure']")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.linkText("REST API"))).perform();

        getDriver().findElement(By.xpath("//div[@id = 'tasks']//div[5]/span")).click();

        getDriver().findElement(By.xpath("//label[contains(text(), 'Add timestamps')]")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testParameterizedBuildWithChoices() {
        final String choices = "Chrome Firefox Edge Safari";
        final String choiceName = "browsers";

        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.id("jenkins-home-link")).click();
        configureParameterizedBuild(PROJECT_NAME, choiceName, choices);

        WebElement build = getDriver().findElement(By.xpath("//div[@id='tasks']//div[4]//a"));
        build.click();

        List<WebElement> actualChoiceList = getDriver().findElements(By.xpath("//div[@class='setting-main']//select/option"));

        Assert.assertNotEquals(getDriver().findElement(By.xpath("//div[@id='tasks']//div[4]//a")).getText(), "Build Now");
        Assert.assertEquals(getDriver().findElement(By.className("jenkins-form-label")).getText(), choiceName);
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='setting-main']//select/option[1]")).isSelected());
        Assert.assertFalse(actualChoiceList.isEmpty());
        for (WebElement ch : actualChoiceList ) {
            Assert.assertTrue(choices.contains(ch.getText()));
        }
    }

    @Test
    public void testTimeStampsInConsoleOutput() {
        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.id("jenkins-home-link")).click();

        addTimeStampToConsoleOutput(PROJECT_NAME);

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//div[@id='tasks']//div[4]//a"))));
        getDriver().findElement(By.xpath("//div[@id='tasks']//div[4]//a")).click();

        getDriver().findElement(By.xpath("//div[@id='tasks']//div[1]//a")).click();

        getDriver().findElement(By.xpath("//a[@href = 'lastBuild/']")).click();
        getDriver().findElement(By.xpath("//div[@id='tasks']//div[3]//a")).click();

        List<WebElement> logs = getDriver().findElements(By.xpath("//span[@class = 'timestamp']"));
        Assert.assertFalse(logs.isEmpty());

        for (WebElement log : logs) {
            Assert.assertTrue(log.getText().trim().matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"));
        }
    }

}
