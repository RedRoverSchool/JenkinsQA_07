package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.ArrayList;
import java.util.Arrays;

public class KPNewItemTest extends BaseTest {
    private final String ITEMFIELDMASSAGE = "» This field cannot be empty, please enter a valid name";
    private final String ITEMRequiredMASSAGE = "» Required field";
    private final By FREESTYLEPROJBYTTON = By.xpath("//span[text()='Freestyle project']");
    private final By PIPELINEBYTTON = By.xpath("//span[text()='Pipeline']");
    private final By MULCONFIGPROJBYTTON = By.xpath("//span[text()='Multi-configuration project'] ");
    private final By SPANBYTTON = By.xpath("//span[text()='Folder'] ");
    private final By MULTPIPELBYTTON = By.xpath("//span[text()='Multibranch Pipeline']");
    private final By ORGANIZFOLDERBYTTON = By.xpath("//span[text()='Organization Folder']");


    @Test
    public void testProjectNameMissingMassage (){
        WebElement newItem =  getDriver().findElement(
                By.xpath("//div[@id='tasks']//a[1]"));
        newItem.click();

        ArrayList<By> text = new ArrayList<>(Arrays.asList(
                FREESTYLEPROJBYTTON,PIPELINEBYTTON,MULCONFIGPROJBYTTON,
                SPANBYTTON,MULTPIPELBYTTON,ORGANIZFOLDERBYTTON));

        for(int a=0;a< text.size();a++)
        {
            WebElement button = getDriver().findElement(text.get(a));
            button.click();

            String itemValidtextMassage = getDriver().findElement(
                    By.xpath("//div[@id='itemname-required']" +
                            "[text()='» This field cannot be empty, please enter a valid name']")).getText();
            Assert.assertEquals(itemValidtextMassage,ITEMFIELDMASSAGE);
        }
    }

    @Test
    public void testOkButtonIsBlocked (){
         boolean okButtonIsClickable = false;

        WebElement newItem =  getDriver().findElement(
                By.xpath("//div[@id='tasks']//a[1]"));
        newItem.click();

        WebElement okButtonStatus =getDriver().findElement(
                By.xpath("//div[@class = 'btn-decorator']/button" +
                        "[@class= 'jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width disabled']"));
        boolean cheakButton = okButtonStatus.isEnabled();
        Assert.assertEquals(cheakButton,okButtonIsClickable);
    }
}
