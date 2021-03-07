package com.example.demo.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryInterface extends JpaRepository<Product, Long> {

}
