package io.kimmking.dubbo.demo.generate.custom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

public class CustomDTOPlugin extends PluginAdapter {
    private boolean hasLombok = false;
    private boolean generatorSerialVersionUID = false;
    private boolean addSwaggerComments = false;
    private boolean addRemarkComments = false;
    private boolean extendsClass = false;
    private boolean implementsClass = false;
    private boolean ignoreFields = false;
    private String fullPathFileName = null;
    private Set<String> ignoreFieldSet = null;
    private static final String EXAMPLE_SUFFIX = "DTO";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
        this.addSwaggerComments = StringUtility.isTrue(properties.getProperty("addSwaggerComments"));
        this.hasLombok = StringUtility.isTrue(properties.getProperty("hasLombok"));
        this.extendsClass = StringUtility.stringHasValue(properties.getProperty("extendsClass"));
        this.implementsClass = StringUtility.stringHasValue(properties.getProperty("implementsClass"));
        this.ignoreFields = StringUtility.stringHasValue(properties.getProperty("ignoreFields"));
        this.generatorSerialVersionUID = StringUtility.stringHasValue(properties.getProperty("generatorSerialVersionUID"));
        if (ignoreFields) {
            ignoreFieldSet = Arrays.stream(properties.getProperty("ignoreFields").split(",")).collect(Collectors.toSet());
        }

        // 初始化DTO 文件名
        String dtoFileName = introspectedTable.getFullyQualifiedTable().getDomainObjectName() + EXAMPLE_SUFFIX;
        String targetPackage = StringUtility.stringHasValue(properties.getProperty("targetPackage")) ? properties.getProperty("targetPackage") : null;
        fullPathFileName = StringUtils.isNoneBlank(targetPackage) ?  targetPackage + "." + dtoFileName : dtoFileName;

    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        TopLevelClass topLevelClass = new TopLevelClass(fullPathFileName);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        // 获取表注释
        String remarks = StringUtils.isNoneBlank(introspectedTable.getRemarks()) ? introspectedTable.getRemarks() : introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" * @author system generate ");
        topLevelClass.addJavaDocLine(" * @since " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        topLevelClass.addJavaDocLine(" */");

        this.classModefiyGenerated(topLevelClass);

        introspectedTable.getAllColumns().forEach(n -> {
            this.fieldGenerated(topLevelClass, n);
        });

        String targetProject = context.getJavaClientGeneratorConfiguration().getTargetProject();
        GeneratedJavaFile dtoFile = new GeneratedJavaFile(topLevelClass, targetProject, new DefaultJavaFormatter());
        return Collections.singletonList(dtoFile);
    }

    /**
     * 对类的修改
     * @param topLevelClass
     */
    private void classModefiyGenerated(TopLevelClass topLevelClass) {
        if (extendsClass) {
            FullyQualifiedJavaType extendsClassJavaType = new FullyQualifiedJavaType(properties.getProperty("extendsClass"));
            topLevelClass.addImportedType(extendsClassJavaType);
            topLevelClass.setSuperClass(extendsClassJavaType);
        }

        if (implementsClass) {
            String[] superInterface = properties.getProperty("implementsClass").split(",");
            FullyQualifiedJavaType implementsClassJavaType;
            for (int i =0 ; i < superInterface.length; i++) {
                implementsClassJavaType = new FullyQualifiedJavaType(properties.getProperty("implementsClass"));
                topLevelClass.addImportedType(implementsClassJavaType);
                topLevelClass.addSuperInterface(implementsClassJavaType);
            }
        }

        if (hasLombok) {
            //添加domain的import
            topLevelClass.addImportedType("lombok.Data");
            topLevelClass.addImportedType("lombok.Builder");
            topLevelClass.addImportedType("lombok.NoArgsConstructor");
            topLevelClass.addImportedType("lombok.AllArgsConstructor");

            //添加domain的注解
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
        }
        // 只在model中添加swagger注解类的导入
        if (addRemarkComments && topLevelClass.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            topLevelClass.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
        // 序列化 Serial
        if (generatorSerialVersionUID) {
            this.generatorSerialVersionUID(topLevelClass);
        }
    }

    private void fieldGenerated(TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        String columnName = JavaBeansUtil.getCamelCaseString(introspectedColumn.getActualColumnName(), false);
        Field field = new Field(columnName, introspectedColumn.getFullyQualifiedJavaType());
        if (ignoreFieldSet.contains(field.getName())) {
            return ;
        }
        field.setVisibility(JavaVisibility.PRIVATE);
        // 根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            addFieldJavaDoc(field, remarks);
            //数据库中特殊字符需要转义
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'");
            }
        }

        // 给model的字段添加swagger注解
        if (addSwaggerComments && topLevelClass.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            remarks = StringUtils.isNoneBlank(remarks) ? remarks : columnName;
            field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
        }
        topLevelClass.addField(field);
    }

    /**
     * 给model的字段添加注释
     */
    private void addFieldJavaDoc(Field field, String remarks) {
        // 文档注释开始
        field.addJavaDocLine("/**");
        // 获取数据库字段的备注信息
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for (String remarkLine : remarkLines) {
            field.addJavaDocLine(" * " + remarkLine);
        }
        // addJavadocTag(field, false);
        field.addJavaDocLine(" */");
    }

    /**
     * 生成serialVersionUID值
     *
     * @param topLevelClass
     */
    private void generatorSerialVersionUID(TopLevelClass topLevelClass) {
        Field field = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
        field.setFinal(true);
        field.setInitializationString("1L");
        field.setStatic(true);
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.getFields().add(0, field);
    }

}
