package com.blog_pakicetus.blog_pakicetus.repositories;

import com.blog_pakicetus.blog_pakicetus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
