package com.gustavosass.orders.service;

import com.gustavosass.orders.model.Item;
import com.gustavosass.orders.model.Stock;
import com.gustavosass.orders.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ItemService itemService;

    @Transactional(readOnly = true)
    public Stock findById(Long id){
        return stockRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Stock not found."));
    }

    @Transactional(readOnly = true)
    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

    @Transactional
    public Stock create (Stock stock){
        Item item = itemService.findById(stock.getItem().getId());
        stock.setItem(item);
        return stockRepository.save(stock);
    }

    @Transactional
    public Stock update (Long id, Stock stock){
        Stock existingStock = findById(id);
        Item existingItem = itemService.findById(stock.getItem().getId());

        existingStock.setItem(existingItem);
        existingStock.setEntryDate(stock.getEntryDate());
        existingStock.setInitialQuantity(stock.getInitialQuantity());
        existingStock.setCurrentQuantity(stock.getCurrentQuantity());
        existingStock.setCostPrice(stock.getCostPrice());

        return stockRepository.save(existingStock);
    }

    @Transactional
    public void delete(Long id){
        findById(id);
        stockRepository.deleteById(id);
    }
}
