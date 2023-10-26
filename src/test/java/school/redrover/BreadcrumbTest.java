package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class BreadcrumbTest extends BaseTest {


  private void searchDashboardOnBreadcrumbBarAndClick() {
    WebElement breadcrumbBar = getDriver().findElement(By.id("breadcrumbBar"));
    List<WebElement> breadcrumbItems = breadcrumbBar.findElements(By.cssSelector("li.jenkins-breadcrumbs__list-item"));

    for (WebElement item : breadcrumbItems) {

      if (item.getText().equals("Dashboard")) {
        getDriver().findElement(By.xpath(
            "//a[@href='/' and contains(@class, 'model-link')]")).click();
        break;
      }
    }
  }


  @Test

  public void testComebackTheMainPageByClickingOnAnyPage() {

    final String mainPageLocator = "Main Page";
    final String projectName1 = "FreeStyleProject1";
    final String projectName2 = "FreeStyleProject2";


    WebElement descriptionButton = getDriver().findElement(By.id("description-link"));
    descriptionButton.click();
    WebElement descriptionField = getDriver().findElement(By.xpath(
        "//textarea[@class='jenkins-input   ']"));
    descriptionField.sendKeys(mainPageLocator);
    getDriver().findElement(By.xpath(
        "//button[@name='Submit' and contains(@class, 'jenkins-button jenkins-button--primary')]")).click();


    //Assert.assertTrue(newDescriprtion.contains(addDescription));
    //Assert.assertTrue(getDriver().findElement(By.id("description")).contains(addDescription));


    getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href='cloud/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));


    getDriver().findElement(By.xpath("//a[@href = '/asynchPeople/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));


    getDriver().findElement(By.xpath("//a[@href = '/view/all/builds']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/me/my-views']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = 'api/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));


    getDriver().findElement(By.xpath(
        "//button[@ class = 'jenkins-button jenkins-button--tertiary jenkins_ver']")).click();
    getDriver().findElement(By.xpath("//a[@href = '/manage/about']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
    getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName1);
    getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
    getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));


    getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
    getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName2);
    getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
    getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

    getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//span[contains(text(), 'FreeStyleProject1')]")).click();
    getDriver().findElement(By.xpath("//a[@href = '/job/FreeStyleProject1/changes']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//span[contains(text(), 'FreeStyleProject1')]")).click();
    getDriver().findElement(By.xpath("//a[@href = '/job/FreeStyleProject1/ws/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//span[contains(text(), 'FreeStyleProject1')]")).click();
    getDriver().findElement(By.xpath("//a[@href = '/job/FreeStyleProject1/configure']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//span[contains(text(), 'FreeStyleProject1')]")).click();
    getDriver().findElement(By.xpath("//a[@href = '/job/FreeStyleProject1/confirm-rename']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//span[contains(text(), 'FreeStyleProject1')]")).click();
    getDriver().findElement(By.xpath("//a[@href = '/job/FreeStyleProject1/buildTimeTrend']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'configure']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'configureTools']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'pluginManager']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'computer']")).click();
    getDriver().findElement(By.xpath("//a[@href = '../computer/(built-in)/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));


    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'user/admin/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'user/admin/']")).click();
    getDriver().findElement(By.xpath("//a[@href = '/user/admin/credentials']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'administrativeMonitor/OldData/']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'cli']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'script']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = 'prepareShutdown']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));

    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
    searchDashboardOnBreadcrumbBarAndClick();
    Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(mainPageLocator));


  }
}



















