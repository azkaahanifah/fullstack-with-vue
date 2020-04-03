package com.coder.springjwtauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coder.springjwtauth.model.ERole;
import com.coder.springjwtauth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(ERole name);

}
