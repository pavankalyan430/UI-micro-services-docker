package com.smartbazaar.priceservice.controller;

import com.smartbazaar.priceservice.model.Price;
import com.smartbazaar.priceservice.repository.PriceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price")
public class PriceController {

    private final PriceRepository priceRepository;

    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @GetMapping("/list")
    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    @PostMapping("/add")
    public Price addPrice(@RequestBody Price price) {
        return priceRepository.save(price);
    }
}
