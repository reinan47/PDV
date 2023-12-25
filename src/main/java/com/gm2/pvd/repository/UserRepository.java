package com.gm2.pvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gm2.pvd.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("select u from User u left join fetch u.sales where u.userName = :userName")
    User findUserByUsername(@Param("userName") String username);
}
