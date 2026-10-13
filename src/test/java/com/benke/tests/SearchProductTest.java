
package com.benke.tests;

import com.benke.base.BaseTest;
import com.benke.pages.ProductsPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest extends BaseTest {

    @Test
    @Description(
            "Verifies that searching for a product displays the " +
                    "Searched Products section and returns results containing " +
                    "at least one product matching the search keyword."
    )
    public void searchProductShouldReturnMatchingResults() {

        ProductsPage productsPage = new ProductsPage(driver);
        String keyword = "top";

        Allure.step("Open products page", productsPage::open);

        Allure.step("Search for product: " + keyword, () -> {
            productsPage.searchProduct(keyword);
        });

        Allure.step("Verify searched products heading", () -> {
            Assert.assertTrue(
                    productsPage.isSearchedProductsDisplayed(),
                    "Searched Products heading was not displayed"
            );
        });

        Allure.step("Verify search results are not empty", () -> {
            Assert.assertTrue(
                    productsPage.getSearchResultsCount() > 0,
                    "No products were found"
            );
        });

        Allure.step("Verify at least one result matches the keyword", () -> {
            Assert.assertTrue(
                    productsPage.doesAnyResultContain(keyword),
                    "No product name contains the search keyword"
            );
        });
    }
}
