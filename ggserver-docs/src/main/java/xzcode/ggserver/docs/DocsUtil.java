package xzcode.ggserver.docs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import xzcode.ggserver.docs.annotation.DocsModel;
import xzcode.ggserver.docs.annotation.DocsModelProperty;
import xzcode.ggserver.docs.model.DocsAnnotatedModel;
import xzcode.ggserver.docs.model.DocsAnnotatedProperty;

/**
 * 文档数据生成工具
 * 
 * @author zai
 * 2018-12-30 11:22:54
 */
public class DocsUtil {
	
	private static final String[] SCAN_PACKAGES = {"com.xzcode.product.app"};
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
	
	public static Map<String, List<DocsAnnotatedModel>> scan() {
		ScanResult scanResult = new ClassGraph()
				//.verbose()                   // Log to stderr
	            .enableAllInfo()             // Scan classes, methods, fields, annotations
	            .whitelistPackages(SCAN_PACKAGES)
	            .scan()
	            ;
				
				//扫描模型注解
				ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(DocsModel.class.getName());
				if (classInfoList.size() <= 0) {
					throw new RuntimeException("找不到 DocsModel 注解的模型!");
				}
				Map<String, List<DocsAnnotatedModel>> modelMap = new TreeMap<>();
				List<DocsAnnotatedModel> modelMapList = null;
				DocsAnnotatedModel annoModel = null;
				DocsAnnotatedProperty annoProperty = null;
				for (ClassInfo classInfo : classInfoList) {
					annoModel = new DocsAnnotatedModel();
					Class<?> loadClass = classInfo.loadClass();
					DocsModel docsModel = loadClass.getAnnotation(DocsModel.class);
					String actionId = docsModel.actionId();
					String desc = docsModel.desc();
					String namespace = docsModel.namespace();
					
					annoModel.setActionId(actionId);
					annoModel.setDesc(desc);
					annoModel.setNamespace(namespace);
					
					modelMapList = modelMap.get(namespace);
					if (modelMapList == null) {
						modelMapList = new ArrayList<>();
					}
					
					modelMapList.add(annoModel);
					
					modelMap.put(namespace, modelMapList);
					
					Class<?> tempClazz = loadClass;
					
					while (tempClazz != null) {
						Field[] fields = tempClazz.getDeclaredFields();
						tempClazz = tempClazz.getSuperclass();
						for (Field field : fields) {
							DocsModelProperty modelProperty = field.getAnnotation(DocsModelProperty.class);
							if (modelProperty == null) {
								continue;
							}
							annoProperty = new DocsAnnotatedProperty();
							annoProperty.setDesc(modelProperty.value());
							annoProperty.setName(field.getName());
							annoProperty.setDataType(field.getType().getSimpleName());
							annoModel.addProperty(annoProperty);
							
							
							NotNull notNull = field.getAnnotation(NotNull.class);
							if (notNull != null ) {
								annoProperty.setRequired(true);
							}
							
							NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
							if (notEmpty != null ) {
								annoProperty.setRequired(true);
							}
							
							NotBlank notBlank = field.getAnnotation(NotBlank.class);
							if (notBlank != null ) {
								annoProperty.setRequired(true);
								
								String extra = annoProperty.getExtra();
								extra = extra == null? "" : extra +" | ";
								annoProperty.setExtra(extra + "必须至少包含一个有效字符");
							}
							
							Size size = field.getAnnotation(Size.class);
							if (size != null ) {
								annoProperty.setMaxLength(size.max());
								annoProperty.setMinLength(size.min());
							}
							
							Min min = field.getAnnotation(Min.class);
							if (min != null ) {
								annoProperty.setMinLength((int) min.value());
							}
							
							Max max = field.getAnnotation(Max.class);
							if (max != null ) {
								annoProperty.setMaxLength((int) max.value());
							}
							
							Pattern pattern = field.getAnnotation(Pattern.class);
							if (pattern != null ) {
								String extra = annoProperty.getExtra();
								extra = extra == null? "" : extra +" | ";
								annoProperty.setExtra(extra + "必须符合正则表达式：" + pattern.regexp());
							}
							
							Email email = field.getAnnotation(Email.class);
							if (email != null ) {
								String extra = annoProperty.getExtra();
								extra = extra == null? "" : extra +" | ";
								annoProperty.setExtra(extra + "Email格式("+email.regexp() +")");
							}
						}
					}
				}
				
				return modelMap;
				
	}
	

}
