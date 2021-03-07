package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.converter.ProductDTOConverter;
import com.example.demo.models.Product;
import com.example.demo.models.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductRepository productRepository;
	private final ProductDTOConverter ProductDTOConverterInstance;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/producto")
	public ResponseEntity<?> obtenerTodos() {
		List<Product> Products= productRepository.findAll();
		
		if(Products.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay productos");
			//return ResponseEntity.noContent().build();
		} 
		List<ProductDTO> dtoList = Products.stream()
				.map(ProductDTOConverterInstance::convertProductToDto)
				.collect(Collectors.toList());
				
		
			return ResponseEntity.ok(dtoList);
		

	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return Null si no encuentra el producto
	 */
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
		// Vamos a modificar este código
		Product result = productRepository.findById(id).orElse(null);
		if(result == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay un producto con el id ");
			//return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(result);
	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return producto insertado
	 */
	@PostMapping("/producto")
	public  ResponseEntity<?> nuevoProducto(@RequestBody Product nuevo) {
		// Vamos a modificar este código
		Product result = productRepository.save(nuevo);
		if(result == null){
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	/**
	 * 
	 * @param editar
	 * @param id
	 * @return
	 */
	@PutMapping("/producto/{id}")
	public ResponseEntity<?> editarProducto(@RequestBody Product editar, @PathVariable Long id) {
		return productRepository.findById(id).map(p -> {
			p.setName(editar.getName());
			p.setPrice(editar.getPrice());
			return ResponseEntity.ok(productRepository.save(p));
		}).orElseGet(()-> {
			return ResponseEntity.notFound().build();
		});
		
		
			//return productRepository.save(editar);
		
	//	return ResponseEntity.notFound().build();
	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return  ResponseEntity.noContent().build();
		}
		return  ResponseEntity.notFound().build();
	}

}
