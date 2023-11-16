package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.NewItemPage;
import school.redrover.runner.BaseTest;

public class OrganizationFolderTest extends BaseTest {
    private static final String PROJECT_NAME = "Organization Folder";
    private static final String NEW_PROJECT_NAME = "Organization Folder Renamed";

    @Test
    public void testCreateOrganizationFolderWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem(new NewItemPage(getDriver()))
                .typeItemName(PROJECT_NAME)
                .selectItemType("Organization Folder")
                .clickOk()
                .goHomePage();

        Assert.assertTrue(homePage.getJobList().contains(PROJECT_NAME));
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

    @DataProvider(name = "wrong-character")
    public Object[][] provideWrongCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Test(dataProvider = "wrong-character")
    public void testCreateProjectWithInvalidChar(String invalidData) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(invalidData);

        String errorMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(errorMessage, "» ‘" + invalidData + "’ is an unsafe character");
    }

    @Test
    public void testCreateProjectWithSpaceInsteadOfName() {
        final String space = " ";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(space);
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Error");
    }

    @Test
    public void testCreateProjectWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
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
}
