package dev.aco.back.VO;

import lombok.Data;

@Data
public class ChangeFindPasswordVO {
    String email;
    // 바꾸려는 비밀번호
    String upassword;
}
