package com.example.mangoplace.domain.shop.repository;

import com.example.mangoplace.domain.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
}
