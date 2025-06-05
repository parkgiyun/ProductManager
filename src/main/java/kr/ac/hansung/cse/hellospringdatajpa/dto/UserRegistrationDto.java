package kr.ac.hansung.cse.hellospringdatajpa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NotEmpty(message = "이메일은 비어있을 수 없습니다")
    @Email(message = "유효한 이메일 주소를 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호는 비어있을 수 없습니다")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
    private String password;

    @NotEmpty(message = "이름은 비어있을 수 없습니다")
    private String name;
}
