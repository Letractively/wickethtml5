package de.adesso.wickethtml5.gui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.adesso.wickethtml5.domain.Employee;
import de.adesso.wickethtml5.persistence.EmployeeRepository;

public class Homepage extends BasePage {

	@SpringBean(name = "employeeRepository", required = true)
	private EmployeeRepository employeeRepository;

	public Homepage() {
		Employee employee = new Employee();
		employee.setFirstName("Tom");
		employee.setLastName("Hombergs");
		Employee savedEmployee = employeeRepository.save(employee);

		Employee e = employeeRepository.findOne(savedEmployee.getId());
		System.out.println(e.getFirstName());
	}

}
