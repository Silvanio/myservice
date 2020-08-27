package com.myservice.mymarket.repository.shared;

import com.myservice.common.dto.auth.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "myservice-auth", path = "/")
public interface AuthClient {

    @RequestMapping(value = "currentUserInfo", method = RequestMethod.POST, consumes = "application/json")
    public UserDTO getCurrentUserInfo();
}
