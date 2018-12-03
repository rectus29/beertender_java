package com.rectus29.beertender.entities.translation;

import com.rectus29.beertender.entities.BasicGenericEntity;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Locale;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 27/11/2018 20:03                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table( name = "translation")
public class Translation extends BasicGenericEntity<Translation> {

    @Column(nullable = false)
    private String field;

    @Column(nullable = false)
    @Type(type = "com.rectus29.beertender.hibernate.types.LocaleUserType")
    private Locale locale;

    @Column(nullable = false)
    private String translation;

    protected Translation() {
    }

    public Translation(Locale locale, String field, String translation) {
        this.locale = locale;
        this.translation = translation;
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
