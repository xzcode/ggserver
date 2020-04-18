package com.xzcode.ggserver.docs.core.protobuf.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.xzcode.ggserver.docs.core.model.Doc;
import com.xzcode.ggserver.docs.core.model.Model;
import com.xzcode.ggserver.docs.core.model.ModelProperty;
import com.xzcode.ggserver.docs.core.model.Namespace;
import com.xzcode.ggserver.docs.core.protobuf.ProtoFile;
import com.xzcode.ggserver.docs.core.protobuf.ProtoFileConverter;
import com.xzcode.ggserver.docs.core.protobuf.ProtoMessage;

/**
 * proto
 * 
 * @author zai 2019-12-17 14:07:59
 */
public class ProtoV2FileConverter implements ProtoFileConverter {
	
	private static String ENTER_LINE = "\n";
	
	private static String HEADER_SYNTAX = "syntax = \"proto2\";";
	
	private static String MODIFIER_MESSAGE = "message";
	
	
	public List<ProtoFile> convertToProtoString(Doc doc) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Namespace> namespaces = doc.getNamespaces();
		
		List<ProtoFile> protoFiles = new ArrayList<>(namespaces.size());
		
		for (Entry<String, Namespace> entry : namespaces.entrySet()) {
			Namespace namespace = entry.getValue();
			List<Model> models = namespace.getModels();
			
			ProtoFile protoFile = new ProtoFile(namespace.getName().toLowerCase() + ".proto");
			
			StringBuilder sb = new StringBuilder(8192);
			if (doc.getAuth() == null) {
				doc.setAuth("GGServer Docs " + this.getClass().getSimpleName());
			}
			sb
			.append(ENTER_LINE)
			.append("//Author : ").append(doc.getAuth())
			.append(ENTER_LINE)
			.append("//Namespace : " + namespace.getName())
			.append(ENTER_LINE)
			.append("//Description : " + namespace.getDescription())
			.append(ENTER_LINE)
			.append("//Create Date : " + dateFormat.format(new Date()))
			.append(ENTER_LINE)
			.append(ENTER_LINE)
			.append(ENTER_LINE)
			.append(HEADER_SYNTAX)
			.append(ENTER_LINE)
			.append(ENTER_LINE)
			.append(ENTER_LINE)
			;
			protoFile.setContentStart(sb.toString());
			
			sb.setLength(0);
		
			protoFile.setContentEnd(sb.toString());
			
			sb.setLength(0);
			
			for (Model model : models) {
				
				String messageName = doc.getMessageModelPrefix() + model.getClazz().getSimpleName();
				
				
				
				String actionId = (StringUtils.isNotEmpty(model.getActionId()) ? doc.getActionIdPrefix() : "") + model.getActionId();
				sb
				.append("// ").append(model.getDesc())
				.append(ENTER_LINE)
				.append("// ").append(actionId)
				.append(ENTER_LINE)
				.append(MODIFIER_MESSAGE).append(" ").append(messageName).append(" {")
				.append(ENTER_LINE);
				
				int seq = 0;
				
				List<ModelProperty> properties = model.getProperties();
				
				for (ModelProperty property : properties) {
					
					Field field = property.getField();
					seq++;
					
					//跳过删除的字段
					Deprecated deprecated = field.getAnnotation(Deprecated.class);
					if (deprecated != null) {
						continue;
					}
					//跳过忽略的字段
					if (Modifier.isTransient(field.getModifiers())) {
						continue;
					}
					
					if (property.getName().equals("playerHandcards")) {
						System.out.println();
					}
					
					sb
					.append("  ")
					.append(getFieldProtoModifier(field))
					.append(" ")
					.append(getFieldProtoDataType(field, doc.getMessageModelPrefix()))
					.append(" ")
					.append(field.getName())
					.append(" ")
					.append("=")
					.append(" ")
					.append(seq)
					.append(";")
					.append("// ").append(property.getDesc())
					.append(ENTER_LINE)
					;
					
				}
				sb
				.append("}")
				.append(ENTER_LINE)
				.append(ENTER_LINE);
				
				protoFile.addMessage(new ProtoMessage(messageName, sb.toString()));
				
				sb.setLength(0);
				
			}
			protoFiles.add(protoFile);
		}
		Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);  
		for (ProtoFile pfile : protoFiles) {
			
			pfile.getMessages().sort((a, b) -> {
				return com.compare(a.getMessageName(), b.getMessageName()); 
			});
		}
		return protoFiles;
	}

	@Override
	public List<File> convertToProtoFileAndOutput(Doc doc, String outpath) {
		File path = new File(outpath);
		if (!path.exists()) {
			path.mkdir();
		}
		List<ProtoFile> list = convertToProtoString(doc);
		List<File> files = new ArrayList<>(list.size());
		for (ProtoFile protoFile : list) {
			File file = new File(outpath + "/" +protoFile.getFilename());
			try (
					FileOutputStream os = new FileOutputStream(file);
			){
				List<ProtoMessage> messages = protoFile.getMessages();
				StringBuilder sb = new StringBuilder(1024);
				sb.append(protoFile.getContentStart());
				for (ProtoMessage msg : messages) {
					sb.append(msg.getContent());
				}
				sb.append(protoFile.getContentEnd());
				os.write(sb.toString().getBytes(Charset.forName("utf-8")));					
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			files.add(file);
		}
		return files;
	}
	
	
	
	
}
