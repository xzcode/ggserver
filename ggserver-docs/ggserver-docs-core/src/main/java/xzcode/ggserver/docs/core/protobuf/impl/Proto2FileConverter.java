package xzcode.ggserver.docs.core.protobuf.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import xzcode.ggserver.docs.core.model.Doc;
import xzcode.ggserver.docs.core.model.Model;
import xzcode.ggserver.docs.core.model.ModelProperty;
import xzcode.ggserver.docs.core.model.Namespace;
import xzcode.ggserver.docs.core.protobuf.ProtoFile;
import xzcode.ggserver.docs.core.protobuf.ProtoFileConverter;

/**
 * proto
 * 
 * @author zai 2019-12-17 14:07:59
 */
public class Proto2FileConverter implements ProtoFileConverter {
	
	private static String ENTER_LINE = "\n";
	
	private static String HEADER_SYNTAX = "syntax = \"proto2\";";
	
	private static String KEYWORD_REQUIRED = "required";
	
	private static String KEYWORD_OPTIONAL = "optional";
	
	private static String KEYWORD_REPEATED = "repeated";
	
	private static String MODIFIER_MESSAGE = "message";
	
	
	public List<ProtoFile> convertToProtoString(Doc doc) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Namespace> namespaces = doc.getNamespaces();
		
		List<ProtoFile> protoFiles = new ArrayList<>(namespaces.size());
		
		for (Entry<String, Namespace> entry : namespaces.entrySet()) {
			Namespace namespace = entry.getValue();
			List<Model> models = namespace.getModels();
			
			StringBuilder sb = new StringBuilder(8192);
			sb
			.append(HEADER_SYNTAX)
			.append(ENTER_LINE)
			.append(ENTER_LINE)
			.append("//Last Update : " + dateFormat.format(new Date()))
			.append(ENTER_LINE)
			.append(ENTER_LINE)
			;
			for (Model model : models) {
				
				sb
				.append("// ").append(model.getDesc())
				.append(ENTER_LINE)
				.append("// ").append(model.getActionId())
				.append(ENTER_LINE)
				.append(MODIFIER_MESSAGE).append(" ").append(model.getClazz().getSimpleName()).append(" {")
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
					sb
					.append("  ")
					.append(getFieldProtoModifier(field))
					.append(" ")
					.append(getFieldProtoDataType(field))
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
			}
			protoFiles.add(new ProtoFile(namespace.getName().toLowerCase() + ".proto", sb.toString()));
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
				os.write(protoFile.getContent().getBytes(Charset.forName("utf-8")));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			files.add(file);
		}
		return files;
	}
	
	
	
	
}
