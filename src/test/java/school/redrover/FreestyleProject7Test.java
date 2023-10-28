package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject7Test extends BaseTest {

  @Test
  public void testCreate() {
      final String projectname = "FreestyleProject";

      getDriver().findElement(By.xpath("//*[@id=\'tasks\']/div[1]/span/a")).click();
      getDriver().findElement(By.xpath("//*[@id=\'name\']")).sendKeys(projectname);
      getDriver().findElement(By.xpath("//*[@id=\'j-add-item-type-standalone-projects\']/ul/li[1]/label/span"))
              .click();
      getDriver().findElement(By.xpath("//*[@id=\'ok-button\']"))
              .click();


      getDriver().findElement(By.xpath("//*[@id=\'bottom-sticker\']/div/button[1]")).click();
      String actualName = getDriver().findElement(By.xpath("//*[@id=\'main-panel\']/h1")).getText();

      Assert.assertEquals( "Project FreestyleProject", actualName);
  }
}

