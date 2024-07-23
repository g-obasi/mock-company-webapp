package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mockcompany.webapp.service.SearchService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Management decided it is super important that we have lots of products that match the following terms.
 * So much so, that they would like a daily report of the number of products for each term along with the total
 * product count.
 */
@RestController
public class ReportController {

    /**
     * The people that wrote this code didn't know about JPA Spring Repository interfaces!
     */
    private final EntityManager entityManager;
    private final SearchService searchService;

    @Autowired
    public ReportController(EntityManager entityManager , SearchService searchService) {
        this.entityManager = entityManager; this.searchService = searchService;}


    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        int count = this.entityManager.createQuery("SELECT item FROM ProductItem item").getResultList().size();
        response.setProductCount(count);

        // Use SearchService for "Cool" search
        Collection<ProductItem> coolItems = searchService.search("cool");
        response.getSearchTermHits().put("Cool", coolItems.size());

        // Use SearchService for "Kids" search
        Collection<ProductItem> kidsItems = searchService.search("kids");
        response.getSearchTermHits().put("Kids", kidsItems.size());

        // Use SearchService for "Amazing" search
        Collection<ProductItem> amazingItems = searchService.search("amazing");
        response.getSearchTermHits().put("Amazing", amazingItems.size());

        // Use SearchService for "Perfect" search
        Collection<ProductItem> perfectItems = searchService.search("perfect");
        response.getSearchTermHits().put("Perfect", perfectItems.size());

        return response;
    }
}
