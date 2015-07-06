/**
 * Licensed to HSLK.Info under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * HSLK.Info licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.hslk.vqtx.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import codereport.daocontroller.UserController;
import codereport.entity.User;

/**
 * @author HuyTCM
 *
 */
public class HomeService {
    /** For logging. */
    private static Logger logger = Logger.getLogger(HomeService.class);
    /**Initial Entity Manager Factory .*/
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DaoGeneratePU");
    /**
     * Initial HomeService constructor.
     */
    public HomeService() {
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword("admin");
//        admin.setRole(true);
//        
//        UserController userController = new UserController(entityManagerFactory);
//        try {
//            userController.create(admin);
//        } catch (Exception ex) {
//            logger.error("Initial Homeservice", ex);
//        }
    }
    /**
     * Check login.
     * @param username - username.
     * @param password - password.
     * @return user.
     */
    public User checkLogin(String username, String password) {
        UserController userController = new UserController(entityManagerFactory);
        User user = userController.checkLogin(username, password);
        
        return user;
    }
    /**
     * Add new user.
     * @param username - username.
     * @param password - password.
     */
    public void addNewUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(true);
        UserController userController = new UserController(entityManagerFactory);
        try {
            userController.create(user);
        } catch (Exception ex) {
            logger.error("Exception occurs when add new user", ex);
        }
    }
}
