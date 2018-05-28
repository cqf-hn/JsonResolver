package com.hn.cqf.lib;

/**
 * Created by cqf on 2018/5/24.
 */
public interface JsonResolver<T> {
    T fromJson(final Class<T> clz, String jsonStr);
}
