package com.iyihua.app.service.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {

	private String name;
	private String url;
}
