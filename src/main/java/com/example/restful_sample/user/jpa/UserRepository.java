package com.example.restful_sample.user.jpa;

import com.example.restful_sample.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 데이터베이스 관련된 형태의 Bean
public interface UserRepository extends JpaRepository<User,Integer> {


}
