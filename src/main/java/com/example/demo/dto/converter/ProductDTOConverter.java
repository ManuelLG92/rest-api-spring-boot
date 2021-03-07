package com.example.demo.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.dto.ProductDTO;
import com.example.demo.models.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductDTOConverter {
	
	private final ModelMapper mapper;
	
	public ProductDTO convertProductToDto(Product product) {
		return mapper.map(product, ProductDTO.class);
	}
	
}
