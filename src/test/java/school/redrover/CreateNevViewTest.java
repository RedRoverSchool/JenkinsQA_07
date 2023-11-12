package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


  public class CreateNevViewTest extends BaseTest {
     private static final String PROJECT_NAME = "MyProject";
     
    @Test
    public void testCreateFreestPro() {
        getDriver().findElement(By.xpath("//a [@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String newFreestPro = getDriver().findElement(By.xpath(
                "//tr[@id=\"job_MyProject\"]/td[3]/a/span")).getText();
        Assert.assertEquals(newFreestPro,"MyProject");
    }

    @Test (dependsOnMethods = "testCreateFreestPro")
    public void testCreateView() {
        getDriver().findElement(By.xpath("//a[@aria-label='New View']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("My view");
        getDriver().findElement(By.xpath("//label[@for = 'hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.name("Submit")).click();

        String nameView = getDriver().findElement(By.xpath(
                "//div[@id='main-panel']/div[2]/div[1]/div[2]/a")).getText();
        Assert.assertEquals(nameView, "My view");
    }

      @Test (dependsOnMethods = "testCreateView")
      public void testRenameView() {
          getDriver().findElement(By.xpath("//div[@id='projectstatus-tabBar']/div/div[1]/div[2]/a")).click();
          getDriver().findElement(By.xpath("//div[@id='tasks']/div[4]/span/a")).click();
          getDriver().findElement(By.name("name")).clear();
          getDriver().findElement(By.name("name")).sendKeys("Job view");
          getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

          Assert.assertEquals(getDriver().findElement(
                  By.xpath("//div[@id=\"main-panel\"]/div[2]/div[1]/div[2]/a")).getText(),
                  "Job view");
      }
  }
