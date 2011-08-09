package com.xsis.security.dao.impl;

import com.xsis.security.dao.SecTypDAO;
import com.xsis.security.model.SecTyp;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:21:21 PM
 */
public class SecTypDAOImpl implements SecTypDAO {
    @Override
    public List<SecTyp> getAllTypes() {
        return SecTyp.getAllTypes();
    }

    @Override
    public SecTyp getTypById(int typ_id) {
        return SecTyp.getTypById(typ_id);
    }
}
