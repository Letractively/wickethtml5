package com.googlecode.wickethtml5.gui.indexeddb;

import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.wickethtml5.domain.Employee;
import com.googlecode.wickethtml5.persistence.EmployeeRepository;


public class EmployeeForm extends Form<Employee> {

	@SpringBean(name = "employeeRepository", required = true)
	EmployeeRepository repository;

	public EmployeeForm(String id) {
		super(id, new CompoundPropertyModel<Employee>(new Employee()));
		add(new FeedbackPanel("feedback"));
		add(new RequiredTextField<String>("firstName"));
		add(new RequiredTextField<String>("lastName"));
		add(new RequiredTextField<Date>("birthDate"));
		add(new RequiredTextField<Integer>("salary"));
	}

	@Override
	protected void onSubmit() {
		Employee employee = getModelObject();
		repository.save(employee);
	}
}
