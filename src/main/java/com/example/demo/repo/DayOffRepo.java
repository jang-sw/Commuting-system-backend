package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DayOffEntity;

public interface DayOffRepo extends JpaRepository<DayOffEntity, Long>{

}
