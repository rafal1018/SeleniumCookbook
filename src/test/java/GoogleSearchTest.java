import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class GoogleSearchTest {
    private WebDriver driver;


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./src/test/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.google.com");
    }

    @Test
    public void testGoogleSearch() {
        WebElement element = driver.findElement(By.name("q"));
        element.clear();

        element.sendKeys("Selenium testing tools cookbook");

        element.submit();

        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("selenium testing tools cookbook");
            }
        });
        assertEquals("Selenium testing tools cookbook - Szukaj w Google" , driver.getTitle());

        List<WebElement> links = driver.findElements(By.tagName("a"));

//        assertEquals(109, links.size());

        for(WebElement link : links){
            System.out.println(link.getAttribute("href"));
        }
    }

    @After
    public void tearDown() throws Exception{
        driver.quit();
    }

}
