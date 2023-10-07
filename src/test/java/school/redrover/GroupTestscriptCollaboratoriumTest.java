package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GroupTestscriptCollaboratoriumTest {
    @Test
    public void getGuru() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.guru99.com/");

        String title = driver.getTitle();
        assertEquals("Meet Guru99 – Free Training Tutorials & Video for IT Courses", title);


        WebElement JUnitButton = driver.findElement(By.xpath("//*[@data-lasso-id='147439']"));
        JUnitButton.click();

        Thread.sleep(900);

        WebElement textButton = driver.findElement(By.xpath("//*[@id='post-862']/div/div/h2[2]"));
        Assert.assertEquals(textButton.getText(),"JUnit Tutorial Syllabus");

        driver.quit();
    }
}
