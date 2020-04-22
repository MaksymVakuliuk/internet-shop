package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ItemDao {
    Product create(Product product);

    Optional<Product> get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
