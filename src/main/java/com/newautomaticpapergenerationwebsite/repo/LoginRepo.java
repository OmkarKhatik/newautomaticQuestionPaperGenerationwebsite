package com.newautomaticpapergenerationwebsite.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.newautomaticpapergenerationwebsite.model.LoginModel;

@Repository
public interface LoginRepo extends  JpaRepository<LoginModel, Long>  {
	LoginModel findByUsername(String username);
}


