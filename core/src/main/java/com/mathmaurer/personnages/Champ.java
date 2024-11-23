package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathmaurer.objets.Objet;

public class Champ extends Personnage implements Runnable {

    private Texture imgChampDroite;
    private Texture imgChampGauche;
    private Texture imgChampEcraseDroite;
    private Texture imgChampEcraseGauche;
    private int dxChamp; // Pas de déplacement
    private boolean vivant; // Est-ce que le champignon est vivant

    private final int PAUSE = 15;

    // Constructeur
    public Champ(float x, float y) {
        super(x, y, 27, 30); // Position et dimensions
        super.setVersDroite(true);
        super.setMarche(true);
        this.dxChamp = 1;
        this.vivant = true;

        // Chargement des textures
        this.imgChampDroite = new Texture("images/champArretDroite.png");
        this.imgChampGauche = new Texture("images/champArretGauche.png");
        this.imgChampEcraseDroite = new Texture("images/champEcraseDroite.png");
        this.imgChampEcraseGauche = new Texture("images/champEcraseGauche.png");

        // Démarrer le thread pour le mouvement
        Thread chronoChamp = new Thread(this);
        chronoChamp.start();
    }

    // Rendu
    public void render(SpriteBatch batch) {
        if (this.vivant) {
            if (super.isVersDroite()) {
                batch.draw(this.imgChampDroite, super.getX(), super.getY());
            } else {
                batch.draw(this.imgChampGauche, super.getX(), super.getY());
            }
        } else {
            if (super.isVersDroite()) {
                batch.draw(this.imgChampEcraseDroite, super.getX(), super.getY());
            } else {
                batch.draw(this.imgChampEcraseGauche, super.getX(), super.getY());
            }
        }
    }

    // Déplacement
    public void bouge() {
        if (super.isVersDroite()) {
            this.dxChamp = 1;
        } else {
            this.dxChamp = -1;
        }
        super.setX(super.getX() + this.dxChamp);
    }

    // Gestion des contacts avec un objet
    public void contact(Objet objet) {
        if (super.contactAvant(objet) && super.isVersDroite()) {
            super.setVersDroite(false);
            this.dxChamp = -1;
        } else if (super.contactArriere(objet) && !super.isVersDroite()) {
            super.setVersDroite(true);
            this.dxChamp = 1;
        }
    }

    // Gestion des contacts avec un personnage
    public void contact(Personnage personnage) {
        if (super.contactAvant(personnage) && super.isVersDroite()) {
            super.setVersDroite(false);
            this.dxChamp = -1;
        } else if (super.contactArriere(personnage) && !super.isVersDroite()) {
            super.setVersDroite(true);
            this.dxChamp = 1;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(20); // Attendre 20 ms avant d'appeler bouge pour que tous les objets soient complètement créés
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) { // boucle infinie
            if (this.vivant) {
                this.bouge();
                try {
                    Thread.sleep(PAUSE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Simulation de mort
    public void meurt() {
        this.vivant = false;
    }

    // Dispose des ressources
    public void dispose() {
        this.imgChampDroite.dispose();
        this.imgChampGauche.dispose();
        this.imgChampEcraseDroite.dispose();
        this.imgChampEcraseGauche.dispose();
    }
}