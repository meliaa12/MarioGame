package com.mathmaurer.jeu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    public static Scene scene; // Rendre `scene` public

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Crée une nouvelle instance de Scene
        scene = new Scene(); 
    }

    public static Scene getScene() {
        return scene;
    }

    @Override
    public void render() {
        // Appelle la méthode render de la scène
    }

    @Override
    public void resize(int width, int height) {
        // Mise à jour de la taille de l'écran si nécessaire
    }

    @Override
    public void pause() {
        super.pause(); // Gère la pause si nécessaire
    }

    @Override
    public void resume() {
        super.resume(); // Reprend l'exécution si nécessaire
    }

    @Override
    public void dispose() {
        // Libère les ressources utilisées
        scene.dispose();
        batch.dispose();
    }
}
