package com.googlecode.wickethtml5.gui.indexeddb;

import com.googlecode.wickethtml5.gui.BasePage;
import com.googlecode.wickethtml5.gui.indexeddb.behavior.IndexedDBFormBehavior;

public class IndexedDBFormPage extends BasePage {

	public IndexedDBFormPage() {
		EmployeeForm employeeForm = new EmployeeForm("employeeForm");
		employeeForm.add(new IndexedDBFormBehavior("employeeDB", "employees", "1", "lastName", "/indexedDBTable"));
		add(employeeForm);
	}
}
