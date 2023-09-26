package bookstore.back.controllers;


import bookstore.back.io.user.UserCreateRequest;
import bookstore.back.io.user.UserResponseRequest;
import bookstore.back.io.user.UserUpdateRequest;
import bookstore.back.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PostMapping
    public void create(@RequestBody UserCreateRequest request) {
        userService.create(request);
    }

    @ApiOperation(value = "Update the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping
    public void update(@RequestBody UserUpdateRequest request) {
        userService.update(request);
    }

    @ApiOperation(value = "List all the users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseRequest>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete the publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}