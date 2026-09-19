package com.example.quiz1150512.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quiz1150512.entity.Userdata;


@Repository
public interface UserdataRepository extends JpaRepository<Userdata,Long>{
	// 💡 加上這行，讓系統可以用手機號碼找到 Userdata
    Optional<Userdata> findByPhone(String phone);
	
	
}
