package old;

import org.openqa.selenium.By;
import org.testng.Assert;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.Keys;

@Ignore
public class SokolovTest extends BaseTest {


    @Test(description = "Проверка Заголовка приветствия")
    public void testJenkinsAuthorization() {

        Assert.assertEquals(findAndGetTexts(Elements.item), "Start building your software project", "Заголовок не совпадает");
    }

    @Test(description = "Проверка адреса URL страницы новой Job")
    public void testCheckUrl() {

        findAndClick(Elements.createJob);

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/newJob", "URL не совпадает");
    }

    @Test
    public void testContextLinks() {

        findAndClick(Elements.myViews);
        String expectedTextResult = findAndGetTexts(Elements.contentText);

        findAndClick(Elements.meContent);
        String expectedTextResultLoginName = findAndGetTexts(Elements.elUserId).substring(16).trim();
        String actualTextResultLoginName = findAndGetTexts(Elements.mePanel).trim();

        Assert.assertEquals("This folder is empty", expectedTextResult, " context не совпадает");
        Assert.assertEquals(actualTextResultLoginName, expectedTextResultLoginName, " context не совпадает");
    }

    @Test
    public void testSearchContext() {

        findAndSendKeys(Elements.searchBox, Elements.SEARCHSTRING);
        findAndSendKeys(Elements.searchBox, String.valueOf(Keys.ENTER));

        Assert.assertEquals(findAndGetTexts(Elements.elError), "Nothing seems to match.", " context не совпадает");

    }

    @Test
    public void testCheckUrlHelpJenkins() {
        findAndClick(Elements.searchBox);
        findAndClick(Elements.icon_trailing);

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.jenkins.io/doc/book/using/searchbox/", "URL не совпадает");
    }

    private String findAndGetTexts(By element) {
        return getDriver().findElement(element).getText();
    }

    private void findAndClick(By element) {
        getDriver().findElement(element).click();

    }

    private void findAndSendKeys(By element, String name) {
        getDriver().findElement(element).sendKeys(name);

    }
}

class Elements {

    static final String SEARCHSTRING = "ASDASDASD";

    static By item = By.xpath("//h2[@class ='h4'][contains(text(), 'Start')]");
    static By createJob = By.xpath("//span[contains(text(),'Create a job')]");
    static By myViews = By.xpath("//a[@href = '/me/my-views']");
    static By contentText = By.xpath("//h2[@class ='h4']");
    static By searchBox = By.xpath("//input[@id= 'search-box']");
    static By meContent = By.xpath("//a[@href = '/me/']");
    static By icon_trailing = By.className("main-search__icon-trailing");
    static By elError = By.className("error");
    static By mePanel = By.xpath("//div[@id= 'main-panel']/h1");
    static By elUserId = By.xpath("//div[contains(text(),'Jenkins User ID')]");

}
