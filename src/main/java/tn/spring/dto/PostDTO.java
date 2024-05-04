package tn.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long post_id;
    private String content;
    private Date createdAt;
    private Long user_id;  // Assuming user_id is Long, not Integer

}
