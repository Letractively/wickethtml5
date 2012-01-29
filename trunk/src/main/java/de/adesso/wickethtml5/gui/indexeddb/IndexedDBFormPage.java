package de.adesso.wickethtml5.gui.indexeddb;


import de.adesso.wickethtml5.gui.BasePage;
import de.adesso.wickethtml5.gui.indexeddb.behavior.IndexedDBFormBehavior;

public class IndexedDBFormPage extends BasePage {

	public IndexedDBFormPage() {
		EmployeeForm employeeForm = new EmployeeForm("employeeForm");
		employeeForm.add(new IndexedDBFormBehavior("employeeDB", "employees", "1", "lastName", "/wickethtml5/indexedDBTable"));
		add(employeeForm);
	}
}
