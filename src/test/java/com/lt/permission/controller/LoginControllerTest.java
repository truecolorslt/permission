package com.lt.permission.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lt.permission.BaseTest;

public class LoginControllerTest extends BaseTest {
	@Autowired
	LoginController loginController;
	@Autowired
	RoleController roleController;
	@Test
	 public void testAOP1(){
	//启动Spring容器        
		//loginController.testAOP("lantian", "111111");
		roleController.deleteRole("4326f6e4-ceab-44c1-a9fb-f2597cc87fe4");
	}
}
