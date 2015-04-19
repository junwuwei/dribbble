package com.huben.designtaste.models;

import java.io.Serializable;

public class Comments implements Serializable {

	private static final long serialVersionUID = 0x0004L;
	
	public int id;
	public String body;
	public int likes_count;
	public String likes_url;
	public String created_at;
	public String updated_at;
	public UserModel user;
}
