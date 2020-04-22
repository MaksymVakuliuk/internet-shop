package com.internet.shop.service;

import com.internet.shop.model.Bucket;

public interface BucketService {
    Bucket addItem(Long bucketId, Long itemId);
}
