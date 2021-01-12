//package com.example.service.intercepter;
//
//import com.baijia.xianzhi.generic.biz.constants.media.AdAccountStatusEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.executor.parameter.ParameterHandler;
//import org.apache.ibatis.executor.resultset.ResultSetHandler;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.DefaultReflectorFactory;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.ReflectorFactory;
//import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
//import org.apache.ibatis.reflection.factory.ObjectFactory;
//import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
//import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Properties;
//
//@Slf4j
//@Intercepts(@Signature(type = Executor.class, method = "query",
//        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
//@Component
//public class UserInfoStatusInterceptor implements Interceptor {
//    @Value("${spring.profiles.active}")
//    private String env;
//
//    private static final String MATCH_ENV = "beta";
//    private static final String FILTER_FIELD = "status";
//
//    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
//    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
//    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        log.info("拦截============");
//        // 获取到返回结果
//        ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
//        MetaObject metaResultSetHandler = MetaObject.forObject(resultSetHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
//        MappedStatement mappedStatement = (MappedStatement) metaResultSetHandler.getValue("mappedStatement");
//        ReverseColumnValue annotation = getAnnotation(mappedStatement);
//        if (annotation != null) {
//            if (MATCH_ENV.equals(env)) {
//                dealField(invocation);
//            }
//        }
////
//        return invocation.proceed();
//
//    }
//    private ReverseColumnValue getAnnotation(MappedStatement mappedStatement) {
//        ReverseColumnValue encryptResultFieldAnnotation = null;
//        try {
//            String id = mappedStatement.getId();
//            String className = id.substring(0, id.lastIndexOf("."));
//            String methodName = id.substring(id.lastIndexOf(".") + 1);
//            final Method[] method = Class.forName(className).getMethods();
//            for (Method me : method) {
//                if (me.getName().equals(methodName) && me.isAnnotationPresent(ReverseColumnValue.class)) {
//                    encryptResultFieldAnnotation = me.getAnnotation(ReverseColumnValue.class);
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return encryptResultFieldAnnotation;
//    }
//    private void dealField(Invocation invocation) throws IllegalAccessException {
//
//        log.info("dealField============");
//        Object parameter = invocation.getArgs()[1];
//        Class<?> objClass = parameter.getClass();
//        Field[] fields = objClass.getDeclaredFields();
//        for (Field field : fields) {
//            log.info("fieldName:{}", field.getName());
//            if (!FILTER_FIELD.equalsIgnoreCase(field.getName())) {
//                continue;
//            }
//            field.setAccessible(true);
//            Object value = field.get(parameter);
//            Integer intValue = (int) value;
//            if (AdAccountStatusEnum.DELETE.getStatus().equals((intValue))) {
//                field.set(parameter, AdAccountStatusEnum.ENABLE.getStatus());
//            } else if (AdAccountStatusEnum.ENABLE.getStatus().equals((intValue))) {
//                field.set(parameter, AdAccountStatusEnum.DELETE.getStatus());
//            }
//        }
//    }
//
//
//    @Override
//    public Object plugin(Object target) {
//        //只对要拦截的对象生成代理
//        if (target instanceof ParameterHandler) {
//            //调用插件
//            return Plugin.wrap(target, this);
//        }
//        return target;
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//}
