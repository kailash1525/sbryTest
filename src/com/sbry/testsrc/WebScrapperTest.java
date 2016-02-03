package com.sbry.testsrc;

import com.sbry.WebScrapper;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kailash on 03/02/16.
 *
 * Unit Test class for @WebScrapper.Java
 */
public class WebScrapperTest {

    private final WebScrapper webScrapperImpl = new WebScrapper();

    @Test
    public void test() throws Exception {

        // Null check for POJO object before calling the functionality
        assertNull(webScrapperImpl.resultData);
        // call the method to test
        webScrapperImpl.generateJsonProductData();
        // List of assertions that get set in POJO
        assertNotNull(webScrapperImpl.resultData);
        assertTrue(webScrapperImpl.resultData.getResults().size() > 0);
        assertNotNull(webScrapperImpl.resultData.getTotal());

        for (WebScrapper.ProductData productData : webScrapperImpl.resultData.getResults()){
            assertNotNull(productData.getTitle());
            assertNotNull(productData.getDescription());
            assertNotNull(productData.getSize());
            assertNotNull(productData.getUnit_Price());
        }
    }

}