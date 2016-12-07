package com.example.hotelreservation.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {


	public String getSet() {
		return set;
	}



	public void setSet(String set) {
		this.set = set;
	}
     private  String set;





	
	private static final long serialVersionUID = 1L;
	
	private Integer age;//年龄

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
