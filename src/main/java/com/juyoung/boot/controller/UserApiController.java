package com.juyoung.boot.controller;

import com.juyoung.boot.model.Board;
import com.juyoung.boot.model.User;
import com.juyoung.boot.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    List<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {
        List<User> users = null;
        if("query".equals(method)) {
            users = userRepository.findByUsernameQuery(text);
        } else if("nativeQuery".equals(method)) {
            users = userRepository.findByUsernameNativeQuery(text);
        }else {
            users = userRepository.findAll();
        }
        return users;
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceuser(@RequestBody User newUser, @PathVariable Long id) {

        return userRepository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
//                    user.setBoards(newUser.getBoards());
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());
                    for(Board board : user.getBoards()) {
                        board.setUser(user);
                    }
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteuser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
