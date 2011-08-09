package com.xsis.security.dao;

import com.xsis.security.model.SecTyp;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:07:20 PM
 */
public interface SecTypDAO {
    public List<SecTyp> getAllTypes();

    public SecTyp getTypById(int typ_id);
}
