package pk.webdoor.fx.service;

import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;

import pk.webdoor.fx.dao.IProductDao;
import pk.webdoor.model.Product;

public class ProductService {

    private static final Logger log = Logger.getLogger(ProductService.class.getName());
    //private IProductDao pDao = new ProductDoaImpl();
    @Inject
    private IProductDao productDao;

    public Product getProductByMonth(String month) {
        return productDao.getProductByMonth(month);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public String updateProduct(Product product) {
        log.info("Invoking ProductService.updateProduct()...");
        return productDao.updateProduct(product);
    }

    public String deleteProduct(Product product) {
        log.info("Invoking ProductService.deleteProduct()...");
        return productDao.deleteProduct(product);
    }
}
