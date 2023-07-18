package com.joecjw.SSMdemo.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.joecjw.SSMdemo.entity.Employee;
import com.joecjw.SSMdemo.utility.MybatisUtil;

@Repository
public class EmployeeMapper {
	
	public List<Employee> getAllEmployees(){
		
		SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
		List<Employee> employeeList = session.selectList("getAllEmployees");
		session.commit();
		session.close();
		
		return employeeList;
	}

	
	public int insertEmployee(Employee e) {
		
		SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
		int rowId = session.insert("insertEmployee", e);
		session.commit();
		session.close();
		
		return rowId;
	}
	
	public int updateEmployee(Employee e) {
		
		SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
		int rowId = session.update("updateEmployee", e);
		session.commit();
		session.close();
		
		return rowId;
	}
	
	public int deleteEmployee(int employeeId) {
		
		SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
		int rowId = session.delete("deleteEmployee", employeeId);
		session.commit();
		session.close();
		
		return rowId;
	}
	
	public Employee findById(int employeeId) {
		
		SqlSession session = MybatisUtil.getSqlSessionFactory().openSession();
		Employee e = session.selectOne("findById", employeeId);
		session.commit();
		session.close();
		
		return e;
	}
}
