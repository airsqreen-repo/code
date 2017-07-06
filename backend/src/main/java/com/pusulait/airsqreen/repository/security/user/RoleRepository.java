package com.pusulait.airsqreen.repository.security.user;

import com.pusulait.airsqreen.domain.base.DataStatus;
import com.pusulait.airsqreen.domain.security.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, Long> {

    /*admin*/

    Page<Role> findByNameContainingIgnoreCaseAndDataStatus(@Param(value = "name") String name, @Param(value = "dataStatus") DataStatus dataStatus, Pageable pageable);

   // Page<Role> findByDataStatus(@Param(value = "dataStatus") DataStatus dataStatus, Pageable pageable);

    @Override
    Page<Role> findAll(Pageable pageable);

    @Override
    Role save(Role role);

    @Override
    void delete(Role role);

    //@Query("select r from Role r where tosearch(r.name) = tosearch(:name) and r.id <> :id")
    List<Role> findByNameContainingAndIdNot(String name, Long id);

    //@Query("select r from Role r where tosearch(r.name) = tosearch(:name) ")
    List<Role> findByNameContaining(String name);

    Page<Role> findByNameContainingIgnoreCaseAndDataStatus(String name,Pageable pageable);
    Page<Role> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Role> findByDataStatus(DataStatus dataStatus, Pageable pageable);

}
