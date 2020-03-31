package com.springboot.crud.fullstackwithvue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.crud.fullstackwithvue.model.ERole;
import com.springboot.crud.fullstackwithvue.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(ERole name);

}
