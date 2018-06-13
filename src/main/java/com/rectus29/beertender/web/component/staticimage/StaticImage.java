package com.rectus29.beertender.web.component.staticimage;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus for
 * Date: 06/03/12
 * Time: 15:13
 */
public class StaticImage extends WebComponent {

    private IModel<String> context;
    private IModel<String> model;

	public StaticImage(String id, String path) {
		super(id);
		this.model = new Model<String>(path);
	}

	public StaticImage(String id, IModel<String> model) {
        super(id, model);
        this.model = model;
    }

    public StaticImage(String id, IModel<String> context, IModel<String> model) {
        super(id, model);
        this.model = model;
        this.context = context;
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        tag.put("src", model.getObject());
    }

}