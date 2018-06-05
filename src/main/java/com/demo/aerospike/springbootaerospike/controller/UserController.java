package com.demo.aerospike.springbootaerospike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.aerospike.springbootaerospike.cache.AerospikeBaseClassWrapper;
import com.demo.aerospike.springbootaerospike.cache.AerospikeRecord;
import com.demo.aerospike.springbootaerospike.cache.User;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userListSet")
	private AerospikeBaseClassWrapper<String, User> aerospikeBaseClassWrapper;

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		return "success!!!!!";
	}

	@RequestMapping("/insert")
	@ResponseBody
	public String insert() {
		User user = new User(2, "AAA", "Delhi");

		aerospikeBaseClassWrapper.insert(user.getId() + "", user);
		return "success inserted.";
	}

	@RequestMapping("/get")
	@ResponseBody
	public String get() {

		AerospikeRecord<User> user = aerospikeBaseClassWrapper.get("2");

		return user.getValue().toString();
	}
}
