package com.manish.blog;

import com.manish.blog.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApiApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	void repoTest(){
		String className=userRepo.getClass().getName();
		String packName=userRepo.getClass().getPackageName();
		System.out.println("Class Name: "+className);
		System.out.println("Package Name: "+packName);
	}

}
