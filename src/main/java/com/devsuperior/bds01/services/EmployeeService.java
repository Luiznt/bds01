package com.devsuperior.bds01.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.EmployeeRepository;
import com.devsuperior.bds01.services.exceptions.ResourceNotFoundException;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Transactional(readOnly = true)
	public Page<EmployeeDTO> findAll(Pageable pageable) {
		Page<Employee> page = repository.findAll(pageable);
		return page.map(x -> new EmployeeDTO(x));
	}
	
	@Transactional(readOnly = true)
	public EmployeeDTO findById(Long id) {
		Optional<Employee> obj = repository.findById(id);
		Employee entity = obj
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found: id " + id + " not found."));
		return new EmployeeDTO(entity);
	}
	
	@Transactional
	public EmployeeDTO insert(EmployeeDTO dto) {
		Employee entity = repository.save(createNewEmployee(dto));
		//entity.setId(null);
		return new EmployeeDTO(entity);
	}
		
	private Employee createNewEmployee (EmployeeDTO dto) {
		Department department = new Department(dto.getDepartmentId(),null); 
		return new Employee(dto.getId(),dto.getName(), dto.getEmail(),department);
	}
	
	
}
