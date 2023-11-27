package school.redrover.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.HomePage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestUtils {

    private static void createProject(BaseTest baseTest, String name) {
        baseTest.getDriver().findElement(By.linkText("New Item")).click();
        baseTest.getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='name']"))).sendKeys(name);

    }

    private static void saveProject(BaseTest baseTest) {
        baseTest.getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        baseTest.getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
    }

    private static void goToHomePage(BaseTest baseTest, boolean goToHomePage) {
        if (goToHomePage) {
            baseTest.getDriver().findElement(By.linkText("Dashboard")).click();
        }
    }

    public static void createFreestyleProject(BaseTest baseTest, String name, boolean goToHomePage) {
        createProject(baseTest, name);
        baseTest.getDriver().findElement(By.xpath("//label/span[text()='Freestyle project']")).click();
        saveProject(baseTest);

        goToHomePage(baseTest, goToHomePage);
    }

    public static void createPipeline(BaseTest baseTest, String name, boolean goToHomePage) {
        createProject(baseTest, name);
        baseTest.getDriver().findElement(By.xpath("//label/span[text()='Pipeline']")).click();
        saveProject(baseTest);

        goToHomePage(baseTest, goToHomePage);
    }

    public static void createMultiConfigurationProject(BaseTest baseTest, String name, boolean goToHomePage) {
        createProject(baseTest, name);
        baseTest.getDriver().findElement(By.xpath("//label/span[contains(text(), 'Multi-configuration project')]")).click();
        saveProject(baseTest);

        goToHomePage(baseTest, goToHomePage);
    }

    public static void createFolder(BaseTest baseTest, String name, boolean goToHomePage) {
        createProject(baseTest, name);
        baseTest.getDriver().findElement(By.cssSelector(".com_cloudbees_hudson_plugins_folder_Folder")).click();
        saveProject(baseTest);

        goToHomePage(baseTest, goToHomePage);
    }

    public static void createMultibranchPipeline(BaseTest baseTest, String name, boolean goToHomePage) {
        createProject(baseTest, name);
        baseTest.getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        saveProject(baseTest);

        goToHomePage(baseTest, goToHomePage);
    }

    public static void createOrganizationFolder(BaseTest baseTest, String name, boolean goToHomePage) {
        createProject(baseTest, name);
        baseTest.getDriver().findElement(By.xpath("//label/span[contains(text(), 'Organization Folder')]")).click();
        saveProject(baseTest);

        goToHomePage(baseTest, goToHomePage);
    }

    public static List<String> getTextOfWebElements(List<WebElement> elements) {
        List<String> textOfWebElements = new ArrayList<>();

        for (WebElement element : elements) {
            textOfWebElements.add(element.getText());
        }
        return textOfWebElements;
    }

    public static void clearAllCustomLogRecorders(BaseTest baseTest) {
        List<WebElement> lst = new ArrayList<>();
        new HomePage(baseTest.getDriver())
            .clickManageJenkins()
            .goSystemLogPage();

        lst = baseTest.getDriver().findElements(By.className("jenkins-table__link"));
        if (lst.size() > 1) {
            Iterator<WebElement> it = lst.iterator();

            while (it.hasNext()) {
                WebElement wb = it.next();
                if (!wb.getText().equals("All Jenkins Logs")) {
                    wb.click();
                    baseTest.getDriver().findElement(By.xpath("//button[@tooltip='More actions']")).click();
                    Actions actions = new Actions(baseTest.getDriver());
                    actions.pause(400)
                        .moveToElement(baseTest.getDriver()
                            .findElement(By.xpath("//a[@data-post='true']")))
                        .click()
                        .perform();
                    baseTest.getWait5().until(ExpectedConditions.alertIsPresent()).accept();
                    baseTest.getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).click();
                    lst = baseTest.getDriver().findElements(By.className("jenkins-table__link"));
                } else if (lst.size() > 1) {
                    lst.get(1).click();
                    baseTest.getDriver().findElement(By.xpath("//button[@tooltip='More actions']")).click();
                    Actions actions = new Actions(baseTest.getDriver());
                    actions.pause(400)
                        .moveToElement(baseTest.getDriver()
                            .findElement(By.xpath("//a[@data-post='true']")))
                        .click()
                        .perform();
                    baseTest.getWait5().until(ExpectedConditions.alertIsPresent()).accept();
                    baseTest.getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).click();
                    lst = baseTest.getDriver().findElements(By.className("jenkins-table__link"));
                }
                if (lst.size() == 1) break;
                it = lst.iterator();
            }
        }
        goToHomePage(baseTest,true);
    }
}

