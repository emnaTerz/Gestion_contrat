package com.emna.micro_service2.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CodeAgence {
    PLACE_BARCELONE("101"),
    BIZERTE("102"),
    SOUSSE_1("103"),
    SFAX_1("104"),
    BAB_BNET("105"),
    KAIROUAN("106"),
    GABES("107"),
    BEJA("108"),
    NABEUL("109"),
    AL_DJAZIRA("110"),
    MONASTIR("111"),
    JEAN_JAURES("112"),
    LAFAYETTE("113"),
    KEF("114"),
    ARIANA("115"),
    BARDO("116"),
    SFAX_2("117"),
    SOUSSE_2("118"),
    BEN_AROUS_I("119"),
    DJERBA("120"),
    GAFSA("121"),
    MOUROUJ("122"),
    SILIANA("123"),
    MAHDIA("124"),
    KELIBIA("125"),
    JENDOUBA("126"),
    LES_BERGES_DU_LAC("127"),
    MANAR("128"),
    SEKHIET_EZZIT("129"),
    HAMMAM_LIF("130"),
    HAMMAMET("131"),
    MSAKEN("132"),
    ZARZIS("133"),
    SEKHIET_EDAYER("134"),
    MANNOUBA("135"),
    ENASR("136"),
    BEN_AROUS_II("137"),
    EL_MENZAH("138"),
    EL_KRAM("139"),
    TATAOUINE("140"),
    SOUKRA("141"),
    BEJA_II("142"),
    NABEUL_II("143"),
    AG_SOUSSE_TEBOULBA("144"),
    AG_GABES("146"),
    AG_RAOUED("147"),
    AG_EL_MOUROUJ_3("149"),
    AG_EZZOUHOUR("150"),
    AG_SOUSSE_ERRIADH("151"),
    AG_SAHLOUL_SOUSSE_2("152"),
    AG_ENNASER_II("154"),
    AG_SIDI_HESSINE("155"),
    AG_EL_OUERDIA("156"),
    AG_MANOUDA("157"),
    AG_CHEBBA("160"),
    AG_BOUMERDES("161"),
    AG_KEF("162"),
    AG_ENFIDHA("165"),
    AG_MENZEL_BOURGUIBA("167"),
    AG_GROMBALIA("168"),
    TASTOUR("169"),
    AG_TRIGUI("170"),
    AG_ETTADHAMEN("171"),
    AG_MJEZ_EL_BAB("172"),
    AG_EZZAHRA("173"),
    AG_LAOUINA("176"),
    AG_GAFSA("179"),
    ZAGHOUAN("180"),
    AG_KAIROUAN("181"),
    AG_KEBILI("183"),
    AG_MEGRINE("184"),
    AG_SFAX("185"),
    AG_SFAX_I("186"),
    AG_MEDENINE("187"),
    AG_KORBA("188"),
    AG_MOKNINE("191"),
    AG_DJERBA("192"),
    AG_FERIANA("193"),
    AG_KASSERINE("194"),
    AG_TOZEUR("195"),
    AG_EL_HAMMA("196"),
    AG_FOUCHANA("198"),
    LAARIBI_TAIEB("271"),
    INRISE("272"),
    ARAB_AFRICAN_INS("273"),
    KARE_KAMOUN("274"),
    EL_AMANA_SELCAR("275"),
    TICAR("276"),
    ST_TUNISIE_CORTAGE("277"),
    ST_MSOSCAR("278"),
    PRO_ASSUR("279");

    private final String code;

    CodeAgence(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }
    @JsonCreator
    public static CodeAgence fromCode(String code) {
        for (CodeAgence c : CodeAgence.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        throw new IllegalArgumentException("CodeAgence inconnu : " + code);
    }
    @Override
    public String toString() {
        return code; // retourne uniquement le code
    }
}
