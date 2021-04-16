package ATD;

import Common.DataBase;
import com.codeborne.selenide.ElementsCollection;
import files.Product;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static Common.CommonMethods.checkingContainsUrl;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.url;

public class Moto_Category_car_list_page_Logic extends Moto_Category_car_list_page {

    @Step("visibility of TecDoc listing .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic visibilityOfTecDocListing(String textInHeadline) {
        headline().shouldNotHave(exactText(textInHeadline));
        tecDocListingBlock().shouldBe(visible);
        return this;
    }

    @Step(" selecting motorcycle in selector .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic selectMotoInSelector(String marke, String model, String motor) {
        brandOfMotoField().selectOptionByValue(marke);
        brandOfMotoField().shouldHave(exactValue(marke));
        modelFiledInSelector().selectOptionByValue(model);
        modelFiledInSelector().shouldHave(exactValue(model));
        motorFiledInSelector().selectOptionByValue(motor);
        motorFiledInSelector().shouldHave(exactValue(motor));
        searchButton().click();
        return page(Moto_Category_car_list_page_Logic.class);
    }

    @Step("check current url .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkCurrentUrl(String subRoute) throws SQLException {
        tecDocListingBlock().shouldBe(visible);
        DataBase db = new DataBase("ATD");
        Assert.assertEquals(url(), db.getFullRouteByRouteAndSubroute("subprod", "DE", "moto_main", subRoute));
        return this;
    }


    @Step("get text from headline .Moto_Category_car_list_page")
    public String getTextFromHeadline() {
        String textFromHeadline = headline().getText();
        return textFromHeadline;
    }

    @Step(" select motorcycle model in selector .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic selectMotoModel(String model) {
        brandOfMotoField().shouldNotHave(exactValue("0"));
        modelFiledInSelector().shouldBe(visible).selectOptionByValue(model);
        return this;
    }

    @Step(" select motorcycle motor in selector .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic selectMotoMotor(String motor) {
        motorFiledInSelector().shouldBe(visible).selectOptionByValue(motor);
        return this;
    }

    @Step(" click on Search button in selector .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic clickOnSearchButton() {
        searchButton().click();
        return this;
    }


    @Step("get amount count of products from TecDoc listing .Moto_Category_car_list_page")
    public double getAmountCountOfProducts() {
        double countOfProductsFromTecDoc = Double.parseDouble(countOfProduct().getText().replaceAll("[^0-9]", ""));
        return countOfProductsFromTecDoc;
    }


    @Step("check amount count of pages at TecDoc listing .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkCountOfPagesAtTecDocListing() {
        String url = lastForwardLinkOfPagination().getAttribute("href");
        String urlPart = url.replace(url.substring(url.lastIndexOf("=")), "");
        int lastPage = Integer.parseInt(url.replace(urlPart, "").replaceAll("[^0-9]", ""));
        int countOfPagesFromAmountCountOfProducts = (int) Math.ceil(getAmountCountOfProducts() / 20);
        Assert.assertEquals(countOfPagesFromAmountCountOfProducts, lastPage);
        return this;
    }


    @Step(" check TecDoc listing on pages .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkTecDocListingOnPages() {
        presenceOfProductsAtTecDocListing();
        while (lastForwardLinkOfPagination().isDisplayed()) {
            nextForwardOfPagination().click();
            presenceOfProductsAtTecDocListing();
        }
        return this;
    }

    @Step(" presence of products at TecDoc listing.Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic presenceOfProductsAtTecDocListing() {
        productsAtTecDocListing().shouldHave(sizeGreaterThan(1));
        return this;
    }


    @Step(" presence of TecDoc listing block.Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic presenceOfTecDocListingBlock() {
        tecDocListingBlock().shouldBe(visible);
        productsAtTecDocListing().shouldHave(sizeGreaterThan(1));
        return this;
    }


    @Step(" appearance pop-up Of Subscription for out of stock products  .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic appearancePopUpOfSubscription() {
        clickOnOutOfStockButton();
        while (lastForwardOfPagination().isDisplayed()) {
            nextForwardOfPagination().click();
            clickOnOutOfStockButton();
        }
        return this;
    }

    @Step(" click on button at out of stock product  .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic clickOnOutOfStockButton() {
        for (int i = 0; i < btnOutOfStockProducts().size(); i++) {
            btnOutOfStockProducts().get(i).shouldBe(visible).click();
            popUpOfSubscription().should(appear);
            btnClosePopUpOfSubscription().click();
            popUpOfSubscription().should(disappear);
        }
        return this;
    }

    @Step("get id of product from his title .Moto_Category_car_list_page")
    public String getIdOfProduct(int position) {
        tecDocListingBlock().shouldBe(visible);
        String idOfSelectedProduct = titleOfProductInTecDocListingBlock().get(position).getAttribute("href");
        return idOfSelectedProduct;
    }


    @Step(" get value of motorcycle from vertical selector .Moto_Category_car_list_page")
    public String getMotoFromSelector() {
        String motoFromSelector = (brandOfMotoField().getSelectedText() + " " + modelFiledInSelector().getSelectedText()).replaceAll("[^A-Z]", "");
        return motoFromSelector;
    }

    @Step("get id of product in TecDoc Listing .Moto_Category_car_list_page")
    public String getIdOfProductFromTecDocListing() {
        String idOfProduct = btnOfFirstProductInTecDocListing().getAttribute("id");
        return idOfProduct;
    }

    @Step("Check of visibility dynamic characteristics of product in TecDoc listing .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic presenceOfDynamicCharacteristics() {
        List<String> artNumberOfProduct = new ArrayList<>();
        artNumberOfProduct.add("Artikelnummer: SKLS-0140083");
        artNumberOfProduct.add("Artikelnummer: 0 258 986 506");
        artNumberOfProduct.add("Artikelnummer: 3922L0226");

        for (int i = 0; i < artNumberOfProduct.size(); i++) {
            dynamicCharacteristicBlock(artNumberOfProduct.get(i)).shouldBe(visible);
        }
        return this;
    }

    @Step("check dynamic characteristic block .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkDynamicCharacteristicBlock(String artNumOfProduct) {
        List<String> dynamicCharacteristic = new ArrayList<>();
        dynamicCharacteristic.add("Baumuster:<0525,0535>");
        dynamicCharacteristic.add("Lambdasonde:Regelsonde");
        dynamicCharacteristic.add("Baujahr ab:09/1988");
        dynamicCharacteristic.add("Abgasanlage:vor Katalysator");
        List<String> artNumberOfProduct = new ArrayList<>();
        for (int i = 0; i < titleOfDynamicCharacteristic(artNumOfProduct).size(); i++) {
            artNumberOfProduct.add(titleOfDynamicCharacteristic(artNumOfProduct).get(i).getText() + valueOfDynamicCharacteristic(artNumOfProduct).get(i).getText());
        }
        Assert.assertTrue(dynamicCharacteristic.containsAll(artNumberOfProduct));

        return this;
    }

    @Step("check sorting of product .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkSortingPrice() {
        List<Double> activeProductsFromTecDocListing = new ArrayList<>(getProductPrice(activePriceOfProduct()));
        List<Double> notActiveProductsFromTecDocListing = new ArrayList<>(getProductPrice(notActivePriceOfProduct()));
        Assert.assertEquals(activeProductsFromTecDocListing, getExpectedSortedPrices(activeProductsFromTecDocListing));
        Assert.assertEquals(notActiveProductsFromTecDocListing, getExpectedSortedPrices(notActiveProductsFromTecDocListing));
        return this;
    }

    @Step("get product price .Moto_Category_car_list_page")
    public List<Double> getProductPrice(ElementsCollection listOfPrice) {
        List<Double> productsPrise = new ArrayList<>();
        for (int i = 0; i < listOfPrice.size(); i++) {
            productsPrise.add(Double.parseDouble(listOfPrice.get(i).getText().replaceAll("[^0-9,]", "").replace(",", ".")));
        }
        return productsPrise;
    }

    @Step("get  expected sorted price .Moto_Category_car_list_page")
    private List<Double> getExpectedSortedPrices(List<Double> pricesList) {
        List<Double> expectedSortedPrices = new ArrayList<>(pricesList);
        Collections.sort(expectedSortedPrices);
        return expectedSortedPrices;
    }

    @Step("appears of an analogs products message .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic appearsOfAnAnalogsProductsMessage() {
        clickOnReplacementBtnOfProduct("0076543755");
        analogBlockMessage().should(appear).shouldHave(text("Keine Äquivalente verfügbar"));
        return this;
    }

    @Step("click on replacement button of product .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic clickOnReplacementBtnOfProduct(String artNumber) {
        btnShowReplacement(artNumber).shouldBe(visible).click();
        return this;
    }

    @Step("appears of an analogs products block .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic appearsOfAnAnalogsProductsBlock(String numberOfProduct) {
        clickOnReplacementBtnOfProduct(numberOfProduct);
        analogBlockOfProduct().should(appear);
        btnAddToBasketAtAnAnalogProduct().shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step(" get id of an analog product .Moto_Category_car_list_page")
    public String getIdOfAnalogProduct(String numberOfProduct) {
        clickOnReplacementBtnOfProduct(numberOfProduct);
        analogBlockOfProduct().shouldBe(visible);
        btnAddToBasketAtAnAnalogProduct().get(0).scrollIntoView("{block: \"center\"}");
        String idOfBtn = btnAddToBasketAtAnAnalogProduct().get(0).shouldBe(visible).getAttribute("data-ga-label");
        return idOfBtn;
    }

    @Step("add product to basket from an analog block .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic addProductToBasketFromAnalogBlock() {
        analogProducts().get(0).hover();
        if (!detailsBlockOfAnalogProduct().get(0).isDisplayed()) {
            analogProducts().get(0).hover();
        }
        detailsBlockOfAnalogProduct().get(0).shouldBe(visible);
        btnAddToBasketAtAnAnalogProduct().get(0).click();
        if (!basketDropMenu().isDisplayed()) {
            btnAddToBasketAtAnAnalogProduct().get(0).click();
        }
        basketDropMenu().should(appear);
        basketDropMenu().should(disappear);
        basket().click();
        return this;
    }

    @Step("check  of third link at bread crumbs block .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkThirdLinkOfBreadCrumbsBlock(String titleOfLink) {
        breadCrumbsBlock().shouldBe(visible);
        linksOfBreadCrumbs().get(2).shouldHave(exactText(titleOfLink)).click();
        return this;
    }

    @Step("check  of sixth link at bread crumbs block .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkSixthLinkOfBreadCrumbsBlock(String titleOfLink) {
        breadCrumbsBlock().shouldBe(visible);
        linksOfBreadCrumbs().get(5).shouldHave(text(titleOfLink)).shouldNotHave(attribute("href"));
        return this;
    }

    @Step(" presence brand and model in headLine .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic presenceBrandAndModelInHeadLine(String brandAndModel) {
        mainHeadline().shouldBe(visible).shouldHave(text(brandAndModel));
        return this;
    }

    @Step("click on child category in sidebar .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic clickOnChildCategoryInSidebar(int position) {
        childCategoriesInSideBar().get(position).shouldBe(visible).click();
        return page(Moto_Category_car_list_page_Logic.class);
    }

    @Step("check Filters Fix In Sidebar. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkFiltersFixInSidebar() {
        brandsFilterInHeader().shouldBe(visible);
        installationSideFilterInHeader().shouldBe(visible);
        productsAtTecDocListing().get(7).scrollTo();
        installationSideFilterInSideBar().shouldBe(visible);
        brandsFilterInSideBar().shouldBe(visible);
        return this;
    }

    @Step("set Plinth Filter By Title. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic setPlinthFilterByTitle(String title) {
        plinthFilterByTitle(title).shouldBe(visible).click();
        return this;
    }

    @Step("set Generic Filter By Title. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic setGenericFilterByTitle(String title) {
        genericFilterByTitle(title).shouldBe(visible).click();
        return this;
    }

    @Step("appears of Loader .Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic appearsOfLoader() {
        loader().should(appear);
        loader().should(disappear);
        return this;
    }

    @Step("set brand by id. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic setBrandById(String id) {
        brandById(id).shouldBe(visible).click();
        return this;
    }

    @Step("check Listing With Selected Plinth filter And Brand filter. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkListingWithSelectedPlinthAndBrand(String lampType, String brand) {
        List<String> lampValues = lampTypeInCharacteristicsBlock().stream().map(value -> value.getText()).collect(Collectors.toList());

        for (int i = 0; i < titleOfProductInTecDocListingBlock().size(); i++) {
            titleOfProductInTecDocListingBlock().get(i).shouldHave(text(brand));
            Assert.assertTrue(lampValues.get(i).equals(lampType));
        }
        return this;
    }

    @Step("check Listing With Selected Generic filter And Brand filter. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkListingWithSelectedGenericAndBrand(String generic, String brand) {
        for (int i = 0; i < titleOfProductInTecDocListingBlock().size(); i++) {
            titleOfProductInTecDocListingBlock().get(i).shouldHave(text(brand)).shouldHave(text(generic));
        }
        return this;
    }

    @Step("check Listing With Selected  lamp filter. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkListingWithSelectedLampFilter(String lampType) {
        List<String> lampValues = lampTypeInCharacteristicsBlock().stream().map(value -> value.getText()).collect(Collectors.toList());
        for (int i = 0; i < titleOfProductInTecDocListingBlock().size(); i++) {
            Assert.assertTrue(lampValues.get(i).equals(lampType), String.format("Characteristic in listing - %s not equals to - %s, position - %d", lampValues.get(i), lampType, i));
        }
        return this;
    }

    @Step("display of Generic filter block. Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic displayGenericFilterBlock() {
        genericFilterBlock().shouldBe(visible);
        return this;
    }

    @Step("get Titles of generics. Moto_Category_car_list_page")
    public List<String> getTitleOfGenerics() {
        List<String> titles = titleOfGenericsFilter().stream().map(n -> n.getText()).collect(Collectors.toList());
        return titles;
    }

    @Step("checking TecDoc listing . Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic checkTecDocListing(List<String> expectedGenerics) {
        List<Product> productList = new ArrayList<>();
        addedProductsToList(productList, expectedGenerics);
        while (forwardNextPaginator().isDisplayed()) {
            forwardNextPaginator().click();
            addedProductsToList(productList, expectedGenerics);
        }
        List<Product> listBeforeSorting = new ArrayList<>(productList);

        Comparator<String> genericsComparator = (g1, g2) -> {
            if (!expectedGenerics.contains(g1)) {
                return 1;
            }
            if (!expectedGenerics.contains(g2)) {
                return -1;
            }
            return expectedGenerics.indexOf(g1) - expectedGenerics.indexOf(g2);
        };

        Comparator<Product> productsComparator = Comparator
                .comparing((Product p) -> "button ".equals(p.getAttributeOfButton()) ? -1 : 0)
                .thenComparing((Product p) -> "RIDEX".equals(p.getBrandOfProduct()) ? -1 : 0)
                .thenComparing(Product::getGenericOfProduct, genericsComparator)
                .thenComparingDouble(Product::getPriceOfProduct);
        productList.sort(productsComparator);
        Assert.assertEquals(listBeforeSorting, productList);

        return this;
    }

    @Step("added products to list . Moto_Category_car_list_page")
    public Moto_Category_car_list_page_Logic addedProductsToList(List<Product> list, List<String> genericList) {
        String brand, generic;
        String genericForList = null;

        for (int i = 0; i < attributeOfBtnAddedToBasket().size(); i++) {

            brand = attributeOfBtnAddedToBasket().get(i).getAttribute("data-brand-name");

            if (subTitleOfProductInTecDocListing().get(i).isDisplayed()) {
                generic = titleOfProductInTecDocListing().get(i).getText().replaceAll(brand + " ", " ")
                        .replace("\n" + subTitleOfProductInTecDocListing().get(i).getText(), "");
            } else {
                generic = titleOfProductInTecDocListing().get(i).getText().replaceAll(brand + " ", " ");
            }
            for (int j = 0; j < genericList.size(); j++) {
                if (generic.contains(genericList.get(j))) {
                    genericForList = genericList.get(j);
                }
            }
            Product productPage = new Product();
            productPage.setGenericOfProduct(genericForList);
            productPage.setBrandOfProduct(brand);
            productPage.setPriceOfProduct(Double.parseDouble(priceOfProduct().get(i).getText().replaceAll("[^0-9,]", "").replace(",", ".")));
            list.add(productPage);
        }
        return this;
    }
}
