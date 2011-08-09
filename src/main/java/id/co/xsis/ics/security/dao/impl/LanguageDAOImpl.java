package com.xsis.security.dao.impl;

import com.xsis.security.dao.LanguageDAO;
import com.xsis.security.model.Language;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 11:08:34 PM
 */
public class LanguageDAOImpl implements LanguageDAO {
    private static final List<Language> LANGUAGES;
    static {
        List<Language> languages = new ArrayList<Language>(1);
//        languages.add(new Language(0, "", ""));
//        languages.add(new Language(1, "de_DE", "german"));
        languages.add(new Language(0, "en_EN", "english"));

        LANGUAGES = Collections.unmodifiableList(languages);

    }

    @Override
    public List<Language> getAllLanguages() {
        return LANGUAGES;
    }

    @Override
    public Language getLanguageById(final int lan_id) {
        return (Language) CollectionUtils.find(LANGUAGES, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return lan_id == ((Language) object).getId();
            }
        });
    }

    @Override
    public Language getLanguageByLocale(final String lanLocale) {
        return (Language) CollectionUtils.find(LANGUAGES, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return StringUtils.equals(lanLocale, ((Language) object).getLanLocale());
            }
        });
    }
}
