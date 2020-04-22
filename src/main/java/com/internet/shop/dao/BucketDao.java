package com.internet.shop.dao;

import com.internet.shop.model.Bucket;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Bucket get(Long BucketId);

    Bucket update(Bucket bucket);
}
