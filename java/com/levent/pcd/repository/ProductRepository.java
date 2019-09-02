package com.levent.pcd.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.levent.pcd.model.Product;

/*
 * Repository Layer is responsible for retrievel of data
 */
@Repository("productRepository")
public interface ProductRepository extends MongoRepository<Product, String> {

	/*
	 * Same functionality with the query below
	 * 
	 * db.products.find({ categories: { '$in':['children'] } })
	 * 
	 */
	@Query("{'$or': [ {'productName':{$regex:?0,$options:'i'}}, {'manufacturer':{$regex:?0,$options:'i'}}, {'description':{$regex:?0,$options:'i'}} ]}")
	List<Product> searchProduct(String keyword);
	
	List<Product> findByProductNameRegexOrManufacturerRegexOrDescriptionRegex(String productName, String manufacturer, String description);
	List<Product> findTop8ByOrderBySellCountDesc();
	List<Product> findTop8ByOrderByDateCreatedDesc();

	@Query("{ 'category.productName':   ?0 }")
	List<Product> findProductsByCategory(String categoryName);

	/*
	 * db.products.find( { 'productName': /mens/i } );
	 * 
	 * @Query("{ 'productName': /?0/i }")
	 * 
	 */
	@Query("{ 'productName':{$regex:?0,$options:'i'} }")
	List<Product> findProductsByProductNameRegex(String searchString);

	@Query("{ 'sku' : ?0 }")
	Product findBySku(String sku);

}