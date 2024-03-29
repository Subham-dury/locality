package com.locality.review.eventmicroservices.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "userDtoBuilder")
@Builder
public class UserDto {
	
	private Long userId;
	private String username;
	private String role;
	
}
