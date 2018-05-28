package com.hn.cqf.lib.model;

import com.hn.cqf.lib.annotation.ResolveTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Created by cqf on 2018/5/24.
 */
public class ResolveTargetModel {
    private TypeElement mFieldElement;

    private ArrayList<String> classPaths = new ArrayList<>();

    public ResolveTargetModel(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.CLASS) {//判断是否是类成员
            throw new IllegalArgumentException(String.format("Only field can be annotated with @%s",
                    ResolveTarget.class.getSimpleName()));
        }
        mFieldElement = (TypeElement) element;
        //获取注解和值
        List<? extends AnnotationMirror> mirrors = mFieldElement.getAnnotationMirrors();
        for (AnnotationMirror mirror : mirrors) {
            Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : values.entrySet()) {
                AnnotationValue value = entry.getValue();
                ExecutableElement key = entry.getKey();
                if (key.getSimpleName().toString().equals("value")) {
                    List list = (List) value.getValue();
                    for (int i = 0; i < list.size(); i++) {
                        String s = list.get(i).toString();
                        String classPath = s.substring(0, s.lastIndexOf(".")).trim();
                        classPaths.add(classPath);
                    }
                }
            }
        }
    }


    public ArrayList<String> getClassPaths() {
        return classPaths;
    }

}
