package com.gerry.pang.common.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "spring.test.student", ignoreInvalidFields = true)
public class StudentProperties {

	private Boolean enabled;

	@Builder.Default
	private Integer id = 99;

	@Builder.Default
	private String name = "demo-name";

	@Builder.Default
	private Integer age = 18;

	@Builder.Default
	private String gender = "male";

	@Builder.Default
	private String address = "demo-address";

	private Map<String, String> map;
}
