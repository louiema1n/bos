package com.lm.bos;

import org.junit.Test;

import com.lm.bos.domain.BcSubarea;
import com.lm.crm.domain.Customer;

public class TestDemo {
	@Test
	public void Demo1() {
		Customer customer = new Customer();
		System.out.println(customer.getAddress());
	}
}
