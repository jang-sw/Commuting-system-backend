package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CommutingEntity;

public interface CommutingRepo extends JpaRepository<CommutingEntity, Long>{

}
