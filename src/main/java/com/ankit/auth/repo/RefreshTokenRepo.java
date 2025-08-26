package com.ankit.auth.repo;

import com.ankit.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

    //it will run the SQL query like: -
    //      find everything from RefreshToken where token == tokenSupplied
    Optional<RefreshToken> findByToken(String tokenSupplied);
}
