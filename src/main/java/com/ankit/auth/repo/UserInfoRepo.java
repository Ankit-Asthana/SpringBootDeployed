package com.ankit.auth.repo;

import com.ankit.auth.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, String> {

    //It will run the sql query like :-
    //      find everything from UserInfo where username == usernameSupplied

    UserInfo findByUsername(String usernameSupplied);
}
