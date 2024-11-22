package com.mathmaurer.jeu.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mathmaurer.jeu.Main;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // Prise en charge spécifique pour macOS et Windows
        createApplication(); // Crée et démarre l'application
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration()); // Lancement de l'application avec la configuration par défaut
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Mario"); // Titre de la fenêtre
        configuration.useVsync(true); // Activer la synchronisation verticale
        // Paramètre FPS en fonction du taux de rafraîchissement du moniteur
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(700, 360); // Dimensions de la fenêtre
        // Configuration des icônes de la fenêtre
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration; // Retourne la configuration
    }
}
