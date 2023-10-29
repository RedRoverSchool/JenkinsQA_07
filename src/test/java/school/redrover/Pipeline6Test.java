package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline6Test extends BaseTest {

  @Test
  void testCreatePipeline() {
    getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

    getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys("PipelineUniqueName");

    getDriver().findElement(By.xpath("//div[@class='desc' and contains(text(), 'Orchestrates long-running')]")).click();

    getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

    getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

    Assert.assertEquals(
      getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
      "Pipeline PipelineUniqueName"
    );

    Assert.assertEquals(
      getDriver().findElement(By.xpath("//div[@class='alert alert-info']")).getText(),
      "No data available. This Pipeline has not yet run."
    );
  }
}
