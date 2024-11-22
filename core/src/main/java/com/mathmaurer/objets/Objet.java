package com.mathmaurer.objets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Objet { /* extends Actor */

    // **** VARIABLES **** //
    private int largeur, hauteur; // Dimensions de l'objet
    private float x, y;           // Position de l'objet

    protected Texture textureObjet;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // **** CONSTRUCTEUR **** //    
    public Objet(float x, float y, int largeur, int hauteur, String texturePath) {            
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;

        // Chargement de la texture
        this.textureObjet = new Texture(texturePath);
    }

    // **** GETTERS **** //    
    public float getX() { return x; }

    public float getY() { return y; }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public int getLargeur() { return largeur; }

    public int getHauteur() { return hauteur; }

    public Texture getTextureObjet() { return textureObjet; }

    // **** SETTERS **** //    
    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public void setLargeur(int largeur) { this.largeur = largeur; }

    public void setHauteur(int hauteur) { this.hauteur = hauteur; }

    // **** METHODES **** //   
    // @Override
    // public void act(float delta) {
    //     this.setX(this.getX());
    // }

    // public void draw(Batch batch, float parentAlpha) {
    //     super.draw(batch, parentAlpha);

    //     batch.end();
    //     // batch.draw(this.textureObjet, this.x, this.y, this.largeur, this.hauteur);
    //     shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    //     shapeRenderer.begin(ShapeType.Filled);
    //     shapeRenderer.setColor(Color.RED);
    //     shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
    //     shapeRenderer.end();
    //     batch.begin();  
    // }

    public void deplacement(float dx) {
        // Déplacement horizontal
        this.x -= dx;
    }

    public void render(SpriteBatch batch) {
        // Dessiner l'objet avec le SpriteBatch
        batch.draw(this.textureObjet, this.x, this.y, this.largeur, this.hauteur);
    }

    public void dispose() {
        // Libérer les ressources de la texture
        if (this.textureObjet != null) {
            this.textureObjet.dispose();
        }
    }
}
