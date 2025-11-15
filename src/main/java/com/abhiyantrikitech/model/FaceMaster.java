package com.abhiyantrikitech.model;


public class FaceMaster {
	
	private String fd_id;
	private String fd_filename;
	private String fd_original_img_base64;
	private String fd_detected_img_base64;
	private String fd_status;
	private String fd_face_count;
	private String fd_create_dt;
	

	public String getFd_id() {
		return fd_id;
	}
	public void setFd_id(String fd_id) {
		this.fd_id = fd_id;
	}
	public String getFd_filename() {
		return fd_filename;
	}
	public void setFd_filename(String fd_filename) {
		this.fd_filename = fd_filename;
	}
	public String getFd_original_img_base64() {
		return fd_original_img_base64;
	}
	public void setFd_original_img_base64(String fd_original_img_base64) {
		this.fd_original_img_base64 = fd_original_img_base64;
	}
	public String getFd_detected_img_base64() {
		return fd_detected_img_base64;
	}
	public void setFd_detected_img_base64(String fd_detected_img_base64) {
		this.fd_detected_img_base64 = fd_detected_img_base64;
	}
	public String getFd_status() {
		return fd_status;
	}
	public void setFd_status(String fd_status) {
		this.fd_status = fd_status;
	}
	public String getFd_face_count() {
		return fd_face_count;
	}
	public void setFd_face_count(String fd_face_count) {
		this.fd_face_count = fd_face_count;
	}
	public String getFd_create_dt() {
		return fd_create_dt;
	}
	public void setFd_create_dt(String fd_create_dt) {
		this.fd_create_dt = fd_create_dt;
	}
	
}