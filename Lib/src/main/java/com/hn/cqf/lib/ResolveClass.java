package com.hn.cqf.lib;


import com.hn.cqf.lib.model.FieldDesc;
import com.hn.cqf.lib.model.MethodDesc;
import com.hn.cqf.lib.model.ResolveTargetModel;
import com.hn.cqf.lib.util.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import jdk.nashorn.internal.codegen.types.Type;

import static com.hn.cqf.lib.ResolveDataProcessor.BASE_TYPE;

/**
 * Created by cqf on 2017/8/27 19:01
 */
public class ResolveClass {
    /**
     * 类名
     */
    public TypeElement mClassElement;

    /**
     * 元素辅助类
     */
    public Elements mElementUtils;
    private ArrayList<MethodDesc> setMethods;
    private Map<String, FieldDesc> fieldDescs;

    public ResolveClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
    }

    /**
     * 获取当前这个类的全名
     */
    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }


    /**
     * 输出Java
     */
    public JavaFile generateFinder() {
        /**
         * 构建方法
         */
        if (fieldDescs.size() == 0) {
            throw new RuntimeException("Un Find Field Handle with ".concat(getFullClassName()));
        }
        ArrayList<FieldDesc> descs = new ArrayList<>();
        for (FieldDesc desc : fieldDescs.values()) {
            descs.add(0, desc);
        }
        MethodSpec.Builder resolveMethod = MethodSpec.methodBuilder("fromJson")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(TypeName.get(mClassElement.asType()))
                .addParameter(Class.class, "clz", Modifier.FINAL)
                .addParameter(String.class, "jsonStr");
        ClassName jSONObject = ClassName.get("org.json", "JSONObject");
        ClassName jSONException = ClassName.get("org.json", "JSONException");
        ClassName textUtils = ClassName.get("android.text", "TextUtils");
//        resolveMethod.beginControlFlow("if(!$T.isEmpty(jsonStr))");
        resolveMethod.beginControlFlow("try");
        resolveMethod.addStatement("$T jo = new $T(jsonStr)", jSONObject,jSONObject);
        for (int i = 0; i < descs.size(); i++) {

        }
        resolveMethod.endControlFlow();
        resolveMethod.beginControlFlow("catch ($T e)",jSONException);
        resolveMethod.addStatement("e.printStackTrace()");
        resolveMethod.endControlFlow();
//        resolveMethod.endControlFlow();
        resolveMethod.addStatement("return null");

        /**
         * 构建类
         */
        String packageName = getPackageName(mClassElement);
        String className = getClassName(mClassElement, packageName);
        ClassName pasteClassName = ClassName.get(packageName, className);
        TypeSpec pasteClass = TypeSpec.classBuilder(pasteClassName.simpleName().concat(ResolveDataProcessor.SUFFIX))
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.DATA_PASTER, TypeName.get(mClassElement.asType())))
                .addMethod(resolveMethod.build())
                .build();
        return JavaFile.builder(packageName, pasteClass).build();

    }

    /**
     * 包名
     */
    public String getPackageName(TypeElement type) {
        return mElementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    /**
     * 类名
     */
    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    public void addSetMethods(ArrayList<MethodDesc> setMethods) {
        this.setMethods = setMethods;
    }

    public void setFieldDescs(Map<String, FieldDesc> fieldDescs) {
        this.fieldDescs = fieldDescs;
    }
}
