/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swanmusic.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MsgBox {
    public static void alert(Component parent,String message){
        JOptionPane.showMessageDialog(parent, message,"SWAN - Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }
    public static int confirm(Component parent,String message){
        return JOptionPane.showConfirmDialog(parent, message,"SWAN - Xác nhận",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
    public static String promt(Component parent,String message){
        return JOptionPane.showInputDialog(parent, message,"SWAN - Vui lòng nhập",JOptionPane.INFORMATION_MESSAGE);
    }
}
