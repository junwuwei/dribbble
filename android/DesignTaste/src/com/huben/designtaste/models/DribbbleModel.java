package com.huben.designtaste.models;

import java.io.Serializable;
import java.util.Arrays;

public class DribbbleModel implements Serializable {

	private static final long serialVersionUID = 0x0001L;
	
	public int id;
	public String title;
	public String description;
	public int width;
	public int height;
	public Images images;
	public int views_count;
	public int likes_count;
	public int comments_count;
	public int attachments_count;
	public int rebounds_count;
	public int buckets_count;
	public String created_at;
	public String updated_at;
	public String html_url;
	public String attachments_url;
	public String buckets_url;
	public String comments_url;
	public String likes_url;
	public String projects_url;
	public String rebounds_url;
	public String[] tags;
	public UserModel user;
	public String team;
	
	@Override
	public String toString() {
		return "DribbbleModel [id=" + id + ", title=" + title
				+ ", description=" + description + ", width=" + width
				+ ", height=" + height + ", images=" + images
				+ ", views_count=" + views_count + ", likes_count="
				+ likes_count + ", comments_count=" + comments_count
				+ ", attachments_count=" + attachments_count
				+ ", rebounds_count=" + rebounds_count + ", buckets_count="
				+ buckets_count + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + ", html_url=" + html_url
				+ ", attachments_url=" + attachments_url + ", buckets_url="
				+ buckets_url + ", comments_url=" + comments_url
				+ ", likes_url=" + likes_url + ", projects_url=" + projects_url
				+ ", rebounds_url=" + rebounds_url + ", tags="
				+ Arrays.toString(tags) + ", user=" + user + ", team=" + team
				+ "]";
	}
	
}
