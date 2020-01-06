import com.thoughtworks.selenium.webdriven.commands.WaitForCondition;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GoogleSearchTest {
    private WebDriver driver;


    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "./src/test/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.get("http://www.google.com");
//        driver.get("https://www.olx.pl/");
        driver.get("https://www.allegro.pl");

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
        assertEquals("Selenium testing tools cookbook - Szukaj w Google", driver.getTitle());

        List<WebElement> links = driver.findElements(By.tagName("a"));

        assertEquals(109, links.size());

        for (WebElement link : links) {
            System.out.println(link.getAttribute("href"));
        }

    }

    @Test
    public void testFindByLinkText() {

        //by linkText
        WebElement gmailLink = driver.findElement(By.linkText("Gmail"));
        assertEquals("https://mail.google.com/mail/?tab=wm&ogbl", gmailLink.getAttribute("href"));

        //by paritaLinkText
        WebElement gmailLin2 = driver.findElement(By.partialLinkText("mail"));
        assertEquals("https://mail.google.com/mail/?tab=wm&ogbl", gmailLink.getAttribute("href"));

        System.out.println(gmailLink.getAttribute("href"));
        System.out.println(gmailLin2.getAttribute("href"));

    }

    @Test
    public void testFindByxPath() {

        WebElement indirectlyXPath = driver.findElement(By.xpath("//div[@class='szppmdbYutt__middle-slot-promo']//span"));
        System.out.println(indirectlyXPath.getText());

        WebElement predicatXPath = driver.findElement(By.xpath("//div[@class='szppmdbYutt__middle-slot-promo']//span[1]"));
        System.out.println(predicatXPath.getText());

        WebElement atributeXPath = driver.findElement(By.xpath("//a[@class='gb_9d gb_4 gb_Vc']"));
        atributeXPath.click();
    }

    @Test
    public void textFieldAutomation() {

        WebElement sendText1 = driver.findElement(By.name("q"));

        sendText1.clear();

        sendText1.sendKeys("Selenium i testowanie aplikacji. Receotury");
        sendText1.submit();
    }

    @Test
    public void clickButton() {

        WebElement logIn = driver.findElement(By.xpath("//a[@id=\"gb_70\"]"));
        logIn.click();

    }

    @Test
    public void testElementText() {

        WebElement message = driver.findElement(By.xpath("//a[@href=\"https://www.olx.pl/moda/\"]/span"));
        String messageText = message.getText();

        assertEquals("Moda", messageText);
    }

    @Test
    public void testElementAttribute() {

        WebElement message = driver.findElement(By.id("postNewAdLink"));
        assertEquals("https://www.olx.pl/nowe-ogloszenie/?bs=homepage_adding", message.getAttribute("href"));

    }

    @Test
    public void testDropDown() {
        // Test dla strony allegro.pl

        WebElement acceptButton = driver.findElement(By.xpath("//button[@class=\"_13q9y _8hkto _11eg6 _7qjq4 _ey68j\"]"));
        acceptButton.click();

        Select make = new Select(driver.findElement(By.xpath("//select[@data-role=\"filters-dropdown-toggle\"]")));


        assertFalse(make.isMultiple());

        assertEquals(16, make.getOptions().size());

        make.selectByVisibleText("Wszystkie kategorie");
        assertEquals("Wszystkie kategorie", make.getFirstSelectedOption().getText());

        make.selectByValue("/kategoria/elektronika");
        assertEquals("Elektronika", make.getFirstSelectedOption().getText());

        make.selectByIndex(3);
        assertEquals("Elektronika", make.getFirstSelectedOption().getText());
    }

    @Test
    public void testMultipleSelectList() {
        // Test dla strony allegro.pl

        WebElement acceptButton = driver.findElement(By.xpath("//button[@class=\"_13q9y _8hkto _11eg6 _7qjq4 _ey68j\"]"));
        acceptButton.click();

        Select make = new Select(driver.findElement(By.xpath("//select[@data-role=\"filters-dropdown-toggle\"]")));


        assertFalse(make.isMultiple());

        assertEquals(16, make.getOptions().size());

        make.selectByVisibleText("Elektronika");
        make.selectByVisibleText("Firma");
        make.selectByVisibleText("Kolekcje i sztuka");

        make.deselectByValue("/kategoria/firma");

        assertEquals(1, make.getAllSelectedOptions().size());

        make.deselectByIndex(3);

        assertEquals(0, make.getAllSelectedOptions().size());
    }

    @Test
    public void testDropdown() {
        // Test dla strony allegro.pl

        WebElement acceptButton = driver.findElement(By.xpath("//button[@class=\"_13q9y _8hkto _11eg6 _7qjq4 _ey68j\"]"));
        acceptButton.click();

        Select make = new Select(driver.findElement(By.xpath("//select[@data-role=\"filters-dropdown-toggle\"]")));

        assertFalse(make.isMultiple());

        assertEquals(16, make.getOptions().size());

        List<String> expectedOptions = Arrays.asList("Wszystkie kategorie","Dom i ogród", "Dziecko", "Elektronika"
        ,"Firma", "Kolekcje i sztuka", "Kultura i rozrywka", "Moda", "Motoryzacja", "Ogłoszenia i usługi"
        ,"Sport i turystyka", "Supermarket", "Uroda", "Zdrowie", "Użytkownicy", "Zakończone");
        List<String> actualOptions = new ArrayList<String>();

        for (WebElement option : make.getOptions()){
            actualOptions.add(option.getText());
        }

        assertEquals(expectedOptions.toArray(), actualOptions.toArray());

        make.selectByVisibleText("Uroda");
        assertEquals("Uroda", make.getFirstSelectedOption().getText());

        make.selectByValue("/kategoria/firma");
        assertEquals("Firma", make.getFirstSelectedOption().getText());

        make.selectByIndex(0);
        assertEquals("Wszystkie kategorie", make.getFirstSelectedOption().getText());
    }

//    @After
//    public void tearDown() throws Exception{
//        driver.quit();
//    }

}
