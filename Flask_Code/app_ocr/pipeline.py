from preprocessing import *
from postOCR import *
import cv2
import numpy as np 
import easyocr
from pdf2image import convert_from_path
from utils import image_quality

def bilanOCR(pdf_file):

    pages = convert_from_path(pdf_file, image_quality,jpegopt=1)

    actifs, passifs, etat = pages[0],pages[1],pages[2] #objects dtype : PpmImageFile 

    #Converting PpmImageFile object to ndarrays
    actifs , passifs, etat = np.array(actifs) ,np.array(passifs) , np.array(etat)

    thresh1 , actifs = cv2.threshold(grayscale(actifs), 220, 250, cv2.THRESH_BINARY)
    thresh2 , passifs = cv2.threshold(grayscale(passifs), 220, 250, cv2.THRESH_BINARY)
    thresh3 , etat = cv2.threshold(grayscale(etat), 220, 250, cv2.THRESH_BINARY)

    actifs , passifs , etat = noise_removal(actifs) , noise_removal(passifs) , noise_removal(etat)

    actifs , passifs , etat = thick_font(actifs) , thick_font(passifs) , thick_font(etat)

    actifs = sharp_inc(actifs)
    passifs = sharp_inc(passifs)
    etat = sharp_inc(etat)

    reader = easyocr.Reader(['fr'])
    results_actifs = reader.readtext(actifs,detail=1,paragraph=False)
    results_passifs = reader.readtext(passifs,detail=1,paragraph=False)
    results_etat = reader.readtext(etat,detail=1,paragraph=False)



    cropped_image_1_actifs = actifs[0:int(actifs.shape[0]) , 0:int(findNotes(results_actifs)[0][0])]
    cropped_image_2_actifs = actifs[0:int(actifs.shape[0]) , int(findNotes(results_actifs)[1][0]):int(actifs.shape[1])]
    actifs = cv2.hconcat([cropped_image_1_actifs, cropped_image_2_actifs])

    cropped_image_1_passifs = passifs[0:int(passifs.shape[0]) , 0:int(findNotes(results_passifs)[0][0])]
    cropped_image_2_passifs = passifs[0:int(passifs.shape[0]) , int(findNotes(results_passifs)[1][0]):int(passifs.shape[1])]
    passifs = cv2.hconcat([cropped_image_1_passifs, cropped_image_2_passifs])

    cropped_image_1_etat = etat[0:int(etat.shape[0] ), 0:int(findNotes(results_etat)[0][0])]
    cropped_image_2_etat = etat[0:int(etat.shape[0]) , int(findNotes(results_etat)[1][0]):int(etat.shape[1])]
    etat = cv2.hconcat([cropped_image_1_etat, cropped_image_2_etat])

    results_actifs = reader.readtext(actifs,detail=1,paragraph=False)
    results_passifs = reader.readtext(passifs,detail=1,paragraph=False)
    results_etat = reader.readtext(etat,detail=1,paragraph=False)

    actifs_values = extractValues(results_actifs)
    passifs_values = extractValues(results_passifs)
    etat_values = extractValues(results_etat)


    immobilisation_incorporelles , amortissements_immo_inco = findValueAndAmortissements(
                                                                                        'immobilisations incorporelles'
                                                                                        ,actifs_values
    )


    immobilisation_corporelles , amortissement_imm_corporelles = findValueAndAmortissements(
                                                                                        'immobilisations corporelles'
                                                                                        ,actifs_values
    )


    immo_financieres , provisions_sur_imm_financieres = findValueAndAmortissements(
                                                                                        'immobilisations financiéres'
                                                                                        ,actifs_values
    )


    autres_actifs_non_courant = findValuewithoutAmortissements(
                                                            'autres actifs non courant',
                                                            actifs_values
    )


    stocks , provisions_sur_stocks = findValueAndAmortissements(
                                                                'stocks'
                                                                ,actifs_values
    )


    clients_et_comptes_rattaches , Provisions_sur_les_clients =  findValueAndAmortissements(
                                                                                        'Clients et comptes rattachés'
                                                                                        ,actifs_values
    )


    autres_actifs_courants =findValuewithoutAmortissements(
                                                            'autres actifs courants',
                                                            actifs_values)


    placement = findValuewithoutAmortissements(
                                                'placement',
                                                actifs_values)

    Liquidites = findValuewithoutAmortissements('liquidités et equivalent de liquidités',
                                                actifs_values)

    capital =findValuewithoutAmortissements('capital social',
                                                passifs_values)


    reserves=findValuewithoutAmortissements('réserves',
                                                passifs_values)

    autres_capitaux_propres=findValuewithoutAmortissements('autres capitaux propres',
                                                passifs_values)


    resultats_reportes=findValuewithoutAmortissements('résultats reportés',
                                                passifs_values)


    resultat_exercise=findValuewithoutAmortissements("Résultat de l'exercice",
                                                passifs_values)


    emprunts=findValuewithoutAmortissements("emprunts",
                                                passifs_values)

    autres_passifs_financiers ,Provisions_passifs_non_courants =findValueAndAmortissements("Autres passifs financiers",
                                                passifs_values)

    fournisseurs =findValuewithoutAmortissements("fournisseurs",
                                                passifs_values)

    autres_passifs_courants = findValuewithoutAmortissements("Autres passifs courants",
                                                passifs_values)

    concours_bancaires =findValuewithoutAmortissements("concours bancaires",
                                                passifs_values)


    revenus  =findValuewithoutAmortissements("revenus",
                                            etat_values)

    autres_produits = findValuewithoutAmortissements("autres produits",
                                            etat_values)

    production_immobilisee = findValuewithoutAmortissements("production immobilisée",
                                            etat_values)


    variation_stock = findValuewithoutAmortissements("variations des stocks",
                                            etat_values)


    achat_consomme = findValuewithoutAmortissements("achats consommés",
                                            etat_values)


    achat_approvisionnement = findValuewithoutAmortissements("Achat d'approvisionnement",
                                            etat_values)


    charges_personnel = findValuewithoutAmortissements("charges du personnel",
                                            etat_values)


    dotations_amortissements = findValuewithoutAmortissements("dotatons aux amortissements",
                                            etat_values)

    autres_charges = findValuewithoutAmortissements("autres charges d'exploitation",
                                            etat_values)
    a = float(extractDigits(revenus)) + float(extractDigits(autres_produits))

    b = float(extractDigits(autres_charges))+float(extractDigits(charges_personnel))+float(extractDigits(achat_approvisionnement))+float(extractDigits(achat_consomme))+float(extractDigits(variation_stock))
    resultat_exploitation = a-b

    charges_financiere = findValuewithoutAmortissements("charges financére",
                                            etat_values)
    produits_placements = findValuewithoutAmortissements("produits des placement",
                                            etat_values)

    autres_gains = findValuewithoutAmortissements("autres gains",
                                            etat_values)
    autres_pertes = findValuewithoutAmortissements("autres pertes",
                                            etat_values)
    impot = findValuewithoutAmortissements("impot sur les societé",
                                            etat_values)
    element_extra = findValuewithoutAmortissements("elements extras",
                                            etat_values)
    modifications_comptables = findValuewithoutAmortissements("modifications comptables",
                                            etat_values)


    result = { "immobilisation_incorporelles" : float(extractDigits(immobilisation_incorporelles)),
            "amortissements_immo_inco" : float(extractDigits(amortissements_immo_inco)),
            "immobilisation_corporelles" : float(extractDigits(immobilisation_corporelles)),
            "amortissement_imm_corporelles" : float(extractDigits(amortissement_imm_corporelles)),
            "immo_financieres" : float(extractDigits(immo_financieres)),
            "provisions_sur_imm_financieres" : float(extractDigits(provisions_sur_imm_financieres)),
            "autres_actifs_non_courant" : float(extractDigits(autres_actifs_non_courant)),
            "stocks" : float(extractDigits(stocks)),
            "provisions_sur_stocks" : float(extractDigits(provisions_sur_stocks)),
            "clients_et_comptes_rattaches" : float(extractDigits(clients_et_comptes_rattaches)),
            "Provisions_sur_les_clients" : float(extractDigits(Provisions_sur_les_clients)),
            "autres_actifs_courants" : float(extractDigits(autres_actifs_courants)),
            "placement" : float(extractDigits(placement)),
            "Liquidites" : float(extractDigits(Liquidites)),
            "capital" : float(extractDigits(capital)),
            "reserves" : float(extractDigits(reserves)),
            "autres_capitaux_propres" : float(extractDigits(autres_capitaux_propres)),
            "resultats_reportes" : float(extractDigits(resultats_reportes)),
            "resultat_exercise" : float(extractDigits(resultat_exercise)),
            "emprunts" : float(extractDigits(emprunts)),
            "autres_passifs_financiers" : float(extractDigits(autres_passifs_financiers)),
            "Provisions_passifs_non_courants" : float(extractDigits(Provisions_passifs_non_courants)),
            "fournisseurs" : float(extractDigits(fournisseurs)),
            "autres_passifs_courants" : float(extractDigits(autres_passifs_courants)),
            "concours_bancaires" : float(extractDigits(concours_bancaires)),
            "revenus" : float(extractDigits(revenus)),
            "autres_produits" : float(extractDigits(autres_produits)),
            "production_immobilisee" : float(extractDigits(production_immobilisee)),
            "variation_stock" : float(extractDigits(variation_stock)),
            "achat_consomme" : float(extractDigits(achat_consomme)),
            "achat_approvisionnement" : float(extractDigits(achat_approvisionnement)),
            "charges_personnel" : float(extractDigits(charges_personnel)),
            "dotations_amortissements" : float(extractDigits(dotations_amortissements)),
            "autres_charges" : float(extractDigits(autres_charges)),
            "resultat_exploitation" : resultat_exploitation,
            "charges_financiere" : float(extractDigits(charges_financiere)),
            "produits_placements" : float(extractDigits(produits_placements)),
            "autres_gains" : float(extractDigits(autres_gains)),
            "autres_pertes" : float(extractDigits(autres_pertes)),
            "impot" : float(extractDigits(impot)),
            "element_extra" : float(extractDigits(element_extra)),
            "modifications_comptables" : float(extractDigits(modifications_comptables))
            }
    return result 