package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserEntity;
import com.example.demo.repo.custom.UserRepoCustom;

public interface UserRepo extends JpaRepository<UserEntity, Long>, UserRepoCustom{
	

	public long countByAccountIdAndEmailAndAuthAndNameAndDelYn(Long accountId, String email, String auth, String name, String delYn);

	
}
