package com.oyoungy.config;

/**
 * 配置文件 key
 *
 * @author tjq
 * @since 2020/8/2
 */
public class WallServerConfigKey {

    /**
     * akka 协议端口号
     */
    public static final String AKKA_PORT = "wall.akka.port";
    /**
     * http 协议端口号
     */
    public static final String HTTP_PORT = "wall.http.port";
    /**
     * 自定义数据库表前缀
     */
    public static final String TABLE_PREFIX = "wall.table-prefix";
    /**
     * 是否使用 mongoDB
     */
    public static final String MONGODB_ENABLE = "wall.mongodb.enable";
    /**
     * 是否启用 Swagger-UI，默认关闭
     */
    public static final String SWAGGER_UI_ENABLE = "wall.swagger.enable";

}
