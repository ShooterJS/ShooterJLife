package com.shooterj.core.threadpool;

import java.io.Serializable;

public interface BaseBuilder<T> extends Serializable {

    T build();

}
