package com.rectus29.beertender.enums;

/*-----------------------------------------------------*/
/*						Rectus29					   */
/*                Date: 13/06/2018 16:28               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public enum State {
	DISABLE, ENABLE, PENDING, DELETED, ERROR, LOCKED;



	public boolean isEnable(){
		return false;
	}
}
