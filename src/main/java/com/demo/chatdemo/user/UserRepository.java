package com.demo.chatdemo.user;

import com.demo.chatdemo.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
}
