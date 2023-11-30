package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.HomePage;

public class ApiPage extends HomePage {

    public ApiPage(WebDriver driver) {
        super(driver);
    }
    public String getTitlePage() {
        return getDriver().getTitle();
    }
}
