package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder17Test extends BaseTest {

    private void  comebackToDashboard() {

        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]")).click();
    }

    private void creatingNewFolder (String folderName){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        comebackToDashboard();
    }

    @Test
    public void testCreating2Folders () {

        creatingNewFolder("FirstFolder");
        comebackToDashboard();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_FirstFolder\"]/td[3]/a/span")).getText(),
                "FirstFolder");

        creatingNewFolder("SecondFolder");
        comebackToDashboard();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_SecondFolder\"]/td[3]/a/span")).getText(),
                "SecondFolder");
    }



    @Test
    public void testNestedFolder(){
        creatingNewFolder("FirstFolder");
        comebackToDashboard();

        creatingNewFolder("SecondFolder");
        comebackToDashboard();

        getDriver().findElement(By.xpath("//*[@id=\"job_SecondFolder\"]/td[3]/a/span")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/SecondFolder/move']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/select")).click();
        getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/form/select/option[2]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/button")).click();


        comebackToDashboard();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_FirstFolder\"]/td[3]/a/span")).getText(),
                "FirstFolder");

        getDriver().findElement(By.xpath("//*[@id=\"job_FirstFolder\"]/td[3]/a/span")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_SecondFolder\"]/td[3]/a/span")).getText(),
                "SecondFolder");
    }

}




