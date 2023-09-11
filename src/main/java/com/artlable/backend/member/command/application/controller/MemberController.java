package com.artlable.backend.member.command.application.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags= "Member CRUD API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

}