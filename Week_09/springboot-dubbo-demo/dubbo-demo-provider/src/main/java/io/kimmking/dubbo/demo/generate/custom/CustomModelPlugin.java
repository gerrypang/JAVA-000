package io.kimmking.dubbo.demo.generate.custom;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CustomModelPlugin extends PluginAdapter {
    private boolean hasLombok = false;
    private boolean generatorSerialVersionUID = false;
    private boolean addJpaComments = false;
    private boolean addRemarkComments = false;
    private boolean extendsClass = false;
    private boolean implementsClass = false;
    private boolean ignoreFields = false;

    private Set<String> ignoreFieldSet = null;
    private static final String EXAMPLE_SUFFIX = "DTO";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";
    private static final String JAVAX_PERSISTENCE_COLUMN = "javax.persistence.Column";
    private static final String JAVAX_PERSISTENCE_ENTITY = "javax.persistence.Entity";
    private static final String JAVAX_PERSISTENCE_TABLE = "javax.persistence.Table";
    private static final String SPRING_REPOSITORY = "org.springframework.stereotype.Repository";

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
        this.addJpaComments = StringUtility.isTrue(properties.getProperty("addJpaComments"));
        this.hasLombok = StringUtility.isTrue(properties.getProperty("hasLombok"));
        this.extendsClass = StringUtility.stringHasValue(properties.getProperty("extendsClass"));
        this.implementsClass = StringUtility.stringHasValue(properties.getProperty("implementsClass"));
        this.ignoreFields = StringUtility.stringHasValue(properties.getProperty("ignoreFields"));
        this.generatorSerialVersionUID = StringUtility.stringHasValue(properties.getProperty("generatorSerialVersionUID"));
        if (ignoreFields) {
            ignoreFieldSet = Arrays.stream(properties.getProperty("ignoreFields").split(",")).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 获取表注释
        String remarks = StringUtils.isNoneBlank(introspectedTable.getRemarks()) ? introspectedTable.getRemarks() : introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" * @author system generate ");
        topLevelClass.addJavaDocLine(" * @since " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        topLevelClass.addJavaDocLine(" */");

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
        // 只在model中添加swagger注解类的导入
        if (addJpaComments && !topLevelClass.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            topLevelClass.addImportedType(new FullyQualifiedJavaType(JAVAX_PERSISTENCE_COLUMN));
            topLevelClass.addImportedType(new FullyQualifiedJavaType(JAVAX_PERSISTENCE_ENTITY));
            topLevelClass.addImportedType(new FullyQualifiedJavaType(JAVAX_PERSISTENCE_TABLE));

            topLevelClass.addJavaDocLine("@Entity");
            topLevelClass.addJavaDocLine("@Table(name = \"" + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "\")");
        }
        // 序列化 Serial
        if (generatorSerialVersionUID) {
            this.generatorSerialVersionUID(topLevelClass);
        }

        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        if (ignoreFieldSet.contains(field.getName())) {
            return false;
        }
        String remarks = introspectedColumn.getRemarks();
        String columnName = introspectedColumn.getActualColumnName();

        // 根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            addFieldJavaDoc(field, remarks);
            //数据库中特殊字符需要转义
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'");
            }
        }

        if (addJpaComments && !topLevelClass.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            boolean isNullable = introspectedColumn.isNullable();
            if (modelClassType.equals(ModelClassType.PRIMARY_KEY)) {
                field.addJavaDocLine("@Id");
                boolean isAutoInc = introspectedColumn.isAutoIncrement();
                if (isAutoInc) {
                    field.addJavaDocLine("@GenericGenerator(name = \"generatedId\")");
                } else {
                    field.addJavaDocLine("@GenericGenerator(name = \"generatedId\", strategy = \"uuid\")");
                }
                field.addJavaDocLine("@GeneratedValue(generator = \"  generatedId \")");
            }
            if (isNullable) {
                field.addJavaDocLine("@Column(name = \"" + columnName + "\")");
            } else {
                field.addJavaDocLine("@Column(name = \"" + columnName + "\", nullable = " + isNullable + ")");
            }
        }

        return true;
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


    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        // 不生成getter
        if (hasLombok) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        // 不生成setter
        if (hasLombok) {
            return false;
        }
        return true;
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

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTableNameAtRuntime() +"Mapper");
        interfaze.addJavaDocLine(" * @author system generate ");
        interfaze.addJavaDocLine(" * @since " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        interfaze.addJavaDocLine(" */");
        interfaze.addAnnotation("@Repository");
        interfaze.addImportedType(new FullyQualifiedJavaType(SPRING_REPOSITORY));
        return true;
    }

}
