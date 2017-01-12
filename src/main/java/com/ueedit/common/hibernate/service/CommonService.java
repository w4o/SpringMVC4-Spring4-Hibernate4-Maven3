package com.ueedit.common.hibernate.service;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Hibernate 通用Service 实现
 * @author xiaowu
 *
 */
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class CommonService {

}
