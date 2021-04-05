package com.winway.demo.threadpool;

import java.io.Serializable;

public interface BaseBuilder<T> extends Serializable {

    T build();

}
