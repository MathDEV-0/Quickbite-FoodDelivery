package com.mathdev.quickbite.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.Restaurant;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

}
