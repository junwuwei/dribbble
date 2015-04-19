package com.huben.designtaste.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.huben.designtaste.models.Comments;
import com.huben.designtaste.models.DribbbleModel;
import com.huben.designtaste.models.Images;
import com.huben.designtaste.models.UserModel;

public class JsonUitls {

	public static DribbbleModel jsonToDribbbleModel(JSONObject jsonObj) {
		DribbbleModel model = new DribbbleModel();
		try {
			model.id = jsonObj.getInt("id");
			model.title = jsonObj.getString("title");
			model.description = jsonObj.getString("description");
			model.width = jsonObj.getInt("width");
			model.height = jsonObj.getInt("height");
			model.views_count = jsonObj.getInt("views_count");
			model.likes_count = jsonObj.getInt("likes_count");
			model.comments_count = jsonObj.getInt("comments_count");
			model.attachments_count = jsonObj.getInt("attachments_count");
			model.rebounds_count = jsonObj.getInt("rebounds_count");
			model.buckets_count = jsonObj.getInt("buckets_count");
			model.created_at = jsonObj.getString("created_at").substring(0, 10);
			model.updated_at = jsonObj.getString("updated_at").substring(0, 10);
			model.html_url = jsonObj.getString("html_url");
			model.attachments_url = jsonObj.getString("attachments_url");
			model.buckets_url = jsonObj.getString("buckets_url");
			model.comments_url = jsonObj.getString("comments_url");
			model.likes_url = jsonObj.getString("likes_url");
			model.projects_url = jsonObj.getString("projects_url");
			model.rebounds_url = jsonObj.getString("rebounds_url");
			model.images = jsonToImages(jsonObj.getJSONObject("images"));
			model.user = jsonToUser(jsonObj.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public static Images jsonToImages(JSONObject jsonObj) {
		Images images = new Images();
		try {
			images.hidpi = jsonObj.getString("hidpi");
			images.normal = jsonObj.getString("normal");
			images.teaser = jsonObj.getString("teaser");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return images;
	}
	
	public static UserModel jsonToUser(JSONObject jsonObj) {
		UserModel user = new UserModel();
		try {
			user.id = jsonObj.getInt("id");
			user.username = jsonObj.getString("username");
			user.avatar_url = jsonObj.getString("avatar_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static Comments jsonToComments(JSONObject jsonObj) {
		Comments comment = new Comments();
		try {
			comment.id = jsonObj.getInt("id");
			comment.body = jsonObj.getString("body");
			comment.likes_count = jsonObj.getInt("likes_count");
			comment.likes_url = jsonObj.getString("likes_url");
			comment.created_at = jsonObj.getString("created_at").substring(0, 10);
			comment.updated_at = jsonObj.getString("updated_at").substring(0, 10);
			comment.user = jsonToUser(jsonObj.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return comment;
	}
}
