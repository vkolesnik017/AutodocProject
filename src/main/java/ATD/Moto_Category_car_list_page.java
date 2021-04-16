package ATD;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$x;

public class Moto_Category_car_list_page {
    SelenideElement tecDocListingBlock() {
        return $x("//ul[contains(@class,'list_products')]");
    }

    SelenideElement brandOfMotoField() {
        return $(byId("form_maker_id"));
    }

    SelenideElement modelFiledInSelector() {
        return $(byId("form_model_id"));
    }

    SelenideElement motorFiledInSelector() {
        return $(byId("form_car_id"));
    }

    SelenideElement searchButton() {
        return $x("//a[contains(@class,'search_button')]");
    }

    SelenideElement btnResetOfSelector() {
        return $(byId("reset_selector_form"));
    }

    SelenideElement headline() {
        return $x("//div[@class='title_count_search ']/h2");
    }

    SelenideElement countOfProduct() {
        return $x("//div[@class='product_count']");
    }

    SelenideElement lastForwardOfPagination() {
        return $x("//span[@class='last']/a");
    }

    SelenideElement lastForwardLinkOfPagination() {
        return $x("//div[@class='pagination']//span[@class='last']/a");
    }

    SelenideElement nextForwardOfPagination() {
        return $x("//span[@class='next']/a");
    }

    ElementsCollection productsAtTecDocListing() {
        return $$x("//*[self::ul[@class='list_products'] or self::ul[@class='list_products ']]/li");
    }

    ElementsCollection btnOutOfStockProducts() {
        return $$x("//div[@class='button not_active']/a");
    }

    SelenideElement popUpOfSubscription() {
        return $x("//div[@class='popup-available']");
    }

    SelenideElement btnClosePopUpOfSubscription() {
        return $x("//div[@class='popup-available__close ga-popup-available-close']");
    }

    ElementsCollection imageOfProductTecDocListingBlock() {
        return $$x("//div[@class='image']//span[2]/img");
    }

    ElementsCollection titleOfProductInTecDocListingBlock() {
        return $$x("//div[@class='name']/*[self::a or self::span][1]");
    }

    ElementsCollection imageBrandOfProductTecDocListingBlock() {
        return $$x("//div[@class='image']//span[2]/img");
    }

    SelenideElement btnOfFirstProductInTecDocListing() {
        return $x("(//div[@class='button '])[1]");
    }

    SelenideElement basketDropMenu() {
        return $x("//div[@class='cart-items-block ']");
    }

    SelenideElement basket() {
        return $x("//a[@class='header-cart__link']");
    }

    SelenideElement dynamicCharacteristicBlock(String artNumOfProduct) {
        return $x("//span[contains(text(),'" + artNumOfProduct + "')]/ancestor::div[@class='description']//li[@class='important desc_group']");
    }

    ElementsCollection titleOfDynamicCharacteristic(String artNumOfProduct) {
        return $$x("//span[contains(text(),'" + artNumOfProduct + "')]/ancestor::div[@class='description']//li[@class='important desc_group']/ul/li/span[1]");
    }

    ElementsCollection valueOfDynamicCharacteristic(String artNumOfProduct) {
        return $$x("//span[contains(text(),'" + artNumOfProduct + "')]/ancestor::div[@class='description']//li[@class='important desc_group']/ul/li/span[2]");
    }

    ElementsCollection activePriceOfProduct() {
        return $$x("//span[contains(text(),'Kaufen')]/ancestor::div[@class='price_box']//p[@class='actual_price']");
    }

    ElementsCollection notActivePriceOfProduct() {
        return $$x("//span[contains(text(),'Verfügbarkeit')]/ancestor::div[@class='price_box']//p[@class='actual_price']");
    }

    SelenideElement btnShowReplacement(String artOfProduct) {
        return $x("//span[contains(text(),'" + artOfProduct + "')]/ancestor::li[@class='ovVisLi  item_not_available']//div[contains(@class,'show_alternative__btn')]");
    }

    SelenideElement analogBlockMessage() {
        return $x("//div[@class='top-small-products__title']");
    }

    SelenideElement analogBlockOfProduct() {
        return $x("//div[@class='top-small-products top-small-products--alternative']");
    }

    ElementsCollection btnAddToBasketAtAnAnalogProduct() {
        return $$x("//div[@class='small-product-button price_box']//a");
    }

    SelenideElement breadCrumbsBlock() {
        return $x("//div[@class='steps breadcrumbs']");
    }

    SelenideElement iconOfFirstLinkBreadCrumbsBlock() {
        return $x("//a[@itemprop='item']/img");
    }

    ElementsCollection linksOfBreadCrumbs() {
        return $$x("//a[@itemprop='item']");
    }

    SelenideElement mainHeadline() {
        return $x("//div[@class='title_count_search ']/h2");
    }

    SelenideElement productWithArtNumber(String artNum) {
        return $x("//span[contains(text(),'" + artNum + "')]/ancestor::div[@class='name']/a");
    }

    ElementsCollection analogProducts() {return $$x("//div[@class='top-small-products-items__item']").filter(visible);}

    SelenideElement logoInHeader() {return $(".header__logo-main");}

    ElementsCollection childCategoriesInSideBar() {return $$x("//div[@class='block categories blue topSubCats']//li/a");}

    ElementsCollection detailsBlockOfAnalogProduct() {return $$x("//div[@class='rec_prod_info_popup']").filter(visible);}

    SelenideElement brandsFilterInHeader() {return $("#selected-instalation__slider");}

    SelenideElement installationSideFilterInHeader() {return $(".installation-side__content");}

    SelenideElement installationSideFilterInSideBar() {return $(".sidebar  .installation-side__content");}

    SelenideElement brandsFilterInSideBar() {return $(".sidebar .branded-filter-sidebar");}

    SelenideElement plinthFilterByTitle(String title) {return $x("//div[@class='filter-disk__form']//span[text()='"+title+"']");}

    SelenideElement loader() { return $x("//div[@class='preloader_wrapper']"); }

    SelenideElement brandById(String id) {return $x("//div[@id='selected-instalation__slider']//label[@for='cb-brand-"+id+"']");}

    ElementsCollection lampTypeInCharacteristicsBlock() {return $$x("//div[@class='about']//span[text()='Lampenart:  ']/following-sibling::span");}

    SelenideElement genericFilterByTitle(String title) {return $x("//div[@class='filter-generics-tecdoc__item-title'][text()='"+title+"']/ancestor::label");}

    public SelenideElement firstBrandInFilterButton() {return $x("(//ul[@class='no-margin']//label)[1]");}

    SelenideElement genericFilterBlock() {return $x("//div[@class='filter-generics-tecdoc js-filter-generic js-filter-50001 js-filter-wrapper criteria-filter-block-js']");}

    ElementsCollection titleOfGenericsFilter() {return $$(".filter-generics-tecdoc__item-title");}

    SelenideElement forwardNextPaginator() {return $x("//span[@class='next'][1]/a");}

    ElementsCollection attributeOfBtnAddedToBasket() { return $$x("//div[@class='count']/following-sibling::div[1]");}

    ElementsCollection subTitleOfProductInTecDocListing() {return $$x("//span[@class='subname']");}

    ElementsCollection titleOfProductInTecDocListing() {return $$x("//div[@class='name']/*[self::a or self::span][1]");}

    ElementsCollection priceOfProduct() {return $$x("//p[contains(@class,'actual_price')]");}
}
