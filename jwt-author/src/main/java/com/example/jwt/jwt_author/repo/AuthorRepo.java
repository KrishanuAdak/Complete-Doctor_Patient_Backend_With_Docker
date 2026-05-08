package com.example.jwt.jwt_author.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jwt.jwt_author.model.User;
@Repository
public interface AuthorRepo extends JpaRepository<User,Integer>{
    @Query(value="select * from users where email=?1",nativeQuery=true)
    public User findByEmail(String email);
    
}
