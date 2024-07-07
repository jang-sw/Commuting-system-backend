package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CommutingEntity;
import com.example.demo.repo.custom.CommutingRepoCustom;

public interface CommutingRepo extends JpaRepository<CommutingEntity, Long>, CommutingRepoCustom{
}
