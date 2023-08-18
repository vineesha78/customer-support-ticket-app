package com.axis.bank.controllers;

import com.axis.bank.entities.Customer;
import com.axis.bank.entities.Employee;
import com.axis.bank.entities.Role;
import com.axis.bank.entities.User;
import com.axis.bank.repositories.CustomerRepository;
import com.axis.bank.repositories.EmployeeRepository;
import com.axis.bank.repositories.RoleRepository;
import com.axis.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/customer")
    public String customerRegister(@RequestBody Customer customer){
        System.out.println(customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Role roles = roleRepository.findByName("USER").orElseThrow(()-> new RuntimeException("User Not Found!!"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roles);
        User user = new User();
        user.setUsername(customer.getAccountId());
        user.setPassword(customer.getPassword());
        user.setRoles(roleSet);
        userRepository.save(user);
        customerRepository.save(customer);
        return "Customer saved successfully and User also Created";
    }
    @PostMapping("/employee")
    public String employeeRegister(@RequestBody Employee employee){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Role roles = roleRepository.findByName("ADMIN").orElseThrow(()-> new RuntimeException("User Not Found!!"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roles);
        User user = new User();
        user.setUsername(employee.getEmployeeId());
        user.setPassword(employee.getPassword());
        user.setRoles(roleSet);
        userRepository.save(user);
        employeeRepository.save(employee);
        return "Employee saved successfully and User also Created";
    }
}
