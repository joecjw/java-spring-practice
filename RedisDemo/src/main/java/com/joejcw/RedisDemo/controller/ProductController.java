package com.joejcw.RedisDemo.controller;

import com.joejcw.RedisDemo.entity.Product;
import com.joejcw.RedisDemo.repository.ProductRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRedisRepository productRedisRepository;

    @PostMapping("/")
    public Product save(@RequestBody Product product){
        return productRedisRepository.save(product);
    }

    @GetMapping("/")
    @Cacheable(cacheNames = "product_list_cache")
    public List<Product> getAllProducts(){
        return productRedisRepository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "product_id_cache", key = "#id")
    public Product getProductById(@PathVariable(name = "id") int id){
        return productRedisRepository.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "product_id_cache", key = "#id")
    public String deleteProductById(@PathVariable(name = "id") int id){
        return productRedisRepository.deleteProductById(id);
    }
}
