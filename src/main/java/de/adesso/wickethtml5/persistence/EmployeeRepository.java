package de.adesso.wickethtml5.persistence;

import de.adesso.wickethtml5.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

}
