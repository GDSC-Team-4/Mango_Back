package com.example.mangoplace.domain.user.repository;

import com.example.mangoplace.domain.user.entity.ERole;
import com.example.mangoplace.domain.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
