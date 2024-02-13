package com.example.app2.services;

import com.example.app2.entity.PastPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class PastPriceService {

   // private final PastPriceRepository repository;

    public void insert(PastPrice pastPrice) {
       // repository.saveAndFlush(pastPrice);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<PastPrice> findAllByProductId(long productId){
        return null; //repository.findAllByProductId(productId);
    }
}
