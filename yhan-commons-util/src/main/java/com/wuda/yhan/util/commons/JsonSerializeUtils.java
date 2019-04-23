package com.wuda.yhan.util.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Json序列化工具类.
 *
 * @author wuda
 */
public class JsonSerializeUtils {

    /**
     * object mapper.
     */
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 序列化.
     *
     * @param object   被序列化的对象
     * @param path     序列化文件路径,是文件不是文件夹
     * @param encoding encoding
     * @throws IOException io exception
     */
    public static void serialize(Object object, Path path, String encoding) throws IOException {
        try {
            String value = objectMapper.writeValueAsString(object);
            FileUtils.write(path.toFile(), value, encoding);
        } catch (JsonProcessingException e) {
            throw new IOException(e);
        }
    }

    /**
     * 反序列化.
     *
     * @param path          序列化文件所在路径,是文件不是文件夹.
     * @param encoding      　encoding
     * @param typeReference 类型
     * @param <T>           　反序列化后的数据类型
     * @return 反序列化数据
     * @throws IOException io exception
     */
    public static <T> T deserialize(Path path, String encoding, TypeReference<T> typeReference) throws IOException {
        String value = FileUtils.readFileToString(path.toFile(), encoding);
        return objectMapper.readValue(value, typeReference);
    }
}
