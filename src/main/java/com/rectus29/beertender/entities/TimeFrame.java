package com.rectus29.beertender.entities;

import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.tools.DateUtil;
import org.apache.logging.log4j.core.config.plugins.validation.Constraint;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*-----------------------------------------------------*/
/*                Date: 13/07/2018 10:01               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(	name = "timeframe",
		uniqueConstraints = @UniqueConstraint(name = "state_unique" ,columnNames = "state")
)
public class TimeFrame extends GenericEntity {

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate = DateUtil.addMonths(new Date(), 2);

	private State state = State.PENDING;

	@OneToMany(mappedBy = "timeFrame")
	private List<Order> orderList = new ArrayList<>();

	public TimeFrame() {
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public int compareTo(Object object) {
		return defaultCompareTo(object);
	}
}
