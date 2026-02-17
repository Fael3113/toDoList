package dev.rafael.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Utils {

	public static void  copyNonNullProperties(Object source, Object target){
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}

	public static String[] getNullPropertyNames(Object source){
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] propertyDescriptor = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<>();

		for (PropertyDescriptor propertyDescriptor1: propertyDescriptor){
			Object srcValue = src.getPropertyValue(propertyDescriptor1.getName());
			if (srcValue == null){
				emptyNames.add(propertyDescriptor1.getName());
			}
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
