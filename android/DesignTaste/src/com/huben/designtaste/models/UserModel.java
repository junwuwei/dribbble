package com.huben.designtaste.models;

import java.io.Serializable;

import com.huben.designtaste.utils.Links;

public class UserModel implements Serializable {

	private static final long serialVersionUID = 0x0002L;
	
	public int id;
	public String name;
	public String username;
	public String html_url;
	public String avatar_url;
	public String bio;
	public String location;
	public Links links;
	public int buckets_count;
	public int comments_received_count;
	public int followers_count;
	public int followings_count;
	public int likes_count;
	public int likes_received_count;
	public int projects_count;
	public int rebounds_received_count;
	public int shots_count;
	public int teams_count;
	public boolean can_upload_shot;
	public String type;
	public boolean pro;
	public String buckets_url;
	public String followers_url;
	public String following_url;
	public String likes_url;
	public String projects_url;
	public String shots_url;
	public String teams_url;
	public String created_at;
	public String updated_at;
	
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", username="
				+ username + ", html_url=" + html_url + ", avatar_url="
				+ avatar_url + ", bio=" + bio + ", location=" + location
				+ ", links=" + links + ", buckets_count=" + buckets_count
				+ ", comments_received_count=" + comments_received_count
				+ ", followers_count=" + followers_count
				+ ", followings_count=" + followings_count + ", likes_count="
				+ likes_count + ", likes_received_count="
				+ likes_received_count + ", projects_count=" + projects_count
				+ ", rebounds_received_count=" + rebounds_received_count
				+ ", shots_count=" + shots_count + ", teams_count="
				+ teams_count + ", can_upload_shot=" + can_upload_shot
				+ ", type=" + type + ", pro=" + pro + ", buckets_url="
				+ buckets_url + ", followers_url=" + followers_url
				+ ", following_url=" + following_url + ", likes_url="
				+ likes_url + ", projects_url=" + projects_url + ", shots_url="
				+ shots_url + ", teams_url=" + teams_url + ", created_at="
				+ created_at + ", updated_at=" + updated_at + "]";
	}
	
}
