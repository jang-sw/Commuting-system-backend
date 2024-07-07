package com.example.demo.repo.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserEntity;


@Repository
public interface UserRepoCustom {
	List<UserEntity> findAllNotDeleted();

}
