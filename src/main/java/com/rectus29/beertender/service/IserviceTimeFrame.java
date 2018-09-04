package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;

import java.util.List;

public interface IserviceTimeFrame extends GenericManager<TimeFrame, Long> {

	public TimeFrame getCurrentTimeFrame();

	public List<TimeFrame> getAll(State[] stateArray);

}
