package com.acastrillo.supermarketbackend.repositories;

import com.acastrillo.supermarketbackend.model.Sample;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends MongoRepository<Sample, String> {

    public Sample findByDescription(String description);

    public long count();

}
