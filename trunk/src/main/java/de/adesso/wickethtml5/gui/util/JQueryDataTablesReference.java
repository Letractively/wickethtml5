package de.adesso.wickethtml5.gui.util;

import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Reference to JQuery DataTables 1.8.2.
 * 
 * @author Tom
 * 
 */
public class JQueryDataTablesReference extends JavaScriptResourceReference {
	private static final long serialVersionUID = 1L;

	public static final ResourceReference INSTANCE = new JQueryDataTablesReference();

	private JQueryDataTablesReference() {
		super(JQueryDataTablesReference.class, "jquery-dataTables-1.8.2.js");
	}
}
