package cn.bdqfork.ioc.container;

import cn.bdqfork.ioc.exception.SpringToyException;

import java.util.HashMap;
import java.util.Map;

/**
 * 容器类，负责管理bean
 *
 * @author bdq
 * @date 2019-02-07
 */
public class BeanContainer {
    private Map<String, BeanDefination> beans = new HashMap<>();

    public void register(String beanName, BeanDefination beanDefination) throws SpringToyException {
        if (beans.containsKey(beanName)) {
            throw new SpringToyException("there are two bean has same name ! ");
        }
        beans.put(beanName, beanDefination);
    }

    /**
     * 根据beanName获取实例
     *
     * @param beanName
     * @return
     * @throws SpringToyException
     */
    public Object getBean(String beanName) throws SpringToyException {
        BeanDefination beanDefination = beans.get(beanName);
        return beanDefination.getInstance();
    }

    /**
     * 获取第一个与clazz匹配的实例
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws SpringToyException
     */
    public <T> T getBean(Class<T> clazz) throws SpringToyException {
        for (BeanDefination beanDefination : beans.values()) {
            if (beanDefination.isType(clazz)) {
                return (T) beanDefination.getInstance();
            }
        }
        return null;
    }

    /**
     * 获取所有与clazz匹配的实例
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws SpringToyException
     */
    public <T> Map<String, T> getBeans(Class<T> clazz) throws SpringToyException {
        Map<String, T> beans = new HashMap<>(8);
        for (Map.Entry<String, BeanDefination> entry : this.beans.entrySet()) {
            BeanDefination beanDefination = entry.getValue();
            if (beanDefination.isType(clazz)) {
                beans.put(entry.getKey(), (T) beanDefination.getInstance());
            }
        }
        return beans;
    }

    /**
     * 获取所有bean的定义
     *
     * @return
     */
    public Map<String, BeanDefination> getBeanDefinations() {
        return beans;
    }
}