package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertTrue;

public class Header2Test extends BaseTest {

    private void goToJenkinsHomePage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void createFreeStyleProject(String projectName) {
        goToJenkinsHomePage();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testExactMatchSearchFunctionality(){
        String itemName = "Test project";
        createFreeStyleProject(itemName);

        getDriver().findElement(By.name("q")).click();
        getDriver().findElement(By.name("q")).sendKeys(itemName);
        new Actions(getDriver()).sendKeys(Keys.ENTER).perform();

        String title = getDriver().getTitle();
        boolean isStatusPageSelected = getDriver()
                .findElement(By.linkText("Status"))
                .getAttribute("class")
                .contains("active");

        assertTrue(title.contains(itemName));
        assertTrue(isStatusPageSelected);
    }

}
