package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // User : Post -> 1 : (0~N), Main : Sub -> Parent : Child
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩방식
    //    User Entity 조회 시 매번 Post Entity가 같이 로딩되는 것이 아니라,
    //    Post data가 로딩되는 시점에 필요한 사용자 데이터를 가지고 오겠다는 뜻이다.
    @JsonIgnore
    private User user;
}
