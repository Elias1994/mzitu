package com.elias.proxy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProxyResponse {
	private int code;
	private String msg;
	private String ip;
	private int port;
	private String location;

	public ProxyResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
