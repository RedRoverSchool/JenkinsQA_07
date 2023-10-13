package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AbramovTest extends BaseTest {

    private void openMainPage() {
        getDriver().get("https://bandcamp.com/");
    }
    @Test
    public void testTitle() {
        openMainPage();

        Assert.assertEquals(getDriver().getTitle(),"Bandcamp");
    }

    @Test
    public void testSearchByTagSelect() {
        openMainPage();

        WebElement searchForm = getDriver().findElement(By.xpath("//div[@id='corphome-autocomplete-form']//input[contains(@class, 'search-bar')]"));
        searchForm.click();

        WebElement electronicTag = getDriver().findElement(By.xpath("//ul[@class='genre-list']//a[contains(@href, 'electronic')]"));
        electronicTag.click();

        String title = getDriver().findElement(By.xpath("//h1[@class='name']")).getText();

        Assert.assertEquals(title, "electronic");
    }

    @Test
    public void testSearchByTagType() {
        openMainPage();

        WebElement searchForm = getDriver().findElement(By.xpath("//div[@id='corphome-autocomplete-form']//input[contains(@class, 'search-bar')]"));
        searchForm.sendKeys("trance");

        WebElement typedTag = getDriver().findElement(By.xpath("//li[@class='simple results-tags']//span[contains(text(),'trance')]"));
        typedTag.click();

        String title = getDriver().findElement(By.xpath("//h1[@class='name']")).getText();
        Assert.assertEquals(title, "trance");
    }
}
