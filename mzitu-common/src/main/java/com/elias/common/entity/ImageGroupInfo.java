package com.elias.common.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "image_group_info")
public class ImageGroupInfo {
	@Id
	private String id;
	// 图组index
	@NonNull
	private Integer index;
	// 图组分类
	@NonNull
	private String group;
	// 图组标题
	@NonNull
	private String title;
	// 发布日期
	private Date publishTime;
}
