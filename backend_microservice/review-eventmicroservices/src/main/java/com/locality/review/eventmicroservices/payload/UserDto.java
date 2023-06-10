package com.locality.review.eventmicroservices.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "userDtoBuilder")
public class UserDto {
	
	private Long userId;
	private String username;

}
