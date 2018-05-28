package com.hn.cqf.lib;

import com.google.auto.service.AutoService;
import com.hn.cqf.lib.annotation.ResolveTarget;
import com.hn.cqf.lib.model.FieldDesc;
import com.hn.cqf.lib.model.MethodDesc;
import com.hn.cqf.lib.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


/**
 * Created by cqf on 2017/8/27 17:51
 */

@AutoService(Processor.class)
public class ResolveDataProcessor extends AbstractProcessor {

    public static final String SUFFIX = "$$JsonResolver";
    public static final String ANDROID_PREFIX = "android.";
    public static final String JAVA_PREFIX = "java.";
    public static final String SEPARATOR = ".";
    public static final ArrayList<String> BASE_TYPE = new ArrayList<String>() {
        {
            add("byte");
            add("java.lang.Byte");//0

            add("short");
            add("java.lang.Short");//0

            add("int");
            add("java.lang.Integer");//0

            add("long");
            add("java.lang.Long");//0l

            add("float");
            add("java.lang.Float");//0f

            add("double");
            add("java.lang.Double");//0d

            add("boolean");
            add("java.lang.Boolean");//0d

            add("char");// '\u0000'
            add("java.lang.Character");
        }
    };

    /**
     * 元素操作的辅助类
     */
    private Elements elementUtils;
    private Types typeUtils;
    /**
     * 文件相关的辅助类
     */
    private Filer filer;
    /**
     * 日记相关的辅助类
     */
    private Messager messager;
    /**
     * 解析的目标注解集合
     */
    private Map<TypeElement, ResolveClass> targetClassMap = new HashMap<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        targetClassMap.clear();
        try {
            processSrcClass(roundEnvironment);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            return true;
        }

        try {
            for (Map.Entry<TypeElement, ResolveClass> entry : targetClassMap.entrySet()) {
                info("generating file for %s", entry.getValue().getFullClassName());
                if (entry.getValue().generateFinder() != null) {
                    entry.getValue().generateFinder().writeTo(filer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error("Generate file failed,reason:%s", e.getMessage());
        }
        return true;
    }


    private void processSrcClass(RoundEnvironment roundEnv) {
        for (Element ele : roundEnv.getElementsAnnotatedWith(ResolveTarget.class)/*获得被该注解声明的元素合集*/) {
            TypeElement typeElement = (TypeElement) ele;
            ResolveClass resolveClass = getPasteClass(typeElement);
            println("p_element=" + ele.getSimpleName() + ",p_set=" + ele.getModifiers());

            //遍历类的内容
            Map<String, FieldDesc> fieldDescs = new LinkedHashMap<>();
            ArrayList<String> unHandleField = new ArrayList<>();
            traverseClass(typeElement, fieldDescs, unHandleField, false);
            resolveClass.setFieldDescs(fieldDescs);
        }
    }

    private void traverseClass(TypeElement typeEle, Map<String, FieldDesc> map, ArrayList<String> unHandleField, boolean isInnerClz) {
        List<? extends Element> elements = typeEle.getEnclosedElements();
        String clzName = typeEle.getSimpleName().toString();
        Map<String, String> setMethodMap = new HashMap<>();//<类.构造对应的set方法名，字段>
        for (Element ele : elements) {
            if (ele.getKind().equals(ElementKind.FIELD)) {//字段->VariableElement
                VariableElement varElement = (VariableElement) ele;
                TypeMirror typeMirror = varElement.asType();
                String fieldName = varElement.getSimpleName().toString();//字段名称
                String fieldType = typeMirror.toString();//字段类型
                String fieldTypePackage = "";
                if (fieldType.contains(SEPARATOR)) {//不是8个基础类型
                    fieldTypePackage = fieldType;
                    if (fieldType.contains("<") && fieldType.contains(">")) {
                        String str = fieldType.substring(0, fieldType.indexOf("<"));
                        fieldType = fieldType.substring(str.lastIndexOf(SEPARATOR) + 1);
                    } else {
                        fieldType = fieldType.substring(fieldType.lastIndexOf(SEPARATOR) + 1);
                    }
                }
                FieldDesc fieldDesc = new FieldDesc();
                fieldDesc.setFieldName(isInnerClz ? clzName.concat(SEPARATOR).concat(fieldName) : fieldName);
                fieldDesc.setFieldType(fieldType);
                fieldDesc.setFieldTypePackage(fieldTypePackage);
                String setMethodName = obtainSetMethodName(fieldName, fieldType);
                if (!StringUtil.isEmpty(setMethodName)) {
                    setMethodMap.put(clzName.concat(SEPARATOR).concat(setMethodName), clzName.concat(SEPARATOR).concat(fieldName));
                }
                map.put(clzName.concat(SEPARATOR).concat(fieldName), fieldDesc);
            } else if (ele.getKind().equals(ElementKind.CLASS)) {//内部类->TypeElement
                traverseClass((TypeElement) ele, map, unHandleField, true);
            } else if (ele.getKind().equals(ElementKind.CONSTRUCTOR)) {//构造器->ExecutableElement

            } else if (ele.getKind().equals(ElementKind.METHOD)) {//方法->ExecutableElement
                ExecutableElement exeElement = (ExecutableElement) ele;
                //List<? extends TypeMirror> thrownTypes = exeElement.getThrownTypes();//异常类型
                //List<? extends VariableElement> params = exeElement.getParameters();//参数
                TypeMirror returnMirror = exeElement.getReturnType();//返回类型
                List<? extends VariableElement> params = exeElement.getParameters();
                Set<Modifier> modifiers = exeElement.getModifiers();//修饰符public/static/final...
                if (params.size() == 1
                        && modifiers.size() == 1 && modifiers.iterator().next().toString().equals("public") //只有一个修饰符：public
                        && returnMirror.toString().equals("void")) {
                    String methodName = exeElement.getSimpleName().toString();//方法名
                    if (methodName.startsWith("set")) {//以set方法为基准
                        String key = clzName.concat(SEPARATOR).concat(methodName);
                        if (setMethodMap.containsKey(key)) {
                            handleRealSetMethod(map, setMethodMap, params, methodName, key);
                        } else if (params.get(0).asType().toString().equals("boolean") || params.get(0).asType().toString().equals("Boolean")) {
                            if (methodName.length() >= 6) {
                                String removeStr = methodName.substring(3, 5);
                                key = clzName.concat(SEPARATOR).concat(methodName.replaceFirst(removeStr, ""));
                                if (setMethodMap.containsKey(key)) {
                                    handleRealSetMethod(map, setMethodMap, params, methodName, key);
                                }
                            }
                        }
                    }
                }
            } else if (ele.getKind().equals(ElementKind.ENUM)) {//枚举->TypeElement
                System.out.println(ele);
            }
        }
        if (setMethodMap.size() > 0) {
            for (String field : setMethodMap.keySet()) {
                unHandleField.add(field);
            }
        }
    }

    private void handleRealSetMethod(Map<String, FieldDesc> map, Map<String, String> setMethodMap, List<? extends VariableElement> params, String methodName, String key) {
        String getMethodName;
        String fieldName = setMethodMap.get(key);
        MethodDesc methodDesc = new MethodDesc();
        methodDesc.setMethodName(methodName);
        methodDesc.setParamType(params.get(0).asType().toString());
        FieldDesc fieldDesc = map.get(fieldName);
        fieldDesc.setSetMethondDesc(methodDesc);
        setMethodMap.remove(key);
        //转换对应的get方法
        VariableElement varElement = params.get(0);//获取set方法参数类型
        TypeMirror typeMirror = varElement.asType();
        String type = typeMirror.toString();
        if (type.equals("Boolean") || type.equals("boolean")) {//判断参数类型
            String str = methodName.replace("set", "");
            if (str.toLowerCase().equals("is")) {//只有is
                if (str.startsWith("Is")) {
                    getMethodName = str.toLowerCase();
                } else {
                    getMethodName = str;
                }
            } else {//is与其他单词拼接或没有is
                if (str.startsWith("iS") || str.startsWith("IS")) {
                    getMethodName = str;
                } else if (str.startsWith("Is")) {
                    getMethodName = str.replace("Is", "is");
                } else if (str.startsWith("is")) {
                    getMethodName = str;
                } else {
                    getMethodName = "is".concat(str);
                }
            }
        } else {
            getMethodName = methodName.replace("set", "get");
        }
        fieldDesc.setGetMethondName(getMethodName);
        fieldDesc.setClzName(key.substring(0, key.indexOf(SEPARATOR)));
    }

    private String obtainSetMethodName(String fieldName, String fieldType) {
        if (fieldType.equals("Boolean") || fieldType.equals("boolean")) {
            if (fieldName.length() == 1) {
                return "set".concat(fieldName.toUpperCase());
            } else if (fieldName.length() == 2) {
                if (fieldName.toLowerCase().equals(fieldName)) {
                    String firstChar = String.valueOf(fieldName.charAt(0));
                    return "set".concat(fieldName.replaceFirst(firstChar, firstChar.toUpperCase()));
                } else {
                    return "set".concat(fieldName);
                }
            } else if (fieldName.length() >= 3) {
                if (fieldName.substring(0, 2).toLowerCase().equals("is")) {
                    String thridChar = String.valueOf(fieldName.charAt(2));
                    if (thridChar.toUpperCase().equals(thridChar)) {
                        String secondChar = String.valueOf(fieldName.charAt(1));
                        if (secondChar.toUpperCase().equals(secondChar)) {
                            return "set".concat(fieldName);
                        } else {
                            return "set".concat(fieldName.substring(2));
                        }
                    } else {
                        String secondChar = String.valueOf(fieldName.charAt(1));
                        if (secondChar.toUpperCase().equals(secondChar)) {
                            return "set".concat(fieldName);
                        } else {
                            String firstChar = String.valueOf(fieldName.charAt(0));
                            if (firstChar.toUpperCase().equals(firstChar)) {
                                return "set".concat(fieldName);
                            } else {
                                return "set".concat(fieldName.replaceFirst(firstChar, firstChar.toUpperCase()));
                            }
                        }
                    }
                } else {
                    String secondChar = String.valueOf(fieldName.charAt(1));
                    if (secondChar.toUpperCase().equals(secondChar)) {
                        return "set".concat(fieldName);
                    } else {
                        String firstChar = String.valueOf(fieldName.charAt(0));
                        if (firstChar.toUpperCase().equals(firstChar)) {
                            return "set".concat(fieldName);
                        } else {
                            return "set".concat(fieldName.replaceFirst(firstChar, firstChar.toUpperCase()));
                        }
                    }
                }
            }
        } else {
            if (fieldName.length() >= 2) {
                // 判断第二个字母是否是大写
                String secondChar = String.valueOf(fieldName.charAt(1));
                if (secondChar.toUpperCase().equals(secondChar)) {
                    //直接拼接set
                    return "set".concat(fieldName);
                } else {
                    String firstChar = String.valueOf(fieldName.charAt(0));
                    if (firstChar.toUpperCase().equals(firstChar)) {
                        return "set".concat(fieldName);
                    } else {
                        return "set".concat(fieldName.replaceFirst(firstChar, firstChar.toUpperCase()));
                    }
                }
            } else {
                return "set".concat(fieldName.toUpperCase());
            }
        }
        return "";
    }

    private ResolveClass getPasteClass(TypeElement typeElement) {
        // 声明类元素
        String fullClassName = typeElement.getQualifiedName().toString();
        println("fullClassName:" + fullClassName);
        ResolveClass resolveClass = targetClassMap.get(typeElement);
        if (resolveClass == null) {
            resolveClass = new ResolveClass(typeElement, elementUtils);
            targetClassMap.put(typeElement, resolveClass);
        }
        return resolveClass;
    }

    /**
     * 注册注解（想要处理的注解类型的合法全称）
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<String>();
        types.add(ResolveTarget.class.getCanonicalName());
        return types;
    }

    /**
     * 指定Java版本：通常返回SourceVersion.latestSupported()；
     */
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void error(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

    private void println(String key) {
        System.out.print("shan:" + key);
    }


}
