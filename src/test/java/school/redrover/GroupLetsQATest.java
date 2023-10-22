package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;


public class GroupLetsQATest extends BaseTest {

    private boolean isItemTitleExists(String itemName){
        List<WebElement> itemsList = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));
        boolean res = false;
        if(itemsList.isEmpty()){
            return res;
        }else {
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
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String createdItemName = "New " + itemName;

        if(isItemTitleExists(createdItemName)){
            int randInt =((int)(Math.random()*100));
            createdItemName = createdItemName +randInt;

        }else{
            createdItemName = createdItemName;

        }

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
        getDriver().findElement(By.id("jenkins-name-icon")).click();

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
    public void testCreateRenameFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestFolder01");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//a[@href='/']")).click();

        Actions action = new Actions(getDriver());
        WebElement myfolder = getDriver().findElement(By.xpath("//a[@href='job/TestFolder01/']"));
        action.moveToElement(myfolder);

        getDriver().findElement(By.xpath("//*[@id='job_TestFolder01']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/TestFolder01/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")).sendKeys("-renamed");
        getDriver().findElement(By.xpath("//*[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_TestFolder01-renamed\"]/td[3]/a/span")).getText(), "TestFolder01-renamed");
    }

    @Test
    public void testTooltipSunIconText() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("123");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Actions action = new Actions(getDriver());
        action.moveToElement(
                getDriver().findElement(By.xpath("//td[@class = 'jenkins-table__cell--tight jenkins-table__icon healthReport']"))
                )
                .perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td[@class = 'jenkins-table__cell--tight jenkins-table__icon healthReport']/div/*")).getAttribute("tooltip"), "100%");
    }

    @Test
    public void testCreatedFolderIsOpened(){
        createAnItem("Folder");
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.cssSelector(".jenkins-table__link.model-link")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel h1")).getText(),"New Folder");

    }

    @Test
    public void testJobAlreadyExists(){
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        createAnItem("Folder");
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Folder");

        Assert.assertEquals(wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//div[@id='itemname-invalid']"))).getText(),
                "» A job already exists with the name ‘New Folder’");

    }

    @Test
    public void testEditDescription() throws URISyntaxException {
        String userPageUrl = new URI(getDriver().getCurrentUrl()).resolve("/user/admin/").toString();
        getDriver().get(userPageUrl);

        WebElement editDescription = getDriver().findElement(By.id("description-link"));
        editDescription.click();

        WebElement descriptionText = getDriver().findElement(By.className("jenkins-input"));
        descriptionText.clear();
        descriptionText.sendKeys("abc");

        WebElement saveButton = getDriver().findElement(By.className("jenkins-button--primary"));
        saveButton.click();

        WebElement description = getDriver().findElement(By.xpath("//*[@id=\"description\"]/div[1]"));

        Assert.assertEquals(description.getText(), "abc");
    }

    @Test
    public void testCreateNewItemFromExistingIsDisplayed(){
        boolean res;
        try {
            createAnItem("Pipeline");
            getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
            getDriver().findElement(By.xpath("//div[@class='add-item-copy yui-ac']//img")).isDisplayed();
            res = true;
        } catch (Exception NoSuchElementException){
            res = false;
        }

        Assert.assertTrue(res, "'Copy from' is not appears.");
    }
}