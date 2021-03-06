package com.hkumru.exercise.repository;

import com.hkumru.exercise.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class UserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Member findUserByName(String name) {
        return mongoTemplate.findOne(query(where("username").is(name)), Member.class);
    }

    public Member save(Member member) {
        return mongoTemplate.save(member);
    }
}
