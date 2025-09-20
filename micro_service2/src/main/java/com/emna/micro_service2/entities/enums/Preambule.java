package com.emna.micro_service2.entities.enums;

public enum Preambule {
    PREMIER(
            "Aux conditions Générales du Contrat d’Assurance « Multirisque Professionnelle » MF N° 403/7 du 24 Novembre 1998, " +
                    "dont l’assuré reconnaît avoir reçu un exemplaire, ainsi qu’aux conditions particulières qui suivent et conformément " +
                    "au formulaire de déclaration de risque ci annexé, la MAE Assurances garantit l’assuré contre les risques énumérées " +
                    "et aux conditions suivantes. Les présentes conditions particulières prévalent sur les conditions générales " +
                    "susmentionnées chaque fois qu’elles-y- dérogent."
    ),

    DEUXIEME(
            "Aux conditions Générales du Contrat d’Assurance « Multirisque Professionnelle » MF N° 403/7 du 24 Novembre 1998 " +
                    "et aux conditions particulières qui suivent, dont l’adhèrent reconnaît avoir reçu un exemplaire, et conformément aux " +
                    "clauses et conditions de l’Appel d’Offres Agence de Mise en Valeur de Promotion Culturelle « A.M.V. P.C »  N°03/2024 " +
                    "pour l’année 2023-2024-2025, et qui prévalent sur toutes autres dispositions, la M.A.E garantit l’adhèrent dans les " +
                    "conditions et limites suivantes. Les présentes conditions particulières prévalent sur les conditions générales " +
                    "susmentionnées chaque fois qu’elles-y- dérogent."
    );

    private final String texte;

    Preambule(String texte) {
        this.texte = texte;
    }

    public String getTexte() {
        return texte;
    }
}
