package com.warehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.model.ProductDetails;
import com.warehouse.repository.WareHouseRepository;

@RestController
public class WareHouseController {

	@Autowired
	private WareHouseRepository repo;

	@GetMapping("/status")
	public List<ProductDetails> getAll() {
		return repo.findByColourNotNull();
	}

	@GetMapping("/product_codes_for_products_with_colour/{colour}")
	public String getProductIdByColour(@PathVariable String colour) {

		List<String> res = repo.findAllProductIdsByColour(colour);

		if (!res.isEmpty()) {
			return res.toString();
		}
		return "Not found";
	}

	@GetMapping("/slot_number_for_product_code/{productId}")
	public String getSlotByProductId(@PathVariable String productId) {
		List<Long> rest = repo.findIdByProductId(productId);
		if (!rest.isEmpty()) {
			return rest.toString();
		}
		return "Not found";
	}

	@GetMapping("/warehouse/{count}")
	public String createWarehouse(@PathVariable int count) {

		for (int i = 0; i < count; i++) {
			repo.save(new ProductDetails());
		}
		return "Created a warehouse with " + count + " slots";

	}

	@PutMapping("/store")
	public String updateProduct(@RequestBody ProductDetails details) {
		Long slotId = repo.findByProductIdAndColour();
		System.out.println("slot id is  " + slotId);
		if (null != slotId) {
			ProductDetails note = repo.findById(slotId)
					.orElseThrow(() -> new ResourceNotFoundException("Slot", "id", slotId));

			note.setColour(details.getColour());
			note.setProductId(details.getProductId());

			ProductDetails note2 = repo.save(note);
			// repo.updateProductDetailsSetProductIdAndColourForId(details.getProductId(),
			// details.getColour(), slotId);
			return "Allocated slot number: " + slotId;
		}
		return "Warehouse is full";

	}

	@DeleteMapping("/sell/{id}")
	public String deleteProduct(@PathVariable(value = "id") Long slotId) {
		if (null != slotId) {
			ProductDetails note = repo.findById(slotId)
					.orElseThrow(() -> new ResourceNotFoundException("Slot", "id", slotId));

			note.setColour(null);
			note.setProductId(null);

			ProductDetails note2 = repo.save(note);

		}

		return "Slot number " + slotId + " is free";

	}
}
