package com.wuda.yhan.util.commons;

import java.io.File;

/**
 * 文件工具类.
 *
 * @author wuda
 */
public class FileUtils {

    /**
     * 获取系统变量<i>user.dir</i>的值.
     *
     * @return user dir
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * 以Maven方式管理,默认布局条件下(即src/main/java管理代码)的Java文件所在的目录.
     *
     * @param clazz java class
     * @return Java文件所在的目录
     */
    public static String getMavenDefaultLayoutJavaFileDir(Class clazz) {
        String canonicalName = clazz.getCanonicalName();
        String onlyPackageName = canonicalName.substring(0, canonicalName.lastIndexOf('.'));
        String packagePart = onlyPackageName.replaceAll("\\.", "\\" + File.separator);
        return getMavenSrcMainJavaDir()
                + File.separator + packagePart;
    }

    /**
     * 以Maven方式管理的项目,获取完整的src/main/java目录.
     *
     * @return src/main/java目录
     */
    public static String getMavenSrcMainJavaDir() {
        return getUserDir()
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "java";
    }

    /**
     * 以Maven方式管理的项目,获取完整的src/main/resources目录.
     *
     * @return src/main/resources 目录
     */
    public static String getMavenSrcMainResourcesDir() {
        return getUserDir()
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources";
    }

    /**
     * 以Maven方式管理的项目,获取完整的src/test/java目录.
     *
     * @return src/test/java 目录
     */
    public static String getMavenSrcTestJavaDir() {
        return getUserDir()
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "java";
    }

    /**
     * 以Maven方式管理的项目,获取完整的src/test/resources目录.
     *
     * @return src/test/resources 目录
     */
    public static String getMavenSrcTestResourcesDir() {
        return getUserDir()
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources";
    }


}
