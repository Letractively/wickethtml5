package de.adesso.wickethtml5.gui.util;

import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Reference to Wicket's debug javascript.
 * 
 * @author Tom
 * 
 */
public class WicketAjaxDebugReference extends JavaScriptResourceReference {
	private static final long serialVersionUID = 1L;

	public static final ResourceReference INSTANCE = new WicketAjaxDebugReference();

	private WicketAjaxDebugReference() {
		super(WicketAjaxReference.class, "wicket-ajax-debug.js");
	}
}
