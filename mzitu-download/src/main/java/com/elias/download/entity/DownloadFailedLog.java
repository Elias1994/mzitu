package com.elias.download.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.elias.common.entity.ImageInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "download_failed_log")
public class DownloadFailedLog {
	@Id
	private String id;
	@NonNull
	private String key;
	private String secret;
	@NonNull
	private String savePath;
	@NonNull
	private ImageInfo info;
	private String failMsg;
	private Integer faileTimes = 1;
	private Long insertTime = new Date().getTime();
	private Long updateTime = new Date().getTime();
}
