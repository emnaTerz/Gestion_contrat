

package com.emna.micro_service2.Service;
import com.emna.micro_service2.Repository.ExclusionRepository;
import com.emna.micro_service2.Repository.ExclusionsGeneraleRepository;
import com.emna.micro_service2.Repository.SousGarantieRepository;
import com.emna.micro_service2.dto.Responses.*;
import com.emna.micro_service2.entities.Exclusion;
import com.emna.micro_service2.entities.ExclusionsGenerale;
import com.emna.micro_service2.entities.SousGarantie;
import com.emna.micro_service2.entities.enums.Branche;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;

@Service
public class ContratPdfService {

    private final PdfFont helvetica;
    private final PdfFont helveticaBold;
    private final PdfFont helveticaOblique;

    private final SousGarantieRepository sousGarantieRepository;
    private final ExclusionsGeneraleRepository exclusionsGeneraleRepository;

    @Autowired
    private ExclusionRepository exclusionRepository;

    public ContratPdfService(SousGarantieRepository sousGarantieRepository,
                             ExclusionsGeneraleRepository exclusionsGeneraleRepository) throws Exception {
        this.sousGarantieRepository = sousGarantieRepository;
        this.exclusionsGeneraleRepository = exclusionsGeneraleRepository;

        // Initialisation des polices une seule fois
        this.helvetica = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        this.helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        this.helveticaOblique = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    }

   /* public byte[] generateContratPdf(ContratResponseDTO contrat) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);
        document.setMargins(50, 50, 50, 50);

        try {
            addHeader(document, contrat);
            addPreambule(document, contrat);
            addAssureInfo(document, contrat);
            addPeriodeAssurance(document, contrat);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addSituationRisques(document, contrat);
            addGaranties(document, contrat);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addResponsabiliteCivileExploitation(document, contrat);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addExclusionsGenerales(document, contrat);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addExclusionsSpecifiquesSection(document, contrat.getSections());
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            if (Branche.M.equals(contrat.getBranche())) {
                addClausesCommunes(document, contrat);
                document.add(new AreaBreak());
            }
            addSignatureAndDate(document);
        } finally {
            document.close();
        }

        return baos.toByteArray();
    }*/



    public byte[] generateContratPdf(ContratResponseDTO contrat) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);
        document.setMargins(50, 50, 50, 50);

        try {
            // Initialisation des fonts dans le try
            PdfFont helvetica = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont helveticaOblique = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);

            // Passez les fonts en paramètre à vos méthodes
            addHeader(document, contrat, helvetica, helveticaBold, helveticaOblique);
            addPreambule(document, contrat, helvetica, helveticaBold);
            addAssureInfo(document, contrat, helvetica, helveticaBold, helveticaOblique);
            addPeriodeAssurance(document, contrat, helvetica, helveticaBold);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addSituationRisques(document, contrat, helvetica, helveticaBold);
            addGaranties(document, contrat, helvetica, helveticaBold);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addResponsabiliteCivileExploitation(document, contrat, helvetica, helveticaBold);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addExclusionsGenerales(document, contrat, helvetica, helveticaBold);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            addExclusionsSpecifiquesSection(document, contrat.getSections(), helvetica, helveticaBold);
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            if (Branche.M.equals(contrat.getBranche())) {
                addClausesCommunes(document, contrat, helvetica, helveticaBold, helveticaOblique);
                document.add(new AreaBreak());
            }

            addSignatureAndDate(document, helvetica);

        } finally {
            document.close();
        }

        return baos.toByteArray();
    }


    private void addHeader(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold, PdfFont helveticaOblique) {
        Paragraph titrePrincipal = new Paragraph("ANNEXES AU CONTRAT N° " + contrat.getNumPolice())
                .setFont(helveticaBold)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(10)
                .setMarginBottom(2);
        document.add(titrePrincipal);

        addSeparatorLine(document, helvetica);

        Paragraph sousTitre = new Paragraph("CLAUSES ET CONDITIONS")
                .setFont(helvetica)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(0)
                .setMarginBottom(5);
        document.add(sousTitre);

        Paragraph texteSecondaire = new Paragraph("Mutuelle assurance de l'éducation")
                .setFont(helveticaOblique)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(0)
                .setMarginBottom(10);
        document.add(texteSecondaire);

        addSeparatorLine(document, helvetica);
    }

    // N'oubliez pas de mettre à jour addSeparatorLine aussi :
    private void addSeparatorLine(Document document, PdfFont font) {
        Paragraph separator = new Paragraph("_______________________________________________________________________________________________________________")
                .setFont(font)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(2)
                .setMarginBottom(2);
        document.add(separator);
    }

    private void addPreambule(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold) {
        if (contrat.getPreambule() != null && !contrat.getPreambule().isEmpty()) {
            Paragraph preambuleTitle = new Paragraph("PRÉAMBULE")
                    .setFont(helveticaBold)
                    .setFontSize(14)
                    .setMarginBottom(10);
            document.add(preambuleTitle);

            addSeparatorLine(document, helvetica);

            // Gestion des textes longs avec justification
            String preambuleText = contrat.getPreambule();
            Paragraph preambuleContent = new Paragraph()
                    .setFont(helvetica)
                    .setFontSize(11)
                    .setMarginBottom(20)
                    .setTextAlignment(TextAlignment.JUSTIFIED);

            // Ajouter le texte au paragraphe
            preambuleContent.add(preambuleText);

            document.add(preambuleContent);
        }
    }

    private void addAssureInfo(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold, PdfFont helveticaOblique) {
        try {
            Paragraph title = new Paragraph("INFORMATION DE L'ASSURÉE")
                    .setFont(helveticaBold)
                    .setFontSize(14)
                    .setMarginBottom(10);
            document.add(title);

            addSeparatorLine(document, helvetica);

            // Extraction sécurisée des données
            String nomAssure = "Non spécifié";
            String adresse = "Non spécifiée";
            String activite = "Non spécifiée";

            if (contrat.getAdherent() != null) {
                nomAssure = contrat.getAdherent().getNomRaison() != null ?
                        contrat.getAdherent().getNomRaison() : "Non spécifié";
                adresse = contrat.getAdherent().getAdresse() != null ?
                        contrat.getAdherent().getAdresse() : "Non spécifiée";
                activite = contrat.getAdherent().getActivite() != null ?
                        contrat.getAdherent().getActivite() : "Non spécifiée";
            }

            // Construction des paragraphes
            document.add(createInfoLine("Nom&Prénom/raison social: ", nomAssure, helveticaBold, helvetica));
            document.add(createInfoLine("Adresse: ", adresse, helveticaBold, helvetica));
            document.add(createInfoLine("Activité professionnelle: ", activite, helveticaBold, helvetica));

            Paragraph note = new Paragraph(
                    "Aucune autre activité professionnelle n'est couverte à moins d'être expressément déclarée et acceptée par l'Assureur.")
                    .setFont(helveticaOblique)
                    .setFontSize(10)
                    .setFontColor(DeviceGray.GRAY)
                    .setMarginBottom(20);
            document.add(note);

        } catch (Exception e) {
            // Fallback en cas d'erreur
            Paragraph errorMsg = new Paragraph("Erreur lors de l'affichage des informations de l'assuré")
                    .setFont(helvetica)
                    .setFontColor(DeviceGray.GRAY)
                    .setFontSize(10);
            document.add(errorMsg);
        }
    }

    // Méthode utilitaire pour créer des lignes d'information
    private Paragraph createInfoLine(String label, String value, PdfFont fontBold, PdfFont fontNormal) {
        return new Paragraph()
                .add(new Text(label).setFont(fontBold).setFontSize(11))
                .add(new Text(value).setFont(fontNormal).setFontSize(11))
                .setMarginBottom(8);
    }
    private void addPeriodeAssurance(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold) {
        Paragraph title = new Paragraph("PÉRIODE D'ASSURANCE")
                .setFont(helveticaBold)
                .setFontSize(14)
                .setMarginBottom(10);
        document.add(title);

        addSeparatorLine(document, helvetica);

        String dateDebut = contrat.getDateDebut() != null ? contrat.getDateDebut().toString() : "Non spécifiée";
        String dateFin = contrat.getDateFin() != null ? contrat.getDateFin().toString() : "Non spécifiée";

        Paragraph periode = new Paragraph()
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(20)
                .add("DU: ").add(dateDebut).add("    AU: ").add(dateFin);
        document.add(periode);
    }

    private void addSituationRisques(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold) {
        if (contrat.getSections() == null || contrat.getSections().isEmpty()) return;

        Paragraph title = new Paragraph("SITUATION DES RISQUES")
                .setFont(helveticaBold)
                .setFontSize(14)
                .setMarginBottom(10);
        document.add(title);

        addSeparatorLine(document, helvetica);

        float[] columnWidths = {1f, 2f, 2f, 2f, 1.5f, 1.5f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);

        addTableHeader(table, "Section", helveticaBold);
        addTableHeader(table, "Identification", helveticaBold);
        addTableHeader(table, "Adresse", helveticaBold);
        addTableHeader(table, "Nature construction", helveticaBold);
        addTableHeader(table, "Contiguïté", helveticaBold);
        addTableHeader(table, "Avoisinage", helveticaBold);

        char lettreSection = 'A';
        for (SectionResponseDTO section : contrat.getSections()) {
            table.addCell(createCell("Section " + lettreSection, helvetica));
            table.addCell(createCell(section.getIdentification() != null ? section.getIdentification() : "", helvetica));
            table.addCell(createCell(section.getAdresse() != null ? section.getAdresse() : "", helvetica));
            table.addCell(createCell(section.getNatureConstruction() != null ? section.getNatureConstruction() : "", helvetica));
            table.addCell(createCell(section.getContiguite() != null ? section.getContiguite() : "", helvetica));
            table.addCell(createCell(section.getAvoisinage() != null ? section.getAvoisinage() : "", helvetica));
            lettreSection++;
        }
        document.add(table);
    }

    // N'oubliez pas de mettre à jour les méthodes helper aussi :
    private void addTableHeader(Table table, String headerText, PdfFont fontBold) {
        table.addHeaderCell(new Cell()
                .add(new Paragraph(headerText)
                        .setFont(fontBold)
                        .setFontSize(10))
                .setBackgroundColor(new DeviceRgb(240, 240, 240))
                .setPadding(5));
    }

    private Cell createCell(String content, PdfFont font) {
        return new Cell()
                .add(new Paragraph(content)
                        .setFont(font)
                        .setFontSize(9))
                .setPadding(5);
    }


    private void addSeparatorLine(Document document) {
        Paragraph line = new Paragraph("_______________________________________________________________________________________________________________")
                .setFont(helvetica)
                .setFontSize(8)
                .setFontColor(DeviceGray.GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(2)
                .setMarginBottom(10);
        document.add(line);
    }
    private void addGaranties(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold) {
        if (contrat.getSections() == null || contrat.getSections().isEmpty()) return;

        char lettreSection = 'A';
        for (SectionResponseDTO section : contrat.getSections()) {
            if (section.getGaranties() == null || section.getGaranties().isEmpty()) continue;

            Paragraph sectionTitle = new Paragraph("GARANTIES - SECTION " + lettreSection + " : " +
                    (section.getIdentification() != null ? section.getIdentification() : ""))
                    .setFont(helveticaBold)
                    .setFontSize(12)
                    .setMarginTop(15)
                    .setMarginBottom(2);
            document.add(sectionTitle);

            addSeparatorLine(document, helvetica);

            float[] columnWidths = {3f, 1.5f, 1.5f, 1.5f, 1f, 1f};
            Table table = new Table(UnitValue.createPercentArray(columnWidths))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(30);

            addTableHeader(table, "Garantie", helveticaBold);
            addTableHeader(table, "Capital assuré", helveticaBold);
            addTableHeader(table, "Prime net", helveticaBold);
            addTableHeader(table, "Franchise", helveticaBold);
            addTableHeader(table, "Minimum", helveticaBold);
            addTableHeader(table, "Maximum", helveticaBold);

            for (GarantieSectionResponseDTO garantie : section.getGaranties()) {
                String nomSousGarantie = getNomSousGarantie(garantie, contrat);
                table.addCell(createCell(nomSousGarantie, helvetica));
                table.addCell(createCell(formatNombre(garantie.getCapitale()), helvetica));
                table.addCell(createCell(formatNombre(garantie.getPrimeNet()), helvetica));
                table.addCell(createCell(formatNombre(garantie.getFranchise()), helvetica));
                table.addCell(createCell(formatNombre(garantie.getMinimum()), helvetica));
                table.addCell(createCell(formatNombre(garantie.getMaximum()), helvetica));
            }
            document.add(table);
            lettreSection++;
        }
    }
    private String getNomSousGarantie(GarantieSectionResponseDTO garantie, ContratResponseDTO contrat) {
        if (garantie.getSousGarantieId() == null) return "Sous-garantie non spécifiée";
        Optional<SousGarantie> sousGarantieOpt = sousGarantieRepository.findById(garantie.getSousGarantieId());
        return sousGarantieOpt.map(SousGarantie::getNom).orElse("Sous-garantie " + garantie.getSousGarantieId() + " (non trouvée)");
    }

    private String formatNombre(Number nombre) {
        if (nombre == null) return "";
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
        return df.format(nombre.doubleValue());
    }

    private void addResponsabiliteCivileExploitation(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold) {
        boolean hasRcExploitation = contrat.getSections().stream()
                .anyMatch(s -> s.isRcExploitationActive() && s.getRcExploitation() != null);
        if (!hasRcExploitation) return;

        Paragraph title = new Paragraph("RESPONSABILITÉ CIVILE EXPLOITATION")
                .setFont(helveticaBold)
                .setFontSize(14)
                .setMarginBottom(10)
                .setMarginTop(20);
        document.add(title);

        addSeparatorLine(document, helvetica);

        RCExploitationResponseDTO rcExploitation = contrat.getSections().stream()
                .filter(s -> s.isRcExploitationActive() && s.getRcExploitation() != null)
                .findFirst().get().getRcExploitation();

        String objetOuverture = rcExploitation.getObjetDeLaGarantie() != null ? rcExploitation.getObjetDeLaGarantie() : "Non spécifié";
        Paragraph objetOuverturePara = new Paragraph()
                .add(new Text("Objet d'ouverture: ").setFont(helveticaBold).setFontSize(11))
                .add(new Text(objetOuverture).setFont(helvetica).setFontSize(11))
                .setMarginBottom(5);
        document.add(objetOuverturePara);

        document.add(new Paragraph("Couvertures: Dommages corporels + Dommages matériels")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(15));

        float[] columnWidths = {2.5f, 1.5f, 1.5f, 1f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(30);

        addTableHeader(table, "Couverture", helveticaBold);
        addTableHeader(table, "Limite annuelle", helveticaBold);
        addTableHeader(table, "Limite par sinistre", helveticaBold);
        addTableHeader(table, "Franchise", helveticaBold);

        table.addCell(createCell("Dommages corporels", helvetica));
        table.addCell(createCell(formatNombre(rcExploitation.getLimiteAnnuelleDomCorporels()), helvetica));
        table.addCell(new Cell(2, 1).add(new Paragraph(formatNombre(rcExploitation.getLimiteParSinistre()))
                .setFont(helvetica).setFontSize(9)).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell(2, 1).add(new Paragraph(formatNombre(rcExploitation.getFranchise()))
                .setFont(helvetica).setFontSize(9)).setTextAlignment(TextAlignment.CENTER));

        table.addCell(createCell("Dommages matériels", helvetica));
        table.addCell(createCell(formatNombre(rcExploitation.getLimiteAnnuelleDomMateriels()), helvetica));

        document.add(table);
        addExclusionsRc(document, rcExploitation, helvetica, helveticaBold);
    }

    private void addExclusionsRc(Document document, RCExploitationResponseDTO rcExploitation, PdfFont helvetica, PdfFont helveticaBold) {
        if (rcExploitation.getExclusionsRc() == null || rcExploitation.getExclusionsRc().isEmpty()) return;

        document.add(new Paragraph("Exclusions spécifiques à la RC Exploitation:")
                .setFont(helveticaBold)
                .setFontSize(12)
                .setMarginTop(10)
                .setMarginBottom(10));

        for (ExclusionRCResponse exclusion : rcExploitation.getExclusionsRc()) {
            if (exclusion.getNom() != null && !exclusion.getNom().trim().isEmpty()) {
                document.add(new Paragraph("• " + exclusion.getNom())
                        .setFont(helvetica)
                        .setFontSize(10)
                        .setMarginBottom(3)
                        .setTextAlignment(TextAlignment.LEFT));
            }
        }
    }

    private void addExclusionsGenerales(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold) {
        Branche brancheContrat = contrat.getBranche();
        List<ExclusionsGenerale> exclusionsGeneraleList = exclusionsGeneraleRepository.findByBranche(brancheContrat);
        if (exclusionsGeneraleList.isEmpty()) return;

        List<String> allExclusions = new ArrayList<>();
        for (ExclusionsGenerale exclusionsGenerale : exclusionsGeneraleList) {
            if (exclusionsGenerale.getListeExclusion() != null)
                allExclusions.addAll(exclusionsGenerale.getListeExclusion());
        }
        if (allExclusions.isEmpty()) return;

        Paragraph title = new Paragraph("EXCLUSION GÉNÉRALE")
                .setFont(helveticaBold)
                .setFontSize(14)
                .setMarginBottom(10)
                .setMarginTop(20);
        document.add(title);

        addSeparatorLine(document, helvetica);

        document.add(new Paragraph("La M.A.E. n'assure jamais les dommages :")
                .setFont(helveticaBold)
                .setFontSize(11)
                .setMarginBottom(10));

        for (int i = 0; i < allExclusions.size(); i++) {
            String exclusion = allExclusions.get(i);
            if (exclusion != null && !exclusion.trim().isEmpty()) {
                document.add(new Paragraph("• " + exclusion.trim())
                        .setFont(helvetica)
                        .setFontSize(10)
                        .setMarginBottom(3)
                        .setTextAlignment(TextAlignment.LEFT));
            }
            if (i == 6 && allExclusions.size() > 7) {
                document.add(new Paragraph("AINSI QUE LES DOMMAGES AUTRES QUE CEUX D'INCENDIE CAUSÉS PAR :")
                        .setFont(helveticaBold)
                        .setFontSize(11)
                        .setMarginTop(15)
                        .setMarginBottom(10));
            }
        }
    }

    private void addExclusionsSpecifiquesSection(Document document, List<SectionResponseDTO> sections, PdfFont helvetica, PdfFont helveticaBold) {
        if (sections == null || sections.isEmpty()) return;

        document.add(new Paragraph("EXCLUSIONS SPÉCIFIQUES")
                .setFont(helveticaBold)
                .setFontSize(14)
                .setMarginTop(10)
                .setMarginBottom(10));

        addSeparatorLine(document, helvetica);

        for (SectionResponseDTO section : sections) {
            if (section.getGaranties() == null || section.getGaranties().isEmpty()) continue;

            document.add(new Paragraph("SECTION : " + section.getIdentification())
                    .setFont(helveticaBold)
                    .setFontSize(13)
                    .setMarginTop(8)
                    .setMarginBottom(5));

            Map<Long, List<Exclusion>> exclusionsParParent = new LinkedHashMap<>();
            for (GarantieSectionResponseDTO gs : section.getGaranties()) {
                if (gs.getGarantieParent() != null && gs.getExclusions() != null) {
                    List<Exclusion> exclusionsNom = gs.getExclusions().stream()
                            .map(e -> exclusionRepository.findById(e.getExclusionId()).orElse(null))
                            .filter(Objects::nonNull)
                            .toList();
                    exclusionsParParent.putIfAbsent(gs.getGarantieParent().getId(), exclusionsNom);
                }
            }

            for (Map.Entry<Long, List<Exclusion>> entry : exclusionsParParent.entrySet()) {
                String nomGarantieParent = section.getGaranties().stream()
                        .filter(g -> g.getGarantieParent() != null && g.getGarantieParent().getId().equals(entry.getKey()))
                        .findFirst().get().getGarantieParent().getLibelle();

                document.add(new Paragraph("Garanties : " + nomGarantieParent)
                        .setFont(helveticaBold)
                        .setFontSize(12)
                        .setMarginTop(5)
                        .setMarginBottom(3));

                for (Exclusion exclusion : entry.getValue()) {
                    document.add(new Paragraph("• " + exclusion.getNom())
                            .setFont(helvetica)
                            .setFontSize(10)
                            .setMarginBottom(2));
                }
            }
        }
    }

    private void addSignatureAndDate(Document document, PdfFont helvetica) {
        String dateImpression = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        document.add(new Paragraph("Fait à ____________________________, le " + dateImpression)
                .setFont(helvetica)
                .setFontSize(10)
                .setMarginTop(20)
                .setMarginBottom(20));
        document.add(new Paragraph("Pour l'Assureur:                                Reçu et accepté par l'Assuré:")
                .setFont(helvetica)
                .setFontSize(10)
                .setMarginBottom(5));
        document.add(new Paragraph("----------------                                -----------------------------")
                .setFont(helvetica)
                .setFontSize(10)
                .setMarginBottom(5));
        document.add(new Paragraph("Nom et Titre                                 Nom et Titre de la personne autorisée")
                .setFont(helvetica)
                .setFontSize(10)
                .setMarginBottom(20));
    }
    private void addClausesCommunes(Document document, ContratResponseDTO contrat, PdfFont helvetica, PdfFont helveticaBold, PdfFont helveticaOblique) {
        document.add(new Paragraph("CLAUSES COMMUNES")
                .setFont(helveticaBold)
                .setFontSize(16)
                .setMarginBottom(10)
                .setMarginTop(20));

        // Règle proportionnelle
        document.add(new Paragraph("La règle proportionnelle")
                .setFont(helveticaBold)
                .setFontSize(14)
                .setMarginBottom(5));
        document.add(new Paragraph("Indemnité réduite = dommage X valeur déclarée / valeur assurable")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(10));

        document.add(new Paragraph("1. Cas du sinistre total :")
                .setFont(helveticaBold)
                .setFontSize(11));
        document.add(new Paragraph("Un bien a une valeur déclarée de 30 000 dinars au jour du sinistre.\n" +
                "Il n'a été déclaré que pour une valeur de 20 000 dinars.\n" +
                "La garantie de l'assureur est limitée à la somme assurée.\n" +
                "D'où l'indemnité = 30 000 x 20 000 / 30 000 = 20 000 dinars")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(10));

        document.add(new Paragraph("2. Cas du sinistre partiel :")
                .setFont(helveticaBold)
                .setFontSize(11));
        document.add(new Paragraph("S'il y a sinistre partiel par exemple le bien n'a été détruit que pour une partie, le dommage n'est donc que de 15 000 dinars.\n" +
                "Indemnité = 15 000 x 20 000 / 30 000 = 10 000 dinars")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(15));

        // Signature souscripteur
        document.add(new Paragraph("LE SOUSCRIPTEUR\nDate et Signature")
                .setFont(helveticaOblique)
                .setFontSize(11)
                .setMarginBottom(20));
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        // Installation électriques
        document.add(new Paragraph("INSTALLATION ELECTRIQUES ORDINAIRES CONTROLEES")
                .setFont(helveticaBold)
                .setFontSize(12)
                .setMarginBottom(5));
        document.add(new Paragraph("L'adhérent déclare que :\n" +
                "a. Les installations électriques de forces et lumière sont conformes aux normes en vigueur ou à défaut aux règles de l'Art ;\n" +
                "b. Les installations sont vérifiées une fois au moins par an par un organisme agréé ;\n" +
                "Bien entendu, chaque vérification doit porter sur la totalité des installations électriques soumise à cette vérification (circuits et matériels) et ne doit pas être limitée à des sondages.\n" +
                "L'adhérent s'engage :\n" +
                "1. À fournir, à l'assureur un exemplaire des rapports annuels complets de vérification de ses installations électriques, établis par l'organisme vérificateur\n" +
                "2. À exécuter dans un délai maximal de trois mois les travaux d'entretien ou les modifications qui auront été portés sur le rapport établi après la vérification\n" +
                "3. À mettre les organes de protection générale (coupe-circuit ou disjoncteurs) hors d'atteinte des personnes non qualifiées en les plaçant dans un local, une armoire, un coffret ou tout autre enceinte fermée à clé, et à ne confier la clé qu'au personnel qualifié et responsable chargé du remplacement des fusibles ou du réarmement des relais des disjoncteurs\n" +
                "4. À faire couper le courant force à la fermeture des ateliers. Pourra toutefois rester sous tension un circuit spécial alimentant uniquement les appareils à fonctionnement continu, mais seulement pendant le temps où il est nécessaire que ces appareils soient en fonctionnement.\n" +
                "Le Souscripteur")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(15));
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        // Extincteurs mobiles
        document.add(new Paragraph("EXTINCTEURS MOBILES")
                .setFont(helveticaBold)
                .setFontSize(12)
                .setMarginBottom(5));
        document.add(new Paragraph("L'adhérent déclare que :\n" +
                "1. Son établissement est pourvu d'une installation d'extincteurs mobiles mise en place conformément aux normes en vigueur par un installateur agréé.\n" +
                "2. Il a pris connaissance de ces normes et s'engage à s'y conformer, notamment en ce qui concerne :\n" +
                "• La qualité minimale de produits extincteurs\n" +
                "• Le nombre minimum d'appareils et leur emplacement\n" +
                "• L'entretien du matériel\n" +
                "• L'entrainement du personnel\n" +
                "3. L'installation est vérifiée au moins une fois par an par un organisme agréé ou par le fournisseur. Faute par l'adhérent de se conformer à ces déclarations, il sera fait application d'une majoration du taux de la prime incendie de 10%.\n" +
                "Le Souscripteur")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(15));
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        // Interdiction de fumer
        document.add(new Paragraph("INTERDICTION DE FUMER")
                .setFont(helveticaBold)
                .setFontSize(12)
                .setMarginBottom(5));
        document.add(new Paragraph("L'adhérent déclare que :\n" +
                "II est formellement interdit de fumer dans toutes les parties de l'établissement assuré (ou contenant des objets assurés) à la seule exception des locaux à usage d'habitation, bureaux, réfectoires, cantines, salles des chaudières, ateliers séparés à usage d'entretien mécanique ou des locaux exclusivement à usage de fumoirs.\n" +
                "Cette interdiction est signalée par des écriteaux judicieusement répartis à l'intérieur et à l'extérieur des locaux et l'adhérent s'engage à prendre toutes mesures en son pouvoir pour la faire respecter.\n" +
                "Le Souscripteur")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(15));
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        // Balayage quotidien
        document.add(new Paragraph("BALAYAGE QUOTIDIEN")
                .setFont(helveticaBold)
                .setFontSize(12)
                .setMarginBottom(5));
        document.add(new Paragraph("L'adhérent déclare que :\n" +
                "Une fois au moins par journée de travail, les ateliers et magasins sont balayés et tous déchets et balayures sont transportés :\n" +
                "- Soit au dehors à plus de 10m de ces ateliers ou magasins.\n" +
                "- Soit dans un local spécial contigu sans aucune communication avec les ateliers et magasins.\n" +
                "Le Souscripteur")
                .setFont(helvetica)
                .setFontSize(11)
                .setMarginBottom(20));
    }


}


