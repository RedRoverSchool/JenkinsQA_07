package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FreestyleProjectSeTest extends BaseTest {
    private boolean isItemTitleExists(String itemName) {
        List<WebElement> itemsList = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));
        boolean res = false;
        if (itemsList.isEmpty()) {
            return res;
        } else {
            for (WebElement e : itemsList) {
                if (e.getText().equals(itemName)) {
                    res = true;
                    break;
                }
            }
        }

        return res;
    }

    private void createAnItem(String itemName) {
        String createdItemName = "New " + itemName;

        if (isItemTitleExists(createdItemName)) {
            int randInt = ((int) (Math.random() * 100));
            createdItemName = createdItemName + randInt;
        } else {
            createdItemName = createdItemName;
        }

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(createdItemName);
        List<WebElement> items = getDriver().findElements(By.cssSelector(".label"));
        for (WebElement el : items) {
            if (itemName.equals(el.getText())) {
                el.click();
                break;
            }
        }
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
    }

    private void createFreeStyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void hoverClickInput(String xpathLocator, String inputText) {
        new Actions(getDriver())
                .moveToElement(getDriver()
                .findElement(By.xpath(xpathLocator)))
                .click()
                .sendKeys(inputText)
                .perform();
    }

    private void hoverClick(String xpathLocator) {
        new Actions(getDriver())
                .moveToElement(getDriver()
                .findElement(By.xpath(xpathLocator)))
                .click()
                .perform();
    }

    @Test
    public void testSettingsOfDiscardOldBuildsIsDisplayed() {
        createAnItem("Freestyle project");
        WebElement checkbox = getDriver().findElement(By.cssSelector(" #cb4[type='checkbox']"));
        new Actions(getDriver())
                .click(checkbox)
                .perform();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("[nameref='rowSetStart26'] .form-container.tr"))
                .getAttribute("style"), "");

    }

    @Test(dependsOnMethods = "testSettingsOfDiscardOldBuildsIsDisplayed")
    public void testDaysToKeepBuildsErrorMessageIsDisplayed() {
        Alert alert = getWait2().until(ExpectedConditions.alertIsPresent());
        alert.accept();
        getDriver().findElement(By.cssSelector("td [href='job/New%20Freestyle%20project/']")).click();
        getDriver().findElement(By.cssSelector(".task-link-wrapper  [href='/job/New%20Freestyle%20project/configure']"))
                .click();
        WebElement checkbox = getDriver().findElement(By.cssSelector(" #cb4[type='checkbox']"));
        new Actions(getDriver())
                .click(checkbox)
                .perform();
        WebElement daysToKeepBuildsField = getDriver().findElement(By.cssSelector("input[name='_.daysToKeepStr']"));
        daysToKeepBuildsField.click();
        daysToKeepBuildsField.sendKeys("-2");
        getDriver().findElement(By.cssSelector("input[name='_.numToKeepStr']")).click();
        WebElement errorMessage =  getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@nameref='rowSetStart26']//div[@class='jenkins-form-item tr '][1]//div[@class='error']")));

        Assert.assertTrue(errorMessage.isDisplayed());
    }

    @Test(dependsOnMethods = "testDaysToKeepBuildsErrorMessageIsDisplayed")
    public void testSettingsGitIsOpened() {
        Alert alert = getWait2().until(ExpectedConditions.alertIsPresent());
        alert.accept();
        getDriver().findElement(By.cssSelector("td [href='job/New%20Freestyle%20project/']")).click();
        getDriver().findElement(By.cssSelector(".task-link-wrapper  [href='/job/New%20Freestyle%20project/configure']"))
                .click();

        WebElement radioGit = getDriver().findElement(By.cssSelector("label[for='radio-block-1']"));
        new Actions(getDriver())
                .click(radioGit)
                .perform();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".form-container.tr[nameref='radio-block-1']"))
                .getAttribute("style"), "");
    }

    @Test
    public void testAddBuildStep() {
        final String projectName = "FSproject";

        By buildStepInputLocator = By
                .xpath("//div[@class='CodeMirror-scroll cm-s-default']");

        createFreeStyleProject(projectName);
        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Actions(getDriver())
                .moveToElement(getDriver()
                        .findElement(By.xpath("//button[contains(text(), 'Add build step')]")))
                .click()
                .perform();

        new Actions(getDriver())
                .moveToElement(getDriver()
                        .findElement(By.xpath("//a[contains(text(), 'Execute shell')]")))
                .click()
                .perform();

        new Actions(getDriver())
                .moveToElement(getDriver()
                .findElement(buildStepInputLocator))
                .click()
                .sendKeys("buildStep")
                .perform();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + projectName + "/configure']")).click();
        getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']")).click();

        Assert.assertEquals(getDriver().findElement(buildStepInputLocator).getText(), "buildStep");
    }

    @Test
    public void testGitHubEditedLabelAppears() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        createAnItem("Freestyle project");

        hoverClick("//label[contains(text(), 'GitHub project')]");

        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.name("_.projectUrlStr")));

        hoverClick("//*[@id='main-panel']/form/div[1]/section[1]/div[6]/div[3]/div[2]/div[1]/button");

        hoverClickInput("//input[@name = '_.displayName']", "GitHubURL");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//span[@class = 'jenkins-edited-section-label']"))
                .getText()
                .trim(),
                "Edited");
    }

    @Test
    public void testDescriptionPreviewAppears() {
        final String inputText = "This project describes smth";
        createAnItem("Freestyle project");

        hoverClickInput("//textarea[@name = 'description']", inputText);

        getDriver().findElement(By.xpath("//a[@previewendpoint = '/markupFormatter/previewDescription']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@class = 'textarea-preview']"))
                .getText(),
                inputText);
    }

    @Test(dependsOnMethods = "testDescriptionPreviewAppears")
    public void testDescriptionPreviewHides() {
        Alert alert = getWait2().until(ExpectedConditions.alertIsPresent());
        alert.dismiss();

        hoverClick("//a[@class = 'textarea-hide-preview']");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@class = 'textarea-preview']"))
                .getCssValue("display"),
                "none");
    }
}