package terrain;

import java.lang.IllegalArgumentException;

public class Carte {
    private int lignes, colonnes;
    private final int tailleCases = 1;
    private Case[] cases;

    Carte(int nbLignes, int nbColonnes) {
        lignes = nbLignes;
        colonnes = nbColonnes;
        cases = new Case[lignes * colonnes];
    }

    
    /**
     * Gets the number of lines.
     * 
     * @return the number of lines.
     */
    public int getNbLignes() {
        return lignes;
    }

    /**
     * Gets the number of columns.
     * 
     * @return the number of columns
     */
    public int getNbColonnes() {
        return colonnes;
    }

    
    /**
     * Gets cases' size.
     * 
     * @return cases' size
     */
    public int getTailleCases() {
        return tailleCases;
    }

    
    /** 
     * Finds a case on the terrain.
     * 
     * @param lig the line of the wanted case
     * @param col the column of the wanted case
     * @return the wanted case
     */
    public Case getCase(int lig, int col) {
        return cases[lig * colonnes + col];
    }

    
    /** 
     * Checks if the given neighbor of the src case exists.
     * 
     * @param src origin case
     * @param dir direction to check the neighbor
     * @return true if there is a neighbor, false otherwise
     */
    public boolean voisinExiste(Case src, Direction dir) {
        switch (dir) {
            case NORD:
                return src.getLigne() > 1;
            case SUD:
                return src.getLigne() < lignes - 1;
            case EST:
                return src.getColonne() > 1;
            case OUEST:
                return src.getColonne() < colonnes - 1;
            default:
                throw new IllegalArgumentException("This is awkward...");
        }
    }

    
    /** 
     * Gets a neighbor from a given case.
     * 
     * @param src origin case
     * @param dir direction to the neighbor
     * @return the neighbor
     */
    public Case getVoisin(Case src, Direction dir) {
        switch (dir) {
            case NORD:
                return getCase(src.getLigne() - 1, src.getColonne());
            case SUD:
                return getCase(src.getLigne() + 1, src.getColonne());
            case EST:
                return getCase(src.getLigne(), src.getColonne() + 1);
            case OUEST:
                return getCase(src.getLigne(), src.getColonne() - 1);
            default:
                throw new IllegalArgumentException("This is awkward...");
        }
    }

}