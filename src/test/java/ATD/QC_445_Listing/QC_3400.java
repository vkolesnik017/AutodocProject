package ATD.QC_445_Listing;

import ATD.Moto_Category_car_list_page_Logic;
import Common.SetUp;
import io.qameta.allure.Description;
import io.qameta.allure.Flaky;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static ATD.CommonMethods.openPage;
import static Common.SetUp.setUpBrowser;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class QC_3400 {
    Moto_Category_car_list_page_Logic motoCategoryPage = new Moto_Category_car_list_page_Logic();

    @BeforeClass
    void setUp() {
        setUpBrowser(false, "chrome", "77.0", false);
    }

    @DataProvider(name = "route", parallel = true)
    Object[] dataProvider() throws SQLException {
        return new SetUp("ATD").setUpShopWithSubroutes("subprod", "DE", "moto_main", "moto_category_car_list17,moto_category_car_list17");
    }

    @Test(dataProvider = "route")
    @Flaky
    @Owner(value = "Kolesnik")
    @Description(value = "test checks Sorting on the current issue by default, provided that there is more than one generic product in the issue")
    public void testCheckSortingInTecDocListing(String route) {
        openPage(route);
        List<String> newList = Arrays.asList("Hello");
        List<String> expectedGenerics = motoCategoryPage
                .displayGenericFilterBlock()
                .getTitleOfGenerics();
        motoCategoryPage
                .checkTecDocListing(expectedGenerics);
    }

    @AfterMethod
    private void close() {
        closeWebDriver();
    }
}
