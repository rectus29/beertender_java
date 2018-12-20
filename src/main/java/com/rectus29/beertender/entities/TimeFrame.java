package com.rectus29.beertender.entities;

import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.tools.DateUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*-----------------------------------------------------*/
/*                Date: 13/07/2018 10:01               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "timeframe",
		uniqueConstraints = @UniqueConstraint(name = "state_unique", columnNames = "state")
)
public class TimeFrame extends BasicGenericEntity<TimeFrame> {

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate = DateUtils.addMonths(new Date(), 2);

	@OneToMany(mappedBy = "timeFrame")
	private List<Order> orderList = new ArrayList<>();

	public TimeFrame() {
		this.state = State.PENDING;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public int compareTo(TimeFrame object) {
		return defaultCompareTo(object);
	}

	public BigDecimal getOrderSum() {
		BigDecimal out = BigDecimal.ZERO;
		for(Order temp : this.getOrderList()){
			if(temp.getState() == State.ENABLE){
				out = out.add(temp.getOrderPrice());
			}
		}
		return null;
	}

	public BigDecimal getOrderPaid() {
		BigDecimal out = BigDecimal.ZERO;
		for(Order temp : this.getOrderList()){
			if(temp.getState() == State.DISABLE){
				out = out.add(temp.getOrderPrice());
			}
		}
		return null;
	}
}
