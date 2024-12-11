package com.weatherinfo.model;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class Response<T> {
	public int status;

	public T result;

	public Response(int status, T result) {
		this.result = result;
		this.status = status;
	}

}
