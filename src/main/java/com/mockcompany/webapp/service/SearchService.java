package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> search(String query) {
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        for (ProductItem item : allItems) {
            if (matchesSearch(item, query)) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    private boolean matchesSearch(ProductItem item, String query) {
        String lowercaseQuery = query.toLowerCase();
        return item.getName().toLowerCase().contains(lowercaseQuery) ||
                item.getDescription().toLowerCase().contains(lowercaseQuery);
    }
}