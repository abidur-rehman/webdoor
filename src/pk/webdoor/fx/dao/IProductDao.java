package pk.webdoor.fx.dao;

import java.util.List;

import pk.webdoor.model.Product;

public interface IProductDao {

	Product getProductByMonth(String month);

	List<Product> getAllProducts();

	String updateProduct(Product product);
        
        String deleteProduct(Product product);
}
