package com.amenbank.bilan_ocr.dto.bilan;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BilanDto extends BilanInfo {
    protected double immoInc;
    protected double ammortissementsImmoInco;
    protected double immoCorp;
    protected double ammortissementsImmoCorp;
    protected double immoFinancieres;
    protected double provImmoFinanciere;
    protected double autreActifsNonCourant;
    protected double stocks;
    protected double provStocks;
    protected double cltCompteRattache;
    protected double provClt;
    protected double autreActifsCourants;
    protected double placementsAutresActifsFinanciers;
    protected double liquidite;
    protected double capital;
    protected double reserves;
    protected double autresCapitauxPropes;
    protected double resultatReportes;
    protected double resultatExercie;
    protected double emprunts;
    protected double autresPassifsFinanciers;
    protected double provPassifsNonCourants;
    protected double fournisseurs;
    protected double autresPassifsCourants;
    protected double concours_bancaires;
    protected double revenus;
    protected double autresProduits;
    protected double productionImmobilisee;
    protected double variationStock;
    protected double achatConsomme;
    protected double achatApprov;
    protected double chargesPersonnel;
    protected double dotationsAmmort;
    protected double autresChargesExploit;
    protected double resultatExploitation;
    protected double chargesFinanciere;
    protected double produitsPlacements;
    protected double autresGains;
    protected double autresPertes;
    protected double impots;
    protected double elementsExtra;
    protected double modifComptables;
}
