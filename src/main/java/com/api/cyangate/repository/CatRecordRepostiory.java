package com.api.cyangate.repository;

import com.api.cyangate.model.CatRecordModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
//@EnableMongoRepositories
public interface CatRecordRepostiory extends MongoRepository<CatRecordModel,String> {
    Optional<CatRecordModel> findCatRecordModelByPath(String path);
}
