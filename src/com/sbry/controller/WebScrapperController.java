package com.sbry.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sbry.dto.ProductData;
import com.sbry.dto.ProductResultData;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kailash on 03/02/16.
 *
 * This class will scrapes the Sainsbury’s grocery site - Ripe Fruits page and
 * returns a JSON array of all the products on the page.
 *
 */
public class WebScrapperController {


    public static final String TEST_SCRAPE_URL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";

    // ResultData is declared as a class variable for testing purpose
    public ProductResultData resultData;

    //Entry point
    public static void main(String args[]) {

        new WebScrapperController().generateJsonProductData();

    }


    public void generateJsonProductData() {

        // Using jsoup API get HTML Document Object of Product list page using test URL
        Document doc = getDocument(TEST_SCRAPE_URL);

        if(doc != null) {

            List<ProductData> productDataList = new ArrayList<ProductData>();
            Double totalPrice = 0.0;

            // Get a list of HTML elements of products
            // Iterate and extract data from each element which contains productInfo and Price elements of a Particular Product
            Elements productInnerElements = doc.getElementsByClass("productInner");
            for (Element productInnerElement : productInnerElements) {
                totalPrice = extractProductDataAndReturnTotalPrice(productDataList, totalPrice, productInnerElement);
            }

            resultData = new ProductResultData();
            resultData.setResults(productDataList);

            DecimalFormat df = new DecimalFormat("#.00");
            resultData.setTotal(df.format(totalPrice));

            convertObjectToJsonString(resultData);

        } else {
            System.out.println("Incorrect TEST URL ! Unable to get HTML document !.");
        }
    }


    private double extractProductDataAndReturnTotalPrice(List<ProductData> productDataList, double totalPrice, Element productInnerElement) {

        ProductData productData = new ProductData();
        // Get productInfo HTML elements
        Elements productInfoElements = productInnerElement.getElementsByClass("productInfo");

        if (productInfoElements.size() > 0) {
            // Assumption (as per test page's DOM) - Each Product will have only one productInfo element which has necessary details
            Element productInfoElement = productInfoElements.get(0);
            // Extract product data from anchor and href tags which is placed inside a h3 tag
            Elements h3Elements = productInfoElement.getElementsByTag("h3");
            if (h3Elements.size() > 0) {
                // Assumption (as per test page's DOM)- Each Product will have only one h3 tag placed inside ProductInfo element
                Element h3Element = h3Elements.get(0);
                Elements anchorTags = h3Element.getElementsByTag("a");
                if (anchorTags.size() > 0) {
                    // Assumption (as per test page's DOM)- Each Product will have only one anchorTag placed inside h3 element
                    Element anchorTag = anchorTags.get(0);
                    productData.setTitle(anchorTag.text());
                    String productPageUrl = anchorTag.attr("href");
                    getProductDescriptionPageDetails(productPageUrl, productData);
                }
            }
        }

        // Get pricePerUnit HTML elements
        Elements pricePerUnitElements = productInnerElement.getElementsByClass("pricePerUnit");

        if (pricePerUnitElements.size() > 0) {
            // Assumption (as per test page's DOM)- Each Product will have only one pricePerUnitElement which has necessary price details
            Element pricePerUnit = pricePerUnitElements.get(0);
            // Get only numerical value of the price
            String unitPrice = StringUtils.substringBetween(pricePerUnit.text(), "&pound", "/unit");
            productData.setUnit_Price(unitPrice);
            // add each product price for calculating total price
            double price = Double.parseDouble(unitPrice);
            totalPrice += price;
        }

        productDataList.add(productData);

        return totalPrice;

    }


    private void getProductDescriptionPageDetails(String productPageUrl, ProductData productData) {

        // Using jsoup API get HTML Document Object of Product description page using it's URL
        Document productPageDoc = getDocument(productPageUrl);
        // Get producttext HTML elements
        Elements producttextElements = productPageDoc.getElementsByClass("producttext");
        if(producttextElements.size() > 0) {
            // Assumption (as per test page's DOM) - Each Product will have only one producttext element which has necessary details
            Elements pTagElements = producttextElements.get(0).getElementsByTag("p");

            // Build a string using all the available <P> tag elements
            StringBuffer sb = new StringBuffer();
            for (Element pTag : pTagElements) {
                sb.append(pTag.text());
            }

            productData.setDescription(sb.toString());
            productData.setSize(String.valueOf(getFileSize(productPageUrl) + "kb"));
        }

    }

    // Using jsoup API get HTML Document Object of a given URL
    private  Document getDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return doc;
    }

    // Get the Size of the HTML in KB, of a given URL
    private  int getFileSize(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (conn == null) {
            return 0;
        }
        return conn.getContentLength() / 1024;
    }

    // Using gson API convert POJO to JSON string
    private void convertObjectToJsonString(ProductResultData resultData) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(resultData));
    }

}
