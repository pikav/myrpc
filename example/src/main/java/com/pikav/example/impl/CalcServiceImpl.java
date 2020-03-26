package com.pikav.example.impl;

import com.pikav.example.CalcService;

public class CalcServiceImpl implements CalcService {

    @Override
    public int add(int a, int b) {

        return a + b;
    }

    @Override
    public int minus(int a, int b) {

        return a - b;
    }

}
