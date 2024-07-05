package com.cicd.todoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    // @Builder.Default
    //생성자로 DTO를 생성하면 지정한 초기값으로 초기화가 되는데, Builder를 통해 만들면 초기값이 반
    //영이 안되고 타입에 따른 0 이나 null 값으로 생성된다. 이때 이 어노테이션을 붙히면, Builder로 생성
    //해도 지정한 초기값이 반영되어 객체 생성된다.

    @Builder.Default // 빌더로 특정 필드 초기값 설정시 어노테이션 부착
    private int page = 1; // 요청 페이지 번호
    @Builder.Default
    private int size = 10; // 한페이지에 보여줄 데이터의 개수
}
