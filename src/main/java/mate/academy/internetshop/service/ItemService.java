package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Product;

import java.util.List;

public interface ItemService {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
