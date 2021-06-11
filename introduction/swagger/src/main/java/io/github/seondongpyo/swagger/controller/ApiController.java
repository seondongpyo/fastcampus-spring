package io.github.seondongpyo.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

}
