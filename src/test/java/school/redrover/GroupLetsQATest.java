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
        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(1));
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
        createAnItem("Freestyle project");

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
        createAnItem("Freestyle project");

        Actions action = new Actions(getDriver());
        action.moveToElement(
                getDriver().findElement(By.xpath("//td[@class = 'jenkins-table__cell--tight jenkins-table__icon healthReport']"))
                )
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//td[@class = 'jenkins-table__cell--tight jenkins-table__icon healthReport']/div/*"))
                        .getAttribute("tooltip"),
                "100%");
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
        String actualdMessageText = wait.until(ExpectedConditions.
                visibilityOfElementLocated((By.id("itemname-invalid")))).getText();

        Assert.assertEquals(actualdMessageText,"» A job already exists with the name ‘New Folder’");

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

    @Test
    public void testItemTitlesListForCopyByLetterIsDisplayed(){
        createAnItem("Folder");
        createAnItem("Folder");
        createAnItem("Folder");

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Folder");
        getDriver().findElement(By.id("from")).sendKeys("N");

       Assert.assertTrue(getDriver().findElement(By.cssSelector(".yui-ac-content[style='width: 420px; height: 75px;']")).isDisplayed());
    }

    @Test
    public void testItemFromOtherExistingListIsHighlighted(){
        createAnItem("Folder");
        createAnItem("Folder");
        createAnItem("Folder");

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Folder");
        getDriver().findElement(By.id("from")).sendKeys("N");
        List<WebElement> s = getDriver().findElements(By.cssSelector(".yui-ac-content[style='width: 420px; height: 75px;'] .yui-ac-bd ul li"));
        new Actions(getDriver())
                .moveToElement(s.get(1))
                .perform();
        Assert.assertTrue(getDriver().findElement(By.cssSelector(".yui-ac-prehighlight")).isDisplayed());

    }

    @Test
    public void testCreateNewItemFromOtherExisting(){
        String newItem = "Item from New Folder";

        createAnItem("Folder");
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newItem);
        getDriver().findElement(By.id("from")).sendKeys("N");
        getDriver().findElement(By.xpath("//div[@class='yui-ac-content'][@style='width: 420px; height: 25px;'] //div //li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertTrue(isItemTitleExists(newItem));
    }
    @Test
    public void testErrorSpecifyWhichJobToCopyRedirect(){
        String newItem = "Item from New Folder";

        createAnItem("Folder");
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newItem);
        WebElement copyFromField = getDriver().findElement(By.id("from"));
        copyFromField.sendKeys("N");
        copyFromField.clear();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel p")).getText(),
                "Specify which job to copy");
    }

    @Test
    public void testErrorNoSucJobRedirect(){
        String newItem = "Item from New Folder";

        createAnItem("Folder");
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newItem);
        WebElement copyFromField = getDriver().findElement(By.id("from"));
        copyFromField.sendKeys("Test");
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel p")).getText(),
                "No such job: Test");
    }
    @Ignore
    @Test
    public void testDashboardDropDownMenuTools() throws InterruptedException {
        Actions action = new Actions(getDriver());
        action.moveToElement(
                getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']/a[@href = '/']")))
                .perform();

        Thread.sleep(100);
        getDriver().findElement(By.xpath("//li[@class = 'jenkins-breadcrumbs__list-item']//button[@class = 'jenkins-menu-dropdown-chevron']")).click();

        action.moveToElement(
                getDriver().findElement(By.xpath("//a[@class = 'jenkins-dropdown__item' and @href = '/manage']")))
                .perform();

        getDriver().findElement(By.xpath("//a[@href = '/manage/configureTools']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1")).getText(),"Tools");
    }

    @Ignore
    @Test
    public void testUseSearchForCreatedJob() {
        createAnItem("Freestyle project");
        String tooltipExpected = getDriver().findElement(By.xpath("//td/a[@class = 'jenkins-table__link model-link inside']")).getText();

        getDriver()
                .findElement(By.xpath("//input[@role = 'searchbox']"))
                .sendKeys("new");

        String tooltipActual = getDriver().findElement(By.xpath("//div[@id = 'search-box-completion']//li[1]")).getText();
        Assert.assertEquals(tooltipActual, tooltipExpected);
    }

    @Test
    public void testNewStyleCreation() {
        createAnItem("Freestyle project");

        getDriver().findElement(By.xpath("//a[@href = '/newView']")).click();

        String styleNameInput = "My new style";
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(styleNameInput);

        getDriver().findElement(By.xpath("//fieldset[@class = 'jenkins-fieldset']//label[@for = 'hudson.model.ListView']")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();

//        getDriver().findElement(By.xpath("//input[@name = 'item_New Freestyle project']")).click(); - the line will be discussed during our team meeting, that's why I left it commented in the code

        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'tabBar']//a[@href = '/view/My%20new%20style/']")).getText(), styleNameInput);
    }
}