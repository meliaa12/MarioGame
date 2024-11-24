package com.mathmaurer.affichage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CompteARebours implements Runnable {

    // VARIABLES
    private final int PAUSE = 1000;    
    private int compteurTemps;
    private String str;
    private BitmapFont font;
    private final int POSITION_X ;
    private final int POSITION_Y ;

    // CONSTRUCTEUR
    public CompteARebours() {
        this.compteurTemps = 100; 
        this.str = "Temps restant : 100";
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(2.0f);
        // Positionner le compte à rebours en haut de l'écran
        POSITION_X = Gdx.graphics.getWidth() - 200;
        POSITION_Y = Gdx.graphics.getHeight() - 20;

        Thread compteARebours = new Thread(this);
        compteARebours.start();
    }

    // GETTERS    
    public int getCompteurTemps() { return compteurTemps; }
    
    public String getStr() { return str; }

    // METHODES    
    @Override
    public void run() {        
        while (true) { // boucle infinie                                            
            try { Thread.sleep(PAUSE); }
            catch (InterruptedException e) { e.printStackTrace(); }
            this.compteurTemps--;
            this.str = "Temps restant : " + this.compteurTemps;
        }        
    }

    // Méthode pour dessiner le compte à rebours
    public void render(SpriteBatch batch) {
        // Dessiner le compte à rebours avec un outline noir pour meilleure visibilité
        font.setColor(Color.BLACK);
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (xOffset != 0 || yOffset != 0) {
                    font.draw(batch, str, POSITION_X + xOffset, POSITION_Y + yOffset);
                }
            }
        }
        font.setColor(Color.WHITE);
        font.draw(batch, str, POSITION_X, POSITION_Y);
    }

    // Dispose des ressources
    public void dispose() {
        font.dispose();
    }
}