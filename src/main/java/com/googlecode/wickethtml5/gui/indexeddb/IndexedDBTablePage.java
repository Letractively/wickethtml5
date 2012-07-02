package com.googlecode.wickethtml5.gui.indexeddb;

import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wickethtml5.gui.BasePage;
import com.googlecode.wickethtml5.gui.indexeddb.behavior.IndexedDBTableBehavior;


public class IndexedDBTablePage extends BasePage {

	public IndexedDBTablePage() {
		WebMarkupContainer employeeTable = new WebMarkupContainer("employeeTable");
		employeeTable.add(new IndexedDBTableBehavior("employeeDB", "employees", "1", "lastName"));
		add(employeeTable);
	}

}
