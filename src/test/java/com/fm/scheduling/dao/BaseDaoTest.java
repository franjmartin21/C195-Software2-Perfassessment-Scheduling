package com.fm.scheduling.dao;

import com.fm.scheduling.domain.User;

public class BaseDaoTest {

    protected User userCreator;

    protected void init(){
        userCreator = new User();
        userCreator.setUserName("creator");
    }
}
