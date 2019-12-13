package com.elias.proxy.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "proxy_info")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProxyInfo {
	@Id
	private String ip;
	private int port;
	private String location;
	private Date time;
	private Date nextTime;
	private long ping;
}
