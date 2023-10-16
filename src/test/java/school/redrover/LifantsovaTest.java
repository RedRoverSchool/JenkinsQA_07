package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class LifantsovaTest extends BaseTest {

    @Ignore
    @Test
    public void testSearch() throws InterruptedException {

        getDriver().get("https://resh.edu.ru/");

        Thread.sleep(6000);

        WebElement textBox = getDriver().findElement(By.id("search"));
        textBox.sendKeys("Математика");

        WebElement searchButton = getDriver().findElement(By.xpath("/html/body/div[1]/div/header/div/div/div[2]/form/div/button"));
        searchButton.click();

        Thread.sleep(3000);

        WebElement title = getDriver().findElement(By.className("content-title"));
        String value = title.getText();
        Assert.assertEquals(value, "РЕЗУЛЬТАТЫ ПОИСКА");
    }

    @Test
    public void testSearch1(){

        getDriver().get("https://resh.edu.ru/");

//        Thread.sleep(3000);

        WebElement element = getDriver().findElement(By.xpath("/html/body/div[1]/div/header/nav/div/div/a[1]"));
        element.click();

//        Thread.sleep(3000);

        WebElement title = getDriver().findElement(By.className("content-title"));
        String value = title.getText();
        Assert.assertEquals(value, "УЧЕБНЫЕ ПРЕДМЕТЫ");
    }

    @Ignore
    @Test
    public void testSearch2() throws InterruptedException {

        getDriver().get("https://resh.edu.ru/");

        Thread.sleep(3000);

        WebElement href1 = getDriver().findElement(By.xpath("/html/body/div[1]/div/main/div[2]/div/div[2]/a[3]"));
        href1.click();

        WebElement title1 = getDriver().findElement(By.className("content-title"));
        String value1 = title1.getText();
        Assert.assertEquals(value1, "МУЗЕИ");

        WebElement href2 = getDriver().findElement(By.xpath("/html/body/div[1]/div/main/div[1]/div/div/div[5]/a[5]/div[2]"));
        href2.click();

        getDriver().get("https://www.culture.ru/institutes/4445/muzei-usadba-n-g-chernyshevskogo-g-saratova");

        Thread.sleep(5000);

        WebElement title2 = getDriver().findElement(By.className("tAsaH"));
        String value2 = title2.getText();
        Assert.assertEquals(value2, "Музей-усадьба Н.Г. Чернышевского г. Саратова");
    }

    @Ignore
    @Test
    public void testSearch3(){

        JenkinsUtils.login(getDriver());

        Assert.assertEquals(
                getDriver().findElement(By.id("jenkins-name-icon")).getText(),
                "Jenkins");

        WebElement href = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a"));
        href.click();

        WebElement title = getDriver().findElement(By.className("h3"));
        String value = title.getText();
        Assert.assertEquals(value, "Enter an item name");
    }
}
