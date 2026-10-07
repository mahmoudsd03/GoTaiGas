package com.gotaigas.repository;

import com.gotaigas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByFirstNameAndPassword(String firstName, String password);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByLastName (String lastName);

    Optional<User> findUserById (int id);


    @Query("select u from User u order by u.id desc")
    List<User> findSomething();
}
