package com.googlecode.wickethtml5.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.googlecode.wickethtml5.domain.Employee;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

}
