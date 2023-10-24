package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;


public class GroupJavaAutomationTest extends BaseTest {

    private void addNewItem(String nameProject) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameProject);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private boolean checkAllSearchMatches(List<WebElement> list, String searchSubString) {
        for (WebElement item : list) {
            if (item.getText().contains(searchSubString)) {
                return false;
            }
        }
        return true;
    }

    private WebElement getInputExecutors() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();
        WebElement inputExecutors = getDriver().findElement(By.name("_.numExecutors"));
        return inputExecutors;
    }

    private int getSizeTableExecutors(List<WebElement> list) {
        int sizeTable = 0;
        for (WebElement eachString: list) {
            if (eachString.getSize().getHeight() != 0) {
                sizeTable++;
            }
        }
        return sizeTable;
    }

    @Test
    public void testJenkinsHomePageAndJenkinsVersion() {

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Dashboard [Jenkins]");

        WebElement versionJenkinsButton = getDriver().findElement
                (By.xpath("//button[@type='button']"));
        String versionJenkins = versionJenkinsButton.getText();
        Assert.assertEquals(versionJenkins, "Jenkins 2.414.2");
    }

    @Test
    public void testJenkinsAddNewItemAndSearch() {

        final String nameProject = "Project_iod";
        addNewItem(nameProject);
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@id='skip2content']/following-sibling::*")).getText(), "Project " + nameProject);

    }

    @Test
    public void testJenkinsSearchItem() {

        final String searchSubString = "project";

        addNewItem("Project_1");

        WebElement inputSearch = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        inputSearch.sendKeys(searchSubString);
        inputSearch.submit();

        List<WebElement> listSearchProjects = getDriver().findElements(By.xpath("//li[@id]"));
        Assert.assertTrue(!listSearchProjects.isEmpty());
        Assert.assertTrue(checkAllSearchMatches(listSearchProjects, searchSubString));

    }

    @Test
    public void testJenkinsSearchItemWithEmptyList() {

        final String searchSubString = "projekt";

        addNewItem("Project_1");

        WebElement inputSearch = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        inputSearch.sendKeys(searchSubString);
        inputSearch.submit();

        List<WebElement> listSearchProjects = getDriver().findElements(By.xpath("//li[@id]"));

        WebElement textError = getDriver().findElement(By.xpath("//h1/following-sibling::*"));

        Assert.assertTrue(listSearchProjects.isEmpty());
        Assert.assertTrue(textError.getAttribute("class").contains("error"));

    }

    @Test
    public void testCreatedNewFolder() {
        final String nameProject = "Folder_1";

        addNewItemFolder(nameProject);

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//a[@id = 'skip2content']/following-sibling::*"))
                        .getText(),
                nameProject);
        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//h2[@class = 'h4']")).getText(),
                "This folder is empty");
    }

    private void addNewItemFolder(String nameProject) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameProject);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testIncreaseNumberExecutors() {

        WebElement inputExecutors = getInputExecutors();
        int currentNumberExecutors = Integer.parseInt(inputExecutors.getAttribute("value"));
        inputExecutors.sendKeys(Keys.ARROW_UP);
        Assert.assertEquals(currentNumberExecutors + 1, Integer.parseInt(inputExecutors.getAttribute("value")));
    }

    @Test
    public void testNegativeNumberExecutors() {

        WebElement inputExecutors = getInputExecutors();
        inputExecutors.clear();
        inputExecutors.sendKeys("-1");
        inputExecutors.submit();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Error");
    }

    @Test
    public void testGetNumberExecutorsOnMainPage() {

        final String INPUT_VALUE = "2";
        WebElement inputExecutors = getInputExecutors();
        inputExecutors.clear();
        inputExecutors.sendKeys(INPUT_VALUE);
        getDriver().findElement(By.name("Submit")).click();
        List<WebElement> listStringsTable = getDriver().findElements(By.xpath("//div[@id='executors']//table//tr"));
        Assert.assertEquals(getSizeTableExecutors(listStringsTable), Integer.parseInt(INPUT_VALUE));

    }

}

