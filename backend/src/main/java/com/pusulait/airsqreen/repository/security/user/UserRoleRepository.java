package com.pusulait.airsqreen.repository.security.user;

import com.pusulait.airsqreen.domain.security.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RepositoryRestResource(exported = false)
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /*admin*/
    public Page<UserRole> findAll(Pageable page);

    public List<UserRole> findByUserId(Long userId);


}
