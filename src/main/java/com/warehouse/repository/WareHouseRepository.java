package com.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.model.ProductDetails;

@Repository
public interface WareHouseRepository extends JpaRepository<ProductDetails, Long> {

	@Query(value = "SELECT Id FROM PRODUCT_DETAILS  where COLOUR is null and PRODUCT_ID is null order by ID limit 1", nativeQuery = true)
	Long findByProductIdAndColour();

//	@Query(value="UPDATE Product_Details c SET c.product_Id = :productId , c.colour = :colour WHERE c.id = :id" , nativeQuery=true)
//	int updateProductDetailsSetProductIdAndColourForId(@Param("productId") String productId, @Param("colour") String colour, @Param("id") Long id);

	List<ProductDetails> findByColourNotNull();

	@Query("SELECT t.productId FROM ProductDetails t where t.colour = :colour")
	List<String> findAllProductIdsByColour(@Param("colour") String colour);

	@Query("SELECT t.Id FROM ProductDetails t where t.productId = :productId")
	List<Long> findIdByProductId(@Param("productId") String productId);
}
