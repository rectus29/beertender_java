package com.rectus29.beertender.web.page.admin.order;

import com.google.common.collect.HashMultimap;
import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.web.component.formattednumberlabel.NumericLabel;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.order.edit.OrderEditPanel;
import com.rectus29.beertender.web.panel.lazyloadPanel.LazyLoadPanel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*-----------------------------------------------------*/
/*                     Rectus_29                       */
/*                                                     */
/*                Date: 08/08/2018 16:04               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderSummaryChildPanel extends Panel {

	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;
	private IModel<TimeFrame> timeFrameModel;
	private BeerTenderModal modal;

	public OrderSummaryChildPanel(String id, IModel<TimeFrame> model) {
		super(id, model);
		this.timeFrameModel = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new CurrencyLabel("orderSum", timeFrameModel.getObject().getOrderSum()));
		add(new CurrencyLabel("orderpaid", timeFrameModel.getObject().getOrderPaid()));

		double nbProduct =0d;
		double nbAttendees =0d;
		for(Order temp:timeFrameModel.getObject().getOrderList()){
			if(temp.getState() != State.DELETED){
				if(temp.getOrderItemList().size() > 0){
					nbAttendees = nbAttendees++;
				}

				for(OrderItem tempItem : temp.getOrderItemList()){
					nbProduct = tempItem.getQuantity();
				}
			}
		}

		add(new Label("orderAttendees", nbAttendees));
		add(new Label("orderProdNb", nbProduct));

		add(new ListView<Map.Entry<Product, Long>>("orderProductRv", new Model<ArrayList<Map.Entry<Product, Long>>>(){
			@Override
			public ArrayList<Map.Entry<Product, Long>> getObject() {
				HashMap<Product, Long> out = new HashMap<>();
				for(Order tempOrder : timeFrameModel.getObject().getOrderList()){
					for(OrderItem tempOrderItem : tempOrder.getOrderItemList()){
						if(out.get(tempOrderItem.getProduct()) != null){
							out.put(tempOrderItem.getProduct(), out.get(tempOrderItem.getProduct()) + tempOrderItem.getQuantity());
						}else{
							out.put(tempOrderItem.getProduct(), tempOrderItem.getQuantity());
						}
					}
				}
				return new ArrayList<>(out.entrySet());
			}
		}) {
			@Override
			protected void populateItem(ListItem<Map.Entry<Product, Long>> item) {
				item.add(new Label("prod", item.getModelObject().getKey()));
				item.add(new NumericLabel<>("nb", item.getModelObject().getValue()));
			}
		});

		ListView rv = new ListView<Order>("orderRv", timeFrameModel.getObject().getOrderList()){
			@Override
			protected void populateItem(final ListItem<Order> item) {
				item.add(new Label("user", item.getModelObject().getUser().getFormattedName()));
				item.add(new AjaxLink("orderEditLink"){
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        modal.setContent(new OrderEditPanel(modal.getContentId(), item.getModel()){
							@Override
							protected void onSave(AjaxRequestTarget target, IModel<Order> orderIModel) {
								serviceOrder.save(orderIModel.getObject());
								modal.close(target);
							}

							@Override
							protected void onCancel(AjaxRequestTarget target, IModel<Order> orderIModel) {
								modal.close(target);
							}
						});
                        modal.setTitle("#" + item.getModelObject().getId() + " - " + item.getModelObject().getUser().getFormattedName() );
                        modal.show(ajaxRequestTarget, BeerTenderModal.ModalFormat.MEDIUM);
                    }
				});

//				item.add(new AjaxLink("orderPaymentLink"){
//					@Override
//					public void onClick(AjaxRequestTarget ajaxRequestTarget) {
//						modal.setContent(new OrderPayPanel(modal.getContentId(), item.getModel()){
//							@Override
//							protected void onSave(AjaxRequestTarget target, IModel<Order> orderIModel) {
//								serviceOrder.save(orderIModel.getObject());
//								modal.close(target);
//							}
//
//							@Override
//							protected void onCancel(AjaxRequestTarget target, IModel<Order> orderIModel) {
//								modal.close(target);
//							}
//						});
//						modal.setTitle("#" + item.getModelObject().getId() + " - " + item.getModelObject().getUser().getFormattedName() );
//						modal.show(ajaxRequestTarget, BeerTenderModal.ModalFormat.MEDIUM);
//					}
//				});

				item.add(new DownloadLink("orderPrintLink", new LoadableDetachableModel<File>() {
					@Override
					protected File load() {
						try {
							File outputFile = new File("Commande.xls");
							Workbook wb = new HSSFWorkbook();
							Sheet sheet = wb.createSheet();

							sheet.createRow(0).createCell(0).setCellValue(item.getModelObject().getTimeFrame().getName());
							sheet.getRow(0).createCell(2).setCellValue("date  de cloture");
							sheet.getRow(0).createCell(3).setCellValue(item.getModelObject().getTimeFrame().getEndDate());

							sheet.createRow(1).createCell(0).setCellValue("Utilisateur");
							sheet.getRow(1).createCell(1).setCellValue(item.getModelObject().getUser().getFormattedName());
							sheet.getRow(1).createCell(2).setCellValue("Commande");
							sheet.getRow(1).createCell(3).setCellValue("#" + item.getModelObject().getId());

							//build productHeader
							Row itemRowHeader = sheet.createRow(9);
							itemRowHeader.createCell(0).setCellValue("Produit");
							itemRowHeader.createCell(1).setCellValue("Packaging");
							itemRowHeader.createCell(2).setCellValue("PU");
							itemRowHeader.createCell(3).setCellValue("Qte");
							itemRowHeader.createCell(4).setCellValue("Total");
							int itemRowStart = 10;
							for(OrderItem temp: item.getModelObject().getOrderItemList()){
								Row itemRow = sheet.createRow(itemRowStart);
								itemRow.createCell(0).setCellValue(temp.getProduct().getName());
								itemRow.createCell(1).setCellValue(temp.getProduct().getPackaging().getName());
								itemRow.createCell(2).setCellValue(temp.getProductPrice().longValue());
								itemRow.createCell(3).setCellValue(temp.getQuantity());
								itemRow.createCell(4).setCellValue(temp.getSum());
								itemRowStart++;
							}

							((HSSFWorkbook) wb).write(outputFile);
							wb.close();
							return outputFile;
						} catch (IOException e) {
							return null;
						}
					}
				}));
				item.add(new ListView<OrderItem>("orderItemRv", item.getModelObject().getOrderItemList()) {
					@Override
					protected void populateItem(ListItem<OrderItem> item) {
						item.add(new Label("product", item.getModelObject().getProduct().getName()));
						item.add(new Label("type", item.getModelObject().getProduct().getPackaging().getName()));
						item.add(new NumericLabel("qte", item.getModelObject().getQuantity()));
						item.add(new CurrencyLabel("unitPrice", new PropertyModel<OrderItem>(item.getModelObject(), "quantity")));
						item.add(new CurrencyLabel("total", new Model(item.getModelObject().getSum())));
					}
				});
				item.add(new CurrencyLabel("orderTotal", new Model<>(item.getModelObject().getOrderPrice())));
			}
		};
		add(rv);

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
	}
}
