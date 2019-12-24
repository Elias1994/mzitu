package com.elias.download.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "download_failed_log_history")
public class DownloadFailedLogHistory {
	@Id
	private String id;
	@NonNull
	private DownloadFailedLog log;
	private Long insertTime = new Date().getTime();
}
