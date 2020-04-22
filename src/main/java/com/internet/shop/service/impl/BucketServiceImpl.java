package com.internet.shop.service.impl;

import com.internet.shop.model.Bucket;
import com.internet.shop.model.Product;
import com.internet.shop.dao.BucketDao;
import com.internet.shop.dao.ItemDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private BucketDao bucketDao;
    @Inject
    private ItemDao itemDao;

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        Bucket bucket = bucketDao.get(bucketId);
        Product product = itemDao.get(itemId);
        bucket.getProducts().add(product);
        return bucketDao.update(bucket);
    }
}
