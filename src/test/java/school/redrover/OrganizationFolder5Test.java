package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder5Test extends BaseTest {

    @Test
    public void testVerifyWarningMessageEmptyName() {
        final String WARNING_MESSAGE_TEXT_EXPECTED = "» This field cannot be empty, please enter a valid name";
        final String CSS_COLOR_WARNING_MESSAGE_EXPECTED = "rgba(255, 0, 0, 1)";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();

        String warningMessageTextActual = getDriver().findElement(By.xpath("//div[@id=\"itemname-required\"]")).getText();
        String cssColorWarningMessageActual = getDriver().findElement(By.xpath("//div[@id=\"itemname-required\"]")).getCssValue("color");

        Assert.assertEquals(cssColorWarningMessageActual, CSS_COLOR_WARNING_MESSAGE_EXPECTED);
        Assert.assertEquals(warningMessageTextActual, WARNING_MESSAGE_TEXT_EXPECTED);
    }


}
