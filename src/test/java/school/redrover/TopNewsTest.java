package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


@Ignore
public class TopNewsTest {
    ChromeDriver driver;
    ChromeOptions chromeOptions;
    private final String URL = "https://topnews.ru";


    @BeforeMethod
    public void initBrowser() {
        this.chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        this.driver = new ChromeDriver(chromeOptions);

    }

    @Test(description = "Сравнение контента заголовков в первом фрейме и в первом блоке боковой панели ")
    public void contentTest() {
        driver.get(URL);
        String mainTitle = driver.findElement(By.xpath("//div[@class = 'first-news-title']")).getText();
        driver.findElement(By.xpath("//div[@class = 'first-news-title']")).click();
        String sideTitle = driver.findElement(By.xpath(" //div[@class ='top-news-item'][1]")).getText();

        Assert.assertEquals(mainTitle, sideTitle, "Заголовки не совпадают");
    }

    @Test(description = "Сравнение главного заголовка в разделе о проекте ", priority = 1)
    public void contentTest2() {
        String TITLE_ABOUT = "TOPNEWS — этот рейтинг делаешь только ты!";
        driver.get(URL);
        driver.findElement(By.id("menu-item-73200")).click();
        driver.findElement(By.id("menu-item-73201")).click();
        String getTitleAboutProject = driver.findElement(By.className("about-title")).getText();

        Assert.assertEquals(getTitleAboutProject, TITLE_ABOUT, "Заголовки не совпадают");

    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }


}
