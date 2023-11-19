package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.NewItemPage;
import school.redrover.model.OrganizationFolderConfigurationPage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrganizationFolderTest extends BaseTest {
    private static final String PROJECT_NAME = "Organization Folder";
    private static final String NEW_PROJECT_NAME = "Organization Folder Renamed";

    @DataProvider(name = "random unsafe character")
    public Object[][] provideUnsafeCharacters() {
        String[] wrongCharacters = {"!", "@", "#", "$", "%", "^", "&", "*", "?", "|", ">", "[", "]"};
        int randomIndex = new Random().nextInt(wrongCharacters.length);
        return new Object[][]{{wrongCharacters[randomIndex]}};
    }

    private String getName(int nameLength) {
        return "a".repeat(nameLength);
    }

    @Test
    public void testCreateOrganizationFolderWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectOrganizationFolder()
                .clickOk(new OrganizationFolderConfigurationPage(getDriver()))
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithValidName")
    public void testCreateOrganizationFolderWithExistingName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(PROJECT_NAME)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘" + PROJECT_NAME + "’");
    }

    @Test(dataProvider = "random unsafe character")
    public void testCreateProjectWithUnsafeCharacters(String unsafeChar) {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(unsafeChar)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» ‘" + unsafeChar + "’ is an unsafe character");
    }

    @Test
    public void testCreateOrganizationFolderWithEmptyName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectOrganizationFolder()
                .getRequiredNameErrorMessage();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled(), "OK button should NOT be enabled");
    }

    @Test
    public void testCreateOrganizationFolderWithSpaceInsteadOfName() {
        final String nameWithSpace = " ";
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(nameWithSpace)
                .selectOrganizationFolder()
                .clickOk(new NewItemPage(getDriver()))
                .getNoNameErrorMessage();

        Assert.assertEquals(errorMessage, "No name is specified");
    }

    @Test
    public void testCreateOrganizationFolderWithLongName() {
        final String longName = getName(256);
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(longName)
                .selectOrganizationFolder()
                .clickOk(new NewItemPage(getDriver()))
                .getRequestErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test
    public void testCreateOrganizationFolderWithInvalidNameWithTwoDots() {
        final String name = "..";
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(name)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» “..” is not an allowed name");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled(), "OK button should NOT be enabled");
    }

    @Test
    public void testCreateOrganizationFolderWithInvalidNameWithDotAtEnd() {
        final String name = "name.";
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .typeItemName(name)
                .selectOrganizationFolder()
                .getInvalidNameErrorMessage();

        Assert.assertEquals(errorMessage, "» A name cannot end with ‘.’");
    }

    private void returnHomeJenkins() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createProject(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void createOrganizationFolder(String organizationFolderName) {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderName);
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        returnHomeJenkins();
    }

    private void clickNewJobButton() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
    }

    private void clickOrganizationFolderButton() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Organization Folder')]")).click();
    }

    private void clickOkButton() {
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void setFolderName(String name) {
        getDriver().findElement(By.name("name")).sendKeys(name);
    }

    private void createOrganizationFolderBySteps(String folderName) {
        clickNewJobButton();
        setFolderName(folderName);
        clickOrganizationFolderButton();
        clickOkButton();
    }

    @Test
    public void testRenameProjectFromProjectPage() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectFromDashboardDropdownMenu() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//*[@id='job_" + PROJECT_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameProjectWithSameName() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//*[@id='job_" + PROJECT_NAME + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div//p")).getText(), "The new name is the same as the current name.");
    }

    @Test
    public void testDisableProject() {
        createProject(PROJECT_NAME);

        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.id("enable-project")).getText().substring(0, 46), "This Organization Folder is currently disabled");
    }

    @Test
    public void testCloneNotExistingJob() {
        String organizationFolderName = "Organization Folder";
        String organizationFolderWrongName = "Organization Folder Wrong";
        String organizationFolderCloneName = "Organization Folder Clone";
        String errorTitle = "Error";
        String errorMessage = "No such job:";

        createOrganizationFolder(organizationFolderName);
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderCloneName);
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys(organizationFolderWrongName);
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), errorTitle);
        Assert.assertTrue(getDriver().findElement(By.xpath("//p")).getText().contains(errorMessage));
    }

    @Test
    public void testCloneOrganizationFolder() {
        String organizationFolderName = "Organization Folder Parent";
        String organizationFolderCloneName = "Organization Folder Clone";
        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderName);
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement checkBox = getDriver().findElement(By.xpath("//section[2]//label[contains(text(), 'Periodically if not otherwise run')]"));
        actions.moveToElement(checkBox).click().build().perform();
        getDriver().findElement(By.name("Submit")).click();
        returnHomeJenkins();

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(organizationFolderCloneName);
        getDriver().findElement(By.xpath("//input[@id='from']")).sendKeys(organizationFolderName);
        getDriver().findElement(By.id("ok-button")).click();
        boolean isCheckBoxChecked = getDriver().findElement(By.xpath("//section[2]//input[@id=\"cb2\"]")).isSelected();
        Assert.assertTrue(isCheckBoxChecked);
        getDriver().findElement(By.name("Submit")).click();
        returnHomeJenkins();

        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@id='job_" + organizationFolderName + "']//td//span")).getText(), organizationFolderName);
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@id='job_" + organizationFolderCloneName + "']//td//span")).getText(), organizationFolderCloneName);
    }

    @Test
    public void testDisableExistingOrganizationFolder() {

        final String folderName = "OrganizationFolder";

        createProject(folderName);

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']", folderName))).click();
        getDriver().findElement(By.name("Submit")).click();
        WebElement submitEnable = getDriver().findElement(By.name("Submit"));

        Assert.assertTrue(submitEnable.isDisplayed() && submitEnable.getText().contains("Enable"), "Folder is enable!");
    }

    @Test
    public void testDisableByButton() {
        final String folderName = "OrganizationFolderEnable";

        createProject(folderName);

        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//span[text()='OrganizationFolderEnable']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@name='Submit']")).getText(),
                "Enable");
        Assert.assertTrue(getDriver().findElement(By.xpath("//form[@method='post']")).getText().contains("This Organization Folder is currently disabled"));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatedNewOrganizationFolder")
    public void testOnDeletingOrganizationFolder() {
        final String folderName = "Organization_Folder";
        boolean deletetOK = true;
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName + "/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        try {
            if (getDriver().findElement(By.xpath("//table[@id ='projectstatus']")).isDisplayed()) {
                List<WebElement> elements = getDriver().findElements(By.xpath("//td/a"));
                List<String> jobs = new ArrayList<>();
                for (WebElement element : elements) {
                    jobs.add(element.getText());
                }
                deletetOK = jobs.contains(folderName);
            }
        } catch (Exception e) {
            deletetOK = false;
        }

        Assert.assertFalse(deletetOK);
    }

    @Ignore
    @Test
    public void testDeleteOrganizationFolder() {
        final String folderName = "Organization_Folder";
        createProject(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/Organization_Folder/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

    @Ignore
    @Test
    public void testRedirectAfterDeleting() {
        final String folderName = "OrganizationFolder";

        createProject(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.xpath("//a[@href='/job/OrganizationFolder/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertTrue(getDriver().getTitle().equals("Dashboard [Jenkins]"));
    }
}
