package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.DaoImpl;
import mate.academy.internetshop.model.Product;
import java.util.List;
import java.util.Optional;

@DaoImpl
public class ItemDaoImpl implements ItemDao {

    @Override
    public Product create(Product product) {
        return null;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
