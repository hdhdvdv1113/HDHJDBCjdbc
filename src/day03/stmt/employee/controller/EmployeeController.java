package day03.stmt.employee.controller;

import java.util.List;

import day03.stmt.employee.model.vo.Employee;
import day04.pstmt.employee.model.dao.EmployeeDAO;

public class EmployeeController {
	private EmployeeDAO eDao;
	
	public EmployeeController() {
		eDao = new EmployeeDAO();
	}
	
	public void registerEmployee(Employee emp) {
		eDao.insertEmployee(emp);
	}
	
	public List<Employee> printAllEmployees() {
		List<Employee> eList = eDao.selectAllEmployees();
		return eList;
	}

	public int removeEmployee(String empId) {
		int result = eDao.deleteEmployee(empId);
		return result;
	}

	public int modifyEmployee(Employee emp) {
		return eDao.updateEmployee(emp);
	}
}
