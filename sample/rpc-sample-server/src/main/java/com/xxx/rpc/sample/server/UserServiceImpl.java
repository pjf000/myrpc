package com.xxx.rpc.sample.server;

import com.xxx.rpc.sample.api.UserService;
import com.xxx.rpc.server.RpcService;

/**
 * Created by JeffPeng on 2018/6/5.
 */
public class UserServiceImpl implements UserService {
    public String getPersonAge() {
        return "age = "+19;
    }
}
