package com.hn.cqf.lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cqf on 2017/8/25 17:40
 */
public class ResolveData {

    private static Map<Class<?>, JsonResolver<Object>> resolverMap = new HashMap<>();

    private ResolveData() {
    }

    public static ResolveData getInstance() {
        return Singleton.instance;
    }

    private static class Singleton {
        public final static ResolveData instance = new ResolveData();
    }

    public Object resolver(Class clz, String jsonStr) {

        try {
            JsonResolver<Object> jsonResolver = findJsonResolverForClass(clz);
            if (jsonResolver != null) {
                return jsonResolver.fromJson(clz, jsonStr);
            }
        } catch (Exception e) {
            //className抛出异常是否使用该注解
            throw new RuntimeException("Unable to resolve ".concat(jsonStr).concat(" to ").concat(clz.getSimpleName()), e);
        }
        return null;
    }

//    public void unpaste(Object dstData) {
//        Class<?> targetClass = dstData.getClass();
//        try {
//            JsonResolver<Object> dataPaster = findJsonResolverForClass(targetClass);
//            if (dataPaster != null) {
//                dataPaster.unpaste(dstData);
//            }
//        } catch (Exception e) {
//            //className抛出异常是否使用该注解
//            throw new RuntimeException("Unable to unpaste data for " + targetClass.getName(), e);
//        }
//    }


    private JsonResolver<Object> findJsonResolverForClass(Class<?> cls)
            throws IllegalAccessException, InstantiationException {
        JsonResolver<Object> jsonResolver = resolverMap.get(cls);
        if (jsonResolver != null) {
            return jsonResolver;
        }
        String clsName = cls.getName();
        if (clsName.startsWith(ResolveDataProcessor.ANDROID_PREFIX) || clsName.startsWith(ResolveDataProcessor.JAVA_PREFIX)) {
            return NOP_DATA_PASTER;
        }
        try {
            Class<?> dataPasterClass = Class.forName(clsName.concat(ResolveDataProcessor.SUFFIX));
            //noinspection unchecked
            jsonResolver = (JsonResolver<Object>) dataPasterClass.newInstance();
        } catch (ClassNotFoundException e) {
            jsonResolver = findJsonResolverForClass(cls.getSuperclass());
        }
        resolverMap.put(cls, jsonResolver);
        return jsonResolver;
    }

    static final JsonResolver<Object> NOP_DATA_PASTER = new JsonResolver<Object>() {

        @Override
        public Object fromJson(Class<Object> clz, String jsonStr) {
            return null;
        }
    };
}
