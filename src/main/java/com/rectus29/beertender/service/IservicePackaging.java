package com.rectus29.beertender.service;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/09/2018 17:22               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.entities.Packaging;
import com.rectus29.beertender.enums.State;

import java.util.List;

public interface IservicePackaging extends GenericManager<Packaging, Long> {

	public List<Packaging> getAll(List<State> stateList);

}
