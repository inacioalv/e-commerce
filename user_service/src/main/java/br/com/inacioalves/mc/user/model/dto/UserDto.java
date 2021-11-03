package br.com.inacioalves.mc.user.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;
	private String first_name;
	private String last_name;
	private String email;
	private String nickname;
	private PhoneDto phone;
}
