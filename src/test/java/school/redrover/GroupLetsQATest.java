package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;





public class GroupLetsQATest extends BaseTest {


    private void createAnItem(String itemName) {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        String createdItemName = "New "+ itemName;

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(createdItemName);
    try {
        List<WebElement> items = getDriver().findElements(By.cssSelector(".label"));
        for (WebElement el : items){
            if (itemName.equals(el.getText())){
                el.click();
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        } catch (Exception timeoutException){
            System.out.println("Error: Wrong Item name");
        }
    }

    @Test
    public void testDescriptionTextAreaAppears() {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement addDescriptionButton = getDriver().findElement(By.id("description-link"));
        addDescriptionButton.click();
        try {
            WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-input")));
            Assert.assertTrue(true);
        } catch (Exception TimeoutException) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testSaveDescriptionButtonAppears() {
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement addDescriptionButton = getDriver().findElement(By.id("description-link"));
        addDescriptionButton.click();

        try {
            WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-button--primary")));
            Assert.assertTrue(true);
        } catch (Exception TimeoutException) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void searchBoxJenkinsTest() {
        WebElement searchBox = getDriver().findElement(By.name("q"));
        searchBox.sendKeys("admin");
        searchBox.sendKeys(Keys.ENTER);

        try {
            WebElement searchResult = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]"));
            Assert.assertTrue(searchResult.getText().contains("admin"));
        } catch (Exception e) {
            System.out.println("You have no admin user");
        }
    }

    @Test
    public void versionJenkinsTest() {
        WebElement versionBox = getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"));

        Assert.assertEquals(versionBox.getText(), "Jenkins 2.414.2");
    }

    @Ignore
    @Test
    public void newItemButtonTest() {
        WebElement newItemButton = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItemButton.click();

        WebElement newItemSpan = getDriver().findElement(By.cssSelector(".h3"));

        Assert.assertEquals(newItemSpan.getText(), "Enter an item name");
    }

    @Test
    public void testNewFolderCreation() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("123");

        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        List<WebElement> itemsList = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));
        boolean result = false;
        for (WebElement e : itemsList) {
            if (e.getText().equals("123")) {
                result = true;
                break;
            }
        }

        Assert.assertTrue(result);
    }

    @Test
    public void testProceedAboutJenkins() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//a[@href='about']")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.className("app-about-paragraph"))
                .getText().contains("The leading open source automation server"));
    }

    @Test
    public void testErrorMessageIsDisplay(){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testOkButtonIsDisabled(){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
    }

    @Test
    public void testIsItemSelected(){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        List<WebElement> items = getDriver().findElements(By.cssSelector("#items li"));
        for (WebElement el : items){
            String locator = el.getAttribute("class");
            el.click();

            Assert.assertEquals(getDriver().findElement(By.className(locator)).getAttribute("aria-checked"), "true");
        }
    }

    @Test
    public void testMyViewsLegendIconColor() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("123");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        getDriver().findElement(By.id("button-icon-legend")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class = 'build-status-icon__wrapper icon-red icon-lg']")).getCssValue("color"), "rgba(230, 0, 31, 1)");
    }

    @Test
    public void testMenuDropdownIconIsDisplayed(){
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        createAnItem("Folder");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        WebElement clickable = getDriver().findElement(By.cssSelector("[data-href='http://localhost:8080/job/New%20Folder/']"));
        new Actions(getDriver())
                .click(clickable)
                .perform();

         Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='tippy-content']")).isDisplayed());


    }


}