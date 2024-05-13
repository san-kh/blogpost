package com.springboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		System.out.println("admin password\t"+passwordEncoder.encode("admin"));
		System.out.println("sankh password\t"+passwordEncoder.encode("sankh"));
	}

}
