package com.rectus29.beertender.web.page.admin.packaging.edit;

import com.rectus29.beertender.entities.Packaging;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 11/07/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class PackagingEditPanel extends Panel {

	private IModel<Packaging> packagingIModel;

	public PackagingEditPanel(String id, IModel<?> model) {
		super(id, model);
	}

	public PackagingEditPanel(String id) {
		super(id);
		this.packagingIModel = new Model<>(new Packaging());
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();


	}
}
