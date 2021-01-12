package com.example.restfulwebservice.user;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserDaoService service;

    // 의존성 주입은 setter 메소드나 생성자의 매개변수를 통해 가능한데, 여기선 생성자를 통해 주입했다.
    // 더 간단하게 @Autowired를 사용할 수 있다.
    // DEBUG 레벨의 log 에서 UserController 검색 후 아래의 메세지로 확인도 가능하다.
    // 'userController' via constructor to bean named 'userDaoService'
    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/1 or /users/10 -> String
    @GetMapping("/users/{id}")
    // String ㅡ> int (converting), 꼭 숫자여야 하기 때문에 이 부분에서 Vaildation check를 해보면 좋을 것 같다...
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id); // return service.findOne(id)에서 앞 코드로 바꾸는 refactor > Introduce Variable (ctrl+alt+V)

        if (user == null) {
            // UserNotFoundException: 직접 예외 클래스 생성.
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS: 현재 리소스와 연관된 (호출 가능한) 자원 상태 성보를 제공
        // Hypermedia As the Engine Of Application State
        // Resource ㅡ> EntityModel, ControllerLinkBuilder ㅡ> WebMvcLinkBuilder (version 2.1.8 ㅡ> 2.2)
        // retrieveAllUsers() 메서드를 all-users로 href 연결
        Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource; // user 객체와 링크를 전달
    }

    // jdk에 포함된 API와 hibernate library에 포함된 validation 기능
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) { // User validation check 진행
        User savedUser = service.save(user);

        // ServletUriComponentsBuilder ㅡ> 현재 요청의 URI를 얻을 수 있다. 생성된 아이디값을 지정하여 URI 생성
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // ResponseEntity: HTTP 상태 코드와 전송하고 싶은 데이터와 함께 전송할 수 있다.
        // HTTP 201 Created는 요청이 성공적으로 처리되었으며, 자원이 생성되었음을 나타내는 성공 상태 응답 코드이다.
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    // {id: "1", name: "new name", password: "new password"}
    @PutMapping("/users/{id}")
    public Resource<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = service.update(id, user);

        if (updatedUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
        }

        // HATEOAS
        Resource<User> resource = new Resource<>(updatedUser);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }
}
