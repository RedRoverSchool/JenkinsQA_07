package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GroupUnicornsTest {

    @Test
    public void usPsPageOpenTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.usps.com/");
        String title = driver.getTitle();
        assertEquals("Welcome | USPS", title);
        driver.quit();
    }

    @Test
    public void usPsSendMailPackageTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.usps.com/");
        WebElement send = driver.findElement(By.xpath("//a[@id='mail-ship-width']"));
        send.click();
        String sendTitle = driver.getTitle();
        assertEquals("Send Mail & Packages | USPS", sendTitle);
        driver.quit();
    }

    @Test
    public void w3SchoolTest()
    {
        WebDriver wd = new ChromeDriver();
        try
        {
            wd.get("https://www.w3schools.com/");

            //title
            String title = wd.getTitle();
            Assert.assertEquals(title, "W3Schools Online Web Tutorials");

            //H1 heading
            WebElement h1Heading = wd.findElement(By.className("learntocodeh1"));
            Assert.assertEquals(h1Heading.getText(), "Learn to Code");

            //H3 heading
            WebElement h3Heading = wd.findElement(By.className("learntocodeh3"));
            Assert.assertEquals(h3Heading.getText(), "With the world's largest web developer site.");

            //H4 heading
            WebElement h4Heading = wd.findElement(By.className("learntocodeh4"));
            Assert.assertEquals(h4Heading.getText(), "Not Sure Where To Begin?");

            //text box
            WebElement textBox = wd.findElement(By.id("search2"));

            //search button
            WebElement searchButton = wd.findElement(By.id("learntocode_searchbtn"));
            textBox.sendKeys("java tutorial");
            searchButton.click();

            //title
            title = wd.getTitle();
            Assert.assertEquals(title, "Java Tutorial");
        }
        finally
        {
            wd.quit();
        }

    }
}