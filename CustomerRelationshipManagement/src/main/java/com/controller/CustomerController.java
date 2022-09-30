package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Customer;
import com.service.CustomerService;


@Controller
@RequestMapping("/customers")
public class CustomerController {
@Autowired
	CustomerService customerService;

	@RequestMapping("/list")
	public String listCustomer(Model theModel) {
		List<Customer> customer = customerService.findAll();
		theModel.addAttribute("Customer", customer);

		return "list-customer";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Customer customer = new Customer();
		theModel.addAttribute("Customer", customer);

		return "Customer-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") int id, Model theModel) {
		Customer customer = customerService.findById(id);
		theModel.addAttribute("Customer", customer);

		return "Customer-form";
	}

	@RequestMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {
		System.out.println(id);
		Customer customer;
		if (id != 0) {
			customer = customerService.findById(id);
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setEmail(email);
		} else
			customer = new Customer(firstName, lastName, email);
		customerService.save(customer);
		return "redirect:/customers/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		customerService.deleteById(id);
		return "redirect:/customers/list";
	}
}
