package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Product {
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	private float price;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
}
