package com.xsis.security.dao;

import com.xsis.security.model.Language;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 11:08:05 PM
 */
public interface LanguageDAO {
    public List<Language> getAllLanguages();

    public Language getLanguageById(int lan_id);

    public Language getLanguageByLocale(String lan_locale);
}
