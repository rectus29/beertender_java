package com.rectus29.beertender.entities.translation;

import java.util.List;
import java.util.Locale;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 27/11/2018 19:58                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public interface TranslatableEntity<T extends Translation> {

	List<T> getTranslationList();

	T getTranslation(String field, Locale locale);
}
