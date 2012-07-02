package com.googlecode.wickethtml5.gui.util;

import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Reference to JQuery 1.7.1.
 * 
 * @author Tom
 * 
 */
public class JQueryReference extends JavaScriptResourceReference {
	private static final long serialVersionUID = 1L;

	public static final ResourceReference INSTANCE = new JQueryReference();

	private JQueryReference() {
		super(JQueryReference.class, "jquery-1.7.1.js");
	}
}
