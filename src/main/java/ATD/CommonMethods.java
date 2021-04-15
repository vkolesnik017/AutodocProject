package ATD;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import com.codeborne.selenide.ex.UIAssertionError;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CommonMethods {
    public static String testMail = "test@gmail.com";
    static String testNumberThatPutOrderInTest = "200+002";
    public static String password = "atdtest";
    public static String passwordForPayments = "atdtest1";

    public static String mailFB = "zhoraautomation@gmail.com";
    public static String passFB = "atdtest2020";

    public static String ridex_82B0896 = "82B0896";
    public static String usualIdProduct = "8340509";
    public static String idProductTire = "8075786";
    public static String idProductMore35EUR = "1367459";
    public static String idPfandProduct = "1145183";
    public static String idProductWithDynamicChar = "2295352";

    @Step("{url} Open page with close popup")
    public static void openPage(String url) {
        System.out.println(url);
        open(url);
        closeCookiesFooterMessage();
    }

    public static void closeCookiesFooterMessage() {
        try {
            $(byXpath("//div[@class='block-cookies__close']")).click();
        } catch (UIAssertionError e) {
            System.out.println("Cookies block doesn't appear");
        }
    }

    public static String getCurrentShopFromJSVarInHTML() {
        String currentShop = executeJavaScript("return $siteSettings.lang");
        if (currentShop.equals("lu")) currentShop = "ld";
        return currentShop.toUpperCase();
    }

    public static String getShopFromRoute(String route) {
        String shop = null;
        String[] words = route.split("\\.");
        if (words.length == 4) {
            if (words[3].equals("no")) shop = "NO";
            else if (words[3].equals("uk")) shop = "EN";
        } else {
            if (words[2].equals("lu")) shop = "LD";
            else shop = words[2].toUpperCase();
        }
        return shop;
    }

    public static String getRandomNumber() {
        int n = (int) Math.round(Math.random() * 1000000);
        return String.valueOf(n);
    }

    public static String firstNameRandom() {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt();
        return "autotestFirstName" + random;
    }

    public static String secondNameRandom() {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt();
        return "autotestSecondName" + random;
    }

    @Step("Generates random email on @mailinator.com")
    public static String mailinatorMailRandom() {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt();
        return "autotest" + random + "@mailinator.com";
    }

    @Step("Generates random email on @mailinator.com")
    public static String mailinatorMailRandom(String QCnumber) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt();
        return "qc_" + QCnumber + "_autotest" + random + "@mailinator.com";
    }

    @Step("get random mail")
    public static String randomMail() {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt();
        return "autotest" + random;
    }

    @Step("Generates random password")
    public static String passRandom() {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt();
        return String.valueOf(random);
    }

    @Step("Checking datenschutzerklarung link behavior")
    public void checkingDatenschutzerklarungLinkBehavior(SelenideElement datenschutzerklarungLink, String cssValue) {
        datenschutzerklarungLink.shouldHave(attribute("title", "Datenschutzerklärung"));
        datenschutzerklarungLink.shouldHave(cssValue("cursor", "pointer"));
        datenschutzerklarungLink.shouldHave(cssValue("text-decoration", cssValue));
        datenschutzerklarungLink.click();
        checkingUrlAndCloseTab("https://www.autodoc.de/services/datenschutz");
    }

    @Step("Generates random email on @mailinator.com")
    public static String mailRandomMailinator(String QCnumber) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt();
        return "qc_" + QCnumber + "_autotestmail" + random + "@mailinator.com";
    }

    @Step("Get currency {nameLocator} and verify")
    public static void getCurrencyAndVerify(SelenideElement currencyLocator, String nameLocator, String shop, String expectedCurrency) {
        String actualCurrency;
        if (shop.equals("EN")) {
            actualCurrency = currencyLocator.getText().split("\\s")[0];
        } else {
            actualCurrency = currencyLocator.getText().split("\\s")[1].replaceAll("[*]", "");
        }
        assertEquals(actualCurrency, expectedCurrency, "Currency in " + nameLocator);
    }

    //Checks element clickability
    public static Condition clickable = and("can be clicked", visible, enabled);

    //Method checking follow url
    public void checkingUrl(String expectedUrl) {
        waitingWhileLinkBecomeExpected(expectedUrl);
        String actualUrl = url();
        Assert.assertEquals(actualUrl, expectedUrl);
        back();
    }

    @Step
    //Method for waiting while link become expected
    public static void waitingWhileLinkBecomeExpected(String expectedEqualsUrl) {
        try {
            Wait().until(webDriver -> url().equals(expectedEqualsUrl));
        } catch (TimeoutException e) {
            System.out.println(url());
            Assert.fail("Url doesn't equals: " + expectedEqualsUrl);
        }
    }

    @Step("Method checking follow url on new tab and close tab")
    public void checkingUrlAndCloseTab(String expectedUrl) {
        switchTo().window(1);
        String actualUrl = url();
        assertTrue(actualUrl.contains(expectedUrl));
        getWebDriver().close();
        switchTo().window(0);
    }

    @Step("Method checking follow url on new tab and close tab")
    public void checkingUrlAndCloseTab(String shop, String expectedUrl) {
        switchTo().window(1);
        String actualUrl = url();
        if (shop.equals("be")) {
            String urlForBE = actualUrl.replaceAll("-", "");
            assertTrue(urlForBE.contains(expectedUrl));
            getWebDriver().close();
            switchTo().window(0);
        } else {
            assertTrue(actualUrl.contains(expectedUrl));
            getWebDriver().close();
            switchTo().window(0);
        }
    }

    @Step("Pulling prices from text of element")
    public static Float getPriceFromElement(SelenideElement element) {
        element.shouldBe(visible);
        return Float.parseFloat(element.text().replaceAll("[^0-9,]", "").replace(",", "."));
    }

    public static SelenideElement universalElementOfBuyBtnForAllPages() {
        return $(byXpath("//a[contains(@class,'add_')]"));
    }

    @Step("Close any popup by click overlay")
    public static void closeAnyPopupByClickOverlay() {
        By overlay = (byXpath("//div[@class='overlay black']"));
        executeJavaScript("arguments[0].click();", $(overlay));
        $(overlay).shouldNotBe(visible);
    }

    @Step("Method for test adding product to basket from all routes")
    public static void clickOfBuyBtnForAllPages() {
        SelenideElement productBlockForHover = $(byCssSelector(".rec_products_block"));
        SelenideElement avaliablePopup = $(".popup-available");
        SelenideElement thirdProductInBlockForHover = $x("//div[3][@class='rec_products_block']");
        SelenideElement thirdProductBuyButton = $x("(//a[contains(@class,'add_')])[3]");
        SelenideElement addBasketBtnFromBlockTop = $x("//*[contains(@class,'active')]//a[contains(@class,'add_')]");
        try {
            sleep(2000); // TODO try delete this sleep if fixed SITES-2830
            if (productBlockForHover.isDisplayed()) {
                productBlockForHover.hover();
            }
            sleep(3000); // TODO try delete this sleep if fixed SITES-2830
            if (universalElementOfBuyBtnForAllPages().isDisplayed()) {
                try {
                    universalElementOfBuyBtnForAllPages().scrollIntoView("{block: \"center\"}").click();
                } catch (ElementShould e) {
                    productBlockForHover.hover();
                    universalElementOfBuyBtnForAllPages().click();
                }
            } else {
                addBasketBtnFromBlockTop.click();
            }
            sleep(2000);
            if (avaliablePopup.is(visible)) {
                new Listing_page_Logic().closePopupByClickOverlayOnListingSearch();
                thirdProductInBlockForHover.hover();
                thirdProductBuyButton.click();
            }
        } catch (ElementShould e1) {
            try {
                $(byXpath("//div[@class='top-small-products__items']//a[contains(@class,'add_')]")).waitUntil(visible, 3000).click();
            } catch (ElementNotFound e2) {
                // for tires listing
                $(byXpath("(//*[@data-ga-action='Add_to_basket'])[5]")).click();
            }
        }
        sleep(4000); // TODO try delete this sleep if fixed SITES-2830
    }

    @Step("Methods and locators for block of top products")
    private SelenideElement titleOfBlockOfTopProducts() {
        return $x("//*[@class='title_list'] | //*[@class='top-small-products__title']");
    }

    private SelenideElement blockOfTopProducts() {
        return $x("//div[contains(@class,'product-list-slider')]");
    }

    private SelenideElement arrowRightBtnInTopProductsBlock() {
        return $(byXpath("(//*[@type='button'])[2]"));
    }

    public SelenideElement detailsButtonInTopProductsBlock() {
        return $(".linkShowPopup ");
    }

    // fits for all pages
    private SelenideElement grayBtn() {
        return $(byXpath("//*[contains(@class,'not_active')]/a"));
    }

    private ElementsCollection miniCardsOfProducts() {
        return $$(byXpath("//*[contains(@class,'active') and @aria-hidden='false']//div[5]/.."));
    }

    private By recoveryCharacteristicInBlockOfTopProducts = By.cssSelector(".default_ul_li_class");

    @Step("Scrolling to title of block top products")
    public CommonMethods scrollToTitleOfBlockOfTopProducts() {
        titleOfBlockOfTopProducts().scrollTo();
        return this;
    }

    @Step("Scrolling to block of top products block")
    public CommonMethods scrollToBlockOfTopProducts() {
        blockOfTopProducts().scrollTo();
        return this;
    }

    @Step
    public void checksProductsNotInStockInBlockOfTopProducts() {
        universalElementOfBuyBtnForAllPages().shouldBe(visible);
        if (arrowRightBtnInTopProductsBlock().isDisplayed()) {
            while (arrowRightBtnInTopProductsBlock().attr("aria-disabled").equals("false")) {
                grayBtn().shouldBe(not(visible));
                arrowRightBtnInTopProductsBlock().click();
            }
        }
        grayBtn().shouldBe(not(visible));
    }

    @Step
    // method for checks output recovery characteristic in block of top products for QASYS_536 (TEST-1)
    public void checksOutputRecoveryCharacteristicInBlocksOfTopProducts(String expectedChar) {
        ArrayList<String> actualCharacteristics = new ArrayList<>();
        scrollToTitleOfBlockOfTopProducts();
        ElementsCollection miniCardsInTopBlock = miniCardsOfProducts().filter(visible).shouldHaveSize(4);
        for (SelenideElement el : miniCardsInTopBlock) {
            el.hover();
            String text = el.$(recoveryCharacteristicInBlockOfTopProducts).shouldBe(visible).getText().replaceAll("\n", "");
            actualCharacteristics.add(text);
            titleOfBlockOfTopProducts().hover();
        }
        arrowRightBtnInTopProductsBlock().click();
        ElementsCollection miniCardsInTopBlockTwoSlide = miniCardsOfProducts().filter(visible).shouldHaveSize(4);
        for (SelenideElement el : miniCardsInTopBlockTwoSlide) {
            el.hover();
            String text = el.$(recoveryCharacteristicInBlockOfTopProducts).shouldBe(visible).getText().replaceAll("\n", "");
            actualCharacteristics.add(text);
            titleOfBlockOfTopProducts().hover();
        }
        assertTrue(actualCharacteristics.contains(expectedChar), "not in a single product is not output the recovery characteristic " + expectedChar + " in the block of top product");
    }

    @Step("Method for checks elements in mini card in block of top products")
    public void checksPresenceElementsInMiniCardInBlocksOfTopProducts(String routeName) {
        By sticker = (byCssSelector(".discount"));
        By image = (byCssSelector(".ovVisLi_image"));
        By productName = (byCssSelector(".product-list__item__title"));
        By articleNumber = (byCssSelector(".product-list__item__nummer"));
        By price = (byCssSelector(".product-list__item__price"));
        By infoVatAndDelivery = (byCssSelector(".product-list__item__info"));
        SelenideElement sliderPrev = $x("//button[@class='slick-prev slick-arrow']");
        SelenideElement sliderNext = $x("//button[@class='slick-next slick-arrow']");

        ElementsCollection miniCardsOfProducts = null;
        if (routeName.equals("supplier_brand_line")) {
            miniCardsOfProducts = miniCardsOfProducts().filterBy(visible);
        } else {
            miniCardsOfProducts = miniCardsOfProducts().filterBy(visible);
        }
        for (SelenideElement miniCardFirsSlide : miniCardsOfProducts) {
            miniCardFirsSlide.$(sticker).should(visible);
            miniCardFirsSlide.$(image).should(visible);
            miniCardFirsSlide.$(productName).should(visible);
            miniCardFirsSlide.$(articleNumber).should(visible);
            miniCardFirsSlide.$(price).should(visible);
            miniCardFirsSlide.$(infoVatAndDelivery).should(visible);
        }
        if (!routeName.equals("supplier_brand_line")) {
            sliderNext.click();
            sleep(2000);
            for (SelenideElement miniCardSecondSlide : miniCardsOfProducts) {
                miniCardSecondSlide.$(sticker).should(visible);
                miniCardSecondSlide.$(image).should(visible);
                miniCardSecondSlide.$(productName).should(visible);
                miniCardSecondSlide.$(articleNumber).should(visible);
                miniCardSecondSlide.$(price).should(visible);
                miniCardSecondSlide.$(infoVatAndDelivery).should(visible);
            }
            sliderPrev.click();
        }
    }

    @Step("Comparing actual and expected characteristics")
    //The method gets characteristics from ElementsCollection and compare their with characteristics from ArrayList
    public void compareCharacteristics(ElementsCollection actualCharacteristics, List<String> expectedCharacteristics) {
        for (int a = 0; a < expectedCharacteristics.size(); a++) {
            actualCharacteristics.get(a).shouldHave(matchText(expectedCharacteristics.get(a)));
        }
    }

    public void writerInFile(String fileName, boolean append, String write) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, append), StandardCharsets.UTF_8));
        System.out.println("Write in file");
        bufferedWriter.newLine();
        bufferedWriter.write(write);
        bufferedWriter.close();
    }

    //Methods for checking Counter Product
    @Step("Checking counter increase of paired product quantity")
    void checkingCounterIncreaseForPaired(String startCount, SelenideElement value, SelenideElement counterPlus) {
        value.shouldHave(value(startCount));
        counterPlus.click();
        String countAfterIncrease = String.valueOf(Integer.parseInt(startCount) + 2);
        value.shouldHave(value(countAfterIncrease));
        sleep(2000);
    }

    @Step("Checking counter decrease of paired product quantity")
    void checkingCounterDecreaseForPaired(String startCount, SelenideElement value, SelenideElement counterMinus) {
        value.shouldHave(value(startCount));
        counterMinus.click();
        String countAfterDecrease = String.valueOf(Integer.parseInt(startCount) - 2);
        value.shouldHave(value(countAfterDecrease));
        sleep(2000);
    }

    @Step("Checking counter increase on {increaseCount} of product quantity")
    void checkingCounterIncrease(int increaseCount, SelenideElement counterValue, SelenideElement counterPlus) {
        int startValue = Integer.parseInt(counterValue.getValue());
        for (int i = 1; i <= increaseCount; i++) {
            counterPlus.click();
            sleep(2000);
            int valueAfterIncrease = startValue + i;
            counterValue.shouldHave(value(String.valueOf(valueAfterIncrease)));
        }
        int valueAfterAllIncrease = startValue + increaseCount;
        counterValue.shouldHave(value(String.valueOf(valueAfterAllIncrease)));
    }

    @Step("Checking counter decrease on {decreaseCount} of product quantity")
    void checkingCounterDecrease(int decreaseCount, SelenideElement counterValue, SelenideElement counterMinus) {
        int startValue = Integer.parseInt(counterValue.getValue());
        for (int i = 1; i <= decreaseCount; i++) {
            counterMinus.click();
            sleep(2000);
            int valueAfterDecrease = startValue - i;
            counterValue.shouldHave(value(String.valueOf(valueAfterDecrease)));
        }
        int valueAfterAllDecrease = startValue - decreaseCount;
        counterValue.shouldHave(value(String.valueOf(valueAfterAllDecrease)));
    }

    @Step("Waiting until element will be visible")
    public static void waitingElementVisibility(SelenideElement element, int minute) {
        minute = (minute * 60 * 1000) / 5;
        for (int i = 1; i <= 5; i++) {
            try {
                element.waitUntil(Condition.visible, minute);
            } catch (ElementNotFound e) {
                System.out.println("Retry number " + i + ". Element doesn't visible");
                refresh();
                if (i == 5) Assert.fail("After 5 retrying " + element);
            }
        }
    }

    @Step("Cut price to the first decimal place")
    public static Float cutPriceToFirstDecimalPlace(Float price) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d{1}");
        Matcher matcher = pattern.matcher(String.valueOf(price));
        String result = null;
        if (matcher.find()) {
            result = matcher.group(0);
        }
        return Float.valueOf((result));
    }

    //checking selector

    public void checkingMakerName(SelenideElement makerNameLocator, String makerName, String fileForReport, String url) throws IOException {
        makerNameLocator.shouldBe(Condition.visible);
        String makerNameTextFromSelector = makerNameLocator.getAttribute("innerText");
        if (!makerName.equals(makerNameTextFromSelector))
            writerInFile(fileForReport, true, "Maker from data doesn't equals maker from selector: data:#" + makerName + "selector#" + makerNameTextFromSelector + "#" + url);
    }

    public void checkingGroupName(SelenideElement groupNameLocator, String groupName, String fileForReport, String url) throws IOException {
        groupNameLocator.shouldBe(Condition.visible);
        String groupNameTextFromSelector = groupNameLocator.getAttribute("innerText");
        if (!groupName.equals(groupNameTextFromSelector))
            writerInFile(fileForReport, true, "Group from data doesn't equals group from selector: data:#" + groupName + "selector#" + groupNameTextFromSelector + "#" + url);
    }

    public void checkingModelName(SelenideElement modelNameLocator, String modelName, String fileForReport, String url) throws IOException {
        modelNameLocator.shouldBe(Condition.visible);
        String modelNameTextFromSelector = modelNameLocator.getAttribute("innerText");
        modelNameTextFromSelector = modelNameTextFromSelector.substring(0, modelNameTextFromSelector.lastIndexOf("(")).trim();
        if (!modelName.equals(modelNameTextFromSelector))
            writerInFile(fileForReport, true, "Model from data doesn't equals model from selector: data:#" + modelName + "selector#" + modelNameTextFromSelector + "#" + url);
    }

    public void checkingCarName(SelenideElement carNameLocator, String carName, String yearBegin, String yearEnd, String kw, String hp, String fileForReport, String url) throws IOException {
        carNameLocator.shouldBe(Condition.visible);
        String carNameTextFromSelector = carNameLocator.getAttribute("innerText");

        String yearBeginMonth = yearBegin.substring(4);
        yearBegin = yearBeginMonth.concat(".").concat(yearBegin.substring(0, 4)).trim();
        if (!yearEnd.equals("0")) {
            String yearEndMonth = yearEnd.substring(4);
            yearEnd = yearEndMonth.concat(".").concat(yearEnd.substring(0, 4)).trim();
        } else {
            yearEnd = "...";
        }
        carNameTextFromSelector = carNameTextFromSelector.replace(" ", "");
        String carNameFull = carName + "(" + kw + "KW" + "/" + hp + "PS" + ")" + "(" + yearBegin + "-" + yearEnd + ")";


        if (!carNameFull.equals(carNameTextFromSelector))
            writerInFile(fileForReport, true, "Car from data doesn't equals car from selector: data:#" + carName + "selector#" + carNameTextFromSelector + "#" + url);
    }

    //  проверка происходит только по первым 23 парент категориям, так как дальше у категорий, в админке, одинаковый рейтинг, из-за этого они могут выводится рандомно,
    // не соответствуя AWS
    @Step("comparing parent categories from routs with AWS")
    public static void comparingParentCategoriesWithAws(List<String> categoriesFromAWS, List<String> categoriesFromRouts) {

        List<String> uiList = categoriesFromRouts.stream().map(title -> title.replaceAll(" ", "")).limit(23).collect(Collectors.toList());
        List<String> awsList = categoriesFromAWS.stream().map(title -> title.replaceAll(" ", "")).limit(23).collect(Collectors.toList());
        Assert.assertEquals(awsList, uiList);
    }

    @Step("get text from not visible element {expected element}")
    public static String getTextFromUnVisibleElement(SelenideElement element) {
        return (String) (executeJavaScript("return jQuery(arguments[0]).text();", element));
    }

    @Step("get attribute from not visible element {expected element}")
    public static String getAttributeFromUnVisibleElement(SelenideElement element, String attribute) {
        return (String) (executeJavaScript("return arguments[0].getAttribute('" + attribute + "')", element));
    }


    @Step("Get Href or URL categories/overCategories from catalog then write to list.")
    public static ArrayList<String> getHrefOrUrlCategoriesThenWriteToList(ElementsCollection categories) {
        ArrayList<String> allCategoriesCatalog = new ArrayList<>();
        for (SelenideElement element : categories) {
            if (element.has(attribute("href"))) {
                String hrefCategory = element.getAttribute("href");
                allCategoriesCatalog.add(hrefCategory);
            } else if (element.has(attribute("url"))) {
                String urlCategory = element.getAttribute("url");
                allCategoriesCatalog.add(urlCategory);
            }
        }
        System.out.println(allCategoriesCatalog.size() + " url = " + allCategoriesCatalog);
        return allCategoriesCatalog;
    }

    @Step("Check categories for server responses 200.")
    public static void checkCategoriesForServerResponses200(List<String> allCategories) throws IOException {
        for (int i = 0; i < allCategories.size(); i++) {
            URL url = new URL(allCategories.get(i));
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setInstanceFollowRedirects(true);
            int responseCode = http.getResponseCode();
            if (responseCode != 200) {
                System.out.println("ResponseCode " + allCategories.get(i) + " = " + responseCode);
            }
            assertEquals(responseCode + " " + allCategories.get(i), 200 + " " + allCategories.get(i));
        }
    }


    @Step("Compare two list  between front and Aws by sorting rating group.")
    public static void compareTwoListsBetweenFrontAndAws(List<String> listFront, List<String> listAws, List<Integer> listRating) {

        for (int i = 0; i < listFront.size(); i++) {
            if (!listFront.get(i).equals(listAws.get(i))) {
                if (!listFront.contains(listAws.get(i))) {
                    listAws.remove(listAws.get(i));
                    listRating.remove(listRating.get(i));
                    i--;
                } else if (listFront.get(i).equals(listAws.get(i - 2))) {
                    Assert.assertEquals(listRating.get(i), listRating.get(i - 2));
                } else if (listFront.get(i).equals(listAws.get(i - 1))) {
                    Assert.assertEquals(listRating.get(i), listRating.get(i - 1));
                } else if (listFront.get(i).equals(listAws.get(i + 1))) {
                    Assert.assertEquals(listRating.get(i), listRating.get(i + 1));
                } else if (listFront.get(i).equals(listAws.get(i + 2))) {
                    Assert.assertEquals(listRating.get(i), listRating.get(i + 2));
                } else {
                    Assert.fail("Products not equals between front and aws!");
                }
            }
            System.out.println(listFront.get(i) + " = " + listAws.get(i) + " = " + listRating.get(i));
        }
    }

    @Step("wait while page is reload. CommonMethods")
    public static void pageReload() {
        Wait().until(webDriver -> executeJavaScript("return document.readyState").equals("complete"));
    }

    @Step("create a new browser window. CommonMethods")
    public static void createNewBrowserWindow() {
        executeJavaScript("window.open('about:blank', '-blank')");
    }

    @Step("upload File via windows PopUp. CommonMethods")
    public static void uploadFileViaWindowsPopUp(String pathToFile) throws AWTException {
        sleep(3000);   // НЕОБХОДИМО ДЛЯ ОЖИДАНИЯ ПОЯВЛЕНИЯ ОКНА ЗАГРУЗКИ
        StringSelection s = new StringSelection(pathToFile);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        sleep(3000);  // НЕОБХОДИМО ДЛЯ ОЖИДАНИЯ ЗАГРУЗКИ ПУТИ К ФАЙЛУ
        robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
    }
}
