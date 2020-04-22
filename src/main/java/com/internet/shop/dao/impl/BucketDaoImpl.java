package com.internet.shop.dao.impl;

import com.internet.shop.dao.BucketDao;
import com.internet.shop.db.Storage;
import com.internet.shop.model.Bucket;
import com.internet.shop.lib.Dao;

import java.util.NoSuchElementException;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        return null;
    }

    @Override
    public Bucket get(Long bucketId) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(bucketId))
                .findFirst()
                .orElseThrow(() ->
                         new NoSuchElementException("Can't find bucket with id " + bucketId));
    }

    @Override
    public Bucket update(Bucket bucket) {
        return null;
    }
}
