package bookstore.back.open_api;

import bookstore.back.io.user.UserCreateRequest;
import bookstore.back.io.user.UserDetailRequest;
import bookstore.back.io.user.UserResponseRequest;
import bookstore.back.io.user.UserUpdateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserControllerOpenApi {


    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PostMapping
    void create(@RequestBody UserCreateRequest request);

    @ApiOperation(value = "Update the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping
    void update(@RequestBody UserUpdateRequest request);

    @GetMapping("/{id}")
    ResponseEntity<UserDetailRequest> findById(@PathVariable Integer id);

    @ApiOperation(value = "List all the users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    ResponseEntity<List<UserResponseRequest>> getAll();

    @ApiOperation(value = "List all the deleted users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    ResponseEntity<List<UserResponseRequest>> getAllDeleted();

    @ApiOperation(value = "Delete the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id);

    @ApiOperation(value = "Delete permanently the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/deleted/{id}")
    void permanentlyDelete(@PathVariable Integer id);
}
