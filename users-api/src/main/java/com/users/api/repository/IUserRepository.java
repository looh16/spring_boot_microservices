package com.users.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.users.api.models.UserEntity;

public interface IUserRepository extends CrudRepository<UserEntity, Long>{

}
