package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
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
        getDriver().findElement(By.xpath("//div[@class = 'first-news-title']")).click();
        String sideTitle = getDriver().findElement(By.xpath(" //div[@class ='top-news-item'][1]")).getText();

        Assert.assertEquals(mainTitle, sideTitle, "Заголовки не совпадают");
    }


    @Test(description = "Сравнение главного заголовка в разделе о проекте ", priority = 1)
    public void testContent2() {
        String TITLE_ABOUT = "TOPNEWS — этот рейтинг делаешь только ты!";
        getDriver().get(BASEURL);
        getDriver().findElement(By.id("menu-item-73200")).click();
        getDriver().findElement(By.id("menu-item-73201")).click();
        String getTitleAboutProject = getDriver().findElement(By.className("about-title")).getText();

        Assert.assertEquals(getTitleAboutProject, TITLE_ABOUT, "Заголовки не совпадают");

    }

    @Test(description = "Проверка инициализации запуска Jenkins локально", priority = 2)
    public void testJenkins() {
        ProjectUtils.get(getDriver());
    }

}
