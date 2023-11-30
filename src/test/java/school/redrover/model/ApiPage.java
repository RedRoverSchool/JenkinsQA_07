package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.HomePage;

public class ApiPage extends HomePage {

    public ApiPage(WebDriver driver) {
        super(driver);
    }

    public Boolean getApiUrl() {return getDriver().getCurrentUrl().contains("api");}
    public String getTitlePage() {
        return getDriver().getTitle();
    }

    public Boolean getRestApi() {return getDriver().findElement(By.xpath("//*[text()='REST API']")).isDisplayed();}
}
