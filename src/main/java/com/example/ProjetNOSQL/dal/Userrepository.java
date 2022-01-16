package com.example.ProjetNOSQL.dal;

import com.example.ProjetNOSQL.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Userrepository extends MongoRepository<User,String> {
}
