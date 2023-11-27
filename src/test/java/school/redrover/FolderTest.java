package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.*;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = "FolderName";
    private static final String NAME_FOR_BOUNDARY_VALUES = "A";
    private static final String RENAMED_FOLDER = "RenamedFolder";
    private static final String NESTED_FOLDER = "Nested";
    private static final String JOB_NAME = "New Job";

    @DataProvider
    public Object[][] provideUnsafeCharacters() {
        String[] wrongCharacters = {"#", "&", "?", "!", "@", "$", "%", "^", "*", "|", "/", "\\", "<", ">", "[", "]", ":", ";"};
        int randomIndex = new Random().nextInt(wrongCharacters.length);
        return new Object[][]{{wrongCharacters[randomIndex]}};
    }

    private void getDashboardLink() {
        getDriver().findElement(By.xpath("//li/a[@href='/']")).click();
    }

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void utilsGoNameField() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']")).click();
    }

    private void create(String folderName) {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(FOLDER_NAME)
                .selectItemFolder()
                .clickOk(new FolderConfigurationPage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(FOLDER_NAME));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRename() {
        HomePage homePage = new HomePage(getDriver())
                .clickJobByName(FOLDER_NAME, new FolderDetailsPage(getDriver()))
                .clickRename()
                .typeNewName(RENAMED_FOLDER)
                .clickSubmit()
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(RENAMED_FOLDER));
    }

    @Test(dependsOnMethods = "testRename")
    public void testCreateNewJob() {
        boolean isJobCreated = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickCreateJob()
                .typeItemName(JOB_NAME)
                .selectFreestyleProject()
                .clickOk(new FreestyleProjectConfigurePage(getDriver()))
                .clickSaveButton()
                .isJobExist();

        Assert.assertTrue(isJobCreated);
    }

    @Ignore
    @Test(dependsOnMethods = "testRename")
    public void testMoveFolderToFolder() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector("#name")).sendKeys(NESTED_FOLDER);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDashboardLink();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//td/a[@href='job/" + NESTED_FOLDER + "/']"))).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + NESTED_FOLDER + "/move']")).click();
        getDriver().findElement(By.name("destination")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//option[@value='/" + RENAMED_FOLDER + "']"))).click();
        getDriver().findElement(By.name("Submit")).click();
        getDashboardLink();

        getDriver().findElement(By.xpath("//li[@class='children'][1]")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/']")).click();
        getDriver().findElement(By.xpath("//li[@class='children'][2]")).click();
        getDriver().findElement(By.xpath("//a[@class='jenkins-dropdown__item']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//td/a[@class='jenkins-table__link model-link inside']")).getText(), NESTED_FOLDER);
    }


    @Test(dependsOnMethods = {"testCreate", "testRename"})
    public void testAddDisplayName() {
        final String expectedFolderDisplayName = "Best folder";

        String actualFolderDisplayName = new HomePage(getDriver())
                .clickJobByName(RENAMED_FOLDER, new FolderDetailsPage(getDriver()))
                .clickConfigure()
                .typeDisplayName(expectedFolderDisplayName)
                .clickSave()
                .goHomePage()
                .getJobDisplayName();

        Assert.assertEquals(actualFolderDisplayName, expectedFolderDisplayName);
    }

    @Ignore
    @Test
    public void testRenameFolderUsingBreadcrumbDropdownOnFolderPage() {

        final String NEW_FOLDER_NAME = "FolderNew";

        createFolder(FOLDER_NAME);

        getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), NEW_FOLDER_NAME,
                FOLDER_NAME + " is not equal " + NEW_FOLDER_NAME);
    }

    @Ignore
    @Test
    public void testErrorMessageIsDisplayedWithoutFolderName() {
        String expectedErrorMessage = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        boolean errorMessageDisplayed = getDriver().findElement(By.id("itemname-required")).isDisplayed();
        String actualErrorMessage = getDriver().findElement(By.id("itemname-required")).getText();

        Assert.assertTrue(errorMessageDisplayed, "Error message for empty name is not displayed!");
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "The error message does not match the expected message!");
    }

    @Ignore
    @Test
    public void testOKbuttonIsNotClickableWithoutFolderName() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        boolean okButtonDisabled = "true".equals(okButton.getAttribute("disabled"));

        Assert.assertTrue(okButtonDisabled, "OK button is clickable when it shouldn't be!");
    }

    @Ignore
    @Test
    public void testCreatedPipelineWasBuiltSuccessfullyInCreatedFolder() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Folder");
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Folder/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Pipeline");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Folder/job/Pipeline/build?delay=0sec']")).click();

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[@href='/job/Folder/job/Pipeline/1/console']")))
                .perform();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//a[@href='/job/Folder/job/Pipeline/1/console']")).getAttribute("tooltip"),
                "Success > Console Output");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedPipelineWasBuiltSuccessfullyInCreatedFolder")
    public void testDeletePipelineInsideOfFolder() {
        getDriver().findElement(By.xpath("//a[@href='job/Folder/']")).click();
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[@href='/job/Folder/configure']")))
                .click()
                .perform();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//a[@href='job/Pipeline/']")).click();
        getDriver().findElement(By.xpath("//a[@data-url='/job/Folder/job/Pipeline/doDelete']")).click();

        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(By.className("h4")).getText(), "This folder is empty");
    }


    @Test(dataProvider = "provideUnsafeCharacters")
    public void testCreateNameSpecialCharacters(String unsafeChar) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(unsafeChar)
                .selectItemFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» ‘" + unsafeChar + "’ is an unsafe character");
    }

    @Test
    public void testPositiveBoundaryValuesName() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .createFolder(NAME_FOR_BOUNDARY_VALUES)
                .goHomePage()
                .clickNewItem()
                .createFolder(NAME_FOR_BOUNDARY_VALUES.repeat(255))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(NAME_FOR_BOUNDARY_VALUES.repeat(255)));
    }

    @Test
    public void testNegativeBoundaryValuesName() {
        HomePage homePage = new HomePage(getDriver());

        AngryErrorPage angryErrorPage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(NAME_FOR_BOUNDARY_VALUES.repeat(256))
                .selectItemFolder()
                .clickOk(new AngryErrorPage(getDriver()));

        Assert.assertEquals(angryErrorPage.getErrorNotification(), "Oops!");
        Assert.assertEquals(angryErrorPage.getErrorMessage(), "A problem occurred while processing the request.");
    }


    @Ignore
    @Test(dependsOnMethods = "testCreate")
    public void testAddDescriptionToFolder() {
        final String descriptionText = "This is Folder's description";

        HomePage homePage = new HomePage(getDriver());
        String actualDescription = homePage
                .clickAlertIfVisibleAndGoHomePage()
                .clickAnyJobCreated(new FolderDetailsPage(getDriver()))
                .clickAddDescription()
                .typeDescription(descriptionText)
                .clickSave()
                .getActualFolderDescription();

        Assert.assertEquals(actualDescription, descriptionText);
    }

    @Ignore
    @Test(dependsOnMethods = {"testAddDescriptionToFolder"})
    public void testEditDescriptionOfFolder() {
        final String newDescriptionText = "This is new Folder's description";

        getDriver().findElement(By.xpath("//table[@id='projectstatus']//tr[1]//a[contains(@href, 'job')]")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'editDescription')]")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(newDescriptionText);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String actualNewDescription = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();
        Assert.assertEquals(actualNewDescription, newDescriptionText);
    }

    @Ignore
    @Test(dependsOnMethods = {"testAddDescriptionToFolder"})
    public void testDeleteDescriptionOfFolder() {
        getDriver().findElement(By.xpath("//table[@id='projectstatus']//tr[1]//a[contains(@href, 'job')]")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'editDescription')]")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        String textOfDescriptionField = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();
        Assert.assertEquals(textOfDescriptionField, "");

        String appearanceOfAddDescriptionButton = getDriver().findElement(By.xpath("//div[@id='description']/div[2]")).getText();
        Assert.assertEquals(appearanceOfAddDescriptionButton, "Add description");
    }

    @Ignore
    @Test
    public void testSubFolderInBreadcrumbs() {
        final String folderName = "Test Folder";
        final String subfolderName = "Test SubFolder";

        create(folderName);

        getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']",
                folderName.replace(" ", "%20")))).click();
        getDriver().findElement(By.xpath("//li/a[@href='newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(subfolderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        List<String> listBreadcrumbsItems = getDriver().findElements(
                        By.xpath("//li[@class='jenkins-breadcrumbs__list-item']/a"))
                .stream().map(WebElement::getText).toList();
        List<String> listExpectedItems = new ArrayList<>(List.of
                (new String[]{"Dashboard", folderName, subfolderName}));

        Assert.assertEquals(listBreadcrumbsItems, listExpectedItems);
    }

    @Test
    public void testAddDescription() {
        final String description = "Test123";

        createFolder("Test");

        getDriver().findElement(By.name("_.description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), description);
    }

    @Test
    public void testClickPreview() {
        createFolder(FOLDER_NAME);

        getDriver().findElement(By.name("_.description")).sendKeys("description123");
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertEquals(getDriver().findElement(By.className("textarea-preview")).getText(), "description123");
    }

    @Test
    public void testRenameWithEndingPeriod() {
        char period = '.';

        createFolder(FOLDER_NAME);
        getDashboardLink();
        getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FOLDER_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys("FOLDER_WITH_UNSAFE_CHARACTER" + period);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p")).getText(),
                "A name cannot end with ‘" + period + "’");
    }

    @Ignore
    @Test
    public void testMoveThroughSidePanel() {
        create(FOLDER_NAME);
        getDashboardLink();

        create(NESTED_FOLDER);
        getDashboardLink();

        getDriver().findElement(By.xpath("//a[@href='job/" + NESTED_FOLDER + "/']")).click();
        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath("//option[contains(text(),'Jenkins » " + FOLDER_NAME + "')]")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDashboardLink();
        getDriver().findElement(By.xpath("//a[@href='job/" + FOLDER_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + NESTED_FOLDER + "/']")).click();

        ArrayList<String> expectedBreadcrumbs = new ArrayList<>();
        expectedBreadcrumbs.add("Dashboard");
        expectedBreadcrumbs.add(FOLDER_NAME);
        expectedBreadcrumbs.add(NESTED_FOLDER);

        ArrayList<String> actualBreadcrumbs = new ArrayList<>();
        List<WebElement> breadcrumbs = getDriver().findElements(By.xpath("//li/a[@class='model-link']"));
        for (WebElement eachBreadcrumb : breadcrumbs) {
            actualBreadcrumbs.add(eachBreadcrumb.getText());
        }

        Assert.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs don't match");
    }

    @Ignore
    @Test(dependsOnMethods = "testRename")
    public void testRenameFolderThroughLeftPanelWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
        getDriver().findElement(By.xpath("//a[@ href='/job/Renamed%20Folder/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/p")).getText(), "No name is specified");
    }

    @Ignore
    @Test(dependsOnMethods = "testAddFolderDescription")
    public void testChangeFolderDescription() {
        getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("description")).sendKeys("Second description");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='description']/div[1]")).getText(), "Second description");
    }

    @Ignore
    @Test
    public void testConfigureFolderCheckConfigurationMenu() {
        getDriver().findElement(By.xpath("//a[@href = 'job/Renamed%20Folder/']")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).click();

        List<String> listOfExpectedMenuItems = Arrays.asList("General", "Health metrics", "Properties");

        List<WebElement> listOfMenuItems = getDriver().findElements(
                By.xpath("//span[@class = 'task-link-text' and contains(., '')]"));
        List<String> extractedMenuItems = listOfMenuItems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(extractedMenuItems, listOfExpectedMenuItems);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreate")
    public void folderDescriptionPreviewWorksCorrectly() {
        String description = "Folder description";
        HomePage homePage = new HomePage(getDriver());
        String previewText = homePage.clickJobByName(FOLDER_NAME, new FolderDetailsPage(getDriver()))
                .clickConfigureFolder()
                .typeDescription(description)
                .clickPreviewDescription()
                .getFolderDescription();

        Assert.assertEquals(previewText, description);
    }

    @Test
    public void testAddChildHealthMetric() {

        boolean isChildHealthMetricDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .createFolder(FOLDER_NAME)
                .clickHealthMetricsInSideMenu()
                .clickHealthMetrics()
                .clickAddHealthMetric()
                .selectChildHealthMetric()
                .clickSaveButton()
                .clickConfigureInSideMenu()
                .clickHealthMetrics()
                .isChildHealthMetricDisplayed();

        Assert.assertTrue(isChildHealthMetricDisplayed);
    }
}
