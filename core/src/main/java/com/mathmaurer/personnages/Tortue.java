package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathmaurer.objets.Objet;




public class Tortue extends Personnage implements Runnable{

    private Texture imgTortueDroite;
    private Texture imgTortueGauche;
    private Texture imgTortueMort;
    private int dxTortue; // Pas de déplacement
    private boolean vivant = true;

    private final int PAUSE = 15;

    // Constructeur
    public Tortue(float x, float y) {
        super(x, y, 43, 50); // Position et dimensions
        super.setVersDroite(true);
        super.setMarche(true);
        this.dxTortue = 1;

        // Chargement des textures
        this.imgTortueDroite = new Texture("images/tortueArretDroite.png");
        this.imgTortueGauche = new Texture("images/tortueArretGauche.png");
        this.imgTortueMort = new Texture("images/tortueFermee.png");
    }

    // Rendu
    public void render(SpriteBatch batch) {
        if (this.vivant) {
            if (super.isVersDroite()) {
                batch.draw(this.imgTortueDroite, super.getX(), super.getY());
            } else {
                batch.draw(this.imgTortueGauche, super.getX(), super.getY());
            }
        } else {
            batch.draw(this.imgTortueMort, super.getX(), super.getY());
        }
    }

    // Déplacement
    public void bouge() {
        if (super.isVersDroite()) {
            this.dxTortue = 1;
        } else {
            this.dxTortue = -1;
        }
        super.setX(super.getX() + this.dxTortue);
    }

    // Gestion des contacts avec un objet
    public void contact(Objet objet) {
        if (super.contactAvant(objet) && super.isVersDroite()) {
            super.setVersDroite(false);
            this.dxTortue = -1;
        } else if (super.contactArriere(objet) && !super.isVersDroite()) {
            super.setVersDroite(true);
            this.dxTortue = 1;
        }
    }

    // Gestion des contacts avec un personnage
    public void contact(Personnage personnage) {
        if (super.contactAvant(personnage) && super.isVersDroite()) {
            super.setVersDroite(false);
            this.dxTortue = -1;
        } else if (super.contactArriere(personnage) && !super.isVersDroite()) {
            super.setVersDroite(true);
            this.dxTortue = 1;
        }
    }

    @Override
	public void run() {
		try{Thread.sleep(20);} // on attend 20 ms avant d'appeler bouge pour que tous les objets soient compl�tement cr��s
		catch (InterruptedException e){}		
		
		while(true){ // boucle infinie
			if(this.vivant == true){
		    this.bouge();
		    try{Thread.sleep(PAUSE);}
			catch (InterruptedException e){}
			}
		}
	}
	


    // Simulation de mort
    public void mourir() {
        this.vivant = false;
    }

    // Dispose des ressources
    public void dispose() {
        this.imgTortueDroite.dispose();
        this.imgTortueGauche.dispose();
        this.imgTortueMort.dispose();
    }
}
