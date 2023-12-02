package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class FolderDetailsPage extends BasePage {

    @FindBy(linkText = "Configure")
    private WebElement configure;

    @FindBy(id = "description-link")
    private WebElement addOrEditDescription;

    @FindBy(className = "jenkins-input")
    private WebElement descriptionTextArea;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(xpath = "//a[contains(@href, '/newJob')]")
    private WebElement newItemButton;

    @FindBy(xpath = "//a[contains(@href,'move')]")
    private WebElement moveJob;

    @FindBy(xpath = "//a[@class='content-block__link']")
    private WebElement createJob;

    public FolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new FolderRenamePage(getDriver());
    }

    public FolderConfigurationPage clickConfigureFolder() {
        configure.click();
        return new FolderConfigurationPage(getDriver());
    }

    public FolderDetailsPage clickAddOrEditDescription() {
        addOrEditDescription.click();

        return this;
    }

    public FolderDetailsPage typeDescription(String description) {
        descriptionTextArea.clear();
        descriptionTextArea.sendKeys(description);

        return this;
    }

    public FolderDetailsPage clickSave() {
        submitButton.click();

        return this;
    }

    public String getActualFolderDescription() {
        return getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();
    }

    public NewItemPage clickCreateJob() {
        createJob.click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemButton() {
        newItemButton.click();

        return new NewItemPage(getDriver());
    }

    public FolderConfigurationPage clickConfigureInSideMenu() {
        configure.click();

        return new FolderConfigurationPage(getDriver());
    }

    public FolderMovePage clickMove() {
        moveJob.click();

        return new FolderMovePage(getDriver());
    }

    public List<String> getJobListInsideFolder() {
        List<WebElement> jobList = getDriver().findElements(By.xpath("//a[contains(@class, 'jenkins-table__link')]"));
        List<String> resultList = jobList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public <T> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + name.replace(" ", "%20") + "/']")).click();

        return page;
    }

    public FolderDetailsPage clearDescriptionTextArea() {
        descriptionTextArea.clear();

        return this;
    }

    public String getDescriptionButtonText() {
        return getDriver().findElement(By.xpath("//div[@id='description']/div[2]")).getText();
    }
}
