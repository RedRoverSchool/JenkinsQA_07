package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.BasePage;

public class CreateNewUserPage extends BasePage {

    public CreateNewUserPage(WebDriver driver) {

        super(driver);
    }

    public  String getLabelText(String labelName){
        WebElement labelElement = getDriver().findElement(By.xpath("//div[text() = '" + labelName + "']"));
        return labelElement.getText();
    }

    public  WebElement getInputField(String labelName) {
        return getDriver().findElement(By.xpath("//div[@class='jenkins-form-label help-sibling'][text() = '"
                + labelName + "']/following-sibling::div/input"));
    }
}

