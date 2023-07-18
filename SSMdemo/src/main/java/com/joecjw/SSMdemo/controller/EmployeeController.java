package com.joecjw.SSMdemo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.joecjw.SSMdemo.dao.EmployeeMapper;
import com.joecjw.SSMdemo.entity.Employee;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeMapper mapper;
	
	@RequestMapping("/")	
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("list-employees");
		mav.addObject("listemployees", mapper.getAllEmployees());
		return mav;
	}
	
	@RequestMapping("/showFormForAddEmployee")	
	public ModelAndView showFormForAddEmployee() {
		ModelAndView mav = new ModelAndView("add-employee");
		mav.addObject("employee", new Employee());
		return mav;
	}
	
	@RequestMapping("/addEmployeeProcess")	
	public String addEmployeeProcess(@ModelAttribute("employee") Employee e) {
		if(e.getId() == null) {
			int row_Id = mapper.insertEmployee(e);
			if(row_Id > 0) {
				return "redirect:/";
			}else {
				return "/addEmployeeProcess";
			}
		}else {
			mapper.updateEmployee(e);
			return "redirect:/";
		}
	}

	@RequestMapping("/updateEmployeeProcessRedirect")	
	public ModelAndView updateEmployeeProcessRedirect(@RequestParam("employeeId") int employeeId) {
		ModelAndView mav = new ModelAndView("add-employee");
		Employee targetEmployee = mapper.findById(employeeId);
		mav.addObject("employee", targetEmployee);
		return mav;
	}
	
	@RequestMapping("/deleteEmployee")	
	public String deleteEmployeeProcess(@RequestParam("employeeId") int employeeId) {
		int row_Id = mapper.deleteEmployee(employeeId);
		return "redirect:/";
	}
}