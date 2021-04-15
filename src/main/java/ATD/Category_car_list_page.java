package ATD;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class Category_car_list_page {
    // Form soft 404
    SelenideElement mailFieldSoftForm() {
        return $(By.id("form_email"));
    }

    SelenideElement submitBtnSoftForm() {
        return $(By.cssSelector(".notification-form__row > button"));
    }

    SelenideElement subscribeCheckboxSoftForm() {
        return $(By.id("subscribe_on"));
    }

    SelenideElement errPopupSoftForm() {
        return $(By.id("popup_update"));
    }

    SelenideElement successPopupSoftForm() {
        return $(By.xpath("//div[@class='new_popup popup_email_not']"));
    }

    SelenideElement closeErrPopupSoftForm() {
        return $(By.xpath("//div[@class='popup_content']//a[@class='close']"));
    }

    SelenideElement closeSuccessPopupSoftForm() {
        return $(By.xpath("//div[@class='button loc']"));
    }

    SelenideElement datenschutzerklarungLinkSoftForm() {
        return $(By.cssSelector("#privacy_policy1>a"));
    }

    SelenideElement loaderInTecDocListing() {
        return $x("//div[@class='preloader_wrapper']");
    }

    SelenideElement  brandsFilterBlock() {return $x("//div[@data-name='brand']");}

    SelenideElement brandsLinkInSideBar(String idOfBrand) {return $x("//div[@id='selected-instalation__slider']//ul//li//label[@for='cb-brand-"+idOfBrand+"']");}

    SelenideElement forwardLinkAtBrandsFilter() {return $x("//a[contains(@class,'next')]");}

    ElementsCollection titleOfProductInTecDocListing() {return $$x("//div[@class='name']/*[self::a or self::span][1]");}

    ElementsCollection subTitleOfProductInTecDocListing() {return $$x("//span[@class='subname']");}

    ElementsCollection imageOfBrandInProductBlock() {return $$x("//div[@class='image']/span[1]/img");}

    ElementsCollection descriptionBlockOfProduct() {return $$x("//div[@class='description']");}

    ElementsCollection characteristicListOfProduct(int positionOfProduct) {return $$x("(//div[@class='description'])["+positionOfProduct+"]//div[@class='about']//ul/li/span[1]");}

    ElementsCollection activeBtnAddProductToBasket() {return $$x("//div[@class='button ']");}

    ElementsCollection priceOfProduct() {return $$x("//p[contains(@class,'actual_price')]");}

    ElementsCollection notActiveBtnAddProductToBasket() {return $$x("//div[@class='button not_active']");}

    SelenideElement forwardNextPaginator() {return $x("//span[@class='next'][1]/a");}

    SelenideElement mainSearchField() {return $x("//input[@class='header-search__input']");}

    SelenideElement btnSearchOfSearchField() {return $x("//a[@class='header-search__submit search_submit form-submitter']");}

    SelenideElement  countOfVehicleInIconOfGarageInHeader() {return $x("//span[@class='header-garage__count header-garage__count--added']");}

    SelenideElement idOfVehicleInGaragePopUp(String idOfVehicle) {return $x("//div[@class='wrapper-radio']/label[@for='"+idOfVehicle+"']");}

    SelenideElement headerGarageIcon(){ return $x("//div[@class='header-garage js-header-garage']"); }

    SelenideElement popUpOfGarageInHeader() {return $x("//div[@class='header-garage__logged-header']");}

    SelenideElement listingOfProductsBlock() {return $x("//div[@class='listing-wrap']");}

    SelenideElement forwardLinkOfPaginator() {return $x("//span[@class='next'][1]"); }

    ElementsCollection allCharacteristicsOfProducts() {return $$x("//div[@class='about']//ul/li");}

    ElementsCollection artNumOfProduct() {return $$x("//span[@class='article_number'][1]");}

    ElementsCollection visibleCharacteristicsOfProducts(int position) {
        return $$x("(//div[@class='about'])[" + position + "]//ul/li");
    }

    ElementsCollection btnAddedProductToWishList() {
        return $$x("//div[@class='price_box']/span");
    }

    SelenideElement popUpSelector() {
        return $x("//div[@class='new_popup popup_content']");
    }

    SelenideElement closePopUpSelector() {
        return $x("//a[@class='back']");
    }

    ElementsCollection visibleBrands() {
        return $$x("//ul[@class='branded-slider slick-initialized slick-slider']//li").filter(visible);
    }

    ElementsCollection addedProductToWishList() {
        return $$x("//span[@class='add-to-wishlist title_btn add-to-wishlist--added remove-article']");
    }

    SelenideElement btnTeilecatalogInSidebar() {
        return $x("//a[@id='teile_catalog_toggle2']");
    }

    ElementsCollection parentsIdFromTeilecatalogInSidebar() {
        return $$x("//div[contains(@class,'block categories blue')][1]//img");
    }

    SelenideElement parentFromTeilecatalogInSidebar() {
        return $x("//div[contains(@class,'block categories blue')][1]//span");
    }

    public ElementsCollection parentFromTeilecatalogInSidebarHref() {
        return $$x("//div[contains(@class,'block categories blue')][1]//a");
    }

    SelenideElement genericBlock() {return $x("//div[@class='filter-generics-tecdoc js-filter-generic js-filter-50001 js-filter-wrapper criteria-filter-block-js']");}

    ElementsCollection visibleTitleOfGenerics() {return $$x("//div[@class='filter-generics-tecdoc__item-title']").filter(visible);}

    SelenideElement rightPaginatorOfGenericBlock() {return $x("//span[@class='next slick-arrow']");}

    SelenideElement leftPaginatorOfGenericBlock() {return $x("//span[@class='prev slick-arrow']");}

    SelenideElement btnSearchOfSelectedSelector() {return $x("//a[@class='submit search_button ripple-out']");}

    SelenideElement visibleBrandsLinkInSideBar(String idOfBrand) {return $x("//*[self::li[@class='slick-slide slick-active'] or self::li[@class='slick-slide slick-current slick-active']]//label[@for='cb-brand-"+idOfBrand+"']");}

    ElementsCollection allBtnAddToBasket() {return $$x("//div[@class='add_info']/div[2]");}

    SelenideElement titleOfProductWithArtNum(String artNum) {return $x("//span[contains(text(),'Artikelnummer: "+artNum+"')]/preceding-sibling::a");}

    SelenideElement recommendationText() {return $x("//p[@class='recommendation__text']");}

    ElementsCollection btnAddProductToWishlist() {return $$x("//span[@class='add-to-wishlist title_btn add-article']");}

    ElementsCollection priceTitle() {return $$x("//div[@class='price-block__inkl']/*[1]");}

    ElementsCollection btnAddProductToWishlistTableView() {return $$x("//div[@class='add-to-wishlist add-article']");}

    ElementsCollection priceTitleTableView() {return $$x("//div[contains(@class,'rec_prod_price ')]/following-sibling::*[2]");}

    SelenideElement soft404() {return $(byId("no_product_find"));}

    SelenideElement titleOfTopLinkingBLockInSidebar() {return $(".sidebar-subcategory__left p");}

    SelenideElement priceOfTopLinkingBLockInSidebar() {return $(".sidebar-subcategory__left span");}

    SelenideElement imageOfTopLinkingBLockInSidebar() {return $(".sidebar-subcategory__right img");}

    SelenideElement btnAddToBasketOfTopLinkingBLockInSidebar() {return $(".sidebar-subcategory__left a");}

    SelenideElement markeFieldInSelector() {return $(byId("form_maker_id"));}

    SelenideElement modelFieldInSelector() {return $(byId("form_model_id"));}

    SelenideElement motorFieldInSelector() {return $(byId("form_car_id"));}

    SelenideElement kbaFirstValueInSelector() {return $(byId("kba1"));}

    SelenideElement kbaSecondValueInSelector() {return $(byId("kba2"));}

    ElementsCollection productsOnPage() {return $$x("//*[@class='all_desc_item']");}

    SelenideElement imageOfProductTecDocListingBlock(int position) {
        return $x("(//div[@class='image']//span[2]//img)["+position+"]");
    }

    ElementsCollection imageOfProductTecDocListingBlock() {
        return $$x("//div[@class='image']//span[2]//img");
    }

    SelenideElement nextPagePagination() {return $x("//span[@class='next'][1]/a");}

    ElementsCollection activeVisibleBrands() {return $$x("//*[self::li[@class='slick-slide slick-active'] or self::li[@class='slick-slide slick-current slick-active']]//input");}

    SelenideElement lampFilterBlock() {return $x("//div[@class='lampenart-filter js-filter-wrapper js-criteria-filter js-filter-criteria_437']");}

    SelenideElement lampDataValue(String value) {return $x("//div[@class='lampenart-filter__slider']//li[@class='slick-slide slick-active']/label[@data-value='"+value+"']");}

    SelenideElement mileageRecommendationIcon() {return $(".recommendation__icon img");}

    ElementsCollection visibleFilterBrands() {return $$x("//li[contains(@class,'slick-active')]//label").filter(visible);}

    ElementsCollection visibleBrandsId() {return $$x("//li[contains(@class,'slick-active')]//label").filter(visible);}

    SelenideElement linksBlock() {return $(".block_links");}

    ElementsCollection topAutoLinks() {return $$(".block_links a");}

}
