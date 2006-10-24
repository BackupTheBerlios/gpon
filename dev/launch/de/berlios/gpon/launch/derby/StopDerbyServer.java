package de.berlios.gpon.launch.derby;

import org.apache.derby.drda.NetworkServerControl;

public class StopDerbyServer {

	public static void main(String[] args) {
		NetworkServerControl.main(new String[]{"shutdown"}); 
	}

}
