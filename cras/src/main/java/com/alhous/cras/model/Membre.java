package com.alhous.cras.model;

/**
 *
 * @author Alhoussaine
 */
import java.time.LocalDate;

public class Membre {

    private String horodateur;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private LocalDate dateNaissance;
    private String sexe;
    private String professionEtudes;
    private String niveauScolaire;
    private String lieuNaissance;
    private String numeroPasseport;
    private boolean estConfirme;
    private int montant;

    // Constructeur
    public Membre(String horodateur, String nom, String prenom, String numeroTelephone,
            LocalDate dateNaissance, String sexe, String professionEtudes,
            String niveauScolaire, String lieuNaissance, String numeroPasseport,
            boolean estConfirme, int montant) {
        this.horodateur = horodateur;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.professionEtudes = professionEtudes;
        this.niveauScolaire = niveauScolaire;
        this.lieuNaissance = lieuNaissance;
        this.numeroPasseport = numeroPasseport;
        this.estConfirme = estConfirme;
        this.montant = montant;
    }

    public String getHorodateur() {
        return horodateur;
    }

    public void setHorodateur(String horodateur) {
        this.horodateur = horodateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getProfessionEtudes() {
        return professionEtudes;
    }

    public void setProfessionEtudes(String professionEtudes) {
        this.professionEtudes = professionEtudes;
    }

    public String getNiveauScolaire() {
        return niveauScolaire;
    }

    public void setNiveauScolaire(String niveauScolaire) {
        this.niveauScolaire = niveauScolaire;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNumeroPasseport() {
        return numeroPasseport;
    }

    public void setNumeroPasseport(String numeroPasseport) {
        this.numeroPasseport = numeroPasseport;
    }

    public boolean isEstConfirme() {
        return estConfirme;
    }

    public void setEstConfirme(boolean estConfirme) {
        this.estConfirme = estConfirme;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Membre{"
                + "horodateur='" + horodateur + '\''
                + ", nom='" + nom + '\''
                + ", prenom='" + prenom + '\''
                + ", numeroTelephone='" + numeroTelephone + '\''
                + ", dateNaissance=" + dateNaissance
                + ", sexe='" + sexe + '\''
                + ", professionEtudes='" + professionEtudes + '\''
                + ", niveauScolaire='" + niveauScolaire + '\''
                + ", lieuNaissance='" + lieuNaissance + '\''
                + ", numeroPasseport='" + numeroPasseport + '\''
                + ", estConfirme=" + estConfirme
                + ", montant=" + montant
                + '}';
    }
}
