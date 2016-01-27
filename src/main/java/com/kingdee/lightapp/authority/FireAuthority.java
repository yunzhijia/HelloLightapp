package com.kingdee.lightapp.authority;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FireAuthority {
	
	AuthorityType[] authorityTypes();  //授权类型
	ResultTypeEnum resultType() default ResultTypeEnum.json;//结果集类型
	
}

