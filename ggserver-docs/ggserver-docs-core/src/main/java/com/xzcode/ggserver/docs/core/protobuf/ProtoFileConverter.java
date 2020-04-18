package com.xzcode.ggserver.docs.core.protobuf;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.xzcode.ggserver.docs.core.model.Doc;

/**
 * proto文件转换器接口
 * 
 * @author zai 2019-12-17 12:32:15
 */
public interface ProtoFileConverter {

	/**
	 * 转换成proto文件
	 * 
	 * @param model
	 * @return 返回字符串
	 * @author zai 2019-12-17 12:33:59
	 */
	List<ProtoFile> convertToProtoString(Doc doc);

	/**
	 * 转换成proto文件并输出到文件
	 * 
	 * @param model
	 * @param outfile
	 * @return
	 * @author zai 2019-12-17 12:35:15
	 */
	List<File> convertToProtoFileAndOutput(Doc doc, String outfile);

	/**
	 * 获取所有字段对象
	 * 
	 * @param object
	 * @return
	 * @author zai 2019-12-17 14:49:50
	 */
	default Field[] getAllFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

	default String getFieldProtoModifier(Field field) {
		Class<?> type = field.getType();
		if (type.isArray() || type == List.class || type == ArrayList.class || type == LinkedList.class) {
			return "repeated";
		}
		return "optional";
	}

	/**
	 * 获取属性对应的proto类型字符串
	 * 
	 * @param field
	 * @return
	 * @author zai
	 * 2019-12-17 15:28:38
	 */
	default String getFieldProtoDataType(Field field, String simpleNamePrefix) {
		Class<?> type = field.getType();
		
		if (simpleNamePrefix == null) {
			simpleNamePrefix = "";
		}

		String protoTypeString = getProtoTypeString(type);
		
		if (protoTypeString == null) {
			String modifier = getFieldProtoModifier(field);
			if (modifier.equals("repeated")) {
				if (type == List.class || type == ArrayList.class || type == LinkedList.class) {

					Type genericType = field.getGenericType();
					if (null == genericType) {
						throw new UnknownError("Unknow Generic Type!!");
					}
					if (genericType instanceof ParameterizedType) {
						ParameterizedType pt = (ParameterizedType) genericType;
						// 得到泛型里的class类型对象
						Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
						protoTypeString = getProtoTypeString(actualTypeArgument);
						if (protoTypeString == null) {
							protoTypeString = actualTypeArgument.getSimpleName();
							if (protoTypeString != null) {
								protoTypeString = simpleNamePrefix + protoTypeString; 
							}
						}
					}
				}else {
					protoTypeString = simpleNamePrefix + type.getSimpleName().replace("[]", "");
				}
			}else {
				protoTypeString = simpleNamePrefix + type.getSimpleName();
			}
		}
		return protoTypeString;
	}
	
	/**
	 * 
	 * 
	 * @param type
	 * @return
	 * @author zai
	 * 2019-12-17 15:26:35
	 */
	default String getProtoTypeString(Class<?> type) {
		
		if (type == String.class || type == String[].class) {
			return "string";
		}
		if (type == boolean.class || type == Boolean.class) {
			return "bool";
		}
		if (type == int.class || type == Integer.class || type == int[].class || type == Integer[].class) {
			return "int32";
		}
		if (type == long.class || type == Long.class || type == long[].class || type == Long[].class) {
			return "int64";
		}
		return null;
	}

}
