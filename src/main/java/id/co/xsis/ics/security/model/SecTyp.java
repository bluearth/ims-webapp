package com.xsis.security.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:46:04 PM
 */
final public class SecTyp implements Serializable {
    final public static List<SecTyp> ALLTYPES;
    final private static Map<Integer, SecTyp> STDID_MAP;
    final public static SecTyp EMPTY_SECTYP = new SecTyp(-1, "");

    static {
        List<SecTyp> result = new ArrayList<SecTyp>();

        result.add(new SecTyp(0, "Page"));
        result.add(new SecTyp(1, "Menu Category"));
        result.add(new SecTyp(2, "Menu Item"));
        result.add(new SecTyp(3, "Method"));
        result.add(new SecTyp(4, "DomainObject/Property"));
        result.add(new SecTyp(5, "Tab"));
        result.add(new SecTyp(6, "Component"));

        ALLTYPES = Collections.unmodifiableList(result);
        STDID_MAP = new HashMap<Integer, SecTyp>(result.size());

        for (SecTyp secTyp : result) {
            STDID_MAP.put(secTyp.stpId, secTyp);
        }
    }

    public static SecTyp getTypById(int typ_id) {
        return STDID_MAP.get(typ_id);
    }

    final private int stpId;
    final private String stpTypname;

    private SecTyp(int stpId, String stp_typname) {
        this.stpId = stpId;
        this.stpTypname = stp_typname;

    }

    public int getStpId() {
        return stpId;
    }

    public String getStpTypname() {
        return stpTypname;
    }

    static public List<SecTyp> getAllTypes() {
        return ALLTYPES;
    }

    @Override
    public int hashCode() {
        return stpId;
    }

    public boolean equals(SecTyp secTyp) {
        return getStpId() == secTyp.getStpId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecTyp) {
            SecTyp secTyp = (SecTyp) obj;
            return equals(secTyp);
        }

        return false;
    }
}
