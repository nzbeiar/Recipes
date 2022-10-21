package com.recipes.Recipes.persistance;

import com.recipes.Recipes.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM USERS u WHERE u.email = :email LIMIT 1", nativeQuery = true)
    User findUserByEmail(@Param("email")String email);
}
