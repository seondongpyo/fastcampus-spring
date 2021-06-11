package io.github.seondongpyo.swagger.controller;

import io.github.seondongpyo.swagger.dto.UserRequestDto;
import io.github.seondongpyo.swagger.dto.UserResponseDto;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"REST API Controller"})
@RestController
public class ApiController {

    @ApiOperation(value = "hello method", notes = "기본 인사 API")
    @GetMapping("/api/hello")
    public String hello(@ApiParam(value = "사용자 이름") @PathVariable String username,
                        @ApiParam(value = "사용자 나이") @RequestParam int age) {

        return "hello";
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "이름", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "age", value = "나이", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/api/user/{username}")
    public UserResponseDto user(UserRequestDto userRequestDto) {
        return new UserResponseDto(userRequestDto.getUsername(), userRequestDto.getAge());
    }

}
