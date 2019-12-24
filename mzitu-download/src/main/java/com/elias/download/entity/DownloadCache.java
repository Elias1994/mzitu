package com.elias.download.entity;

import java.io.File;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "download_cache")
public class DownloadCache {
	@Id
	@NonNull
	private String key;
	private String secret;
	@NonNull
	private String savePath;
	@NonNull
	private String currentId;
	private Long insertTime = new Date().getTime();
	private Long updateTime = new Date().getTime();

	public static void main(String[] args) {
		File file = new File("x:/mzitu");
		deleteFile(file);
	}

	private static void deleteFile(File file) {
		if (file.isDirectory() && file.listFiles().length > 0) {
			for (File subFile : file.listFiles()) {
				deleteFile(subFile);
			}
		}
		System.out.println(file.getName());
		file.deleteOnExit();
	}

}
