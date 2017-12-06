package com.central1.course1;

import java.io.Serializable;

public class Content implements Serializable {

    private int test;

    public Content(int test) {
        this.test = test;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }
}
