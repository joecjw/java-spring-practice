package com.joejcw.RedisDemo.repository;

import com.joejcw.RedisDemo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRedisRepository {

    @Autowired
    private RedisTemplate template;

    private static final String PRODUCT_HASH_KEY = "Product";

    public Product save(Product product){
        template.opsForHash().put(PRODUCT_HASH_KEY, product.getId(), product);
        return  product;
    }

    public List<Product> findAll(){
        System.out.println("Through DB");
        return template.opsForHash().values(PRODUCT_HASH_KEY);
    }

    public Product findProductById(int id){
        System.out.println("Through DB");
        Product product = (Product) template.opsForHash().get(PRODUCT_HASH_KEY, id);
        if(product == null){
            return null;
        }else {
            return product;
        }
    }

    public String deleteProductById(int id){
        template.opsForHash().delete(PRODUCT_HASH_KEY, id);
        return "Product With ID="+id+" Deleted";
    }
}
