package com.moaaz.modernhome.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    @Query(value = "select * from user where user.email = :email " , nativeQuery = true)
    public Optional<User> getUserByEmail(String email);

    public Optional<User> findByEmailAndPassword(String email, String password);

    public Optional<User> findByEmail(String email);
}
