package bookstore.back.controllers;


import bookstore.back.io.user.*;
import bookstore.back.open_api.UserControllerOpenApi;
import bookstore.back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
@SuppressWarnings("unused")
public class UserController implements UserControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Override
    @PostMapping
    public void create(@RequestBody UserCreateRequest request) {
        userService.create(request);
    }

    @Override
    @PutMapping
    public void update(@RequestBody UserUpdateRequest request) {
        userService.update(request);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailRequest> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userMapper.toUserDetailRequest(userService.findById(id)), HttpStatus.OK);
    }git push

    @Override
    @GetMapping
    public ResponseEntity<List<UserResponseRequest>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}