package com.huben.designtaste.models;

import java.io.Serializable;

public class Images implements Serializable {
	
	private static final long serialVersionUID = 0x0003L;
	
	public String hidpi;
	public String normal;
	public String teaser;
	
	@Override
	public String toString() {
		return "Images [hidpi=" + hidpi + ", normal=" + normal + ", teaser="
				+ teaser + "]";
	}
	
}
