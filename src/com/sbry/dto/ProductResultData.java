package com.sbry.dto;

import java.util.List;

/**
 * Created by Kailash on 04/02/16.
 */
public class ProductResultData {

        private List<ProductData> results;

        private String total;

        public List<ProductData> getResults() {
            return results;
        }

        public void setResults(List<ProductData> results) {
            this.results = results;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

}
