package old;
import org.openqa.selenium.Alert;
import org.testng.annotations.Ignore;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

@Ignore
public class VasilyiDrakeTest  extends BaseTest {


    private static final String PROJECT_NAME = "Starlight";

    private void getNewJobPage() {

        getDriver().findElement(By.xpath("//a[starts-with(@href,'/view/all/newJob') and contains (@class,'task-link')]")).click();

    }

    private void createFreestyleProject() {

        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    private void getProjectPage() {

        getNewJobPage();
        getDriver().findElement(By.xpath("//*[starts-with(@name,'name') and contains(@id,'name')]")).sendKeys(PROJECT_NAME);
        createFreestyleProject();
        getDriver().findElement(By.xpath("//*[@class = 'jenkins-button jenkins-button--primary '] ")).click();

    }


    @Test
    public void createFreestyleProjectWithoutName() {

        getNewJobPage();
        createFreestyleProject();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class = 'input-validation-message']")).getText(),
                            "» This field cannot be empty, please enter a valid name");

    }

    @Test
    public void createFreestyleProjectValidName() {

        getProjectPage();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(@class,'job')]")).getText(),
                             "Project " + PROJECT_NAME);

    }

    @Test
    public void createFreestyleProjectDuplicateName() {

        getProjectPage();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();
        getDriver().findElement(By.xpath("//*[starts-with(@name,'name') and contains(@id,'name')]")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class = 'input-validation-message']")).getText(),
                "» A job already exists with the name ‘Starlight’");

    }

    @Test
    public void deleteFreestyleProject() {

        getProjectPage();
        getDriver().findElement(By.xpath("//a[@class='task-link  confirmation-link']")).click();
        Alert popup = getDriver().switchTo().alert();
        popup.accept();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[text()='Welcome to Jenkins!']")).getText(),
                            "Welcome to Jenkins!");

    }


    }






























