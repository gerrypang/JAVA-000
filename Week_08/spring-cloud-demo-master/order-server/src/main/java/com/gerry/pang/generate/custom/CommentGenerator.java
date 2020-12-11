package com.gerry.pang.generate.custom;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * 自定义注释生成器
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addSwaggerComments = false;
    private boolean addJpaComments = false;
    private boolean addRemarkComments = false;
    private static final String EXAMPLE_SUFFIX = "Example";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";
    private static final String JAVAX_PERSISTENCE_COLUMN = "javax.persistence.Column";
    private static final String JAVAX_PERSISTENCE_ENTITY = "javax.persistence.Entity";
    private static final String JAVAX_PERSISTENCE_TABLE = "javax.persistence.Table";

    /**
     * 设置用户配置的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
        this.addJpaComments = StringUtility.isTrue(properties.getProperty("addJpaComments"));
        this.addSwaggerComments = StringUtility.isTrue(properties.getProperty("addSwaggerComments"));
    }

    /**
     * 给字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();

        // 根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            addFieldJavaDoc(field, remarks);
            //数据库中特殊字符需要转义
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'");
            }
            // 给model的字段添加swagger注解
            if (addSwaggerComments) {
                field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
            }
            if (addJpaComments) {
                String columnName = introspectedColumn.getActualColumnName();
                boolean isNullable = introspectedColumn.isNullable();
                boolean isKey = introspectedColumn.isIdentity();
                if (isKey) {
                    field.addJavaDocLine("@Id");
                    boolean isAutoInc = introspectedColumn.isAutoIncrement();
                    if (isAutoInc) {
                        field.addJavaDocLine("@GenericGenerator(name = \"generatedId\")");
                    } else {
                        field.addJavaDocLine("@GenericGenerator(name = \"generatedId\", strategy = \"uuid\")");
                    }
                    field.addJavaDocLine("@GeneratedValue(generator = \"  generatedId \")");
                }
                field.addJavaDocLine("@Column(name = \"" + columnName + "\", nullable= " + isNullable + ")");
            }
        }
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
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        // 只在model中添加swagger注解类的导入
        if (addRemarkComments && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }
        // 只在model中添加swagger注解类的导入
        if (addJpaComments) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType(JAVAX_PERSISTENCE_COLUMN));
            compilationUnit.addImportedType(new FullyQualifiedJavaType(JAVAX_PERSISTENCE_ENTITY));
            compilationUnit.addImportedType(new FullyQualifiedJavaType(JAVAX_PERSISTENCE_TABLE));
        }
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 获取表注释
        String remarks = introspectedTable.getRemarks();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @since " + LocalDateTime.now().toString());
        topLevelClass.addJavaDocLine(" */");
        if (addJpaComments) {
            topLevelClass.addJavaDocLine("@Entity");
            topLevelClass.addJavaDocLine("@Table(name = \"" + introspectedTable.getFullyQualifiedTable() + "\")");
        }
    }
}
