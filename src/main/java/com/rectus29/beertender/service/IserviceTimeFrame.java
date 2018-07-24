package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.TimeFrame;

public interface IserviceTimeFrame extends GenericManager<TimeFrame, Long> {

	public TimeFrame getCurrentTimeFrame();

}
