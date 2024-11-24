// Score.java
package com.mathmaurer.affichage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
    
    private int score;
    private BitmapFont font;
    private final int POSITION_X = 20;
    private final int POSITION_Y;

    public Score() {
        this.score = 0;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2.0f);
        // Positionner le score en haut de l'écran
        POSITION_Y = Gdx.graphics.getHeight() - 20;
    }

    public void render(SpriteBatch batch) {
        // Dessiner le score avec un outline noir pour meilleure visibilité
        font.setColor(Color.BLACK);
        for(int xOffset=-1; xOffset<=1; xOffset++) {
            for(int yOffset=-1; yOffset<=1; yOffset++) {
                if(xOffset != 0 || yOffset != 0) {
                    font.draw(batch, "SCORE: " + score + " / 100",POSITION_X + xOffset, POSITION_Y + yOffset);
                }
            }
        }
        font.setColor(Color.WHITE);
        font.draw(batch, "SCORE: " + score + " / 100", POSITION_X, POSITION_Y);
    }

    public void addScore(int points) {
        this.score += points;
        System.out.println("Score mis à jour: " + score); // Debug
    }

    public int getScore() {
        return score;
    }

    public void dispose() {
        if (font != null) {
            font.dispose();
        }
    }
}