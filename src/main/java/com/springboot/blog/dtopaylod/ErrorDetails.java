package com.springboot.blog.dtopaylod;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
	
	private Date timeStamp;
	private String message;
	private String details;
	

}
