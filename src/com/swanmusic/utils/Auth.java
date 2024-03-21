/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swanmusic.utils;

import com.swanmusic.entity.Account;

/**
 *
 * @author Admin
 */
public class Auth {
    public static Account USER =null;
    public static void clear(){
        Auth.USER=null;
    }
    public static boolean isLogin(){
        return Auth.USER!=null;
    }
    public static boolean isManager(){
        return Auth.isLogin() && USER.isVaitro();
    }
}
