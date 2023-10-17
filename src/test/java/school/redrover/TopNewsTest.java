package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;

import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import school.redrover.runner.JenkinsUtils;
import school.redrover.runner.ProjectUtils;


public class TopNewsTest extends BaseTest {

    private static final String BASEURL = "https://topnews.ru";

    @Test(description = "Сравнение контента заголовков в первом фрейме и в первом блоке боковой панели ")
    public void testContent1() {
        getDriver().get(BASEURL);
        String mainTitle = getDriver().findElement(By.xpath("//div[@class = 'first-news-title']")).getText();
        int sub = mainTitle.indexOf("(");
        mainTitle = mainTitle.substring(0, sub);
        getDriver().findElement(By.xpath("//div[@class = 'first-news-title']")).click();
        String sideTitle = getDriver().findElement(By.xpath(" //div[@class ='top-news-item'][1]")).getText();
        sideTitle = sideTitle.substring(0, sub);
        Assert.assertEquals(mainTitle, sideTitle, "Заголовки не совпадают");
    }


    @Test(description = "Сравнение главного заголовка в разделе о проекте ")
    public void testContent2() {
        String TITLE_ABOUT = "TOPNEWS — этот рейтинг делаешь только ты!";
        getDriver().get(BASEURL);
        getDriver().findElement(By.id("menu-item-73200")).click();
        getDriver().findElement(By.id("menu-item-73201")).click();
        String getTitleAboutProject = getDriver().findElement(By.className("about-title")).getText();

        Assert.assertEquals(getTitleAboutProject, TITLE_ABOUT, "Заголовки не совпадают");

    }


    @Test(description = "Проверка Заголовка приветствия")
    public void testJenkinsAuthorization() {
        JenkinsUtils.login(getDriver());
        String actualInfo = getDriver().findElement(By.xpath("//h2[@class ='h4'][contains(text(), 'Start')]")).getText();

        Assert.assertEquals(actualInfo, "Start building your software project", "Заголовок не совпадает");
    }

    @Test(description = "Проверка адреса URL страницы новой Job")
    public void testJenkins() {
        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.xpath("//span[contains(text(),'Create a job')]")).click();
        String actualURL = getDriver().getCurrentUrl();

        Assert.assertEquals(actualURL, "http://localhost:8080/newJob", "URL не совпадает");
    }

}
