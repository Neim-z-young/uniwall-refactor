package com.oyoungy.config;

import com.oyoungy.util.PropertyUtils;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 自定义表前缀，配置项 wall.table-prefix 不配置时，不增加表前缀。
 * 参考实现：{@link org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy}
 * <p>
 * 1. 继承 CamelCaseToUnderscoresNamingStrategy 类，实现自定义表前缀并移除后缀DO；
 * </p>
 *
 * @author oyoungy
 * @since 2023/6/26
 */
public class WallPhysicalTableNamingStrategy extends CamelCaseToUnderscoresNamingStrategy implements Serializable {


    /**
     * 映射物理表名称，如：把实体表 UserDO 的 DO 去掉，再加上表前缀，最后转化为下划线格式
     *
     * @param name            实体名称
     * @param jdbcEnvironment jdbc环境变量
     * @return 映射后的物理表
     */
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {

        String tablePrefix = PropertyUtils.getProperties().getProperty(WallServerConfigKey.TABLE_PREFIX);

        String text = name.getText();
        String noDOText = StringUtils.endsWithIgnoreCase(text, "do") ? text.substring(0, text.length() - 2) : text;
        String newText = StringUtils.hasLength(tablePrefix) ? tablePrefix + noDOText : noDOText;
        return super.toPhysicalTableName(new Identifier(newText, name.isQuoted()), jdbcEnvironment);
    }


}
