package com.amenbank.bilan_ocr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "bilan")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Bilan {
    @Id
    @Column(name = "matricule", length = 64)
    private String matricule;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "last_modified_at", nullable = false)
    @LastModifiedDate
    private LocalDate lastModifiedAt;

    @Column(name = "rs", nullable = false, length = 64)
    private String rs;

    @Column(name = "year", nullable = false, length = 4)
    private int year;

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String publisher;

    @Column(name = "last_modified_by", nullable = false)
    @LastModifiedBy
    private String modifier;

    @Column(name = "etat", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private EtatBilan etat;

    // Immobilisation Incorporelles
    @Column(name = "immo_inc", nullable = false)
    private double immoInc;

    // Amortissements Immo Inco
    @Column(name = "ammortissements_immo_inco", nullable = false)
    private double ammortissementsImmoInco;

    // Immo Corporelles
    @Column(name = "immo_corp", nullable = false)
    private double immoCorp;

    // Amortissement imm corporelles
    @Column(name = "ammortissements_immo_corp", nullable = false)
    private double ammortissementsImmoCorp;

    // Immo Financières
    @Column(name = "immo_financieres", nullable = false)
    private double immoFinancieres;

    // Provisions sur Imm Financières
    @Column(name = "prov_immo_financiere", nullable = false)
    private double provImmoFinanciere;

    // Autres actifs non courant
    @Column(name = "autre_actifs_non_courant", nullable = false)
    private double autreActifsNonCourant;

    // Stocks
    @Column(name = "stocks", nullable = false)
    private double stocks;

    // Provisions sur stock
    @Column(name = "prov_stocks", nullable = false)
    private double provStocks;

    // Clients et comptes rattachés
    @Column(name = "clt_compte_rattache", nullable = false)
    private double cltCompteRattache;

    // Provisions sur les clients
    @Column(name = "prov_clt", nullable = false)
    private double provClt;

    // Autres actifs courants
    @Column(name = "autre_actifs_courants", nullable = false)
    private double autreActifsCourants;

    // Placements et autres actifs financiers
    @Column(name = "placements_autres_actifs_financiers", nullable = false)
    private double placementsAutresActifsFinanciers;

    // Liquidités
    @Column(name = "liquidite", nullable = false)
    private double liquidite;

    // Capital
    @Column(name = "capital", nullable = false)
    private double capital;

    // Réserves
    @Column(name = "reserves", nullable = false)
    private double reserves;

    // Autres capitaux propres
    @Column(name = "autres_capitaux_propes", nullable = false)
    private double autresCapitauxPropes;

    // Résultats reportés
    @Column(name = "resultat_reportes", nullable = false)
    private double resultatReportes;

    // Résultat de l'exercice
    @Column(name = "resultat_exercice", nullable = false)
    private double resultatExercie;

    // Emprunts
    @Column(name = "emprunts", nullable = false)
    private double emprunts;

    // Autres passifs financiers
    @Column(name = "autres_passifs_financiers", nullable = false)
    private double autresPassifsFinanciers;

    // Provisions sur passifs non courants
    @Column(name = "prov_passifs_non_courants", nullable = false)
    private double provPassifsNonCourants;

    // Fournisseurs
    @Column(name = "fournisseurs", nullable = false)
    private double fournisseurs;

    // Autres passifs courants
    @Column(name = "autres_passifs_courants", nullable = false)
    private double autresPassifsCourants;

    // Concours bancaires
    @Column(name = "concours_bancaires", nullable = false)
    private double concours_bancaires;

    // Revenus
    @Column(name = "revenus", nullable = false)
    private double revenus;

    // Autres produits
    @Column(name = "autres_produits", nullable = false)
    private double autresProduits;

    // Production immobilisée
    @Column(name = "production_immobilisee", nullable = false)
    private double productionImmobilisee;

    // Variation de stock
    @Column(name = "variation_stock", nullable = false)
    private double variationStock;

    // Achat consommé
    @Column(name = "achat_consomme", nullable = false)
    private double achatConsomme;

    // Achat d'approvisionnement
    @Column(name = "achat_approv", nullable = false)
    private double achatApprov;

    // Charges de personnel
    @Column(name = "charges_personnel", nullable = false)
    private double chargesPersonnel;

    // Dotations aux amortissements
    @Column(name = "dotations_ammort", nullable = false)
    private double dotationsAmmort;

    // Autres charges d'exploitation
    @Column(name = "autres_charges_exploit", nullable = false)
    private double autresChargesExploit;

    // Résultat d'exploitation
    @Column(name = "resultat_exploitation", nullable = false)
    private double resultatExploitation;

    // Charges financière
    @Column(name = "charges_financiere", nullable = false)
    private double chargesFinanciere;

    // Produits des placements
    @Column(name = "produits_placements", nullable = false)
    private double produitsPlacements;

    // Autres gains
    @Column(name = "autres_gains", nullable = false)
    private double autresGains;

    // Autres pertes
    @Column(name = "autres_pertes", nullable = false)
    private double autresPertes;

    // Impôts
    @Column(name = "impots", nullable = false)
    private double impots;

    // Eléments Extra
    @Column(name = "elements_extra", nullable = false)
    private double elementsExtra;

    // Modifications comptables
    @Column(name = "modif_comptables", nullable = false)
    private double modifComptables;

    public enum EtatBilan {
        PROVISOIRE,
        DEFINITIF,
        AUDITE,
        DEFINITIVE_APP_PAR_CA,
        DEFINITIVE_CERTIFIE_CC
    }

    public Bilan(Map<String, Double> jsonBilan) {
        this.immoInc = jsonBilan.get("immobilisation_incorporelles");
        this.ammortissementsImmoInco = jsonBilan.get("amortissements_immo_inco");
        this.immoCorp = jsonBilan.get("immobilisation_corporelles");
        this.ammortissementsImmoCorp = jsonBilan.get("amortissement_imm_corporelles");
        this.immoFinancieres = jsonBilan.get("immo_financieres");
        this.provImmoFinanciere = jsonBilan.get("provisions_sur_imm_financieres");
        this.autreActifsNonCourant = jsonBilan.get("autres_actifs_non_courant");
        this.stocks = jsonBilan.get("stocks");
        this.provStocks = jsonBilan.get("provisions_sur_stocks");
        this.cltCompteRattache = jsonBilan.get("clients_et_comptes_rattaches");
        this.provClt = jsonBilan.get("Provisions_sur_les_clients");
        this.autreActifsCourants = jsonBilan.get("autres_actifs_courants");
        this.placementsAutresActifsFinanciers = jsonBilan.get("placement");
        this.liquidite = jsonBilan.get("Liquidites");
        this.capital = jsonBilan.get("capital");
        this.reserves = jsonBilan.get("reserves");
        this.autresCapitauxPropes = jsonBilan.get("autres_capitaux_propres");
        this.resultatReportes = jsonBilan.get("resultats_reportes");
        this.resultatExercie = jsonBilan.get("resultat_exercise");
        this.emprunts = jsonBilan.get("emprunts");
        this.autresPassifsFinanciers = jsonBilan.get("autres_passifs_financiers");
        this.provPassifsNonCourants = jsonBilan.get("Provisions_passifs_non_courants");
        this.fournisseurs = jsonBilan.get("fournisseurs");
        this.autresPassifsCourants = jsonBilan.get("autres_passifs_courants");
        this.concours_bancaires = jsonBilan.get("concours_bancaires");
        this.revenus = jsonBilan.get("revenus");
        this.autresProduits = jsonBilan.get("autres_produits");
        this.productionImmobilisee = jsonBilan.get("production_immobilisee");
        this.variationStock = jsonBilan.get("variation_stock");
        this.achatConsomme = jsonBilan.get("achat_consomme");
        this.achatApprov = jsonBilan.get("achat_approvisionnement");
        this.chargesPersonnel = jsonBilan.get("charges_personnel");
        this.dotationsAmmort = jsonBilan.get("dotations_amortissements");
        this.autresChargesExploit = jsonBilan.get("autres_charges");
        this.resultatExploitation = jsonBilan.get("resultat_exploitation");
        this.chargesFinanciere = jsonBilan.get("charges_financiere");
        this.produitsPlacements = jsonBilan.get("produits_placements");
        this.autresGains = jsonBilan.get("autres_gains");
        this.autresPertes = jsonBilan.get("autres_pertes");
        this.impots = jsonBilan.get("impot");
        this.elementsExtra = jsonBilan.get("element_extra");
        this.modifComptables = jsonBilan.get("modifications_comptables");
    }
}
