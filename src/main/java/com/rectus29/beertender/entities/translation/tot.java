package com.rectus29.beertender.entities.translation;
//

//import javax.persistence.MappedSuperclass;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.Objects;
//
///*-----------------------------------------------------*/
///*                     Rectus29                        */
///*                                                     */
///*                Date: 28/11/2018 17:36               */
///*                 All right reserved                  */
///*-----------------------------------------------------*/
//@MappedSuperclass
//public abstract class YoloTranslatableObject<T extends AdTranslation> extends YoloObject {
//
//	public static final String defaultErrorMsg = "No translations found for field ";
//	public static final String noAnnotatedErrorMsg = "The given field is not annotated as translatable ";
//
//	public abstract List<T> getTranslationList();
//
//	public abstract void setTranslationList(List<T> list);
//
//	public String getTranslationAsString(String fieldName, Locale locale) {
//		T translation = this.getTranslation(fieldName, locale);
//		if (translation != null) {
//			return translation.getValue();
//		}
//		//if nothing found return default translation
//		try {
//			Field field = ReflectUtil.getField(this.getClass(), fieldName);
//			if (field.isAnnotationPresent(Translatable.class)) {
//				PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, this.getClass());
//				return (String) descriptor.getReadMethod().invoke(this);
//			} else {
//				return "[" + noAnnotatedErrorMsg + fieldName + "]";
//			}
//		} catch (Exception e) {
//			return "[" + defaultErrorMsg + fieldName + "]";
//		}
//	}
//
//	public T getTranslation(String fieldName, Locale locale) {
//		try {
//			Field field = ReflectUtil.getField(this.getClass(), fieldName);
//			if (field.isAnnotationPresent(Translatable.class)) {
//				//search in current list
//				for (T temp : this.getTranslationList()) {
//					if (Objects.equals(fieldName, temp.getFieldName()) && TranslationController.isLocaleCompliant(locale, temp.getLocale())) {
//						return temp;
//					}
//				}
//			} else {
//				GlobalLog.getLogger().debug("Trying to get Translation on an non annoted field " + this.getClass() + ":" + field.getName());
//			}
//		} catch (NoSuchFieldException e) {
//			GlobalLog.getLogger().debug("Trying to get translation on an non existing field" + this.getClass() + ":" + fieldName);
//		}
//		return null;
//	}
//
//	public List<T> getTranslations(String fieldName) {
//		List<T> out = new ArrayList<>();
//		//search in current list
//		for (T temp : this.getTranslationList()) {
//			if (Objects.equals(fieldName, temp.getFieldName())) {
//				out.add(temp);
//			}
//		}
//		return out;
//	}
//
//	protected  AdelyaTranslatableObject addTranslation(T adTranslation){
//		try {
//			Field field = ReflectUtil.getField(this.getClass(), adTranslation.getFieldName());
//			if(!field.isAnnotationPresent(Translatable.class)){
//				throw new AdelyaProcessException("non translatable Field" + field.getName());
//			}
//			this.getTranslationList().add(adTranslation);
//		} catch (Exception e) {
//			GlobalLog.getLogger().error("Trying to add translation on a non annotated field" + this.getClass() + ":" + adTranslation.getFieldName());
//		}
//		return this;
//	}
//}
