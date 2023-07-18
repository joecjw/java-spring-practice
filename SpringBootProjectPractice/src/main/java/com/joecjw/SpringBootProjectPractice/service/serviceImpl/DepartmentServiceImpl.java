package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.Department;
import com.joecjw.SpringBootProjectPractice.exception.DepartmentNotFoundException;
import com.joecjw.SpringBootProjectPractice.repository.DepartmentRepository;
import com.joecjw.SpringBootProjectPractice.repository.TeacherRepository;
import com.joecjw.SpringBootProjectPractice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartmentWithNoTeachers(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() throws DepartmentNotFoundException {
        List<Department> departmentList = new ArrayList<>();
        departmentRepository.findAll().forEach(departmentList::add);
        if (departmentList.isEmpty()) {
            throw new DepartmentNotFoundException("There Is No Department Created yet");
        }else {
            return departmentList;
        }
    }

    @Override
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    @Override
    public Department updateDepartmentById(Long id, Department department) throws DepartmentNotFoundException {
        Optional<Department> departmentContainer = departmentRepository.findById(id);
        if(departmentContainer.isPresent()){
            Department _department = departmentContainer.get();
            _department.setName(department.getName());
            _department.setAddress(department.getAddress());
            _department.setCode(department.getCode());
            return  departmentRepository.save(_department);
        }else {
            throw new DepartmentNotFoundException("Department With id= "+id+" Is Not Found");
        }
    }

    @Override
    public Department getDepartmentById(Long id) throws DepartmentNotFoundException {
        Optional<Department> departmentContainer = departmentRepository.findById(id);
        if(departmentContainer.isPresent()){
            return departmentContainer.get();
        }else {
            throw new DepartmentNotFoundException("Department With id= "+id+" Is Not Found");
        }
    }

    @Override
    public void deleteDepartmentById(Long id) throws DepartmentNotFoundException {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new DepartmentNotFoundException("Department With id= " + id + " Is Not Found");
        }
    }

    @Override
    public Department getDepartmentByNameIgnoreCase(String name) throws DepartmentNotFoundException {

        if(departmentRepository.findByNameIgnoreCase(name) != null){
            return departmentRepository.findByNameIgnoreCase(name);
        }else {
            throw new DepartmentNotFoundException("Department With name= "+name+" Is Not Found");
        }
    }
}
