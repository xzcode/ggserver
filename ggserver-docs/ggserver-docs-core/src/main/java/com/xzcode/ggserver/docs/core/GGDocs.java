package com.xzcode.ggserver.docs.core;

import java.lang.reflect.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.docs.core.annotation.DocsModel;
import com.xzcode.ggserver.docs.core.annotation.DocsModelProperty;
import com.xzcode.ggserver.docs.core.annotation.DocsNamespace;
import com.xzcode.ggserver.docs.core.config.GGDocsConfig;
import com.xzcode.ggserver.docs.core.model.Doc;
import com.xzcode.ggserver.docs.core.model.Model;
import com.xzcode.ggserver.docs.core.model.ModelProperty;
import com.xzcode.ggserver.docs.core.model.Namespace;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

/**
 * 文档数据生成工具
 * 
 * @author zai 2018-12-30 11:22:54
 */
public class GGDocs {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GGDocs.class);

	private GGDocsConfig config;


	public GGDocs(GGDocsConfig config) {
		super();
		this.config = config;
	}



	public Doc scan() {
		
		Doc doc = new Doc();
		doc.setActionIdPrefix(config.getActionIdPrefix());
		doc.setMessageModelPrefix(config.getMessageModelPrefix());
		
		String[] scanPackages = config.getScanPackages();//扫描的路径
		String[] excludedPackages = config.getExcludedPackages();//排除的路径
		
		if (scanPackages == null || scanPackages.length == 0) {
			throw new NullPointerException("The attribute 'scanPackages' in 'GGDocsConfig' cannot be empty!");
		}
		
		ScanResult scanResult = new ClassGraph()
				.enableAllInfo()
				.whitelistPackages(scanPackages)//扫描的路径
				.blacklistPackages(excludedPackages)//排除的路径
				.scan();

		// 扫描模型注解
		ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(DocsModel.class.getName());
		if (classInfoList.size() <= 0) {
			LOGGER.warn("Cannot find class files annotated by DocsModel!");
			return null;
		}
		
		Model annoModel = null;
		ModelProperty annoProperty = null;
		for (ClassInfo classInfo : classInfoList) {
			
			
			annoModel = new Model();
			Class<?> loadClass = classInfo.loadClass();
			DocsModel docsModel = loadClass.getAnnotation(DocsModel.class);
			String actionId = docsModel.actionId();
			String desc = docsModel.desc();
			String namespaceName = "default";
			String namespaceDesc = "默认命名空间";
			
			
			DocsNamespace docsNamespace = loadClass.getAnnotation(DocsNamespace.class);
			
			if (config.getNamespace() != null) {
				namespaceName = config.getNamespace();
				namespaceDesc = config.getNamespaceDesc();
			}
			else
			if (docsNamespace != null) {
				namespaceName = docsNamespace.name();
				namespaceDesc = docsNamespace.desc();
			}
			
			Namespace namespaceModel = doc.getNamespace(namespaceName);
			if (namespaceModel == null) {
				namespaceModel = new Namespace();
				namespaceModel.setName(namespaceName);
				namespaceModel.setDescription(namespaceDesc);
			}
			

			annoModel.setActionId(actionId);
			annoModel.setDesc(desc);
			annoModel.setNamespace(namespaceModel);

			namespaceModel.addModel(annoModel);
			
			doc.addNamespace(namespaceModel);

			Class<?> tempClazz = loadClass;
			
			annoModel.setClazz(tempClazz);

			while (tempClazz != null) {
				Field[] fields = tempClazz.getDeclaredFields();
				tempClazz = tempClazz.getSuperclass();
				for (Field field : fields) {
					DocsModelProperty modelProperty = field.getAnnotation(DocsModelProperty.class);
					if (modelProperty == null) {
						continue;
					}
					annoProperty = new ModelProperty();
					annoProperty.setField(field);
					annoProperty.setDesc(modelProperty.value());
					annoProperty.setName(field.getName());
					annoProperty.setDataType(field.getType().getSimpleName());
					annoModel.addProperty(annoProperty);

					NotNull notNull = field.getAnnotation(NotNull.class);
					if (notNull != null) {
						annoProperty.setRequired(true);
					}

					NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
					if (notEmpty != null) {
						annoProperty.setRequired(true);
					}

					NotBlank notBlank = field.getAnnotation(NotBlank.class);
					if (notBlank != null) {
						annoProperty.setRequired(true);

						String extra = annoProperty.getExtra();
						extra = extra == null ? "" : extra + " | ";
						annoProperty.setExtra(extra + "必须至少包含一个有效字符");
					}

					Size size = field.getAnnotation(Size.class);
					if (size != null) {
						annoProperty.setMaxLength(size.max());
						annoProperty.setMinLength(size.min());
					}

					Min min = field.getAnnotation(Min.class);
					if (min != null) {
						annoProperty.setMinLength((int) min.value());
					}

					Max max = field.getAnnotation(Max.class);
					if (max != null) {
						annoProperty.setMaxLength((int) max.value());
					}

					Pattern pattern = field.getAnnotation(Pattern.class);
					if (pattern != null) {
						String extra = annoProperty.getExtra();
						extra = extra == null ? "" : extra + " | ";
						annoProperty.setExtra(extra + "必须符合正则表达式：" + pattern.regexp());
					}

					Email email = field.getAnnotation(Email.class);
					if (email != null) {
						String extra = annoProperty.getExtra();
						extra = extra == null ? "" : extra + " | ";
						annoProperty.setExtra(extra + "Email格式(" + email.regexp() + ")");
					}
				}
			}
		}

		return doc;

	}

}
