/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utiles;

/**
 *
 * @author edgar
 */
public class ComboItem {
    
    private int value;
    private String text;

    public ComboItem() {
    }

    public ComboItem(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
    
}
