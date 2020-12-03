package com.unisound.iot.controller.jdk.daili.compli;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.unisound.iot.controller.jdk.daili.Bird;
import com.unisound.iot.controller.jdk.daili.Flyable;

import javax.lang.model.element.Modifier;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * 第一步生成Java文件
 */
public class ProxyCompli {

    public static Object newProxyInstance() throws IOException {
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder("TimeProxy")
                .addSuperinterface(Flyable.class);

        FieldSpec fieldSpec = FieldSpec.builder(Flyable.class, "flyable", Modifier.PRIVATE).build();
        typeSpecBuilder.addField(fieldSpec);

        MethodSpec constructorMethodSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Flyable.class, "flyable")
                .addStatement("this.flyable = flyable")
                .build();
        typeSpecBuilder.addMethod(constructorMethodSpec);

        Method[] methods = Flyable.class.getDeclaredMethods();
        for (Method method : methods) {
            MethodSpec methodSpec = MethodSpec.methodBuilder(method.getName())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .returns(method.getReturnType())
                    .addStatement("long start = $T.currentTimeMillis()", System.class)
                    .addCode("\n")
                    .addStatement("this.flyable." + method.getName() + "()")
                    .addCode("\n")
                    .addStatement("long end = $T.currentTimeMillis()", System.class)
                    .addStatement("$T.out.println(\"Fly Time =\" + (end - start))", System.class)
                    .build();
            typeSpecBuilder.addMethod(methodSpec);
        }

        JavaFile javaFile = JavaFile.builder("com.unisound.iot.controller.jdk.daili.compli.Proxy", typeSpecBuilder.build()).build();
        // 为了看的更清楚，我将源码文件生成到桌面
        String sourcePath = "/Users/test/Desktop/";
        javaFile.writeTo(new File(sourcePath ));
        //第二步骤：编译
        try {
            File file = new File(sourcePath);
            javaFile.writeTo(file );
            JavaCompiler.compile(new File(sourcePath + "com.unisound.iot.controller.jdk.daili.compli.Proxy/TimeProxy.java"));
            File file2 = new File("/Users/test/Desktop/com/unisound/iot/controller/jdk/daili/compli/Proxy/TimeProxy.java");
            compile( file2 );
        } catch (IOException e) {
            e.printStackTrace();
        }
        //第三步加载到内存并创建对象
        URL[] urls = new URL[] {new URL("file:/" + "/Users/test/Desktop/")};
        URLClassLoader classLoader = new URLClassLoader(urls);
        try {
            Class clazz = classLoader.loadClass("com.unisound.iot.controller.jdk.daili.TimeProxy");
            Constructor constructor = clazz.getConstructor(Flyable.class);
            Flyable flyable = (Flyable) constructor.newInstance(new Bird());
            flyable.fly();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }



        return null;
    }


    public static void compile(File javaFile) throws IOException {
        javax.tools.JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable iterable = fileManager.getJavaFileObjects(javaFile);
        javax.tools.JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, null, null, null, iterable);
        task.call();
        fileManager.close();
    }




    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ProxyCompli proxy = new ProxyCompli();
        try {
            //第一步
            proxy.newProxyInstance();
            //第二步骤

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
