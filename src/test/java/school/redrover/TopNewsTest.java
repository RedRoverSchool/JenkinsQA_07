package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;

import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.Keys;




public class TopNewsTest extends BaseTest {
    private static final String SEARCHSTRING = "ASDASDASD";

    @Test(description = "Проверка Заголовка приветствия")
    public void testJenkinsAuthorization() {

        String actualInfo = getDriver().findElement(By.xpath("//h2[@class ='h4'][contains(text(), 'Start')]")).getText();

        Assert.assertEquals(actualInfo, "Start building your software project", "Заголовок не совпадает");
    }

    @Test(description = "Проверка адреса URL страницы новой Job")
    public void testCheckUrl() {

        getDriver().findElement(By.xpath("//span[contains(text(),'Create a job')]")).click();

        String actualURL = getDriver().getCurrentUrl();

        Assert.assertEquals(actualURL, "http://localhost:8080/newJob", "URL не совпадает");
    }

    @Test
    public void testContextLinks() {

        getDriver().findElement(By.xpath("//a[@href = '/me/my-views']")).click();
        String expectedTextResult = getDriver().findElement(By.xpath("//h2[@class ='h4']")).getText();

        getDriver().findElement(By.xpath("//a[@href = '/me/']")).click();
        String expectedTextResultLoginName = getDriver().findElement(By.xpath("//div[contains(text(),'Jenkins')]"))
                .getText().substring(16).trim();
        String actualTextResultLoginName = getDriver().findElement(By.xpath("//div[@id= 'main-panel']/h1")).getText().trim();

        Assert.assertEquals(expectedTextResult, "This folder is empty", " context не совпадает");
        Assert.assertEquals(expectedTextResultLoginName, actualTextResultLoginName, " context не совпадает");
    }

    @Test
    public void testSearchContext() {

        getDriver().findElement(By.xpath("//input[@id= 'search-box']")).sendKeys(SEARCHSTRING);
        getDriver().findElement(By.xpath("//input[@id= 'search-box']")).sendKeys(Keys.ENTER);

        String expectedTextResultSearch = getDriver().findElement(By.className("error")).getText();

        Assert.assertEquals(expectedTextResultSearch, "Nothing seems to match.", " context не совпадает");

    }

    @Test
    public void testCheckUrlHelpJenkins() {
        getDriver().findElement(By.xpath("//input[@id= 'search-box']")).click();
        getDriver().findElement(By.className("main-search__icon-trailing")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.jenkins.io/doc/book/using/searchbox/", "URL не совпадает");
    }
}
