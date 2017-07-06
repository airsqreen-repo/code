package com.pusulait.airsqreen.repository.security.user;

import com.pusulait.airsqreen.domain.security.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {

    /*admin*/
    public Page<User> findAll(Pageable page);

    @Deprecated
    @Query("select u from User u where u.activated = ?1")
    List<User> findUsersByActivated(Boolean activated);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByUsername(String username);

    Page<User> findUsersByEmailContaining(String email, Pageable page);

    Page<User> findUsersByFirstnameContaining(String firstname, Pageable pageable);

    Page<User> findUsersByLastnameContaining(String lastname, Pageable pageable);

    Page<User> findUsersByFirstnameContainingLastnameContaining(String firstname, String lastname, Pageable pageable);

    Page<User> findUsersByLastnameContainingEmailContaining(String lastname, String email, Pageable pageable);

    Page<User> findUsersByFirstnameContainingEmailContaining(String firstname, String email,  Pageable pageable);

    Page<User> findUsersByFirstnameContainingLastnameContainingEmailContaining(String firstname, String lastname, String email, Pageable pageable);


}
