package com.devsuperior.bds01.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.services.EmployeeService;





@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

		@Autowired
		private EmployeeService service;

		// Retrieve all with paging.
		@GetMapping
		public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable) {
			// Force sort by name
			PageRequest pageRequest = 
					PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("name"));
			
			Page<EmployeeDTO> list = service.findAll(pageRequest);
			
			return ResponseEntity.ok().body(list);
		}
				
		@GetMapping(value = "/{id}")
		public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) {
			EmployeeDTO dto = service.findById(id);
			return ResponseEntity.ok().body(dto);

		}
		
		@PostMapping
		public ResponseEntity<EmployeeDTO> insert(@RequestBody EmployeeDTO dto) {
			dto = service.insert(dto);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
			return ResponseEntity.created(uri).body(dto);
		}
}
