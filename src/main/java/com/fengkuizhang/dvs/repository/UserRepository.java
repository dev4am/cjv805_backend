package com.fengkuizhang.dvs.repository;

import com.fengkuizhang.dvs.model.User;
import com.fengkuizhang.dvs.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{

    User findByEmail(String email);

}
