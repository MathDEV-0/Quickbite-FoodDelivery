package com.mathdev.quickbite.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{

}
